package com.github.hsanchezlandaverde.testman.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.hsanchezlandaverde.testman.dtos.ExamDTO;
import com.github.hsanchezlandaverde.testman.entities.Exam;
import com.github.hsanchezlandaverde.testman.exceptions.InvalidFieldException;
import com.github.hsanchezlandaverde.testman.exceptions.MissingRequiredFieldsException;
import com.github.hsanchezlandaverde.testman.repositories.IExamRepository;
import com.github.hsanchezlandaverde.testman.utils.Mapper;

@Service
public class ExamService {

	public List<Exam> findAll() {
		return examRepository.findAll();
	}

	public Optional<Exam> findById(UUID id) {
		return examRepository.findById(id);
	}

	public Exam create(ExamDTO dto) throws MissingRequiredFieldsException, InvalidFieldException {
		if (!StringUtils.hasText(dto.getTitle())) {
			throw new MissingRequiredFieldsException(EXAM_TITLE_FIELDNAME);
		}
		if (dto.getMinimumApproval() > 100.0 || dto.getMinimumApproval() <= 0.0) {
			throw new InvalidFieldException(EXAM_MINIMUMAPPROVAL_FIELDNAME, EXAM_MINIMUMAPPROVAL_FORMAT);
		}
		if (dto.getDuration() < 0.0) {
			throw new InvalidFieldException(EXAM_DURATION_FIELDNAME, EXAM_DURATION_FORMAT);
		}
		return examRepository.save(Mapper.examDTOToEntity(dto));
	}

	@Autowired(required = false)
	private IExamRepository examRepository;

	private static final String EXAM_TITLE_FIELDNAME = "title";
	private static final String EXAM_MINIMUMAPPROVAL_FIELDNAME = "minimumApproval";
	private static final String EXAM_MINIMUMAPPROVAL_FORMAT = "100 <= minimumApproval > 0.0";
	private static final String EXAM_DURATION_FIELDNAME = "duration";
	private static final String EXAM_DURATION_FORMAT = "duration >= 0";

}
