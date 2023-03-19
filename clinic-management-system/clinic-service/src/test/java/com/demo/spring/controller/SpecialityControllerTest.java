package com.demo.spring.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.demo.spring.entity.Speciality;
import com.demo.spring.repository.SpecialityRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SpecialityControllerTest {

	@Autowired
	MockMvc mvc;

	@Autowired
	TestRestTemplate testRestTemplate;

	@MockBean
	SpecialityRepository specialityRepository;
	
	@Test
	void testListAllDoctorsOfSpecialitySuccess() throws Exception {
		Speciality speciality=new Speciality(1,"nurology");
		Doctor doctor =new Doctor(1,"vishwa","rh","vishrh@gmail.com");
		
		
		Set<Doctor> setDoctor=new HashSet<Doctor>();
		setDoctor.add(doctor);
		List<Doctor> listDoctor=setDoctor.stream().collect(Collectors.toList());
		
		ObjectMapper mapper=new ObjectMapper();
		String doctorJson=mapper.writeValueAsString(listDoctor);
		
		speciality.setDoctors(setDoctor);
		
		when(specialityRepository.findById(1)).thenReturn(Optional.of(speciality));
		
		mvc.perform(get("/clinic/speciality/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json(doctorJson));
	}
	
	@Test
	void testListAllDoctorsOfSpecialityFailure() throws Exception {
		when(specialityRepository.findById(1)).thenReturn(Optional.empty());
		
		mvc.perform(get("/clinic/speciality/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Speciality Not Exists"));
 }
	
	@Test
	void testListAllDoctorsOfSpecialityFailureTwo() throws Exception {
		Speciality speciality=new Speciality(1,"nurology");
		Set<Doctor> setDoctor=new HashSet<Doctor>();		
		speciality.setDoctors(setDoctor);
		
		when(specialityRepository.findById(1)).thenReturn(Optional.of(speciality));
		
		mvc.perform(get("/clinic/speciality/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Doctor not found"));
	}

}
