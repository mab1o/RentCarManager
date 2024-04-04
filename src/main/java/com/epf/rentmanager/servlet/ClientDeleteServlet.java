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

@WebServlet("/deleteUser")
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
            throws IOException {
        String clientIdStr = request.getParameter("id");

        if (clientIdStr != null && !clientIdStr.isEmpty()) {
            long clientId = Long.parseLong(clientIdStr);
            try {
                clientService.delete(clientId);
            } catch (ServiceException e) {
                System.out.println("Erreur lors de la suppression du client" + e);
            }
        } else {
            System.out.println("L'id client n'est pas reconnu ou existant");
        }
        response.sendRedirect(request.getContextPath() + "/users");
    }
}
