package com.ecommerce.factory;

public abstract class EntityFactory {
	public abstract Object create(String type, Object... args);
}
