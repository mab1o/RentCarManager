package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.ReservationComplete;
import com.epf.rentmanager.model.Vehicle;
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
import java.util.List;

@WebServlet("/vehicles/details")
public class VehicleDetailsServlet extends HttpServlet {
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
        String vehicleIdStr = request.getParameter("id");
        List<ReservationComplete> reservationCompletes = null;
        int nbReservationCompletes = 0;
        Vehicle vehicle = null;

        if (vehicleIdStr != null && !vehicleIdStr.isEmpty()) {
            long vehicleId = Long.parseLong(vehicleIdStr);
            try {
                reservationCompletes = reservationCompleteService.findByVehicleId(vehicleId);
                nbReservationCompletes = reservationCompletes.size();
            } catch (ServiceException e) {
                System.out.println("Erreur lors de l'affichage de detail du vehicle" + e);
            }

            try {
                vehicle = vehicleService.findById(vehicleId);
            } catch (ServiceException e) {
                System.out.println("Erreur lors de l'affichage de detail du vehicle" + e);
            }
        } else {
            System.out.println("L'id n'est pas reconnu ou existant");
        }

        request.setAttribute("reservationCompletes",reservationCompletes);
        request.setAttribute("nbReservation",nbReservationCompletes);
        request.setAttribute("vehicle",vehicle);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/vehicles/details.jsp").forward(request, response);
    }
}
