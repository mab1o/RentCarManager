package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
public class ReservationDao {
	private ReservationDao() {}
	
	private static final String CREATE_RESERVATION_QUERY
			= "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY
			= "DELETE FROM Reservation WHERE id=?;";
	private static final String DELETE_RESERVATION_BY_CLIENT_QUERY
			= "DELETE FROM Reservation WHERE client_id=?;";
	private static final String DELETE_RESERVATION_BY_VEHICLE_QUERY
			= "DELETE FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATION_BY_ID_QUERY
			= "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY
			= "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY
			= "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY
			= "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String COUNT_RESERVATIONS_QUERY
			= "SELECT COUNT(*) FROM Reservation;";
	private static final String UPDATE_RESERVATION_QUERY
			= "UPDATE Reservation SET client_id=?, vehicle_id=?, debut=?, fin=? WHERE id=?;";
	public static final String COUNT_RESERVATION_SAME_DATE_QUERY = "SELECT COUNT(*) " +
			"FROM Reservation " +
			"WHERE vehicle_id=? AND" +
			"((debut >= ? AND debut <= ?) " +
			"OR (fin >= ? AND fin <= ?)" +
			"OR (debut <= ? AND fin >= ?));";
		
	public long create(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement =
					 connection.prepareStatement(CREATE_RESERVATION_QUERY,
							 PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setLong(1, reservation.getClient_id());
			preparedStatement.setLong(2, reservation.getVehicule_id());
			preparedStatement.setDate(3, Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(4, Date.valueOf(reservation.getFin()));

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new DaoException(
						"La création d'un reservation a échoué, aucune ligne ajoutée dans la base de données.");
			}

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getLong(1);
				} else {
					throw new DaoException("La création d'un reservation a échoué, aucun ID auto-généré retourné.");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création d'un reservation.", e);
		}
	}

	public void update(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement =
					 connection.prepareStatement(UPDATE_RESERVATION_QUERY)) {
			preparedStatement.setLong(1, reservation.getClient_id());
			preparedStatement.setLong(2, reservation.getVehicule_id());
			preparedStatement.setDate(3, Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(4, Date.valueOf(reservation.getFin()));
			preparedStatement.setLong(5, reservation.getId());

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new DaoException(
						"La modification d'un reservation a échoué, aucune ligne modifié dans la base de données.");
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la modification d'un reservation.", e);
		}
	}
	
	public long delete(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement =
					 connection.prepareStatement(DELETE_RESERVATION_QUERY)) {
			preparedStatement.setLong(1, reservation.getId());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression d'une reservation.", e);
		}
	}

	public long deleteByClient(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement =
					 connection.prepareStatement(DELETE_RESERVATION_BY_CLIENT_QUERY)) {
			preparedStatement.setLong(1, id);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression d'une reservation.", e);
		}
	}

	public long deleteByVehicle(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement =
					 connection.prepareStatement(DELETE_RESERVATION_BY_VEHICLE_QUERY)) {
			preparedStatement.setLong(1, id);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression d'une reservation.", e);
		}
	}

	public long delete(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_QUERY)) {
			preparedStatement.setLong(1, id);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression d'une reservation.", e);
		}
	}
	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);) {
			preparedStatement.setLong(1, clientId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				reservations.add(
						new Reservation(resultSet.getLong("id"),
								clientId,
								resultSet.getLong("vehicle_id"),
								resultSet.getDate("debut").toLocalDate(),
								resultSet.getDate("fin").toLocalDate()
								)
				);
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche des reservations par recherche du client.", e);
		}
		return reservations;
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);) {
			preparedStatement.setLong(1,vehicleId);
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				reservations.add(
						new Reservation(resultSet.getLong("id"),
								resultSet.getLong("client_id"),
								vehicleId,
								resultSet.getDate("debut").toLocalDate(),
								resultSet.getDate("fin").toLocalDate()
						)
				);
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de tous les reservations.", e);
		}
		return reservations;
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_QUERY);) {
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				reservations.add(
						new Reservation(resultSet.getLong("id"),
								resultSet.getLong("client_id"),
								resultSet.getLong("vehicle_id"),
								resultSet.getDate("debut").toLocalDate(),
								resultSet.getDate("fin").toLocalDate()
						)
				);
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de tous les reservations.", e);
		}
		return reservations;
	}

	public Reservation findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATION_BY_ID_QUERY);) {
			preparedStatement.setLong(1,id);
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return new Reservation(id,
								resultSet.getLong("client_id"),
								resultSet.getLong("vehicle_id"),
								resultSet.getDate("debut").toLocalDate(),
								resultSet.getDate("fin").toLocalDate()

				);
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de la reservation : "+id + ".", e);
		}
		return null;
	}

	public int count() throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(COUNT_RESERVATIONS_QUERY);
			 ResultSet resultSet = preparedStatement.executeQuery()) {
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DaoException("Erreur lors de la récupération du nombre de reservation.");
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur SQL lors de la récupération du nombre de reservation.", e);
		}
	}

	public int countBetweenDateForAVehicle(Reservation reservation) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(COUNT_RESERVATION_SAME_DATE_QUERY);) {
			preparedStatement.setLong(1,reservation.getVehicule_id());
			preparedStatement.setDate(2,Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(3,Date.valueOf(reservation.getFin()));
			preparedStatement.setDate(4,Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(5,Date.valueOf(reservation.getFin()));
			preparedStatement.setDate(6,Date.valueOf(reservation.getDebut()));
			preparedStatement.setDate(7,Date.valueOf(reservation.getFin()));
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt(1);
			} else {
				throw new DaoException("Erreur lors de la récupération du nombre de reservation entre deux dates données.");
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur SQL lors de la récupération du nombre de reservation entre deux dates données.", e);
		}
	}

}
