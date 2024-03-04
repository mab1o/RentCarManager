package com.epf.rentmanager.servlet;
import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/vehicles")
public class VehicleListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        VehicleService vehicleservice = context.getBean(VehicleService.class);;
        List<Vehicle> vehicles = null;
        try {
            vehicles = vehicleservice.findAll();
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        request.setAttribute("vehicles", vehicles);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/list.jsp").forward(request, response);
    }

}