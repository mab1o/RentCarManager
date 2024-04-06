package com.epf.rentmanager.model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Client {
    private long id;
    private String nom, prenom, email;
    private LocalDate naissance;
    private String pretty;

    public Client(long id, String nom, String prenom, String email, LocalDate naissance) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = naissance;
        modPretty();
    }

    public Client() {
    }

    public long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
        this.modPretty();
    }

    public String getPretty() {
        return pretty;
    }

    public void modPretty() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.pretty = this.naissance.format(formatter);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", naissance=" + naissance +
                '}';
    }
}
