package com.ibc.training.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibc.training.business.bean.EmployeeBean;
import com.ibc.training.entity.EmployeeEntity;

@Repository
@SuppressWarnings("rawtypes")
public class EmployeeDAOWrapper {

	@Autowired
	private EmployeeDAO employeeDAO;

	public List<EmployeeBean> getAllEmployeesBySalary(Double salary) throws Exception {
		List<EmployeeBean> resultRet = new ArrayList<EmployeeBean>();
		try {
			List<EmployeeEntity> listEntity = (List<EmployeeEntity>) employeeDAO.getAllEmployeesBySalary(salary);
			for (EmployeeEntity employeeEntity : listEntity) {
				EmployeeBean bean = convertEmployeeEntityToBean(employeeEntity);
				resultRet.add(bean);
			}
		} catch (Exception ex) {
			throw ex;
		}
		return resultRet;
	}

	public List<EmployeeBean> getAllEmployeesBySalary2(Double salary) throws Exception {
		List<EmployeeBean> resultRet = new ArrayList<EmployeeBean>();
		try {
			List<EmployeeEntity> listEntity = (List<EmployeeEntity>) employeeDAO.getAllEmployeesBySalary(salary);
			for (EmployeeEntity employeeEntity : listEntity) {
				EmployeeBean bean = convertEmployeeEntityToBean(employeeEntity);
				resultRet.add(bean);
			}
		} catch (Exception ex) {
			throw ex;
		}
		return resultRet;
	}

	public List getRolesAndCountOfEmployeesInEachRole() throws Exception {
		List resultRet = null;
		try {
			resultRet = employeeDAO.getRolesAndCountOfEmployeesInEachRole();
		} catch (Exception ex) {
			throw ex;
		}
		return resultRet;
	}

	public static EmployeeBean convertEmployeeEntityToBean(EmployeeEntity entity) {
		EmployeeBean employee = new EmployeeBean();
		BeanUtils.copyProperties(entity, employee);
		return employee;
	}

	public static EmployeeEntity convertEmployeeBeanToEntity(EmployeeBean bean) {
		EmployeeEntity employeeEntityBean = new EmployeeEntity();
		BeanUtils.copyProperties(bean, employeeEntityBean);
		return employeeEntityBean;
	}

}
