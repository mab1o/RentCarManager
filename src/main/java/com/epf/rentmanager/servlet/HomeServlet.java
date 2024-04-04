package com.epf.rentmanager.servlet;

import com.epf.rentmanager.exception.ServiceException;
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

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int vehicleCount = 0;
		try {
			vehicleCount = vehicleService.count();
		} catch (ServiceException e) {
			System.out.println(e);
		}
		request.setAttribute("vehicleCount", vehicleCount);

		int clientCount = 0;
		try {
			clientCount = clientService.count();
		} catch (ServiceException e) {
			System.out.println(e);
		}
		request.setAttribute("clientCount", clientCount);

		int reservationCount = 0;
		try {
			reservationCount = reservationService.count();
		} catch (ServiceException e) {
			System.out.println(e);
		}
		request.setAttribute("reservationCount", reservationCount);

		this.getServletContext().getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
	}

}
