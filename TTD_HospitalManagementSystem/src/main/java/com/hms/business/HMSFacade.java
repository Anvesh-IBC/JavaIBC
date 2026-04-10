package com.hms.business;

import com.hms.service.*;
import com.hms.domain.*;
import com.hms.exceptions.*;

public class HMSFacade {
	private final AppointmentService apptService;
	private final BedService bedService;

	public HMSFacade(AppointmentService a, BedService b) {
		this.apptService = a;
		this.bedService = b;
	}

	public Appointment schedule(Appointment a) throws ValidationException, OverbookingException {
		return apptService.schedule(a);
	}

	public void complete(String apptId) {
		apptService.complete(apptId);
	}

	public RoomBed allocateBed() throws InterruptedException {
		return bedService.allocate();
	}

	public void freeBed(RoomBed b) {
		bedService.free(b);
	}
}
