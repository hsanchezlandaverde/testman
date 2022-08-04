package com.github.hsanchezlandaverde.testman.controllers;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.FileCopyUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static org.mockito.Mockito.when;

import com.github.hsanchezlandaverde.testman.entities.Exam;
import com.github.hsanchezlandaverde.testman.repositories.IExamRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ExamControllerTest {

	public static String getResourceAsString(String resourceName) throws IOException {
		ResourceLoader resourceLoader = new DefaultResourceLoader();
		Resource resource = resourceLoader.getResource(String.format("classpath:%s", resourceName));
		try (Reader reader = new InputStreamReader(resource.getInputStream(), "utf-8")) {
			return FileCopyUtils.copyToString(reader);
		}
	}

	@Test
	public void testGet_WhenNoResults_ExpectHTTP200AndEmptyJSONList() throws Exception {
		when(examRepository.findAll()).thenReturn(new ArrayList<Exam>());
		var expectedBody = getResourceAsString("emptyExamList.json");
		mockMvc.perform(get(GET_EXAMS_ENDPOINT))
				.andExpect(status().isOk())
				.andExpect(content().string(expectedBody));
	}

	@Test
	public void testGet_WhenEverythingOK_ExpectHTTP200AndJSONList() throws Exception {
		var exams = Arrays.asList(
				Exam.builder()
				.id(UUID.fromString("455ee128-0b96-4b49-a4c0-3c30d4c8af01"))
				.title("World War 1 basic Exam")
				.signature("History")
				.minimunApproval(70.0)
				.duration(100)
				.build(),
				Exam.builder()
				.id(UUID.fromString("aed46871-a9b3-4f33-b527-65e607c62f31"))
				.title("World War 2 basic Exam")
				.signature("History")
				.minimunApproval(75.0)
				.duration(120)
				.build());
		when(examRepository.findAll()).thenReturn(exams);
		var expectedBody = getResourceAsString("examList.json");
		mockMvc.perform(get(GET_EXAMS_ENDPOINT))
				.andExpect(status().isOk())
				.andExpect(content().string(expectedBody));
	}

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IExamRepository examRepository;

	private static final String GET_EXAMS_ENDPOINT = "/api/exams";

}
