package com.hms.business.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.hms.business.HMSFacade;
import com.hms.domain.RoomBed;
import com.hms.service.AppointmentService;
import com.hms.service.BedService;

class HMSFacadeTest {

	@Test
	void covers_complete_allocate_and_free() throws Exception {
		AppointmentService apptSvc = mock(AppointmentService.class);
		BedService bedSvc = new BedService(Arrays.asList(new RoomBed("101", "A")));
		HMSFacade facade = new HMSFacade(apptSvc, bedSvc);

		// call complete to cover that facade method
		facade.complete("A-1");
		verify(apptSvc, times(1)).complete("A-1");

		// also allocate/free to exercise facade pass-throughs (already covered by Main,
		// but harmless)
		RoomBed bed = facade.allocateBed();
		facade.freeBed(bed);
	}
}
