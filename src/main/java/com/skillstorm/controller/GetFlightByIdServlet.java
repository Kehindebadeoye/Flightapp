package com.skillstorm.controller;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.bean.Flight;
import com.skillstorm.data.FlightDAO;

@WebServlet(urlPatterns = "/api/flight/getById")
public class GetFlightByIdServlet extends HttpServlet {
	FlightDAO dao = new FlightDAO();
	
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// get flight by id and checking that null is not passed
		if (req.getParameter("id") != null) {
			int id = Integer.parseInt(req.getParameter("id")); 
			Flight result = dao.getFlightById(id); 
			String json = new ObjectMapper().writeValueAsString(result);
			System.out.println(json);
			resp.getWriter().print(json); 
		} else {
			Set<Flight> flight = dao.getAllFlights();
			String json = new ObjectMapper().writeValueAsString(flight);
			resp.getWriter().print(json);
		}
	}

}
