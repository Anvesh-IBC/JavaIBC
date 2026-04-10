package com.ibc.training.utility;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			// Loads settings from hibernate.cfg.xml by default
			Configuration cfg = new Configuration().configure("hibernate.cfg.xml");

			// If you are not listing mappings in hibernate.cfg.xml,
			// you can add annotated classes programmatically:
			 cfg.addAnnotatedClass(com.ibc.training.entity.EmployeeEntity.class);
			 cfg.addAnnotatedClass(com.ibc.training.entity.DepartmentEntity.class);

			return cfg.buildSessionFactory();

		} catch (Throwable ex) {
			System.err.println("Initialization of SessionFactory failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {
		if (sessionFactory != null && !sessionFactory.isClosed()) {
			sessionFactory.close();
		}
	}
}
