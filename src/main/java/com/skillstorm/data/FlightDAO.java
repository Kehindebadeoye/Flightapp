package com.skillstorm.data;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.skillstorm.bean.Flight;




public class FlightDAO {
	
	static String url = "jdbc:mysql://localhost:3306/flight";
	static String username = "root";
	static String password = "kehinde";
	
	
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("...Error Loading Connection Driver. Contact DB Admin");
			e.printStackTrace();
		}
	}
	
	public Flight createFlight(Flight flight) {
		
		try(Connection conn = DriverManager.getConnection(url, username,password)){
			String sql = "insert into flight(source, destination, departure, arrival, flight_number) values (?, ?, ?, ?, ?)";
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, flight.getSource());
			stmt.setString(2, flight.getDestination());
			stmt.setString(3, flight.getDeparture());
			stmt.setString(4, flight.getArrival());
			stmt.setString(5, flight.getFlightNumber());
			stmt.executeUpdate();
			ResultSet key = stmt.getGeneratedKeys();
			key.next();
			int id = key.getInt(1);
			flight.setId(id);
			
		} catch (SQLException e) {
			System.out.println("..Oops Invalid Connection Credential");
			e.printStackTrace();
		}
		return flight;
		
	}
	
	public Flight updateFlight(Flight flight) {
		try(Connection conn = DriverManager.getConnection(url, username,password)){
			String sql = "Update flight" + " set source = ?, destination = ?, departure = ?, arrival = ?, flight_number = ?" + " where id = ?";	
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(6, flight.getId());
			stmt.setString(1, flight.getSource());
			stmt.setString(2, flight.getDestination());
			stmt.setString(3, flight.getDeparture());
			stmt.setString(4, flight.getArrival());
			stmt.setString(5, flight.getFlightNumber());
			stmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("..Oops Invalid Connection Credential");
			e.printStackTrace();
		}
		return flight;
	}
	public Flight updateFlightDepartureAndArrivalDate(Flight flight) {
		try(Connection conn = DriverManager.getConnection(url, username,password)){
			String sql = "Update flight" + " set departure = ?, arrival = ?" + " where id = ?";	
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(3, flight.getId());		
			stmt.setString(1, flight.getDeparture());
			stmt.setString(2, flight.getArrival());
			stmt.executeUpdate();
			String sql2 = "select id,source,destination,departure,arrival, flight_number from flight where id = ?";
			PreparedStatement stmt2 = conn.prepareStatement(sql2);
			stmt2.setInt(1, flight.getId());		
			ResultSet results= stmt2.executeQuery();
			results.next();
			return new Flight(results.getInt("id"),results.getString("source"),results.getString("destination"),
					results.getString("departure"),results.getString("arrival"),
					results.getString("flight_number"));
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return flight;
	
	}
	public Flight getFlightById(int id) {
			try(Connection conn = DriverManager.getConnection(url, username, password)){
				String sql = "select id,source,destination,departure,arrival, flight_number from flight where id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet results= stmt.executeQuery();
			results.next();
			return new Flight(results.getInt("id"),results.getString("source"),results.getString("destination"),
					results.getString("departure"),results.getString("arrival"),
					results.getString("flight_number"));
		
		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
	}
	
	public Set<Flight> getAllFlights(){
		Set<Flight> flightList = new HashSet<Flight>();
		try(Connection conn = DriverManager.getConnection(url,username, password);){
			String sql = "select id, source, destination, departure, arrival, flight_number from flight";
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String source = rs.getString("source");
				String destination = rs.getString("destination");
				String departure = rs.getString("departure");
				String arrival = rs.getString("arrival");
				String flightNumber = rs.getString("flight_number");
				Flight flight = new Flight(id,source,destination,departure,arrival,flightNumber);
				flightList.add(flight);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return flightList;
		
		
	}
	public Flight getFlightByFlightNumber(String flightNumber) {
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			String sql = "select id, source, destination, departure, arrival, flight_number from flight where flight_number like ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, "%"+flightNumber+"%");
		ResultSet results= stmt.executeQuery();
		results.next();
		return new Flight(results.getInt("id"), results.getString("source"),
				results.getString("destination"),results.getString("departure"),results.getString("arrival"),results.getString("flight_number"));
		
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	public Flight deleteFlght(int id) {
		try(Connection conn = DriverManager.getConnection(url, username, password)){
			String sql = "delete from flight where id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		stmt.executeUpdate();
		System.out.println(id +"deleted");
		} 
		catch (SQLException e) {
		
			e.printStackTrace();
		 //you can rethrow here
		}
		return null;	
	}

}
