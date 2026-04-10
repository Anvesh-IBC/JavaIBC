package com.bitebuddy.iterator;

import com.bitebuddy.domain.MenuItem;

import java.util.Iterator;
import java.util.List;

public class PagedMenuIterator implements Iterator<List<MenuItem>> {
	private final List<MenuItem> data;
	private final int pageSize;
	private int index = 0;

	public PagedMenuIterator(List<MenuItem> data, int pageSize) {
		this.data = data;
		this.pageSize = pageSize;
	}

	public boolean hasNext() {
		return index < data.size();
	}

	public List<MenuItem> next() {
		int to = Math.min(index + pageSize, data.size());
		List<MenuItem> page = data.subList(index, to);
		index = to;
		return page;
	}
}