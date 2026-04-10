package com.example.demos;

import java.util.Objects;

class CourseEntity {
	private final int id;
	private final String title;
	private final Integer duration; // wrapper for demo (nullable allowed)

	CourseEntity(int id, String title, Integer duration) {
		this.id = id;
		this.title = title;
		this.duration = duration;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Integer getDuration() {
		return duration;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof CourseEntity))
			return false;
		CourseEntity other = (CourseEntity) o;
		return id == other.id && Objects.equals(title, other.title) && Objects.equals(duration, other.duration);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, duration);
	}

	@Override public String toString() {
        return "CourseEntity{" + "id=" + id + ", title='" + title + ", duration=" + duration + '}';
    }
}
