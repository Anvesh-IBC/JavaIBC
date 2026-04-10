package com.ibc.training.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ibc.training.businessbean.AssetBean;
import com.ibc.training.businessbean.EmployeeBean;
import com.ibc.training.entity.AssetEntity;
import com.ibc.training.entity.EmployeeEntity;
import com.ibc.training.utility.HibernateUtil;

public class EmployeeDAOImpl implements EmployeeDAO {

	@Override
	public Integer insertAssetWithEmployee(EmployeeBean employeeBean, AssetBean assetBean) throws Exception {
		Session session = null;
		Transaction tx = null;
		Integer employeeId = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tx = session.beginTransaction();

			// Map bean -> entity
			AssetEntity assetEntity = new AssetEntity();
			assetEntity.setAssetName(assetBean.getAssetName());
			assetEntity.setAssetBrandName(assetBean.getAssetBrandName());
			assetEntity.setValidityYears(assetBean.getValidityYears());

			EmployeeEntity employeeEntity = new EmployeeEntity();
			employeeEntity.setEmployeeName(employeeBean.getEmployeeName());
			employeeEntity.setRole(employeeBean.getRole());
			employeeEntity.setSalary(employeeBean.getSalary());
			employeeEntity.setInsertTime(employeeBean.getInsertTime());

			// Associate employee -> asset (FK assetId_fk on employee table)
			employeeEntity.setAssetEntity(assetEntity);

			// Persist both (no cascade on mapping)
			session.persist(assetEntity);
			session.persist(employeeEntity);

			tx.commit();
			employeeId = employeeEntity.getEmployeeId();
		} catch (Exception e) {
			if (tx != null) {
				try {
					tx.rollback();
				} catch (Exception ignore) {
				}
			}
			throw e; // rethrow so caller can handle/log
		} finally {
			if (session != null) {
				try {
					session.close();
				} catch (Exception ignore) {
				}
			}
		}

		return employeeId;
	}
}