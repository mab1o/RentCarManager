package com.epf.rentmanager.service;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    @Autowired
    private ReservationService (ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public long create(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException("La reservation n'a pas pu etre crée.", e);
        }
    }

    public void update(Reservation reservation) throws ServiceException {
        try {
            reservationDao.update(reservation);
        } catch (DaoException e) {
            throw new ServiceException("La reservation n'a pas pu etre modifié.", e);
        }
    }

    public long delete(long id) throws ServiceException {
        try {
            return reservationDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException("La reservation n'a pas pu etre suprimer.", e);
        }
    }

    public List<Reservation> findByVehicleId(long id) throws ServiceException {
        try {
            return reservationDao.findResaByVehicleId(id);
        } catch (DaoException e) {
            throw new ServiceException("La reservation avec l'id vehicle "+ id +" n'a pas pu etre trouvé.", e);
        }
    }

    public List<Reservation> findByClientId(long id) throws ServiceException {
        try {
            return reservationDao.findResaByClientId(id);
        } catch (DaoException e) {
            throw new ServiceException("La reservation avec l'id client "+ id +" n'a pas pu etre trouvé.", e);
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("La liste des reservations n'ont pas pu etre trouvé.", e);
        }
    }

    public Reservation findById(long id) throws ServiceException {
        try {
            return reservationDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("La reservation avec l'id "+ id +" n'a pas pu etre trouvé.", e);
        }
    }

    public int count() throws ServiceException{
        try {
            return reservationDao.count();
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la récupération du nombre de reservation.", e);
        }
    }

}
