package com.github.hsanchezlandaverde.testman.utils;

import com.github.hsanchezlandaverde.testman.dtos.ExamDTO;
import com.github.hsanchezlandaverde.testman.entities.Exam;

public final class Mapper {

	public static ExamDTO examEntityToDTO(Exam entity) {
		return ExamDTO.builder()
				.id(entity.getId().toString())
				.title(entity.getTitle())
				.signature(entity.getSignature())
				.minimumApproval(entity.getMinimumApproval())
				.duration(entity.getDuration())
				.build();
	}
	
	public static Exam examDTOToEntity(ExamDTO dto) {
		return Exam.builder()
				.title(dto.getTitle())
				.signature(dto.getSignature())
				.minimumApproval(dto.getMinimumApproval())
				.duration(dto.getDuration())
				.build();
	}

}
