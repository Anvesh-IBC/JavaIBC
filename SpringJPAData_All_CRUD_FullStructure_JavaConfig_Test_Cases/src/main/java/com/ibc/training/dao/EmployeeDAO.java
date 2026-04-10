package com.ibc.training.dao;

import org.springframework.data.repository.CrudRepository;

import com.ibc.training.entity.EmployeeEntity;

public interface EmployeeDAO extends CrudRepository<EmployeeEntity, Integer> {

}
