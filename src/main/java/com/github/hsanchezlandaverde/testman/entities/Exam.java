package com.github.hsanchezlandaverde.testman.entities;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Exam {

	private UUID id;
	private String title;
	private String signature;
	private double minimumApproval;
	private int duration;

}
