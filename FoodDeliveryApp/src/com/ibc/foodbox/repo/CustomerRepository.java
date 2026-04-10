package com.ibc.foodbox.repo;

import java.util.List;
import com.ibc.foodbox.domain.Customer;

public interface CustomerRepository {
	Customer save(Customer c);

	Customer findById(int id);

	List<Customer> findAll();
}