package com.ibc.training.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibc.training.business.bean.EmployeeBean;
import com.ibc.training.entity.EmployeeEntity;

@Repository
public class EmployeeDAOWrapper {

	@Autowired
	private EmployeeDAO employeeDAO;

	public List<EmployeeBean> findAllEmployeesBySalaryGreaterThanEqual(double salary) throws Exception {
		List<EmployeeBean> resultRet = new ArrayList<EmployeeBean>();
		try {
			List<EmployeeEntity> listEntity = (List<EmployeeEntity>) employeeDAO.findBySalaryGreaterThanEqual(salary);
			for (EmployeeEntity employeeEntity : listEntity) {
				EmployeeBean bean = convertEmployeeEntityToBean(employeeEntity);
				resultRet.add(bean);
			}
		} catch (Exception ex) {
			throw ex;
		}
		return resultRet;
	}

	public List<EmployeeBean> findAllEmployeesBySalaryGreaterThanEqualOrderByIdDesc(double salary) throws Exception {
		List<EmployeeBean> resultRet = new ArrayList<EmployeeBean>();
		try {
			List<EmployeeEntity> listEntity = (List<EmployeeEntity>) employeeDAO
					.findBySalaryGreaterThanEqualOrderByIdDesc(salary);
			for (EmployeeEntity employeeEntity : listEntity) {
				EmployeeBean bean = convertEmployeeEntityToBean(employeeEntity);
				resultRet.add(bean);
			}
		} catch (Exception ex) {
			throw ex;
		}
		return resultRet;
	}

	public List<EmployeeBean> findAllEmployeesByNameContainingOrderBySalaryDesc(String pattern) throws Exception {
		List<EmployeeBean> resultRet = new ArrayList<EmployeeBean>();
		try {
			List<EmployeeEntity> listEntity = (List<EmployeeEntity>) employeeDAO
					.findByNameContainingOrderBySalaryDesc(pattern);
			for (EmployeeEntity employeeEntity : listEntity) {
				EmployeeBean bean = convertEmployeeEntityToBean(employeeEntity);
				resultRet.add(bean);
			}
		} catch (Exception ex) {
			throw ex;
		}
		return resultRet;
	}

	public List<EmployeeBean> findAllEmployeesBySalaryGreaterThanEqualAndRole(Double param1, String param2)
			throws Exception {
		List<EmployeeBean> resultRet = new ArrayList<EmployeeBean>();
		try {
			List<EmployeeEntity> listEntity = (List<EmployeeEntity>) employeeDAO
					.findBySalaryGreaterThanEqualAndRole(param1, param2);
			for (EmployeeEntity employeeEntity : listEntity) {
				EmployeeBean bean = convertEmployeeEntityToBean(employeeEntity);
				resultRet.add(bean);
			}
		} catch (Exception ex) {
			throw ex;
		}
		return resultRet;
	}

	public List<EmployeeBean> findAllEmployeesBySalaryBetween(Double param1, Double param2) throws Exception {
		List<EmployeeBean> resultRet = new ArrayList<EmployeeBean>();
		try {
			List<EmployeeEntity> listEntity = (List<EmployeeEntity>) employeeDAO.findBySalaryBetween(param1, param2);
			for (EmployeeEntity employeeEntity : listEntity) {
				EmployeeBean bean = convertEmployeeEntityToBean(employeeEntity);
				resultRet.add(bean);
			}
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
