package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private final ClientDao clientDao;
	private final ReservationDao reservationDao;

	@Autowired
	public ClientService(ClientDao clientDao, ReservationDao reservationDao) {
		this.clientDao = clientDao;
		this.reservationDao = reservationDao;
	}

	public long create(Client client) throws ServiceException {
		if (valuesOk(client)) {
			client = prepareValues(client);
            try {
                return clientDao.create(client);
            } catch (DaoException e) {
                throw new ServiceException("Le client n'a pas pu etre creer.",e);
            }
        }
		return -1;
	}

	public void update(Client client) throws ServiceException {
		if (valuesOk(client)) {
			client = prepareValues(client);
			try {
				clientDao.update(client);
			} catch (DaoException e) {
				throw new ServiceException("Le client n'a pas pu etre modifier.",e);
			}
		}
	}

	private boolean valuesOk (Client client) throws ServiceException{
		Period period = Period.between(client.getNaissance(), LocalDate.now());

		boolean nameOk = !Objects.equals(client.getNom(), "") && client.getNom().length() > 2;
		boolean prenomOk = !Objects.equals(client.getPrenom(), "") && client.getPrenom().length() > 2;
		boolean ageOk = period.getYears() >= 18;
		boolean emailOk = emailUnique(client);

		if (!nameOk){
			throw new ServiceException("Un client ne peut pas avoir un nom null ou inferieur à 3 characteres.") ;
		}
		if (!prenomOk){
			throw new ServiceException("Un client ne peut pas avoir un prenom null ou inferieur à 3 characteres.") ;
		}
		if (!ageOk){
			throw new ServiceException("Un client ne peut pas avoir mois de 18 ans.") ;
		}
		if (!emailOk){
			throw new ServiceException("Deux client ne peuvent pas avoir le meme email.") ;
		}
		return true; // if not true, return exception
	}

	private Client prepareValues (Client client){
		client.setNom(client.getNom().toUpperCase());
		return client;
	}

	public long delete(long id) throws ServiceException {
		try {
			reservationDao.deleteByClient(id);
			return clientDao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la supression d'un client.",e);
		}
	}

	public Client findById(long id) throws ServiceException{
        try {
            return clientDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de rechercher du client.",e);
        }
    }

	public List<Client> findAll() throws ServiceException{
        try {
            return clientDao.findAll();
        } catch (DaoException e) {
			throw new ServiceException("Erreur lors de rechercher des clients.",e);
        }
    }

	public int count() throws ServiceException{
		try {
			return clientDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération du nombre de client.", e);
		}
	}

	private boolean emailUnique(Client client) throws ServiceException {
		List<Client> clients = null;
		boolean emailUnique = true;

		try {
			 clients = clientDao.findByEmail(client);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération des clients avec un email specifique.", e);
		}

		for (Client client1 : clients){
            if (client.getId() != client1.getId()) {
                emailUnique = false;
                break;
            }
		}
		return emailUnique;
	}
	
}
