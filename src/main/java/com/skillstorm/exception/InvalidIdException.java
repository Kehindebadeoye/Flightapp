package com.skillstorm.exception;

public class InvalidIdException extends RuntimeException{
	
	public InvalidIdException() {
		System.out.println("Please provide valid Flight Id");
	}
	
	public InvalidIdException(String msg) {
		System.out.println("Please provide valid Flight Id");
	}


}
