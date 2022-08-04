package com.github.hsanchezlandaverde.testman.dtos;

import java.util.UUID;

import lombok.Data;

@Data
public class ErrorResponse {

	public ErrorResponse(String detail) {
		this.id = UUID.randomUUID().toString();
		this.detail = detail;
	}

	private String id;
	private String detail;

}
