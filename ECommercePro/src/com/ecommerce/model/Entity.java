package com.ecommerce.model;

import java.time.LocalDate;

public abstract class Entity {
	protected final String entityId;
	protected LocalDate createdDate;

	protected Entity(String id) {
		this.entityId = id;
		this.createdDate = LocalDate.now();
	}

	public abstract String getType();

	public String getEntityId() {
		return entityId;
	}
}
