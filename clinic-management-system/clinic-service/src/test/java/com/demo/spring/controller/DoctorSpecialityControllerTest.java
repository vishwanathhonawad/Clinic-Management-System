package com.demo.spring.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.entity.DoctorSpeciality;
import com.demo.spring.repository.DoctorRepository;
import com.demo.spring.repository.DoctorSpecialityRepository;
import com.demo.spring.repository.SpecialityRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DoctorSpecialityControllerTest {

	@Autowired
	MockMvc mvc;

	@Autowired
	TestRestTemplate testRestTemplate;

	@MockBean
	DoctorSpecialityRepository doctorSpecialityRepository;

	@MockBean
	DoctorRepository doctorRepository;

	@MockBean
	SpecialityRepository specialityRepository;
	
	@Test
	void testAddSpecialityToDoctorSuccess() throws Exception {
		when(doctorSpecialityRepository.getAllByDocIdAndSpecId(1, 2)).thenReturn(new ArrayList<>());
		when(doctorRepository.existsById(1)).thenReturn(true);
		when(specialityRepository.existsById(2)).thenReturn(true);

		mvc.perform(post("/clinic/doctorspeciality/1/2").contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Doctor Successfully added to Speciality"));
	}

	@Test
	void testAddSpecialityToDoctorFailureOne() throws Exception {
		DoctorSpeciality doctorSpeciality = new DoctorSpeciality(1, 1, 1);
		List<DoctorSpeciality> listDoctorSpeciality = new ArrayList<>();
		listDoctorSpeciality.add(doctorSpeciality);

		when(doctorSpecialityRepository.getAllByDocIdAndSpecId(1, 1)).thenReturn(listDoctorSpeciality);
		when(doctorRepository.existsById(1)).thenReturn(false);
		when(specialityRepository.existsById(1)).thenReturn(true);

		mvc.perform(post("/clinic/doctorspeciality/1/1").contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Doctor not found"));
	}
	@Test
	void testAddSpecialityToDoctorFailureTwo() throws Exception {
		DoctorSpeciality doctorSpeciality = new DoctorSpeciality(2, 2, 2);
		List<DoctorSpeciality> listDoctorSpeciality = new ArrayList<>();
		listDoctorSpeciality.add(doctorSpeciality);

		when(doctorSpecialityRepository.getAllByDocIdAndSpecId(2, 2)).thenReturn(listDoctorSpeciality);
		when(doctorRepository.existsById(2)).thenReturn(true);
		when(specialityRepository.existsById(2)).thenReturn(false);

		mvc.perform(post("/clinic/doctorspeciality/2/2").contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Speciality Not Exists"));
	}
	@Test
	void testAddSpecialityToDoctorFailureThree() throws Exception {
		DoctorSpeciality doctorSpeciality = new DoctorSpeciality(3, 3, 3);
		List<DoctorSpeciality> listDoctorSpeciality = new ArrayList<>();
		listDoctorSpeciality.add(doctorSpeciality);
		
		when(doctorSpecialityRepository.getAllByDocIdAndSpecId(3, 3)).thenReturn(listDoctorSpeciality);
		when(doctorRepository.existsById(3)).thenReturn(true);
		when(specialityRepository.existsById(3)).thenReturn(true);

		mvc.perform(post("/clinic/doctorspeciality/3/3").contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Doctor Speciality Alredy Exists"));
	}

	@Test
	void testRemoveSpecialityFromDoctorSuccess() throws Exception {
		DoctorSpeciality doctorSpeciality = new DoctorSpeciality(4, 4, 4);
		List<DoctorSpeciality> listDoctorSpeciality = new ArrayList<>();
		listDoctorSpeciality.add(doctorSpeciality);

		when(doctorSpecialityRepository.getAllByDocIdAndSpecId(4, 4)).thenReturn(listDoctorSpeciality);
		when(doctorRepository.existsById(4)).thenReturn(true);
		when(specialityRepository.existsById(4)).thenReturn(true);

		mvc.perform(delete("/clinic/doctorspeciality/4/4").contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Doctor Speciality Deleted"));
	}

	@Test
	void testRemoveSpecialityFromDoctorFailureOne() throws Exception {

		when(doctorSpecialityRepository.getAllByDocIdAndSpecId(5, 5)).thenReturn(new ArrayList<>());
		when(doctorRepository.existsById(5)).thenReturn(false);
		when(specialityRepository.existsById(5)).thenReturn(false);

		mvc.perform(delete("/clinic/doctorspeciality/5/5").contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Doctor not found"));
	}
	
	@Test
	void testRemoveSpecialityFromDoctorFailureTwo() throws Exception {

		when(doctorSpecialityRepository.getAllByDocIdAndSpecId(6, 6)).thenReturn(new ArrayList<>());
		when(doctorRepository.existsById(6)).thenReturn(true);
		when(specialityRepository.existsById(6)).thenReturn(false);

		mvc.perform(delete("/clinic/doctorspeciality/6/6").contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Speciality Not Exists"));
	}
	@Test
	void testRemoveSpecialityFromDoctorFailureThree() throws Exception {

		when(doctorSpecialityRepository.getAllByDocIdAndSpecId(7, 7)).thenReturn(new ArrayList<>());
		when(doctorRepository.existsById(7)).thenReturn(true);
		when(specialityRepository.existsById(7)).thenReturn(true);

		mvc.perform(delete("/clinic/doctorspeciality/7/7").contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Doctor Speciality Not Exists"));
	}

}
