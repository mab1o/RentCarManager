package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vehicles/create")
public class VehicleCreateServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Affichage du formulaire
        request.getRequestDispatcher("/WEB-INF/views/vehicles/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String manufacturer = request.getParameter("manufacturer");
        String model = request.getParameter("modele");
        int numberOfSeats = Integer.parseInt(request.getParameter("seats"));

        Vehicle vehicle = new Vehicle();
        vehicle.setConstructeur(manufacturer);
        vehicle.setModele(model);
        vehicle.setNb_places(numberOfSeats);

        try {
            long id = vehicleService.create(vehicle);
        } catch (ServiceException e) {
            System.out.println("\nServletException: Erreur lors de la suppression du client." + e);
        }

        response.sendRedirect(request.getContextPath() + "/vehicles");
    }
}
