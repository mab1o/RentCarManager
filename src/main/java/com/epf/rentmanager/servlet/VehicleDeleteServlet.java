package com.epf.rentmanager.servlet;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteVehicle")
public class VehicleDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String vehicleIdStr = request.getParameter("id");

        if (vehicleIdStr != null && !vehicleIdStr.isEmpty()) {
            long vehicleId = Long.parseLong(vehicleIdStr);
            VehicleService vehicleService = context.getBean(VehicleService.class);;
            try {
                vehicleService.delete(vehicleId);
            } catch (ServiceException e) {
                System.out.println("Erreur lors de la suppression du véhicule" + e);
            }
        } else {
            System.out.println("L'id n'est pas reconnu ou existant");
        }
        response.sendRedirect(request.getContextPath() + "/vehicles");
    }
}


