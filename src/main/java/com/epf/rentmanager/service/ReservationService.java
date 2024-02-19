package com.epf.rentmanager.service;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;

import java.util.List;
import java.util.Objects;

public class ReservationService {

    private final ReservationDao reservationDao;
    public static ReservationService instance;

    private ReservationService () {
        this.reservationDao = ReservationDao.getInstance();
    }

    public static  ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }

        return instance;
    }

    public long create(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new ServiceException("DAO : ", e);
        }
    }

    public long delete(long id) throws ServiceException {
        try {
            return reservationDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException("DAO : ", e);
        }
    }

    public List<Reservation> findByVehicleId(long id) throws ServiceException {
        try {
            return reservationDao.findResaByVehicleId(id);
        } catch (DaoException e) {
            throw new ServiceException("DAO : ", e);
        }
    }

    public List<Reservation> findByClientId(long id) throws ServiceException {
        try {
            return reservationDao.findResaByClientId(id);
        } catch (DaoException e) {
            throw new ServiceException("DAO : ", e);
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("DAO : ", e);
        }
    }

}
