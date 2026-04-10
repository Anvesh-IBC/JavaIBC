package com.ibc.training.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ibc.training.bean.EmployeeBean;
import com.ibc.training.entity.EmployeeEntity;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public static EmployeeBean convertEntityToBean(EmployeeEntity entity) {
		EmployeeBean employee = new EmployeeBean();
		BeanUtils.copyProperties(entity, employee);
		return employee;
	}

	public static EmployeeEntity convertBeanToEntity(EmployeeBean bean) {
		EmployeeEntity employeeEntityBean = new EmployeeEntity();
		BeanUtils.copyProperties(bean, employeeEntityBean);
		return employeeEntityBean;
	}

	@Override
	public Integer addEmployee(EmployeeBean employeeBean) throws Exception {
		Integer employeeId = 0;
		EmployeeEntity employeeEntityBean = convertBeanToEntity(employeeBean);
		Session session = sessionFactory.getCurrentSession();
		session.save(employeeEntityBean);
		employeeId = employeeEntityBean.getId();
		return employeeId;
	}

	@Override
	public EmployeeBean getEmployeeDetails(Integer id) throws Exception {
		EmployeeBean employeeBean = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			EmployeeEntity employeeEntity = (EmployeeEntity) session.get(EmployeeEntity.class, id);
			if (employeeEntity != null) {
				employeeBean = convertEntityToBean(employeeEntity);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null)
				session.close();
		}
		return employeeBean;
	}

	@Override
	public EmployeeBean updateEmployeeDetails(EmployeeBean employeeBean) throws Exception {
		EmployeeBean updatedBean = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			EmployeeEntity employeeEntity = (EmployeeEntity) session.get(EmployeeEntity.class, employeeBean.getId());
			if (employeeEntity != null) {
				employeeEntity.setInsertTime(employeeBean.getInsertTime());
				employeeEntity.setName(employeeBean.getName());
				employeeEntity.setRole(employeeBean.getRole());
				employeeEntity.setSalary(employeeBean.getSalary());

				session.update(employeeEntity);
				tx.commit();

				updatedBean = convertEntityToBean(employeeEntity);
			}
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			if (session != null)
				session.close();
		}
		return updatedBean;
	}

	@Override
	public EmployeeBean deleteEmployeeDetails(Integer id) throws Exception {
		EmployeeBean deleteBean = null;
		Session session = null;
		Transaction tx = null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();

			EmployeeEntity employeeEntity = (EmployeeEntity) session.get(EmployeeEntity.class, id);
			if (employeeEntity != null) {
				session.delete(employeeEntity);
				tx.commit();

				deleteBean = convertEntityToBean(employeeEntity);
			}
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			if (session != null)
				session.close();
		}
		return deleteBean;
	}

	@Override
	public List<EmployeeBean> getEmployeeList() throws Exception {
		List<EmployeeBean> listEmployeeBean = new ArrayList<>();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			List<EmployeeEntity> listEmployeeEntity = (List<EmployeeEntity>) session.createQuery("From EmployeeEntity")
					.list();

			for (EmployeeEntity entity : listEmployeeEntity) {
				EmployeeBean bean = convertEntityToBean(entity);
				listEmployeeBean.add(bean);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (session != null)
				session.close();
		}
		return listEmployeeBean;
	}
}
