package com.ibc.training.dao;

import org.springframework.data.repository.CrudRepository;

import com.ibc.training.entity.EmployeeEntityBean;

public interface EmployeeDAO extends CrudRepository<EmployeeEntityBean, Integer> {

}