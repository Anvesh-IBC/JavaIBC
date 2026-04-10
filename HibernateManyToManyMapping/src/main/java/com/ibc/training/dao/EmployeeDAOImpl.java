package com.ibc.training.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ibc.training.bean.EmployeeBean;
import com.ibc.training.bean.MeetingBean;
import com.ibc.training.entity.EmployeeEntity;
import com.ibc.training.entity.MeetingEntity;
import com.ibc.training.utility.HibernateUtil;

public class EmployeeDAOImpl implements EmployeeDAO {
	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@Override

	public Integer insertEmployeeAndMeetings(EmployeeBean employeeBean1, EmployeeBean employeeBean2, MeetingBean m1,
			MeetingBean m2) throws Exception {

		Session session = null;

		Transaction transaction = null;

		Integer employeeId = 0;

		try {

			session = sessionFactory.openSession();

			transaction = session.beginTransaction();

			MeetingEntity meetingSlot1 = convertMeetingBeanToEntity(m1);

			MeetingEntity meetingSlot2 = convertMeetingBeanToEntity(m2);

			// List of Meetings for the Employees

			List<MeetingEntity> meetings = new ArrayList<>();

			meetings.add(meetingSlot1);

			meetings.add(meetingSlot2);

			EmployeeEntity employeeEntity1 = convertEmployeeBeanToEntity(employeeBean1);

			EmployeeEntity employeeEntity2 = convertEmployeeBeanToEntity(employeeBean2);

			// Employee1 and Employee2 sharing the same list of meetings

			employeeEntity1.setMeetings(meetings);

			employeeEntity2.setMeetings(meetings);

			// Persist employees (cascade all will persist meetings)

			session.persist(employeeEntity1);

			session.persist(employeeEntity2);

			transaction.commit();

			employeeId = employeeEntity1.getEmployeeId();

		} catch (Exception exception) {

			if (transaction != null)
				transaction.rollback();

			throw exception;

		} finally {

			if (session != null)
				session.close();

		}

		return employeeId;

	}

	@Override

	public void deletingEmployeeAndMeeting(EmployeeBean employeeBean1) throws Exception {

		Session session = null;

		Transaction transaction = null;

		try {

			session = sessionFactory.openSession();

			EmployeeEntity employeeEntity = (EmployeeEntity) session.get(EmployeeEntity.class,
					employeeBean1.getEmployeeId());

			if (employeeEntity != null) {

				transaction = session.beginTransaction();

				session.delete(employeeEntity);

				transaction.commit();

			}

		} catch (Exception exception) {

			if (transaction != null)
				transaction.rollback();

			throw exception;

		} finally {

			if (session != null)
				session.close();

		}

	}

	public static MeetingEntity convertMeetingBeanToEntity(MeetingBean meetingBean) {

		MeetingEntity meetingEntity = new MeetingEntity();

		meetingEntity.setTimeSlot(meetingBean.getTimeSlot());

		return meetingEntity;

	}

	public static EmployeeEntity convertEmployeeBeanToEntity(EmployeeBean employeeBean) {

		EmployeeEntity e1 = new EmployeeEntity();

		e1.setEmployeeName(employeeBean.getEmployeeName());

		e1.setRole(employeeBean.getRole());

		return e1;

	}

}