package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.ServiceException;

public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;
	
	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		
		return instance;
	}

	public long create(Client client) throws ServiceException {
		if (Objects.equals(client.getNom(), "") || Objects.equals(client.getPrenom(), "")){
			throw new ServiceException("Service : Un client ne peut pas avoir un prenom ou un nom null") ;
		}else{
			client.setNom(client.getNom().toUpperCase());
            try {
                return clientDao.create(client);
            } catch (DaoException e) {
                throw new ServiceException("DAO : ",e);
            }
        }
	}

	public long delete(long id) throws ServiceException {
		try {
			return clientDao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException("DAO : ",e);
		}
	}

	public Client findById(long id) throws ServiceException{
        try {
            return clientDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("DAO : ",e);
        }
    }

	public List<Client> findAll() throws ServiceException{
        try {
            return clientDao.findAll();
        } catch (DaoException e) {
			throw new ServiceException("DAO : ",e);
        }
    }
	
}
