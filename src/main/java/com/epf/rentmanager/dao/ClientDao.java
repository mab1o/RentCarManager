package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	
	public long create(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CLIENT_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
			preparedStatement.setString(1, client.getNom());
			preparedStatement.setString(2, client.getPrenom());
			preparedStatement.setString(3, client.getEmail());
			preparedStatement.setDate(4, Date.valueOf(client.getNaissance()));

			int affectedRows = preparedStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new DaoException("La création d'un client a échoué, aucune ligne ajoutée dans la base de données.");
			}

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getLong(1);
				} else {
					throw new DaoException("La création d'un client a échoué, aucun ID auto-généré retourné.");
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la création d'un client.", e);
		}
	}
	
	public long delete(Client client) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT_QUERY)) {
			preparedStatement.setLong(1, client.getId());
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression d'un client.", e);
		}
	}

	public long delete(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT_QUERY)) {
			preparedStatement.setLong(1, id);
			return preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la suppression d'un client.", e);
		}
	}

	public Client findById(long id) throws DaoException {
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_QUERY)) {
			preparedStatement.setLong(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return new Client(resultSet.getLong("id"),
							resultSet.getString("nom"),
							resultSet.getString("prenom"),
							resultSet.getString("email"),
							resultSet.getDate("naissance").toLocalDate());
				}
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche du client par ID.", e);
		}
		return null;
	}

	public List<Client> findAll() throws DaoException {
		List<Client> clients = new ArrayList<>();
		try (Connection connection = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENTS_QUERY);
			 ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				clients.add(
					new Client(resultSet.getLong("id"),
						resultSet.getString("nom"),
						resultSet.getString("prenom"),
						resultSet.getString("email"),
						resultSet.getDate("naissance").toLocalDate())
				);
			}
		} catch (SQLException e) {
			throw new DaoException("Erreur lors de la recherche de tous les clients.", e);
		}
		return clients;
	}

}
