package com.bitebuddy.service;

import com.bitebuddy.domain.MenuItem;
import java.util.List;
import java.util.Optional;

public interface MenuService {
	List<MenuItem> getAllItems();

	Optional<MenuItem> findById(int id);

	List<MenuItem> findByName(String query);

	List<MenuItem> findVeg(boolean veg);
}
