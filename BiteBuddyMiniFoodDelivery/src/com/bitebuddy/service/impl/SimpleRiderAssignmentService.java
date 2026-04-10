package com.bitebuddy.service.impl;

import com.bitebuddy.domain.Rider;
import com.bitebuddy.service.RiderAssignmentService;

import java.util.Comparator;
import java.util.PriorityQueue;

public class SimpleRiderAssignmentService implements RiderAssignmentService {
	private final PriorityQueue<Rider> pq;

	public SimpleRiderAssignmentService() {
		this.pq = new PriorityQueue<Rider>(11, new Comparator<Rider>() {
			public int compare(Rider a, Rider b) {
				return Integer.compare(a.getCurrentLoad(), b.getCurrentLoad());
			}
		});
		// Seed a few riders
		pq.add(new Rider(1L, "Ravi", 0, "BTM"));
		pq.add(new Rider(2L, "Lakshmi", 1, "Jayanagar"));
		pq.add(new Rider(3L, "Arun", 0, "HSR"));
	}

	public Rider assignRider() {
		Rider r = pq.poll();
		if (r == null)
			return null;
		r.setCurrentLoad(r.getCurrentLoad() + 1);
		return r;
	}

	public void releaseRider(Rider rider) {
		if (rider == null)
			return;
		rider.setCurrentLoad(Math.max(0, rider.getCurrentLoad() - 1));
		pq.add(rider);
	}
}
