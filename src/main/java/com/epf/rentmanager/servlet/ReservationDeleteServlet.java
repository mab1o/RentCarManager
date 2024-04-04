package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteRent")
public class ReservationDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String reservationIdStr = request.getParameter("id");

        if (reservationIdStr != null && !reservationIdStr.isEmpty()) {
            long reservationId = Long.parseLong(reservationIdStr);
            try {
                reservationService.delete(reservationId);
            } catch (ServiceException e) {
                System.out.println("Erreur lors de la suppression de la reservation" + e);
            }
        } else {
            System.out.println("L'id n'est pas reconnu ou existant");
        }
        response.sendRedirect(request.getContextPath() + "/rents");
    }
}
