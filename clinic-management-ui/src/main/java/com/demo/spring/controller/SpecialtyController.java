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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.dto.DoctorDTO;

@Controller
public class SpecialtyController {

	@Autowired
	RestTemplate restTemplate;

	@GetMapping(path = "/findDoctorswithSpeciality")
	public ModelAndView findDoctorBySpecialityId(
			@RequestParam(name = "specialityId", required = true) int specialityId) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);

		String str = restTemplate
				.exchange("http://localhost:9091/clinic/speciality/" + specialityId, HttpMethod.GET, req, String.class)
				.getBody();
		if (str != null && str.equals("{\"status\":\"Speciality Not Exists\"}")) {
			mv.addObject("response", "speciality not exists");

		} else if (str != null && str.equals("{\"status\":\"Doctor not found\"}")) {
			mv.addObject("response", "No doctors found");

		} else {
			ResponseEntity<List<DoctorDTO>> response = restTemplate.exchange(
					"http://localhost:9091/clinic/speciality/" + specialityId, HttpMethod.GET, req,
					new ParameterizedTypeReference<List<DoctorDTO>>() {
					});
			mv.addObject("doctorList", response.getBody());

		}
		mv.setViewName("speciality/speciality-find");
		return mv;
	}
}
