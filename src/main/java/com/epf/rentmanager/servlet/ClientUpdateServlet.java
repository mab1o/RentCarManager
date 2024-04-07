package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
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

@WebServlet("/users/update")
public class ClientUpdateServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ClientService clientService;

    private Long clientId = null;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String clientIdStr = request.getParameter("id");
        Client client = null;

        if (clientIdStr != null && !clientIdStr.isEmpty()) {
            this.clientId = Long.parseLong(clientIdStr);
            try {
                client = clientService.findById(clientId);
                request.setAttribute("client",client);
                request.getRequestDispatcher("/WEB-INF/views/users/update.jsp").forward(request, response);
            } catch (ServiceException e) {
                response.sendRedirect(request.getContextPath() + "/users");
                System.out.println("\nServletException: Erreur lors de la recherche du client." + e);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/users");
            System.out.println("\nServletException: L'id n'est pas existant.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (clientId != null) {
            String prenom = request.getParameter("firstname");
            String nom = request.getParameter("lastname");
            String email = request.getParameter("email");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dateOfBirth = LocalDate.parse(
                    request.getParameter("dob"),dateFormatter);

            Client client = new Client();
            client.setId(clientId);
            client.setEmail(email);
            client.setNom(nom);
            client.setPrenom(prenom);
            client.setNaissance(dateOfBirth);

            try {
                clientService.update(client);

            } catch (ServiceException e) {
                System.out.println("\nServletException: Erreur lors la recherche du client." + e);

            } finally {
                response.sendRedirect(request.getContextPath() + "/users");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/users");
            System.out.println("\nServletException: L'id n'est pas existant.");
        }
    }
}