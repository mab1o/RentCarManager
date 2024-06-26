package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/delete")
public class ClientDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Autowired
    private ClientService clientService;

    @Override
    public void init() throws ServletException {
        super.init();
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String clientIdStr = request.getParameter("id");

        if (clientIdStr != null && !clientIdStr.isEmpty()) {
            long clientId = Long.parseLong(clientIdStr);
            try {
                clientService.delete(clientId);
            } catch (ServiceException e) {
                System.out.println("\nServletException: Erreur lors de la suppression du client." + e);
            } finally{
                response.sendRedirect(request.getContextPath() + "/users");
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/users");
            System.out.println("L'id client n'est pas existant.");
        }
    }
}
