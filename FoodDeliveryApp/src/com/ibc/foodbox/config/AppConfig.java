package com.ibc.foodbox.config;

import com.ibc.foodbox.pricing.DeliveryPricingStrategy;
import com.ibc.foodbox.pricing.FlatFeeStrategy;
import com.ibc.foodbox.repo.CustomerRepository;
import com.ibc.foodbox.repo.OrderRepository;
import com.ibc.foodbox.repo.RestaurantRepository;
import com.ibc.foodbox.repo.impl.InMemoryCustomerRepo;
import com.ibc.foodbox.repo.impl.InMemoryOrderRepo;
import com.ibc.foodbox.repo.impl.InMemoryRestaurantRepo;

public class AppConfig {
	private static AppConfig INSTANCE;

	private CustomerRepository customerRepo;
	private RestaurantRepository restaurantRepo;
	private OrderRepository orderRepo;

	private DeliveryPricingStrategy pricingStrategy;

	private AppConfig() {
		this.customerRepo = new InMemoryCustomerRepo();
		this.restaurantRepo = new InMemoryRestaurantRepo();
		this.orderRepo = new InMemoryOrderRepo();
		this.pricingStrategy = new FlatFeeStrategy(30.0); // default flat fee
	}

	public static synchronized AppConfig getInstance() {
		if (INSTANCE == null)
			INSTANCE = new AppConfig();
		return INSTANCE;
	}

	public CustomerRepository customers() {
		return customerRepo;
	}

	public RestaurantRepository restaurants() {
		return restaurantRepo;
	}

	public OrderRepository orders() {
		return orderRepo;
	}

	public DeliveryPricingStrategy pricingStrategy() {
		return pricingStrategy;
	}

	public void setPricingStrategy(DeliveryPricingStrategy ps) {
		this.pricingStrategy = ps;
	}
}