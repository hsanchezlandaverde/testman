package com.github.hsanchezlandaverde.testman.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.hsanchezlandaverde.testman.entities.Exam;
import com.github.hsanchezlandaverde.testman.repositories.IExamRepository;

@Service
public class ExamService {

	public List<Exam> findAll() {
		return examRepository.findAll();
	}

	public Optional<Exam> findById(UUID id) {
		return examRepository.findById(id);
	}

	@Autowired(required = false)
	private IExamRepository examRepository;

}
