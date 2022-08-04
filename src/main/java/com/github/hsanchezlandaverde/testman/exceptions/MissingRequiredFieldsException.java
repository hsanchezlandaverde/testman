package com.github.hsanchezlandaverde.testman.exceptions;

public class MissingRequiredFieldsException extends Exception {

	public MissingRequiredFieldsException(String... fields) {
		super(String.format("Missing required fields: [%s]", String.join(",", fields)));
	}

	private static final long serialVersionUID = 9190547922421950190L;

}
