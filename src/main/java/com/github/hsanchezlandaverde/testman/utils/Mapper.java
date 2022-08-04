package com.github.hsanchezlandaverde.testman.utils;

import com.github.hsanchezlandaverde.testman.dtos.ExamDTO;
import com.github.hsanchezlandaverde.testman.entities.Exam;

public final class Mapper {

	public static ExamDTO examEntityToDTO(Exam exam) {
		return ExamDTO.builder()
				.id(exam.getId().toString())
				.title(exam.getTitle())
				.signature(exam.getSignature())
				.minimumApproval(exam.getMinimunApproval())
				.duration(exam.getDuration())
				.build();
	}

}
