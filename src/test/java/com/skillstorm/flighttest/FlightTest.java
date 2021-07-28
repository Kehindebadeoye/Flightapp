package com.skillstorm.flighttest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import com.skillstorm.bean.Flight;
import com.skillstorm.data.FlightDAO;

public class FlightTest {

	Flight flight = new Flight();
	FlightDAO dao = new FlightDAO();

	@Test
	public void testGetFlightById() {
		flight = dao.getFlightById(3);
		assertEquals (3,"CNY", "PHP", "2021-06-09", "2021-07-17", "13668-137", flight);
	}
	@Test
	public void testGetFlightByFakeId() {
		flight = dao.getFlightById(3);
		assertFalse (98,"CNY", "PHP", "2021-06-09", "2021-07-17", "13668-137", flight);
	}

	private void assertFalse(int i, String string, String string2, String string3, String string4, String string5,
			Flight flight2) {
		// TODO Auto-generated method stub
		
	}
	private void assertEquals(int i, String string, String string2, String string3, String string4, String string5,
			Flight flight2) {
		// TODO Auto-generated method stub

	}
	@Test
	public void testUpdateFlight() {
		Flight updated = dao.updateFlight(new Flight(3, "CNY", "PHP", "2021-17", "21", "137"));
		assertNotNull(updated);
		
	}
	
	@Test
	public void testCreateNewFlight() {
		Flight updated = dao.createFlight(new Flight("ATLANTA","AMMAN","2020-00-00","2015-00-02","BO777"));
		int newid =updated.getId();
		assertTrue(true,103,newid);
		
	}
	private void assertTrue(boolean b, int i, int newid) {
		// TODO Auto-generated method stub
		
	}
	
	public void testGetFlightByFlightNumber() {
		String flightNumber = "B0777";
		Flight flight = dao.getFlightByFlightNumber(flightNumber);
		Assert.assertEquals(flight, dao.getFlightByFlightNumber(flightNumber));
	}


}