package com.github.hsanchezlandaverde.testman.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class SmokeTest {

	@Test
	public void contextLoads() throws Exception {
		assertThat(healthController).isNotNull();
		assertThat(examController).isNotNull();
	}

	@Autowired
	private HealthController healthController;

	@Autowired
	private ExamController examController;

}