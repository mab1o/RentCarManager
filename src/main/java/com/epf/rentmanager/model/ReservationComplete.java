package com.epf.rentmanager.model;

import java.time.format.DateTimeFormatter;

public class ReservationComplete {

    private Reservation reservation;
    private Client client;
    private Vehicle vehicle;
    private String prettyDebut, prettyFin;

    public ReservationComplete() {
    }

    public ReservationComplete(Reservation reservation, Client client, Vehicle vehicle) {
        this.reservation = reservation;
        this.client = client;
        this.vehicle = vehicle;
        modPrettyDebut();
        modPrettyFin();
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        modPrettyDebut();
        modPrettyFin();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getPrettyDebut() {
        return prettyDebut;
    }
    public String getPrettyFin() {
        return prettyFin;
    }

    public void modPrettyFin() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        prettyFin = reservation.getFin().format(formatter);
    }

    public void modPrettyDebut() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        prettyDebut = reservation.getDebut().format(formatter);
    }

    @Override
    public String toString() {
        return "\nReservationComplete{" +
                "\nreservation= " + reservation +
                ",\nclient= " + client +
                ",\nvehicle= " + vehicle +
                "\n}";
    }
}
