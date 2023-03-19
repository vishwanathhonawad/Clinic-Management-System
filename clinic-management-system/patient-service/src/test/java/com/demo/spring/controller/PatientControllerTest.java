package com.demo.spring.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.demo.spring.dto.PatientDTO;
import com.demo.spring.entity.Patient;
import com.demo.spring.repository.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PatientControllerTest {



	@Autowired
	MockMvc mvc;

	@Autowired
	TestRestTemplate testRestTemplate;
	
	@MockBean
	PatientRepository patientRepository;
	
	@Test
	void testRegisterPatientSuccess() throws Exception {
		PatientDTO patient=new PatientDTO(1,"vishwa","rh","vishwarh@gmail.com");
		ObjectMapper mapper=new ObjectMapper();
		String patientJson=mapper.writeValueAsString(patient);
		
		when(patientRepository.findAllByEmail("vishwarh@gmail.com")).thenReturn(new ArrayList<>());
		
		mvc.perform(post("/patient/").content(patientJson)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Patient Saved Successfully"));
	
	}

	
	@Test
	void testRegisterPatientFailure() throws Exception {
		Patient patient=new Patient(1,"vishwa","rh","vishwarh@gmail.com");
		ObjectMapper mapper=new ObjectMapper();
		String patientJson=mapper.writeValueAsString(patient);
		List<Patient> listPatient=new ArrayList<>();
		listPatient.add(patient);
		
		when(patientRepository.findAllByEmail("vishwarh@gmail.com")).thenReturn(listPatient);
		
		mvc.perform(post("/patient/").content(patientJson)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Patient alredy exists.."));
	}
	
	@Test
	void testfindPatientByIdSuccess() throws Exception {
		Patient patient=new Patient(1,"vishwa","rh","vishwarh@gmail.com");
		ObjectMapper mapper=new ObjectMapper();
		String patientJson=mapper.writeValueAsString(patient);
		
		when(patientRepository.findById(1)).thenReturn(Optional.of(patient));
		
		mvc.perform(get("/patient/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json(patientJson));
	}
	
	@Test
	void testfindPatientByIdFailure() throws Exception {
		when(patientRepository.findById(1)).thenReturn(Optional.empty());
		
		mvc.perform(get("/patient/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Patient Not exists.."));
	}
	


	@Test
	void testFindPatientByNameSuccess() throws Exception {
		Patient patient=new Patient(1,"vishwa","rh","vishwarh@gmail.com");
		List<Patient> listPatient=new ArrayList<>();
		listPatient.add(patient);
		
		ObjectMapper mapper=new ObjectMapper();
		String patientJson=mapper.writeValueAsString(listPatient);
		when(patientRepository.findAllByFirstName("vishwa")).thenReturn(listPatient);
		
		mvc.perform(get("/patient/name/vishwa")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json(patientJson));
	}
	
	@Test
	void testFindPatientByNameFailure() throws Exception {
		List<Patient> listPatient=new ArrayList<>();

		
		when(patientRepository.findAllByFirstName("vishwa")).thenReturn(listPatient);
		
		mvc.perform(get("/patient/name/vishwa")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Patient Not found.."));
	}

	
	

	@Test
	void testUpdatePatientSuccess() throws Exception {
		PatientDTO patient=new PatientDTO(1,"vishwa","rh","vishwarh@gmail.com");
		ObjectMapper mapper=new ObjectMapper();
		String patientJson=mapper.writeValueAsString(patient);
		
		when(patientRepository.existsById(1)).thenReturn(true);
		
		mvc.perform(patch("/patient/update").content(patientJson)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Patient updated successfully.."));
		}
	
	@Test
	void testUpdatePatientFailure() throws Exception {
		PatientDTO patient=new PatientDTO(1,"vishwa","rh","vishwarh@gmail.com");
		ObjectMapper mapper=new ObjectMapper();
		String patientJson=mapper.writeValueAsString(patient);
		
		when(patientRepository.existsById(1)).thenReturn(false);
		
		mvc.perform(patch("/patient/update").content(patientJson)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Patient Not exists.."));
		}
	@Test
	void testUpdatePatientFailureTwo() throws Exception {
		Patient patient=new Patient(1,"vishwa","rh","vishwarh@gmail.com");
		Patient patient1=new Patient(2,"vishwa","rh","vishwarh@gmail.com");
		ObjectMapper mapper=new ObjectMapper();
		String patientJson=mapper.writeValueAsString(patient);
		List<Patient> lisPatient=new ArrayList<>();
		lisPatient.add(patient1);
		when(patientRepository.existsById(1)).thenReturn(true);
		when(patientRepository.findAllByEmailNotId(1,"vishwarh@gmail.com")).thenReturn(lisPatient);
		mvc.perform(patch("/patient/update").content(patientJson)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Email alredy exists"));
		}
	

}
