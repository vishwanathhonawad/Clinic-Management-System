package com.demo.spring.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.demo.spring.dto.PatientDTO;
import com.demo.spring.entity.Diagnostic;
import com.demo.spring.repository.DiagnosticRepository;
import com.demo.spring.repository.PatientDiagnosticRepository;
import com.demo.spring.util.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,properties = {
        "patientServer=http://localhost:${wiremock.server.port}",
        "appointmentServer=http://localhost:${wiremock.server.port}",
        "clinicServer=http://localhost:${wiremock.server.port}" } )
@AutoConfigureWireMock(port = 0)
class PatientDiagnosticControllerTest {

	@LocalServerPort
    int port;
	
	@Autowired
	TestRestTemplate testRestTemplate;

	@MockBean
	PatientDiagnosticRepository patientDiagnosticRepository;
	
	@MockBean
	DiagnosticRepository diagnosticRepository;

	@Test
	void testAddTestToPatient() throws JsonProcessingException {
		PatientDTO patientDTO=new PatientDTO(1,"vishwa","rh","vrh@gmail.com");
		Diagnostic test=new Diagnostic(2,"ECG");
        ObjectMapper mapper = new ObjectMapper();
        String patientJson = mapper.writeValueAsString(patientDTO);
        
        Message message = new Message("Test added successfully");
        String messageJson = mapper.writeValueAsString(message);
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> req = new HttpEntity<>(headers);
       
       when(diagnosticRepository.findById(2)).thenReturn(Optional.of(test));
       stubFor(get(urlEqualTo("/patient/1")).willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(patientJson)));
       ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/clinic/patientdiagnostic/save/1/2",HttpMethod.POST,req,String.class);
       Assertions.assertEquals(messageJson,response.getBody());
		
	}
	
	@Test
	void testAddTestToPatientFailure() throws JsonProcessingException {
		PatientDTO patientDTO=new PatientDTO(2,"vishwa","rh","vrh@gmail.com");
        ObjectMapper mapper = new ObjectMapper();
        String patientJson = mapper.writeValueAsString(patientDTO);
        
        Message message = new Message("Patient Not Found");
        String messageJson = mapper.writeValueAsString(message);
    
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> req = new HttpEntity<>(headers);
       
       stubFor(get(urlEqualTo("/patient/1")).willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(patientJson)));
       ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/clinic/patientdiagnostic/save/1/2",HttpMethod.POST,req,String.class);
       Assertions.assertEquals(messageJson,response.getBody());
		
	}
	
	@Test
	void testAddTestToPatientFailureTwo() throws JsonProcessingException {
		PatientDTO patientDTO=new PatientDTO(1,"vishwa","rh","vrh@gmail.com");
        ObjectMapper mapper = new ObjectMapper();
        String patientJson = mapper.writeValueAsString(patientDTO);
        
        Message message = new Message("Diagnostic test not exists");
        String messageJson = mapper.writeValueAsString(message);
    
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Void> req = new HttpEntity<>(headers);
       
       when(diagnosticRepository.findById(2)).thenReturn(Optional.empty());
       stubFor(get(urlEqualTo("/patient/1")).willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(patientJson)));
       ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/clinic/patientdiagnostic/save/1/2",HttpMethod.POST,req,String.class);
       Assertions.assertEquals(messageJson,response.getBody());
		
	}
	


}
