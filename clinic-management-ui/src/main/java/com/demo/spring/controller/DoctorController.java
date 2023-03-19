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

import com.demo.spring.dto.DoctorDTO;
import com.demo.spring.dto.DoctorSpeciality;

@Controller
public class DoctorController {

	@Autowired
	RestTemplate restTemplate;

	@PostMapping(path = "/addSpecialtyToDoctor")
	public ModelAndView addSpecialtyToDoctor(DoctorSpeciality doctorSpeciality) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);

		String str = restTemplate
				.exchange("http://localhost:9091/clinic/doctorspeciality/" + doctorSpeciality.getDoctorId() + "/"
						+ doctorSpeciality.getSpecialityId(), HttpMethod.POST, req, String.class)
				.getBody();

		if (str != null && str.equals("{\"status\":\"Doctor Successfully added to Speciality\"}")) {
			mv.addObject("responseS", "Doctor Successfully added to Speciality");

		} else if (str != null && str.equals("{\"status\":\"Speciality Not Exists\"}")) {
			mv.addObject("response", "Speciality Not Exists");
		} else if (str != null && str.equals("{\"status\":\"Doctor Speciality Alredy Exists\"}")) {
			mv.addObject("response", "Doctor Speciality Alredy Exists");

		} else {
			mv.addObject("response", "Doctor not found");

		}
		mv.setViewName("doctor/doctor-add");
		return mv;
	}

	@GetMapping(path = "/doctor-list")
	public ModelAndView doctorList() {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);

		String str = restTemplate
				.exchange("http://localhost:9091/clinic/doctor/list", HttpMethod.GET, req, String.class).getBody();
		if (str != null && str.equals("{\"status\":\"Doctor not found\"}")) {
			mv.addObject("response", str);

		} else {
			List<DoctorDTO> doctorList = restTemplate.exchange("http://localhost:9091/clinic/doctor/list",
					HttpMethod.GET, req, new ParameterizedTypeReference<List<DoctorDTO>>() {
					}).getBody();
			mv.addObject("doctorList", doctorList);

		}
		mv.setViewName("doctor/doctor-list");
		return mv;
	}

	@PostMapping(path = "/removeSpecialtyFromDoctor")
	public ModelAndView removeTestToDoctor(DoctorSpeciality doctorSpeciality) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);

		String str = restTemplate
				.exchange("http://localhost:9091/clinic/doctorspeciality/" + doctorSpeciality.getDoctorId() + "/"
						+ doctorSpeciality.getSpecialityId(), HttpMethod.DELETE, req, String.class)
				.getBody();

		if (str != null && str.equals("{\"status\":\"Doctor Speciality Deleted\"}")) {
			mv.addObject("responseS", "Doctor Successfully Removed from Speciality");
		} else if(str != null && str.equals("{\"status\":\"Speciality Not Exists\"}")) {
			mv.addObject("response", "Speciality Not Exists");
		}else if(str != null && str.equals("{\"status\":\"Doctor Speciality Not Exists\"}")){
			mv.addObject("response", "Doctor Speciality Not Exists");
		}else {
			mv.addObject("response", "Doctor Not Exists");

		}
		mv.setViewName("doctor/doctor-remove");
		return mv;
	}

	@GetMapping(path = "/doctorById")
	public ModelAndView findById(@RequestParam(name = "doctorId", required = true) int doctorId) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);
		ResponseEntity<DoctorDTO> response = restTemplate
				.exchange("http://localhost:9091/clinic/doctor/find/" + doctorId, HttpMethod.GET, req, DoctorDTO.class);
		mv.addObject("doctor", response.getBody());
		mv.setViewName("doctor/doctor-find");
		return mv;

	}

}
