package com.hms.cli;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Currency;

import com.hms.business.HMSFacade;
import com.hms.concurrency.CheckInQueue;
import com.hms.concurrency.DoctorWorker;
import com.hms.dao.AppointmentDAO;
import com.hms.dao.DoctorDAO;
import com.hms.dao.InMemoryAppointmentDAO;
import com.hms.dao.InMemoryDoctorDAO;
import com.hms.dao.InMemoryPatientDAO;
import com.hms.dao.PatientDAO;
import com.hms.domain.Appointment;
import com.hms.domain.Doctor;
import com.hms.domain.Money;
import com.hms.domain.Patient;
import com.hms.domain.RoomBed;
import com.hms.domain.Specialization;
import com.hms.exceptions.OverbookingException;
import com.hms.exceptions.ValidationException;
import com.hms.patterns.factory.NotificationFactory;
import com.hms.patterns.factory.Notifier;
import com.hms.patterns.observer.AppointmentEventBus;
import com.hms.patterns.observer.AppointmentListener;
import com.hms.patterns.strategy.OPDConsultationBilling;
import com.hms.patterns.strategy.WardBilling;
import com.hms.reports.DailyCensusReport;
import com.hms.service.AppointmentService;
import com.hms.service.BedService;
import com.hms.service.BillingService;

public class Main {
	public static void main(String[] args) throws Exception {
		// DAOs
		PatientDAO pdao = new InMemoryPatientDAO();
		DoctorDAO ddao = new InMemoryDoctorDAO();
		AppointmentDAO adao = new InMemoryAppointmentDAO();

		// seed data
		Patient p = new Patient("P-1001", "Arun");
		pdao.save(p);
		Doctor d = new Doctor("D-11", "Dr. Meera");
		d.addSpecialization(Specialization.CARDIOLOGY);
		ddao.save(d);

		// Service & Facade
		AppointmentService apptService = new AppointmentService(adao, ddao, pdao);
		BedService bedService = new BedService(Arrays.asList(new RoomBed("101", "A"), new RoomBed("101", "B")));
		HMSFacade facade = new HMSFacade(apptService, bedService);

		// Observer + Notifier
		AppointmentEventBus bus = new AppointmentEventBus();
		Notifier notifier = NotificationFactory.of("SMS");
		bus.register(new AppointmentListener() {
			public void onScheduled(Appointment a) {
				notifier.notify("+910000000000", "Appt scheduled: " + a.getId());

			}

			public void onCompleted(Appointment a) {
				System.out.println("Appointment completed: " + a.getId());
			}
		});

		// Schedule appointment
		Appointment appt = new Appointment.Builder().id("A-1").patient(p).doctor(d)
				.slot(LocalDateTime.now().plusMinutes(30)).build();
		System.out.println("Appointment" + appt);

		try {
			facade.schedule(appt);
			bus.fireScheduled(appt);
			System.out.println("Scheduled: " + appt.getId() + " with " + d.getName() + " at " + appt.getSlot());
		} catch (ValidationException | OverbookingException e) {
			System.out.println("Failed" + e.getMessage());
		}

		// Strategy demo
		BillingService billing = new BillingService(new OPDConsultationBilling());
		Money base = new Money(new BigDecimal("500.00"), Currency.getInstance("INR"));
		System.out.println("OPD Bill: " + billing.compute(base, 1));
		billing.setStrategy(new WardBilling());
		System.out.println("Ward(3 days) Bill: "
				+ billing.compute(new Money(new BigDecimal("2000.00"), Currency.getInstance("INR")), 3));

		// Report
		new DailyCensusReport(pdao).generate(new File("reports/daily.txt"));
		System.out.println("Generated reports/daily.txt");

		// Concurrency demo (Producer-Consumer)
		CheckInQueue q = new CheckInQueue();
		Thread doctorThread = new Thread(new DoctorWorker(q, bus), "Doctor-Worker");
		doctorThread.start();
		q.submit(appt);
		Thread.sleep(400);
		doctorThread.interrupt();

		// Bed allocation demo
		RoomBed bed = facade.allocateBed();
		System.out.println("Allocated bed: " + bed);
		facade.freeBed(bed);
		System.out.println("Freed bed: " + bed);

		System.out.println("HMS demo completed.");

	}
}
