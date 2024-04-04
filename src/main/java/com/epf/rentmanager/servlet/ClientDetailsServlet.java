package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.ReservationComplete;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationCompleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/users/details")
public class ClientDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ClientService clientService;
    @Autowired
    private ReservationCompleteService reservationCompleteService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String clientIdStr = request.getParameter("id");
        List<ReservationComplete> reservationCompletes = null;
        int nbReservationCompletes = 0;
        Client client = null;

        if (clientIdStr != null && !clientIdStr.isEmpty()) {
            long clientId = Long.parseLong(clientIdStr);
            try {
                reservationCompletes = reservationCompleteService.findByClientId(clientId);
                nbReservationCompletes = reservationCompletes.size();
            } catch (ServiceException e) {
                System.out.println("Erreur lors de l'affichage de detail du client" + e);
            }

            try {
                client = clientService.findById(clientId);
            } catch (ServiceException e) {
                System.out.println("Erreur lors de l'affichage de detail du client" + e);
            }
        } else {
            System.out.println("L'id n'est pas reconnu ou existant");
        }

        request.setAttribute("reservationCompletes",reservationCompletes);
        request.setAttribute("nbReservation",nbReservationCompletes);
        request.setAttribute("client",client);
        this.getServletContext().getRequestDispatcher("/WEB-INF/views/users/details.jsp").forward(request, response);
    }
}
