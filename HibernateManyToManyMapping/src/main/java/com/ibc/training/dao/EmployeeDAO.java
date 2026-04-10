package com.ibc.training.dao;

import com.ibc.training.bean.EmployeeBean;
import com.ibc.training.bean.MeetingBean;

public interface EmployeeDAO {
	public Integer insertEmployeeAndMeetings(EmployeeBean employeeBean1, EmployeeBean employeeBean2, MeetingBean m1,
			MeetingBean m2) throws Exception;

	public void deletingEmployeeAndMeeting(EmployeeBean employeeBean1) throws Exception;
}