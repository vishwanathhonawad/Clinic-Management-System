package com.demo.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.dto.PatientDTO;
import com.demo.spring.dto.PatientDiagnosticDTO;

@Controller
public class PatientController {

	@Autowired
	RestTemplate restTemplate;

	@PostMapping(path = "/patientSave")
	public ModelAndView patientSave(PatientDTO patientDTO) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<PatientDTO> req = new HttpEntity<>(patientDTO, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:9091/patient/", HttpMethod.POST, req,
				String.class);
		String res = response.getBody();
		if (res != null && res.contains("Patient Saved Successfully")) {
			mv.addObject("responseS", "Patient Saved");
		} else if (res != null && res.contains("Patient alredy exists..")) {
			mv.addObject("response", "Email exists");
		}
		mv.setViewName("patient/patient-register");
		return mv;
	}

	@GetMapping(path = "/patientById")
	public ModelAndView findById(@RequestParam(name = "patientId", required = true) int patientId) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);
		ResponseEntity<PatientDTO> response = restTemplate.exchange("http://localhost:9091/patient/" + patientId,
				HttpMethod.GET, req, PatientDTO.class);
		mv.addObject("patient", response.getBody());
		mv.setViewName("patient/patient-findId");
		return mv;

	}

	@GetMapping(path = "/upatientById")
	public ModelAndView findByIdUpdate(@RequestParam(name = "patientId", required = true) int patientId) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);
		String response = restTemplate
				.exchange("http://localhost:9091/patient/" + patientId, HttpMethod.GET, req, String.class).getBody();
		if (response != null && response.contains("Patient Not exists..")) {
			mv.addObject("response", "Patient Not exists");
			mv.setViewName("patient/patient-updatedetails");
			return mv;
		} else {
			PatientDTO patientDTO = restTemplate
					.exchange("http://localhost:9091/patient/" + patientId, HttpMethod.GET, req, PatientDTO.class)
					.getBody();
			mv.addObject("patient", patientDTO);
			mv.setViewName("patient/patient-updatedetails2");
			return mv;
		}

	}

	@GetMapping(path = "/patientByName")
	public ModelAndView findByName(@RequestParam(name = "firstName", required = true) String firstName) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);

		String str = restTemplate
				.exchange("http://localhost:9091/patient/name/" + firstName, HttpMethod.GET, req, String.class)
				.getBody();

		if (str != null && str.equals("{\"status\":\"Patient Not found..\"}")) {
			mv.addObject("response", "No patient found");
			mv.setViewName("patient/patient-findName");
			return mv;
		} else {
			ResponseEntity<List<PatientDTO>> response = restTemplate.exchange(
					"http://localhost:9091/patient/name/" + firstName, HttpMethod.GET, req,
					new ParameterizedTypeReference<List<PatientDTO>>() {
					});
			mv.addObject("patientList", response.getBody());
			mv.setViewName("patient/patient-findName");
			return mv;
		}

	}

	@PostMapping(path = "/addTest")
	public ModelAndView addTest(PatientDiagnosticDTO patientDiagnosticDTO) {
		System.out.println(patientDiagnosticDTO.getPatientId());
		System.out.println(patientDiagnosticDTO.getDiagnosticId());
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<PatientDiagnosticDTO> req = new HttpEntity<>(patientDiagnosticDTO, headers);
		String response = restTemplate.exchange("http://localhost:9091/clinic/patientdiagnostic/save/"
				+ patientDiagnosticDTO.getPatientId() + "/" + patientDiagnosticDTO.getDiagnosticId(), HttpMethod.POST,
				req, String.class).getBody();
		
		if(response!=null && response.equals("{\"status\":\"Test added successfully\"}")) {
			mv.addObject("responseS", "Test added successfully");
		}else if(response!=null && response.equals("{\"status\":\"Patient Not Found\"}")){
			mv.addObject("response", "Patient Not Found");
		}else {
			mv.addObject("response", "Test Not Found");
		}
		mv.setViewName("patient/patient-Diagnostic");
		return mv;

	}

	@PostMapping(path = "/updatePatient")
	public ModelAndView patientUpdate(PatientDTO patientDTO)	{
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<PatientDTO> req = new HttpEntity<>(patientDTO, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:9091/patient/update", HttpMethod.PATCH, req,
				String.class);
		String res = response.getBody();
		if (res != null && res.contains("Patient updated successfully..")) {
			mv.addObject("updateS", "Patient Updated");
		} else if(res != null && res.contains("Email alredy exists")){
			mv.addObject("update", "Email exists");
		}else {
			mv.addObject("update", "Patient not exists");
		}
		mv.setViewName("patient/patient-updatedetails2");
		return mv;
	}

}
