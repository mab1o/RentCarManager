package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebServlet("/rents/create")
public class  ReservationCreateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Client> clients = null;
        try {
            clients = clientService.findAll();
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        request.setAttribute("clients", clients);

        List<Vehicle> vehicles = null;
        try {
            vehicles = vehicleService.findAll();
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        request.setAttribute("vehicles", vehicles);

        request.getRequestDispatcher("/WEB-INF/views/rents/create.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long carId= Long.parseLong(
                request.getParameter("car"));
        long clientId = Long.parseLong(
                request.getParameter("client"));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate beginDate = LocalDate.parse(
                request.getParameter("begin"),dateFormatter);
        LocalDate endDate =
                LocalDate.parse(request.getParameter("end"),dateFormatter);

        Reservation reservation = new Reservation();
        reservation.setVehicule_id(carId);
        reservation.setClient_id(clientId);
        reservation.setDebut(beginDate);
        reservation.setFin(endDate);

        try {
            reservationService.create(reservation);
        } catch (ServiceException e) {
            System.out.println(e);
        }
        response.sendRedirect(request.getContextPath() + "/rents");
    }
}