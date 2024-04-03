package com.epf.rentmanager.model;

public class ReservationComplete {

    private Reservation reservation;
    private Client client;
    private Vehicle vehicle;

    public ReservationComplete() {
    }

    public ReservationComplete(Reservation reservation, Client client, Vehicle vehicle) {
        this.reservation = reservation;
        this.client = client;
        this.vehicle = vehicle;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
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

    @Override
    public String toString() {
        return "\nReservationComplete{" +
                "\nreservation= " + reservation +
                ",\nclient= " + client +
                ",\nvehicle= " + vehicle +
                "\n}";
    }
}
