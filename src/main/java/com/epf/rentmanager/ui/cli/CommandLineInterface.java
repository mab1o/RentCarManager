package com.epf.rentmanager.ui.cli;
import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.ReservationComplete;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationCompleteService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
            System.out.println("13. la list de reservation complete");
            System.out.println("14. Affiche le client d'un id donné");

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
                case 13: listReservationComplete();     break;
                case 14: listClientsById();             break;
                case 0:  exit = true;                   break;
                default: System.out.println("Choix invalide.");
            }
        }
    }




    static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
    static ClientService clientservice = context.getBean(ClientService.class);
    static VehicleService vehicleservice = context.getBean(VehicleService.class);
    static ReservationService reservationservice = context.getBean(ReservationService.class);
    static ReservationCompleteService reservationcompleteservice = context.getBean(ReservationCompleteService.class);

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
            long id = clientservice.create(client);
            System.out.println("Un nouveau client a ete crée avec l'id :" + id);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    private static void listClients() {
        try {
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
            long id = vehicleservice.create(vehicle);
            System.out.println("Un nouveau vehicule a ete crée avec l'id :" + id);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    private static void listVehicles() {
        try {
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
            long mes = clientservice.delete(id);
            System.out.println("Un client a ete suprimer : " + mes);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    private static void deleteVehicle() {
        long id = IOUtils.readInt("Id du vehicule: ");
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
            long id = reservationservice.create(reservation);
            System.out.println("Un nouveau reservation a ete crée avec l'id :" + id);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    private static void listReservation() {
        try {
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
            int nb_vehicle = vehicleservice.count();
            System.out.println("nb de vehicle: " + nb_vehicle);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    // les erreur trows sont des client ou vehicule non trouvé car suprimer precedement
    private static void listReservationComplete() {
        try {
            List<ReservationComplete> reservationCompletes = reservationcompleteservice.findAll();
            for (ReservationComplete reservation : reservationCompletes){
                System.out.println(reservation);
            }
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }

    private static void listClientsById() {
        long client_id = IOUtils.readInt("Id du client: ");
        try {
            Client client = clientservice.findById(client_id);
            System.out.println(client);
        } catch (ServiceException e) {
            System.out.println(e);
        }
    }
}
