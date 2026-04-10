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

	public EmployeeBean addEmployee(EmployeeBean bean) throws Exception {
		EmployeeBean result = null;
		try {
			EmployeeEntity employeeEntity = convertEmployeeBeanToEntity(bean);
			EmployeeEntity employeeEntity2 = employeeDAO.save(employeeEntity);
			result = convertEmployeeEntityToBean(employeeEntity2);
		} catch (Exception ex) {
			throw ex;
		}
		try {

		} catch (Exception ex) {
			throw ex;
		}
		return result;
	}

	public EmployeeBean getEmployeeDetails(Integer id) throws Exception {
		EmployeeBean result = null;
		try {
			EmployeeEntity entity = employeeDAO.findOne(id);
			if (entity != null) {
				result = convertEmployeeEntityToBean(entity);
			}

		} catch (Exception ex) {
			throw ex;
		}
		return result;
	}

	public List<EmployeeBean> getAllEmployeeDetails() throws Exception {
		List<EmployeeBean> resultRet = new ArrayList<EmployeeBean>();
		try {
			List<EmployeeEntity> listEntity = (List<EmployeeEntity>) employeeDAO.findAll();
			for (EmployeeEntity employeeEntity : listEntity) {
				EmployeeBean bean = convertEmployeeEntityToBean(employeeEntity);
				resultRet.add(bean);
			}
		} catch (Exception ex) {
			throw ex;
		}
		return resultRet;
	}

	public void deleteEmployee(EmployeeBean bean) throws Exception {
		EmployeeEntity employeeEntity = convertEmployeeBeanToEntity(bean);
		employeeDAO.delete(employeeEntity);
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