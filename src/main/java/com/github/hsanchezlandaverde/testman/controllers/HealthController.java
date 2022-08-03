package com.github.hsanchezlandaverde.testman.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HealthController {

	@GetMapping("/health")
	public ResponseEntity<?> health() {
		Map<String, Object> response = new HashMap<>();
		response.put("status", "OK");
		return ResponseEntity.ok(response);
	}

}
