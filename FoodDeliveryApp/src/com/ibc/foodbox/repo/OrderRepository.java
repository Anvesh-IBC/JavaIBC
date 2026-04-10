package com.ibc.foodbox.repo;

import java.util.List;
import com.ibc.foodbox.domain.Order;

public interface OrderRepository {
	Order save(Order o);

	Order findById(int id);

	List<Order> findAll();
}