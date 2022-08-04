package com.github.hsanchezlandaverde.testman.controllers;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.FileCopyUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
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
	public void testGetAll_WhenNoResults_ExpectHTTP200AndEmptyJSONList() throws Exception {
		when(examRepository.findAll()).thenReturn(new ArrayList<Exam>());
		var expectedBody = getResourceAsString("emptyExamList.json");
		mockMvc.perform(get("/api/exams"))
				.andExpect(status().isOk())
				.andExpect(content().string(expectedBody));
	}

	@Test
	public void testGetll_WhenEverythingOK_ExpectHTTP200AndJSONList() throws Exception {
		var exams = Arrays.asList(
				Exam.builder()
				.id(UUID.fromString("455ee128-0b96-4b49-a4c0-3c30d4c8af01"))
				.title("World War 1 basic Exam")
				.signature("History")
				.minimumApproval(70.0)
				.duration(100)
				.build(),
				Exam.builder()
				.id(UUID.fromString("aed46871-a9b3-4f33-b527-65e607c62f31"))
				.title("World War 2 basic Exam")
				.signature("History")
				.minimumApproval(75.0)
				.duration(120)
				.build());
		when(examRepository.findAll()).thenReturn(exams);
		var expectedBody = getResourceAsString("examList.json");
		mockMvc.perform(get("/api/exams"))
				.andExpect(status().isOk())
				.andExpect(content().string(expectedBody));
	}
	
	@Test
	public void testGetById_GivenInvalidId_ExpectHTTP400AndErrorObject() throws Exception {
		var expectedBody = "Invalid UUID string";
		mockMvc.perform(get("/api/exams/455ee128"))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString(expectedBody)));
	}
	
	@Test
	public void testGetById_WhenNotFound_ExpectHTTP404AndEmptyJSONObject() throws Exception {
		Optional<Exam> exam = Optional.empty();
		when(examRepository.findById(any(UUID.class))).thenReturn(exam);
		var expectedBody = "";
		mockMvc.perform(get("/api/exams/455ee128-0b96-4b49-a4c0-3c30d4c8af01"))
				.andExpect(status().isNotFound())
				.andExpect(content().string(expectedBody));
	}
	
	@Test
	public void testGetById_WhenFound_ExpectHTTP404AndEmptyJSONObject() throws Exception {
		var exam = Optional.of(Exam.builder()
				.id(UUID.fromString("455ee128-0b96-4b49-a4c0-3c30d4c8af01"))
				.title("World War 1 basic Exam")
				.signature("History")
				.minimumApproval(70.0)
				.duration(100)
				.build());
		when(examRepository.findById(any(UUID.class))).thenReturn(exam);
		var expectedBody = getResourceAsString("examObject.json");
		mockMvc.perform(get("/api/exams/455ee128-0b96-4b49-a4c0-3c30d4c8af01"))
				.andExpect(status().isOk())
				.andExpect(content().string(expectedBody));
	}

	@Test
	public void testCreate_WhenMissingTitle_ExpectHTTP400AndErrorObject() throws Exception {
		var requestBody = getResourceAsString("examObjectRequestMissingFields.json");
		var expectedBody = "Missing required fields: [title]";
		mockMvc.perform(post("/api/exams")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString(expectedBody)));
	}
	
	@Test
	public void testCreate_GivenInvalidApproval_ExpectHTTP400AndErrorObject() throws Exception {
		var requestBody = getResourceAsString("examObjectRequestInvalidApproval.json");
		var expectedBody = "Invalid format for field: [minimumApproval], must be: [100 <= minimumApproval > 0.0]";
		mockMvc.perform(post("/api/exams")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString(expectedBody)));
	}
	
	@Test
	public void testCreate_GivenInvalidDuration_ExpectHTTP400AndErrorObject() throws Exception {
		var requestBody = getResourceAsString("examObjectRequestInvalidDuration.json");
		var expectedBody = "Invalid format for field: [duration], must be: [duration >= 0]";
		mockMvc.perform(post("/api/exams")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString(expectedBody)));
	}
	
	@Test
	public void testCreate_GivenValidBody_ExpectHTTP201AndJSONObject() throws Exception {
		var exam = Exam.builder()
				.id(UUID.fromString("455ee128-0b96-4b49-a4c0-3c30d4c8af01"))
				.title("World War 1 basic Exam")
				.signature("History")
				.minimumApproval(70.0)
				.duration(100)
				.build();
		when(examRepository.save(any(Exam.class))).thenReturn(exam);
		var requestBody = getResourceAsString("examObjectRequest.json");
		var expectedBody = getResourceAsString("examObject.json");
		mockMvc.perform(post("/api/exams")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(content().string(expectedBody));
	}

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IExamRepository examRepository;

}
