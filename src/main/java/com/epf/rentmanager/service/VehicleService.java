package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
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
		if (valuesOk(vehicle)){
            try {
                return vehicleDao.create(vehicle);
            } catch (DaoException e) {
				throw new ServiceException("Le vehicle n'a pas pu etre créé.",e);
            }
        }
		return 0;
	}

	public void update(Vehicle vehicle) throws ServiceException {
		if (valuesOk(vehicle)){
			try {
				vehicleDao.update(vehicle);
			} catch (DaoException e) {
				throw new ServiceException("Le vehicle n'a pas pu etre modifié.",e);
			}
		}
	}

	public boolean valuesOk (Vehicle vehicle) throws ServiceException{
		boolean constructeurOk = !Objects.equals(vehicle.getConstructeur(), "");
		boolean modeleOk = !Objects.equals(vehicle.getModele(), "");
		boolean placeOk = vehicle.getNb_places() > 1 && vehicle.getNb_places() < 10;

		if (!constructeurOk){
			throw new ServiceException("Un vehicle ne peut pas avoir un constructeur null.") ;
		}
		if (!modeleOk){
			throw new ServiceException("Un vehicle ne peut pas avoir un modele null.") ;
		}
		if (!placeOk){
			throw new ServiceException("Un vehicule doit avoir un nombre de place comprit entre 2 et 9.") ;
		}
		return true; // if not true, return exception
	}

	public long delete(long id) throws ServiceException {
		try {
			reservationDao.deleteByVehicle(id);
			return vehicleDao.delete(id);
		} catch (DaoException e) {
			throw new ServiceException("Le vehicle n'a pas pu etre supprimé.",e);
		}
	}

	public Vehicle findById(long id) throws ServiceException{
        try {
            return vehicleDao.findById(id);
        } catch (DaoException e) {
			throw new ServiceException("Le vehicle avec l'id " + id + " n'a pas pu etre trouvé.",e);
        }
    }

	public List<Vehicle> findAll() throws ServiceException{
        try {
            return vehicleDao.findAll();
        } catch (DaoException e) {
			throw new ServiceException("La liste des vehicule n'a pas ete trouvé.",e);
        }
    }

	public int count() throws ServiceException{
		try {
			return vehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException("Erreur lors de la récupération du nombre de véhicules.", e);
		}
	}
	
}
