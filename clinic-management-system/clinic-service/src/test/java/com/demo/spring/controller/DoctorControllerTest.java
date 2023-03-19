package com.demo.spring.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.entity.Doctor;
import com.demo.spring.repository.DoctorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DoctorControllerTest {

	@Autowired
	MockMvc mvc;

	@Autowired
	TestRestTemplate testRestTemplate;
	
	@MockBean
	DoctorRepository doctorRepository;
	
	@Test
	void testListAllDoctorsSuccess() throws Exception {
		Doctor doctor=new Doctor(1,"vishwa","rh","vishwarh@gmail.com");
		ObjectMapper mapper=new ObjectMapper();
		List<Doctor> listDoctors=new ArrayList<>();
		listDoctors.add(doctor);
		String doctorJson=mapper.writeValueAsString(listDoctors);
		
		when(doctorRepository.findAll()).thenReturn(listDoctors);
		
		mvc.perform(get("/clinic/doctor/list")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json(doctorJson));
	}
	
	@Test
	void testListAllDoctorsFailure() throws Exception {
		when(doctorRepository.findAll()).thenReturn(new ArrayList<>());
		
		mvc.perform(get("/clinic/doctor/list")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Doctor not found"));
	}
	@Test
	void testFindDoctorByIdSuccess() throws Exception {
		Doctor doctor=new Doctor(1,"vishwa","rh","vishwarh@gmail.com");
		ObjectMapper mapper=new ObjectMapper();
		String doctorJson=mapper.writeValueAsString(doctor);
		
		when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
		
		mvc.perform(get("/clinic/doctor/find/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json(doctorJson));
	}
	@Test
	void testFindDoctorByIdFailure() throws Exception {

		when(doctorRepository.findById(1)).thenReturn(Optional.empty());
		
		mvc.perform(get("/clinic/doctor/find/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Doctor not found"));
	}

}
