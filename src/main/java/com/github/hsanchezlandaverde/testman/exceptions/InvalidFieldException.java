package com.github.hsanchezlandaverde.testman.exceptions;

public class InvalidFieldException extends Exception {

	public InvalidFieldException(String fieldName, String format) {
		super(String.format("Invalid format for field: [%s], must be: [%s]", fieldName, format));
	}

	private static final long serialVersionUID = -6873370513825970227L;

}
