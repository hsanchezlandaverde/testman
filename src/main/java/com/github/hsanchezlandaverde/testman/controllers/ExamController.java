package com.github.hsanchezlandaverde.testman.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.hsanchezlandaverde.testman.dtos.ErrorResponse;
import com.github.hsanchezlandaverde.testman.dtos.ExamDTO;
import com.github.hsanchezlandaverde.testman.services.ExamService;
import com.github.hsanchezlandaverde.testman.utils.Mapper;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

	@GetMapping
	public ResponseEntity<List<ExamDTO>> get() {
		return ResponseEntity.ok(examService
				.findAll()
				.stream()
				.map(Mapper::examEntityToDTO)
				.collect(Collectors.toList()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable(name="id") String id) {
		UUID examId = null;
		try {
			examId = UUID.fromString(id);
		} catch (IllegalArgumentException iae) {
			return ResponseEntity.badRequest().body(new ErrorResponse(iae.getMessage()));
		}
		var oExam = examService.findById(examId);
		if (oExam.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(Mapper.examEntityToDTO(oExam.get()));
	}

	@Autowired
	private ExamService examService;

}
