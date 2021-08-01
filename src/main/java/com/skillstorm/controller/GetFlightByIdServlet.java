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

}
