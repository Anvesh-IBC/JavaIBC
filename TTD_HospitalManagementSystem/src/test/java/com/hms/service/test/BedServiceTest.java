package com.hms.service.test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.hms.domain.RoomBed;
import com.hms.service.BedService;

class BedServiceTest {

	@Test
	void allocateAndFree_withNotifyAll_wakesWaiters() throws Exception {
		BedService svc = new BedService(Arrays.asList(new RoomBed("101", "A"), new RoomBed("101", "B")));

		// First two allocations should be immediate
		RoomBed b1 = svc.allocate();
		RoomBed b2 = svc.allocate();
		assertNotNull(b1);
		assertNotNull(b2);
		assertNotEquals(b1.toString(), b2.toString());

		// Third allocation will block until a bed is freed
		ExecutorService pool = Executors.newSingleThreadExecutor();
		Future<RoomBed> waiter = pool.submit(() -> {
			return svc.allocate(); // should wait
		});

		// Let it block for a bit, then free one
		Thread.sleep(100);
		svc.free(b1);

		RoomBed b3 = waiter.get(1, TimeUnit.SECONDS);
		assertNotNull(b3);
		svc.free(b2);
		svc.free(b3);
		pool.shutdownNow();
	}
}
