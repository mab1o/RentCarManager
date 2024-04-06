package com.epf.rentmanager.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Reservation {
    private long id, client_id, vehicule_id;
    private LocalDate debut, fin;
    private String prettyDebut, prettyFin;

    public Reservation(long id, long client_id, long vehicule_id, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client_id = client_id;
        this.vehicule_id = vehicule_id;
        this.debut = debut;
        this.fin = fin;
        modPrettyDebut();
        modPrettyFin();
    }
    public Reservation() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClient_id() {
        return client_id;
    }

    public void setClient_id(long client_id) {
        this.client_id = client_id;
    }

    public long getVehicule_id() {
        return vehicule_id;
    }

    public void setVehicule_id(long vehicule_id) {
        this.vehicule_id = vehicule_id;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
        modPrettyDebut();
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
        modPrettyFin();
    }

    public String getPrettyDebut() {
        return prettyDebut;
    }

    public void modPrettyDebut() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.prettyDebut = this.debut.format(formatter);
    }

    public String getPrettyFin() {
        return prettyFin;
    }

    public void modPrettyFin() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.prettyFin = this.fin.format(formatter);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", vehicule_id=" + vehicule_id +
                ", debut=" + debut +
                ", fin=" + fin +
                '}';
    }
}
