package com.epf.rentmanager.ui.cli;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.utils.IOUtils;

import java.time.LocalDate;
import java.util.List;

public class CommandLineInterface {

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n0. Quitter");
            System.out.println("1. Créer un Client");
            System.out.println("2. Lister tous les Clients");
            System.out.println("3. Créer un Véhicule");
            System.out.println("4. Lister tous les Véhicules");
            System.out.println("5. Supprimer un Client");
            System.out.println("6. Supprimer un Véhicule");
            System.out.println("7. Créer une Réservation");
            System.out.println("8. Lister toutes les Réservations");
            System.out.println("9. Lister toutes les Réservations associées à un Véhicule donné");
            System.out.println("10. Lister toutes les Réservations associées à un Client donné");
            System.out.println("11. Supprimer une Réservation");
            System.out.println("12. le nombre de vehicule referencer");

            // lire choix
            int choice = IOUtils.readInt("Choix: ");

            switch (choice) {
                case 1:  createClient();                break;
                case 2:  listClients();                 break;
                case 3:  createVehicle();               break;
                case 4:  listVehicles();                break;
                case 5:  deleteClient();                break;
                case 6:  deleteVehicle();               break;
                case 7:  createReservation();           break;
                case 8:  listReservation();             break;
                case 9:  listReservationByVehicule();   break;
                case 10: listReservationByClient();     break;
                case 11: deleteReservation();           break;
                case 12: countVehicles();               break;
                case 0:  exit = true;                   break;
                default: System.out.println("Choix invalide.");
            }
        }
    }

    private static void createClient() {

        String nom = IOUtils.readString("Nom du client: ", true);
        String prenom = IOUtils.readString("Prénom du client: ", true);
        String email = IOUtils.readString("Email du client: ",true);
        LocalDate naissance = IOUtils.readDate("Date de naissance du client:", true);

        Client client = new Client();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setEmail(email);
        client.setNaissance(naissance);

        try {
            ClientService clientservice = ClientService.getInstance();
            long id = clientservice.create(client);
            System.out.println("Un nouveau client a ete crée avec l'id :" + id);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    private static void listClients() {
        try {
            ClientService clientservice = ClientService.getInstance();
            List<Client> clients = clientservice.findAll();
            for (Client client : clients){
                System.out.println(client);
            }
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    private static void createVehicle() {
        System.out.println();
        String constructeur = IOUtils.readString("Constructeur du vehicule: ", true);
        String modele = IOUtils.readString("Modele du vehicule: ", true);
        int nb_place = IOUtils.readInt("Nombre de place du vehicule: ");

        Vehicle vehicle = new Vehicle();
        vehicle.setConstructeur(constructeur);
        vehicle.setModele(modele);
        vehicle.setNb_places(nb_place);

        try {
            VehicleService vehicleservice = VehicleService.getInstance();
            long id = vehicleservice.create(vehicle);
            System.out.println("Un nouveau vehicule a ete crée avec l'id :" + id);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    private static void listVehicles() {
        try {
            VehicleService vehicleservice = VehicleService.getInstance();
            List<Vehicle> vehicles = vehicleservice.findAll();
            for (Vehicle vehicle : vehicles){
                System.out.println(vehicle);
            }
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    private static void deleteClient() {
        long id = IOUtils.readInt("Id du client: ");

        try {
            ClientService clientservice = ClientService.getInstance();
            long mes = clientservice.delete(id);
            System.out.println("Un client a ete suprimer : " + mes);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    private static void deleteVehicle() {
        long id = IOUtils.readInt("Id du vehicule: ");

        VehicleService vehicleservice = VehicleService.getInstance();
        long mes = 0;
        try {
            mes = vehicleservice.delete(id);
        } catch (ServiceException e) {
            System.out.println(e);
        }
        System.out.println("Un vehicule a ete suprimer : " + mes);
    }

    private static void createReservation() {
        long client_id = IOUtils.readInt("Id du client: ");
        long vehicle_id = IOUtils.readInt("Id du vehicule: ");
        LocalDate debut = IOUtils.readDate("Date de debut de la reservation:", true);
        LocalDate fin = IOUtils.readDate("Date de fin de la reservation:", true);

        Reservation reservation = new Reservation();
        reservation.setClient_id(client_id);
        reservation.setVehicule_id(vehicle_id);
        reservation.setDebut(debut);
        reservation.setFin(fin);

        try {
            ReservationService reservationservice = ReservationService.getInstance();
            long id = reservationservice.create(reservation);
            System.out.println("Un nouveau reservation a ete crée avec l'id :" + id);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    private static void listReservation() {
        try {
            ReservationService reservationservice = ReservationService.getInstance();
            List<Reservation> reservations = reservationservice.findAll();
            for (Reservation reservation : reservations){
                System.out.println(reservation);
            }
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    private static void listReservationByVehicule() {
        long vehicle_id = IOUtils.readInt("Id du vehicule: ");
        try {
            ReservationService reservationservice = ReservationService.getInstance();
            List<Reservation> reservations = reservationservice.findByVehicleId(vehicle_id);
            for (Reservation reservation : reservations){
                System.out.println(reservation);
            }
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    private static void listReservationByClient() {
        long client_id = IOUtils.readInt("Id du client: ");
        try {
            ReservationService reservationservice = ReservationService.getInstance();
            List<Reservation> reservations = reservationservice.findByClientId(client_id);
            for (Reservation reservation : reservations){
                System.out.println(reservation);
            }
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    private static void deleteReservation() {
        long id = IOUtils.readInt("Id de la reservation: ");

        ReservationService reservationservice = ReservationService.getInstance();
        long mes = 0;
        try {
            mes = reservationservice.delete(id);
        } catch (ServiceException e) {
            System.out.println(e);
        }
        System.out.println("Un vehicule a ete suprimer : " + mes);
    }

    private static void countVehicles() {
        try {
            VehicleService vehicleservice = VehicleService.getInstance();
            int nb_vehicle = vehicleservice.count();
            System.out.println("nb de vehicle: " + nb_vehicle);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

}
