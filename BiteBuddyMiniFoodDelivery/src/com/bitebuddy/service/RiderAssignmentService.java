package com.bitebuddy.service;

import com.bitebuddy.domain.Rider;

public interface RiderAssignmentService {
	Rider assignRider();

	void releaseRider(Rider rider);
}
