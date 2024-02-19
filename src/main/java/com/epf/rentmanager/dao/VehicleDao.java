package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.jetbrains.annotations.NotNull;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	
	public long create(@NotNull Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(CREATE_VEHICLE_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, vehicle.getConstructeur());
			preparedStatement.setString(2, vehicle.getModele());
			preparedStatement.setInt(3, vehicle.getNb_places());

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new DaoException("La création du véhicule a échoué, aucune ligne ajoutée dans la base de données.");
			}

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getLong(1);
				} else {
					throw new DaoException("La création du véhicule a échoué, aucun ID auto-généré retourné.");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création du véhicule.", e);
		}
	}

	public long delete(@NotNull Vehicle vehicle) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VEHICLE_QUERY)) {
			preparedStatement.setLong(1, vehicle.getId());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du véhicule.", e);
		}
	}

	public long delete(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VEHICLE_QUERY)) {
			preparedStatement.setLong(1, id);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression du véhicule.", e);
		}
	}

	public Vehicle findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLE_QUERY)) {
			preparedStatement.setLong(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return new Vehicle(resultSet.getLong("id"),
							resultSet.getString("constructeur"),
							resultSet.getString("modele"),
							resultSet.getInt("nb_places"));
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du véhicule par ID.", e);
		}
		return null;
	}

	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> vehicles = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLES_QUERY);
			 ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				vehicles.add(
						new Vehicle(resultSet.getLong("id"),
								resultSet.getString("constructeur"),
								resultSet.getString("modele"),
								resultSet.getInt("nb_places"))
				);
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de tous les véhicules.", e);
		}
		return vehicles;
	}

}
