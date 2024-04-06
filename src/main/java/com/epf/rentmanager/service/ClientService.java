package com.epf.rentmanager.service;

import java.util.ArrayList;
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
		if (Objects.equals(client.getNom(), "") || Objects.equals(client.getPrenom(), "")){
			throw new ServiceException("Service : Un client ne peut pas avoir un prenom ou un nom null") ;
		}else{
			client.setNom(client.getNom().toUpperCase());
            try {
                return clientDao.create(client);
            } catch (DaoException e) {
                throw new ServiceException("Le client n'a pas pu etre creer :",e);
            }
        }
	}

	public void update(Client client) throws ServiceException {
		if (Objects.equals(client.getNom(), "") || Objects.equals(client.getPrenom(), "")){
			throw new ServiceException("Service : Un client ne peut pas avoir un prenom ou un nom null") ;
		}else{
			client.setNom(client.getNom().toUpperCase());
			try {
				clientDao.update(client);
			} catch (DaoException e) {
				throw new ServiceException("Le client n'a pas pu etre modifier :",e);
			}
		}
	}

	public long delete(long id) throws ServiceException {
		try {
			reservationDao.deleteByClient(id);
			return clientDao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la supression d'un client : ",e);
		}
	}

	public Client findById(long id) throws ServiceException{
        try {
            return clientDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de rechercher du client : ",e);
        }
    }

	public List<Client> findAll() throws ServiceException{
        try {
            return clientDao.findAll();
        } catch (DaoException e) {
			throw new ServiceException("Erreur lors de rechercher des clients : ",e);
        }
    }

	public int count() throws ServiceException{
		try {
			return clientDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération du nombre de client", e);
		}
	}
	
}
