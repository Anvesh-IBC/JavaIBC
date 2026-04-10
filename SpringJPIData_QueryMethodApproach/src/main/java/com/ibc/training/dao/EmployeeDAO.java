package com.ibc.training.dao;

import java.util.List;

import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.transaction.annotation.Transactional;

import com.ibc.training.entity.EmployeeEntity;

@RepositoryDefinition(idClass = Integer.class, domainClass = EmployeeEntity.class)
@Transactional(value = "txManager")
// name of TransactionManger can be any thing
public interface EmployeeDAO {
	List<EmployeeEntity> findBySalaryGreaterThanEqual(double salary);

	List<EmployeeEntity> findBySalaryGreaterThanEqualOrderByIdDesc(double salary);

	List<EmployeeEntity> findByNameContainingOrderBySalaryDesc(String pattern);

	List<EmployeeEntity> findBySalaryGreaterThanEqualAndRole(Double param1, String param2);

	List<EmployeeEntity> findBySalaryBetween(Double param1, Double param2);

}
//name of the method is translated into query