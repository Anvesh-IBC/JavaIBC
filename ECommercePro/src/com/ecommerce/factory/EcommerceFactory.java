package com.ecommerce.factory;

import com.ecommerce.enums.Category;

import com.ecommerce.model.*;

public class EcommerceFactory extends EntityFactory {
	public Object create(String type, Object... args) {
		if ("Customer".equals(type))
			return new Customer((String) args[0]);
		if ("Product".equals(type))
			return new Product((String) args[0], (Category) args[1], (Double) args[2], (Integer) args[3]);
		return null;
	}
}
