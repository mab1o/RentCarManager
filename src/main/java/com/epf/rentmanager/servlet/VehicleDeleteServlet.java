package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vehicle/delete")
public class VehicleDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private VehicleService vehicleService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String vehicleIdStr = request.getParameter("id");

        if (vehicleIdStr != null && !vehicleIdStr.isEmpty()) {
            long vehicleId = Long.parseLong(vehicleIdStr);
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
