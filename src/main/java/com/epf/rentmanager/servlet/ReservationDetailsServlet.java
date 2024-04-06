package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.ReservationComplete;
import com.epf.rentmanager.service.ReservationCompleteService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/rents/details")
public class ReservationDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ReservationCompleteService reservationCompleteService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reservationIdStr = request.getParameter("id");
        ReservationComplete reservationComplete = null;
        int nbReservationCompletes = 0;
        Reservation reservation = null;

        if (reservationIdStr != null && !reservationIdStr.isEmpty()) {
            long reservationId = Long.parseLong(reservationIdStr);
            try {
                reservationComplete = reservationCompleteService.findById(reservationId);
            } catch (ServiceException e) {
                System.out.println("Erreur lors de l'affichage de detail du vehicle" + e);
            }

        } else {
            System.out.println("L'id n'est pas reconnu ou existant");
        }

        request.setAttribute("reservationComplete",reservationComplete);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/rents/details.jsp").forward(request, response);
    }
}
