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

@WebServlet(urlPatterns = "/api/flight")
public class FlightServlet extends HttpServlet {

	// servlets are singleton(tread-safe)..its not sharing state of FlightDao
	FlightDAO dao = new FlightDAO();

	// GET /api/flight?id=
	// route or request-mapping
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// get flight by id and checking that null is not passed
		if (req.getParameter("id") != null) {
			int id = Integer.parseInt(req.getParameter("id")); // the request is coming in as a string hence we need to
																// parse it to int
			Flight result = dao.getFlightById(id); // this is a java object which cant be sent over the network except
													// converted to json
			String json = new ObjectMapper().writeValueAsString(result);// the binding will be done by
																		// JacksonBind,JacksonCore,&Jackson annotation
																		// dependency
			System.out.println(json);
			// how do i send this back to anyone making this request? getWriter
			// Note - http is all string so whenever you are sending or receiving its string
			// (its a plain text protocol)
			resp.getWriter().print(json); // write the data to the response
		} else {
			Set<Flight> flight = dao.getAllFlights();
			String json = new ObjectMapper().writeValueAsString(flight);
			resp.getWriter().print(json);
		}
	}

	// not SAFE
	// idempotence: subsequent/repetitive calls have an adverse result
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getInputStream() != null) {
			InputStream requestBody = req.getInputStream(); // getting the body of the request
			Flight flight = new ObjectMapper().readValue(requestBody, Flight.class);// convert the req body to java																				// object(Flight)
//			System.out.println(flight);
//	dao.createFlight(flight);
			Flight updated = dao.createFlight(flight); // trying to return the updated flight
			resp.getWriter().print(new ObjectMapper().writeValueAsString(updated));
			resp.setStatus(201); // just changing the status shown from 200 to 201
			resp.setContentType("application/json"); // This is the header format for json in postman (not mandatory)
		} else {
			resp.getWriter().print(new Flight()); // printing empty object for null
		}
	}

	// not SAFE
	// not idempotent
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getInputStream() != null) {
			InputStream requestBody = req.getInputStream();
			Flight flight = new ObjectMapper().readValue(requestBody, Flight.class);
			
			Flight updatedFlight = dao.updateFlight(flight);
			resp.getWriter().print(new ObjectMapper().writeValueAsString(updatedFlight));
			System.out.println(updatedFlight.getId());
			System.out.println(updatedFlight);
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

	// not idempotent
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
