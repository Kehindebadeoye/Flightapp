package com.skillstorm.flighttest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.skillstorm.bean.Flight;
import com.skillstorm.data.FlightDAO;

public class DbTest {

	
	static String url = "jdbc:mysql://localhost:3306/dbtest";
	static String username = "root";
	static String password = "kehinde";
	
	
	FlightDAO dao = new FlightDAO();
	
	@BeforeClass
	public static void beforeDbTest() {
		try {
			String ddl = "CREATE TABLE `dbtest`.`flight` (\r\n" + 
					"  `id` INT NOT NULL AUTO_INCREMENT,\r\n" + 
					"  `source` VARCHAR(45) NOT NULL,\r\n" + 
					"  `destination` VARCHAR(45) NOT NULL,\r\n" + 
					"  `departure` VARCHAR(45) NOT NULL,\r\n" + 
					"  `arrival` VARCHAR(45) NOT NULL,\r\n" + 
					"  `flight_number` VARCHAR(45) NOT NULL,\r\n" + 
					"  PRIMARY KEY (`id`))";
			
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement statement = conn.createStatement();
			statement.executeUpdate(ddl);
			conn.close();
			
		}catch (Exception e) {
			fail();
		}
		
	}
	@AfterClass
	public static void afterDbTest() {
		
		String ddl2 = "drop table flight";
		
		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement statement = conn.createStatement();
			statement.executeUpdate(ddl2);
			conn.close();
		}catch (Exception e) {
			fail();
		}
	}
	
	@Before
	public void beforeTest() {
		String sql= "insert into flight(source, destination, departure, arrival, flight_number) values ('USA','NGN', '08-04-2021', '08-05-2021', 'US700')";
		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement statement = conn.createStatement();
			statement.executeUpdate(sql);
			conn.close();
			
		}catch (Exception e) {
			fail();
		}
		
	}
	
	@Test
	public void testCreateFlight() {
		String sql = "select count(*) from flight";
		
		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int fromDb = rs.getInt(1);
			
			dao.createFlight(new Flight("USA","ITL","08-07-2022","08-06-2022","US701"));
			Statement stmt2 = conn.createStatement();
			ResultSet rs2 = stmt2.executeQuery(sql);
			rs2.next();
			int fromDao = rs2.getInt(1);
			
			assertEquals(fromDao, fromDb);
		
		
	}catch (Exception e) {
		}
	}
	
	@Test
	public void testGetFlightById() {
		String sql = "select * from flight where id = 1";
		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			int firstId = rs.getInt("id");
			
			Flight flight = dao.getFlightById(1);
			int id =flight.getId();
			
			assertEquals(firstId, id);
		}catch (Exception e) {
			fail();
		}
		
	}
}
