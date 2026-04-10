package com.ibc.training.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibc.training.controller.AddressController;
import com.ibc.training.dto.AddressRequest;
import com.ibc.training.entity.Address;
import com.ibc.training.exception.ResourceNotFoundException;
import com.ibc.training.service.AddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(controllers = AddressController.class)
@ActiveProfiles("test")
public class AddressControllerTests {

	@Autowired
	MockMvc mvc;
	@Autowired
	ObjectMapper om;

	@MockBean
	AddressService addressService;

	@Test
	void createAddress_employeeNotFound_404() throws Exception {
		AddressRequest req = new AddressRequest();
		req.setStreet("HSR");
		req.setCity("BLR");
		req.setEmployeeId(999L);

		// when(addressService.create(req)).thenThrow(new
		// ResourceNotFoundException("Employee not found: 999"));
		when(addressService.create(any(AddressRequest.class)))
				.thenThrow(new ResourceNotFoundException("Employee not found: 999"));

		mvc.perform(post("/api/addresses").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(req)))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.error").value("NOT_FOUND"));
	}

	@Test
	void createAddress_success() throws Exception {
		AddressRequest req = new AddressRequest();
		req.setStreet("HSR");
		req.setCity("BLR");
		req.setEmployeeId(1L);

		Address a = new Address("HSR", "BLR");
		a.setId(5L);

		// when(addressService.create(req)).thenReturn(a);
		when(addressService.create(any(AddressRequest.class))).thenReturn(a);

		mvc.perform(post("/api/addresses").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(req)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.id").value(5))
				.andExpect(jsonPath("$.street").value("HSR"));
	}
}