package com.ibc.training.service;

import com.ibc.training.dto.AddressRequest;
import com.ibc.training.entity.Address;

public interface AddressService {
	Address create(AddressRequest request);

	Address update(Long id, AddressRequest request);

	void delete(Long id);

	Address get(Long id);
}
