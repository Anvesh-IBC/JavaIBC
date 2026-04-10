package com.ibc.foodbox.repo;

import java.util.List;
import com.ibc.foodbox.domain.Restaurant;

public interface RestaurantRepository {
	Restaurant save(Restaurant r);

	Restaurant findById(int id);

	List<Restaurant> findAll();
}