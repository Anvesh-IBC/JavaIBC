package com.ibc.training.test;

import java.util.Collection;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ibc.training.business.bean.EmployeeBean;
import com.ibc.training.resource.JavaConfig;
import com.ibc.training.service.EmployeeServiceImpl;

//Following Annotation is used to tell that SpringJunit is used to run the tests 
@RunWith(SpringJUnit4ClassRunner.class)

// Following is  @ContextConfiguration annotation
// it is used to point to the files having the configuration and helps to load and start the context
// Context will be cached for all test cases and classes
// Same cached Context is used for all test cases and test classes
// it is not reloaded

@ContextConfiguration(classes = JavaConfig.class)

// Following Annotation is used to run each test case in a individual Transaction
// with default strategy as rollback, as service layer is hitting DB layer
// so changes done to database must be undone
//@Rollback(false) can be used at the class level/method level to override the same

@Transactional
public class EmployeeServiceTest {
	@Autowired
	private EmployeeServiceImpl empServiceIMPL;

	@Before
	public void testMethodBeforeInterceptor() {
		System.out.println("Starting a new Test Method..");
	}

	@After
	public void testMethodAfterInterceptor() {
		System.out.println("End the Test Method..");
	}

	@Test
	public void testFindAll() throws Exception {
		Collection<EmployeeBean> empList = empServiceIMPL.getAllEmployeeDetails();
		Assert.assertTrue(empList != null);
	}

	@Test
	public void testSaveValid() throws Exception {
		EmployeeBean bean = new EmployeeBean("MSD", "TL", new Date(), 2345.0);
		EmployeeBean bean2 = empServiceIMPL.addEmployee(bean);
		Assert.assertTrue(bean2 != null);
	}

	@Test
	public void testFindByIdValid() throws Exception {
		EmployeeBean emp = empServiceIMPL.getEmployeeDetails(1001);
		Assert.assertTrue(emp != null);
	}

	@Test
	public void testFindByIdInValid() throws Exception {
		EmployeeBean emp = empServiceIMPL.getEmployeeDetails(5122);
		Assert.assertTrue(emp == null);
	}
}

//Remember for all the test cases Spring context will be loaded only once
//to reload the context you have to use dirties context annotation
//refer Spring-Test module documentation
//https://docs.spring.io/spring/docs/4.2.4.RELEASE/spring-framework-reference/html/integration-testing.html