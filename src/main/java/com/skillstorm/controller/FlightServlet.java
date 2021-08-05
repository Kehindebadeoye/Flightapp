package com.skillstorm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillstorm.bean.Flight;
import com.skillstorm.data.FlightDAO;
import com.skillstorm.exception.InvalidIdException;

@WebServlet(urlPatterns = "/api/flight")
public class FlightServlet extends HttpServlet {

	FlightDAO dao = new FlightDAO();

	@Override
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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getInputStream() != null) {
			InputStream requestBody = req.getInputStream(); // getting the body of the request
			Flight flight = new ObjectMapper().readValue(requestBody, Flight.class);// convert the req body to java																				// object(Flight)

			Flight updated = dao.createFlight(flight); // trying to return the updated flight
			
			resp.getWriter().print(new ObjectMapper().writeValueAsString(updated));
			resp.setStatus(201); 
			resp.setContentType("application/json"); 
		} else {
			resp.getWriter().print(new Flight()); // printing empty object for null
		}
	}

	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getInputStream() != null) {
			InputStream requestBody = req.getInputStream();
			Flight flight = new ObjectMapper().readValue(requestBody, Flight.class);
			
			Flight updatedFlight = dao.updateFlight(flight);
			resp.getWriter().print(new ObjectMapper().writeValueAsString(updatedFlight));
			System.out.println(updatedFlight.getId());
			System.out.println(updatedFlight);
		}else {
			throw new InvalidIdException("Enter Valid Id");
		}
	}
//	@Override
//	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		if (req.getInputStream() != null) {
//			InputStream requestBody = req.getInputStream();
//			Flight flight = new ObjectMapper().readValue(requestBody, Flight.class);
//			Flight updatedFlight = dao.updateFlightDepartureAndArrivalDate(flight);
//			
//			resp.getWriter().print(new ObjectMapper().writeValueAsString(updatedFlight));
//		}
//	}


	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getParameter("id") != null) {
			int id = Integer.parseInt(req.getParameter("id"));
			System.out.println(id);
			Flight result = dao.deleteFlght(id);
			System.out.println("DELETED SUCCESSFULLY");
		}
	}
}
