package com.demo.spring.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

import com.demo.spring.dto.AppointmentDTO;
import com.demo.spring.dto.DoctorDTO;
import com.demo.spring.dto.PatientDTO;
import com.demo.spring.entity.Appointment;
import com.demo.spring.repository.AppointmentRepository;
import com.demo.spring.util.Message;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT,properties = {
        "patientServer=http://localhost:${wiremock.server.port}",
        "appointmentServer=http://localhost:${wiremock.server.port}",
        "clinicServer=http://localhost:${wiremock.server.port}" } )
@AutoConfigureWireMock(port = 0)
class AppointmentControllerTest {

	@LocalServerPort
    int port;
	
	@Autowired
	TestRestTemplate testRestTemplate;
	
	@MockBean
	AppointmentRepository appointmentRepository;

	@Test
	void testAddAppointmentSuccess() throws Exception {
		
		AppointmentDTO appointment=new AppointmentDTO(1,3,3,"2022-10-14");
		DoctorDTO doctorDTO=new DoctorDTO(3,"sawad","m","vrh@gmail.com");
		PatientDTO patientDTO=new PatientDTO(3,"vishwa","rh","vrh@gmail.com");
		
        ObjectMapper mapper = new ObjectMapper();
        String doctorJson = mapper.writeValueAsString(doctorDTO);
        String patientJson = mapper.writeValueAsString(patientDTO);
        
        Message message = new Message("Appointment Booked..");
        String messageJson = mapper.writeValueAsString(message);
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

       HttpEntity<AppointmentDTO> req = new HttpEntity<>(appointment, headers);
       
       stubFor(get(urlEqualTo("/clinic/doctor/find/3")).willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(doctorJson)));
       stubFor(get(urlEqualTo("/patient/3")).willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(patientJson)));

       ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/appointment/", HttpMethod.POST, req, String.class);

       Assertions.assertEquals(messageJson,response.getBody());
	}
	
	@Test
	void testAddAppointmentFailureOne() throws Exception {
		
		AppointmentDTO appointment=new AppointmentDTO(1,1,2,"2022-10-14");
		DoctorDTO doctorDTO=new DoctorDTO(1,"sawad","m","vrh@gmail.com");
		PatientDTO patientDTO=new PatientDTO(1,"vishwa","rh","vrh@gmail.com");
		
        ObjectMapper mapper = new ObjectMapper();
        String doctorJson = mapper.writeValueAsString(doctorDTO);
        String patientJson = mapper.writeValueAsString(patientDTO);
        
        Message message = new Message("Patient Not Found");
        String messageJson = mapper.writeValueAsString(message);
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

       HttpEntity<AppointmentDTO> req = new HttpEntity<>(appointment, headers);
       
       stubFor(get(urlEqualTo("/clinic/doctor/find/1")).willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(doctorJson)));
       stubFor(get(urlEqualTo("/patient/2")).willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(patientJson)));

       ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/appointment/", HttpMethod.POST, req, String.class);

       Assertions.assertEquals(messageJson,response.getBody());
	}
	
	@Test
	void testAddAppointmentFailureTwo() throws Exception {
		
		AppointmentDTO appointment=new AppointmentDTO(1,1,2,"2022-10-14");
		DoctorDTO doctorDTO=new DoctorDTO(2,"sawad","m","vrh@gmail.com");
		PatientDTO patientDTO=new PatientDTO(2,"vishwa","rh","vrh@gmail.com");
		
        ObjectMapper mapper = new ObjectMapper();
        String doctorJson = mapper.writeValueAsString(doctorDTO);
        String patientJson = mapper.writeValueAsString(patientDTO);
        
        Message message = new Message("Doctor Not Found");
        String messageJson = mapper.writeValueAsString(message);
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

       HttpEntity<AppointmentDTO> req = new HttpEntity<>(appointment, headers);
       
       stubFor(get(urlEqualTo("/clinic/doctor/find/1")).willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(doctorJson)));
       stubFor(get(urlEqualTo("/patient/2")).willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(patientJson)));

       ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/appointment/", HttpMethod.POST, req, String.class);

       Assertions.assertEquals(messageJson,response.getBody());
	}
       

	

	@Test
	void testlistAllAppointmentOfPatientSuccess() throws Exception {
		Appointment appointment=new Appointment(1,1,1,"2022-10-14");
		PatientDTO patientDTO=new PatientDTO(1,"vishwa","rh","vrh@gmail.com");
		List<Appointment> listAppointment =new ArrayList<>();
		listAppointment.add(appointment);
        ObjectMapper mapper = new ObjectMapper();
        String patientJson = mapper.writeValueAsString(patientDTO);
        
        String appointmentJson = mapper.writeValueAsString(listAppointment);
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

       HttpEntity<List<Appointment>> req = new HttpEntity<>(listAppointment, headers);
       when(appointmentRepository.findAllByPatientId(1)).thenReturn(listAppointment);
       
       stubFor(get(urlEqualTo("/patient/1")).willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(patientJson)));

       ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/appointment/1", HttpMethod.GET, req, String.class);

       Assertions.assertEquals(appointmentJson,response.getBody());
	}
	
	@Test
	void testlistAllAppointmentOfPatientFailure() throws Exception {
		PatientDTO patientDTO=new PatientDTO(1,"vishwa","rh","vrh@gmail.com");
		List<Appointment> listAppointment =new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        String patientJson = mapper.writeValueAsString(patientDTO);
        
        Message message=new Message("Patient Appointment Not Found..");
        String messageJson = mapper.writeValueAsString(message);
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

       HttpEntity<List<Appointment>> req = new HttpEntity<>(listAppointment, headers);
       when(appointmentRepository.findAllByPatientId(1)).thenReturn(listAppointment);
       
       stubFor(get(urlEqualTo("/patient/1")).willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(patientJson)));

       ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/appointment/1", HttpMethod.GET, req, String.class);

       Assertions.assertEquals(messageJson,response.getBody());
	}
	
	@Test
	void testlistAllAppointmentOfPatientFailureTwo() throws Exception {
		PatientDTO patientDTO=new PatientDTO(2,"vishwa","rh","vrh@gmail.com");
		List<Appointment> listAppointment =new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        String patientJson = mapper.writeValueAsString(patientDTO);
        
        Message message=new Message("Patient Not Found");
        String messageJson = mapper.writeValueAsString(message);
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

       HttpEntity<List<Appointment>> req = new HttpEntity<>(listAppointment, headers);
       when(appointmentRepository.findAllByPatientId(1)).thenReturn(listAppointment);
       
       stubFor(get(urlEqualTo("/patient/1")).willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(patientJson)));

       ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/appointment/1", HttpMethod.GET, req, String.class);

       Assertions.assertEquals(messageJson,response.getBody());
	}
	
	@Test
	void testListAllAppointmentOfDoctorSuccess() throws Exception {
		
		Appointment appointment=new Appointment(1,1,1,"2022-10-14");
		DoctorDTO doctorDTO=new DoctorDTO(1,"sawad","m","vrh@gmail.com");
		List<Appointment> listAppointment =new ArrayList<>();
		listAppointment.add(appointment);
        ObjectMapper mapper = new ObjectMapper();
        String doctorJson = mapper.writeValueAsString(doctorDTO);
        
        String appointmentJson = mapper.writeValueAsString(listAppointment);
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

       HttpEntity<Appointment> req = new HttpEntity<>(appointment, headers);
       when(appointmentRepository.findAllByDoctorIdAndDate(1,"2022-10-14")).thenReturn(listAppointment);
       stubFor(get(urlEqualTo("/clinic/doctor/find/1")).willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(doctorJson)));

       ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/appointment/1/2022-10-14", HttpMethod.GET, req, String.class);

       Assertions.assertEquals(appointmentJson,response.getBody());
	}
	@Test
	void testListAllAppointmentOfDoctorFailure() throws Exception {
		
		Appointment appointment=new Appointment(1,1,1,"2022-10-14");
		DoctorDTO doctorDTO=new DoctorDTO(1,"sawad","m","vrh@gmail.com");
		List<Appointment> listAppointment =new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        String doctorJson = mapper.writeValueAsString(doctorDTO);
        
        Message message=new Message("Doctor has no appointment on this date");
        String messageJson = mapper.writeValueAsString(message);
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

       HttpEntity<Appointment> req = new HttpEntity<>(appointment, headers);
       when(appointmentRepository.findAllByDoctorIdAndDate(1,"2022-10-14")).thenReturn(listAppointment);
       stubFor(get(urlEqualTo("/clinic/doctor/find/1")).willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(doctorJson)));

       ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/appointment/1/2022-10-14", HttpMethod.GET, req, String.class);

       Assertions.assertEquals(messageJson,response.getBody());
  }
	@Test
	void testListAllAppointmentOfDoctorFailureTwo() throws Exception {
		
		Appointment appointment=new Appointment(1,1,1,"2022-10-14");
		DoctorDTO doctorDTO=new DoctorDTO(2,"sawad","m","vrh@gmail.com");
		List<Appointment> listAppointment =new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        String doctorJson = mapper.writeValueAsString(doctorDTO);
        
        Message message=new Message("Doctor Not Found");
        String messageJson = mapper.writeValueAsString(message);
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

       HttpEntity<Appointment> req = new HttpEntity<>(appointment, headers);
       when(appointmentRepository.findAllByDoctorIdAndDate(1,"2022-10-14")).thenReturn(listAppointment);
       stubFor(get(urlEqualTo("/clinic/doctor/find/1")).willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(doctorJson)));

       ResponseEntity<String> response = testRestTemplate.exchange("http://localhost:" + port + "/appointment/1/2022-10-14", HttpMethod.GET, req, String.class);

       Assertions.assertEquals(messageJson,response.getBody());
  }
	

}
