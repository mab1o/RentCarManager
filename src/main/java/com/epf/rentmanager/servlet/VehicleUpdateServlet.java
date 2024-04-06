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

@WebServlet("/vehicles/update")
public class  VehicleUpdateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private VehicleService vehicleService;

    private Long vehicleId = null;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String vehicleIdStr = request.getParameter("id");
        Vehicle vehicle = null;

        if (vehicleIdStr != null && !vehicleIdStr.isEmpty()) {
            this.vehicleId = Long.parseLong(vehicleIdStr);
            try {
                vehicle = vehicleService.findById(vehicleId);
            } catch (ServiceException e) {
                System.out.println("Erreur lors la recherche du vehicle :" + e);
            }
            request.setAttribute("vehicle",vehicle);
            request.getRequestDispatcher("/WEB-INF/views/vehicles/update.jsp").forward(request, response);

        } else {
            response.sendRedirect(request.getContextPath() + "/vehicles");
            System.out.println("L'id n'est pas reconnu ou existant");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (vehicleId != null) {
            String manufacturer = request.getParameter("manufacturer");
            String model = request.getParameter("modele");
            int numberOfSeats = Integer.parseInt(request.getParameter("seats"));

            Vehicle vehicle = new Vehicle();
            vehicle.setConstructeur(manufacturer);
            vehicle.setModele(model);
            vehicle.setNb_places(numberOfSeats);
            vehicle.setId(vehicleId);

            try {
                vehicleService.update(vehicle);
            } catch (ServiceException e) {
                System.out.println(e);
            }
        } else {
            System.out.println("L'id n'est pas reconnu ou existant");
        }
        response.sendRedirect(request.getContextPath() + "/vehicles");
    }
}