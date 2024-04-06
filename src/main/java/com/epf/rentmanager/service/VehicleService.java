package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	private ReservationDao reservationDao;

	@Autowired
	private VehicleService(VehicleDao vehicleDao, ReservationDao reservationDao) {
		this.vehicleDao = vehicleDao;
		this.reservationDao = reservationDao;
	}

	public long create(Vehicle vehicle) throws ServiceException {
		if (vehicle.getConstructeur()== null || vehicle.getNb_places() <= 1){
			throw new ServiceException("Un vehicule ne peut pas avoir un constructeur vide ou un nb place <= 1") ;
		}else{
            try {
                return vehicleDao.create(vehicle);
            } catch (DaoException e) {
				throw new ServiceException("DAO : ",e);
            }
        }
	}

	public void update(Vehicle vehicle) throws ServiceException {
		if (vehicle.getConstructeur()== null || vehicle.getNb_places() <= 1){
			throw new ServiceException("Un vehicule ne peut pas avoir un constructeur vide ou un nb place <= 1") ;
		}else{
			try {
				vehicleDao.update(vehicle);
			} catch (DaoException e) {
				throw new ServiceException("DAO : ",e);
			}
		}
	}

	public long delete(long id) throws ServiceException {
		try {
			reservationDao.deleteByVehicle(id);
			return vehicleDao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException("DAO : ",e);
		}
	}

	public Vehicle findById(long id) throws ServiceException{
        try {
            return vehicleDao.findById(id);
        } catch (DaoException e) {
			throw new ServiceException("DAO : ",e);
        }
    }

	public List<Vehicle> findAll() throws ServiceException{
        try {
            return vehicleDao.findAll();
        } catch (DaoException e) {
			throw new ServiceException("DAO : ",e);
        }
    }

	public int count() throws ServiceException{
		try {
			return vehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération du nombre de véhicules", e);
		}
	}
	
}
