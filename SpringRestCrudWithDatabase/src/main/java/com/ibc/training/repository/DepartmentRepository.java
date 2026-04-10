package com.ibc.training.repository;

import com.ibc.training.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
	Department findByName(String name);
}