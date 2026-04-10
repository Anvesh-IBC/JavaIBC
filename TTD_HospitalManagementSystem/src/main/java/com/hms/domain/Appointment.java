package com.hms.domain;

import java.time.LocalDateTime;

public class Appointment {
	private final String id;
	private final Patient patient;
	private final Doctor doctor;
	private final LocalDateTime slot;
	private AppointmentStatus status = AppointmentStatus.SCHEDULED;
	private Prescription prescription;

	private Appointment(Builder b) {
		this.id = b.id;
		this.patient = b.patient;
		this.doctor = b.doctor;
		this.slot = b.slot;
		this.patient.addAppointment(this);
	}

	public static class Builder {
		private String id;
		private Patient patient;
		private Doctor doctor;
		private LocalDateTime slot;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder patient(Patient p) {
			this.patient = p;
			return this;
		}

		public Builder doctor(Doctor d) {
			this.doctor = d;
			return this;
		}

		public Builder slot(LocalDateTime s) {
			this.slot = s;
			return this;
		}

		public Appointment build() {
			if (id == null || patient == null || doctor == null || slot == null)
				throw new IllegalStateException("Missing fields");
			return new Appointment(this);
		}
	}

	public String getId() {
		return id;
	}

	public Patient getPatient() {
		return patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public LocalDateTime getSlot() {
		return slot;
	}

	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus s) {
		this.status = s;
	}

	public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription p) {
		this.prescription = p;
	}

//	@Override
//	public String toString() {
//		return "Appointment [id=" + id + ", patient=" + patient + ", doctor=" + doctor + ", slot=" + slot + ", status="
//				+ status + ", prescription=" + prescription + "]";
//	}

	@Override
	public String toString() {
		String patientId = (patient == null ? null : patient.getId());
		String doctorId = (doctor == null ? null : doctor.getId());
		String prescriptionSummary = (prescription == null ? "null" : "present");
		return "Appointment{id=" + id + ", patientId=" + patientId + ", doctorId=" + doctorId + ", slot=" + slot
				+ ", status=" + status + ", prescription=" + prescriptionSummary + '}';
	}

}
