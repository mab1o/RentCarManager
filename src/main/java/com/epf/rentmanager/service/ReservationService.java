package com.epf.rentmanager.service;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    @Autowired
    private ReservationService (ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public long create(Reservation reservation) throws ServiceException {
        if(valueOk(reservation)){
            try {
                return reservationDao.create(reservation);
            } catch (DaoException e) {
                throw new ServiceException("La reservation n'a pas pu etre crée.", e);
            }
        }
        return 0;
    }

    public void update(Reservation reservation) throws ServiceException {
        if(valueOk(reservation)){
            try {
                reservationDao.update(reservation);
            } catch (DaoException e) {
                throw new ServiceException("La reservation n'a pas pu etre modifié.", e);
            }
        }
    }

    private boolean valueOk(Reservation reservation) throws ServiceException{
        int nbReservation = countNbReservation(reservation);
        boolean vehicleOk =  nbReservation== 0 || (nbReservation==1 && reservation.getId() != 0);
        boolean debutBeforeFin = reservation.getDebut().isBefore(reservation.getFin());
        boolean max7ForSameClientAndVehicle = countReservationForAVehicleAndClientInThe7dayGap(reservation) == 0
                && ChronoUnit.DAYS.between(reservation.getDebut(), reservation.getFin()) <= 7;
        boolean vehicleMax30WithoutPause = !VehicleIsUse30DaysWithoutPause(reservation);

        if(!debutBeforeFin){
            throw new ServiceException("La reservation doit avoir une date de debut anterieur a sa date de fin");
        }
        if(!vehicleOk){
            throw new ServiceException("Le vehicle est deja utilisé sur autre reservation");
        }
        if(!max7ForSameClientAndVehicle){
            throw new ServiceException("Ce client utilise ce vehicle sur une periode superieur à 7 jour.");
        }
        if(!vehicleMax30WithoutPause){
            throw new ServiceException("Ce vehicule est utilise ce vehicle plus de 30 jours ou plus sans pause.");
        }
        return true;
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

    private int countNbReservation(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.countBetweenDateForAVehicle(reservation);
        } catch (DaoException e){
            throw new ServiceException("Erreur lors de la récupération du nombre de reservation entre deux dates.");
        }
    }

    private int countReservationForAVehicleAndClientInThe7dayGap(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.countReservationForSameVehicleClient(reservation);
        } catch (DaoException e){
            throw new ServiceException("Erreur lors de la récupération du nombre de reservation pour un client, " +
                    "un vehicle, une periode donnée");
        }
    }

    private boolean VehicleIsUse30DaysWithoutPause(Reservation reservation) throws ServiceException {
        List<Reservation> reservations = listDayVehicleIsUseWithoutPause(reservation);
        long duree = ChronoUnit.DAYS.between(reservation.getDebut(), reservation.getFin());
        long dureeMax = 30L;
        LocalDate debutMin = reservation.getDebut().minusDays(dureeMax-duree);
        LocalDate finMax = reservation.getFin().plusDays(dureeMax-duree);

        if (!reservations.isEmpty()){

            // add reservation to the sorted list
            int ind = 0;
            for (int i = 0; i < reservations.size();i++){
                if (reservations.get(i).getDebut().isBefore(reservation.getDebut())){
                    ind = i;
                }
            }
            reservations.add(ind+1,reservation);

            // Count max day
            long nbDay = 0;
            for (int i = 0; i < reservations.size()-1;i++){
                Reservation reservation1 = reservations.get(i);
                Reservation reservation2 = reservations.get(i+1);

                LocalDate debut1 = reservation1.getDebut();
                LocalDate fin1 = reservation1.getFin();
                LocalDate debut2 = reservation2.getDebut();
                LocalDate fin2 = reservation2.getFin();

                // test if pause between two rents
                boolean test = ChronoUnit.DAYS.between(fin1, debut2) - 1 == 0;
                if (test){
                    nbDay += ChronoUnit.DAYS.between(debut1, fin2);
                } else {
                    nbDay = 0;
                }

                if (nbDay >= 30){
                    return true;
                }
            }
        }
        return false;
    }

    private List<Reservation> listDayVehicleIsUseWithoutPause(Reservation reservation) throws ServiceException {
        try {
            return reservationDao.findAllIn30DaysAround(reservation);
        } catch (DaoException e){
            throw new ServiceException("Erreur lors de la recherche de tous les reservations autour des 30 jour " +
                    "de la reservation donnée");
        }
    }

}
