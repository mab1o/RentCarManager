package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/vehicles/create")
public class VehicleCreateServlet extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Affichage du formulaire
        request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Traitement du formulaire (appel à la méthode de sauvegarde)
        String manufacturer = request.getParameter("manufacturer");
        String model = request.getParameter("modele");
        int numberOfSeats = Integer.parseInt(request.getParameter("seats"));

        // Appel à la méthode de sauvegarde pour insérer le véhicule dans la base de données
        Vehicle vehicle = new Vehicle();
        vehicle.setConstructeur(manufacturer);
        vehicle.setModele(model);
        vehicle.setNb_places(numberOfSeats);

        try {
            VehicleService vehicleservice = VehicleService.getInstance();
            long id = vehicleservice.create(vehicle);
            System.out.println("Un nouveau vehicule a ete crée avec l'id :" + id);

        } catch (ServiceException e) {
            System.out.println(e);
        }

        response.sendRedirect(request.getContextPath() + "/vehicles");
    }
}
