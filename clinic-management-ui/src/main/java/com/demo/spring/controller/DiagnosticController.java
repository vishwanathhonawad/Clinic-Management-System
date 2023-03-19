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

import com.demo.spring.dto.DiagnosticDTO;

@Controller
public class DiagnosticController {

	@Autowired
	RestTemplate restTemplate;

	@GetMapping(path = "/diagnostic-list")
	public ModelAndView findAll() {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);
		String str = restTemplate
				.exchange("http://localhost:9091/clinic/diagnostic/list", HttpMethod.GET, req, String.class).getBody();
		if (str != null && str.equals("{\"status\":\"There are No Diagnostic Tests exists\"}")) {
			mv.addObject("diag", "No Test Available");
			mv.setViewName("diagnostic/diagnostic-list");
			return mv;
		} else {
			ResponseEntity<List<DiagnosticDTO>> response = restTemplate.exchange(
					"http://localhost:9091/clinic/diagnostic/list", HttpMethod.GET, req,
					new ParameterizedTypeReference<List<DiagnosticDTO>>() {
					});
			mv.addObject("diagList", response.getBody());
			mv.setViewName("diagnostic/diagnostic-list");
			return mv;
		}
	}

	@PostMapping(path = "/saveTest")
	public ModelAndView saveTest(DiagnosticDTO diagnosticDTO) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<DiagnosticDTO> req = new HttpEntity<>(diagnosticDTO,headers);
		String response = restTemplate.exchange(
				"http://localhost:9091/clinic/diagnostic/save", HttpMethod.POST, req,String.class).getBody();
		if(response!=null && response.contains("Test Saved Successfully")) {
			mv.addObject("responseS", "Test Saved Successfully");

		}else {
			mv.addObject("response", "Test Alredy exists");	
		}
		
		mv.setViewName("diagnostic/diagnostic-add");
		return mv;
	}
	
	@PostMapping(path = "/deleteTest")
	public ModelAndView deleteTest(@RequestParam(name = "testId", required = true) int testId) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);
		String response = restTemplate.exchange(
				"http://localhost:9091/clinic/diagnostic/delete/"+testId, HttpMethod.DELETE, req,String.class).getBody();
		if(response!=null && response.contains("Test deleted Successfully")) {
			mv.addObject("responseS", "Test deleted Successfully");
		}else {
			mv.addObject("response", "Test Not exists");	
		}
		mv.setViewName("diagnostic/diagnostic-remove");
		return mv;
	}
}
