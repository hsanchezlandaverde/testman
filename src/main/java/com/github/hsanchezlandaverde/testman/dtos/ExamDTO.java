package com.github.hsanchezlandaverde.testman.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamDTO {

	private String id;
	private String title;
	private String signature;
	private double minimumApproval;
	private int duration;

}
