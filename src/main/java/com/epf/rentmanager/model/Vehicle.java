package com.epf.rentmanager.model;

public class Vehicle {
    private long id;
    private int nb_places;
    private String constructeur, modele;

    public Vehicle(long id, String constructeur, String modele, int nb_places) {
        this.id = id;
        this.nb_places = nb_places;
        this.constructeur = constructeur;
        this.modele = modele;
    }

    public Vehicle() {
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNb_places() {
        return nb_places;
    }

    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", nb_places=" + nb_places +
                ", constructeur='" + constructeur + '\'' +
                ", modele='" + modele + '\'' +
                '}';
    }
}
