package com.hms.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AppointmentDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	public String apptId, patientId, doctorId;
	public LocalDateTime slot;
}