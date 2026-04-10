package com.ibc.training.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibc.training.controller.EmployeeController;
import com.ibc.training.dto.EmployeeRequest;
import com.ibc.training.dto.EmployeeResponse;
import com.ibc.training.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(controllers = EmployeeController.class)
@ActiveProfiles("test")
public class EmployeeControllerTests {

	@Autowired
	MockMvc mvc;
	@Autowired
	ObjectMapper om;

	@MockBean
	EmployeeService employeeService;

	@Test
	void createEmployee_success() throws Exception {
		EmployeeRequest req = new EmployeeRequest();
		req.setName("Alice");
		req.setEmail("alice@example.com");
		req.setSalary(88000.0);
		req.setDepartmentId(1L);
		req.setAddressStreet("MG Road");
		req.setAddressCity("BLR");
		req.setProjectIds(Arrays.asList(10L, 20L));

		EmployeeResponse resp = new EmployeeResponse();
		resp.setId(99L);
		resp.setName("Alice");
		resp.setEmail("alice@example.com");
		resp.setSalary(88000.0);
		resp.setDepartmentId(1L);
		resp.setDepartmentName("Engineering");
		resp.setAddressStreet("MG Road");
		resp.setAddressCity("BLR");
		resp.setProjectIds(Arrays.asList(10L, 20L));
		resp.setProjectNames(Arrays.asList("Project X", "Project Y"));

		// when(employeeService.create(req)).thenReturn(resp);
		when(employeeService.create(any(EmployeeRequest.class))).thenReturn(resp);

		mvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(req)))
				.andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(99)).andExpect(jsonPath("$.departmentName").value("Engineering"))
				.andExpect(jsonPath("$.projectNames[0]").value("Project X"));
	}

	@Test
	void createEmployee_validationFailure() throws Exception {
		EmployeeRequest bad = new EmployeeRequest(); // missing required fields

		mvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bad)))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.error").value("VALIDATION_FAILED"));
	}
}