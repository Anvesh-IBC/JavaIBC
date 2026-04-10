package com.ibc.training.iterator;

import com.ibc.training.domain.Course;

import java.util.Iterator;

public class CourseCatalogIterator implements Iterator<Course> {
	private final Iterator<Course> inner;

	public CourseCatalogIterator(CourseCatalog catalog) {
		this.inner = catalog.asList().iterator();
	}

	@Override
	public boolean hasNext() {
		return inner.hasNext();
	}

	@Override
	public Course next() {
		return inner.next();
	}

	@Override
	public void remove() {
		inner.remove();
	}
}