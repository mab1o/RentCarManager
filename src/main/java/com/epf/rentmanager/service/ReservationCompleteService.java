package com.epf.rentmanager.service;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.ReservationComplete;
import com.epf.rentmanager.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationCompleteService {

    @Autowired
    private ClientService clientService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ReservationService reservationService;

    public List<ReservationComplete> findByClientId(long id) throws ServiceException{
        List<ReservationComplete> reservationCompletes = new ArrayList<>();
        List<Reservation> reservations = null;

        reservations = reservationService.findByClientId(id);
        return getReservationCompletes(reservationCompletes, reservations);
    }

    public List<ReservationComplete> findByVehicleId(long id) throws ServiceException{
        List<ReservationComplete> reservationCompletes = new ArrayList<>();
        List<Reservation> reservations = null;

        reservations = reservationService.findByVehicleId(id);
        return getReservationCompletes(reservationCompletes, reservations);
    }

    public List<ReservationComplete> findAll() throws ServiceException{
        List<ReservationComplete> reservationCompletes = new ArrayList<>();
        List<Reservation> reservations = null;

        reservations = reservationService.findAll();
        return getReservationCompletes(reservationCompletes, reservations);
    }

    private List<ReservationComplete> getReservationCompletes(List<ReservationComplete> reservationCompletes, List<Reservation> reservations) {
        for (Reservation reservation : reservations){

            Client client = null;
            try{
                client = clientService.findById(reservation.getClient_id());
            } catch (ServiceException e){
                System.out.println("Client Not found");
                System.out.println(e);
            }

            Vehicle vehicle = null;
            try{
                vehicle = vehicleService.findById(reservation.getVehicule_id());
            } catch (ServiceException e){
                System.out.println("vehicle Not found");
                System.out.println(e);
            }

            reservationCompletes.add(new ReservationComplete(reservation, client, vehicle));
        }
        return reservationCompletes;
    }
}
