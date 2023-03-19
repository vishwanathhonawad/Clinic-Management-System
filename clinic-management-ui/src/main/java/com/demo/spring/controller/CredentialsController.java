package com.demo.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.dto.Credentials;
import com.demo.spring.dto.CredentialsDTO;

@Controller
public class CredentialsController {

	@Autowired
	RestTemplate restTemplate;
	

	
	@GetMapping(path = "/findUser")
	public ModelAndView findUser(CredentialsDTO credentialsDTO) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<CredentialsDTO> req = new HttpEntity<>(credentialsDTO,headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:9091/find", HttpMethod.POST, req, String.class);
		String res=response.getBody();
		if(res!=null && res.contains("User Found")) {
			mv.addObject("response","");
			mv.setViewName("home/home");
		}else {
			mv.addObject("response","WRONG CREDENTIALS PLEASE ENTER AGAIN");
			mv.setViewName("login");
		}		
		return mv;
	}
	
	@PostMapping(path = "/save")
	public ModelAndView saveUser(Credentials credentials) {
		ModelAndView mv = new ModelAndView();
		if(credentials.getPassword().equals(credentials.getPasswordTwo())) {
		CredentialsDTO credentialsDTO=new CredentialsDTO(credentials.getUserName(),credentials.getPassword());
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<CredentialsDTO> req = new HttpEntity<>(credentialsDTO,headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:9091/save", HttpMethod.POST, req, String.class);
		String res=response.getBody();
		if(res!=null && res.contains("User Saved")) {
			mv.addObject("response","");
			mv.setViewName("home/home");
		}else if(res!=null && res.contains("User Exists")){
			mv.addObject("response","UserName Alredy taken");
			mv.setViewName("signUp");
		}else {
			mv.addObject("response1","Password do not match Condition");
			mv.addObject("response2","Should contain at least 1 Capital letter");
			mv.addObject("response3","Should contain at least 1 special case @#$%^&+=");
			mv.addObject("response4","Should contain at least 1 digit");
			mv.setViewName("signUp");
		}
	}else {
		mv.addObject("response","PASSWORDS DO NOT MATCH");
		mv.setViewName("signUp");
	}
		return mv;
}
	
	@PostMapping(path = "/update")
	public ModelAndView updateUser(Credentials credentials) {
		ModelAndView mv = new ModelAndView();
		if(credentials.getPassword().equals(credentials.getPasswordTwo())) {
		CredentialsDTO credentialsDTO=new CredentialsDTO(credentials.getUserName(),credentials.getPassword());
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<CredentialsDTO> req = new HttpEntity<>(credentialsDTO,headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:9091/update", HttpMethod.PATCH, req, String.class);
		String res=response.getBody();
		if(res!=null && res.contains("User Updated")) {
			mv.addObject("response","");
			mv.setViewName("home/home");
		}else if(res!=null && res.contains("User Not Found")){
			mv.addObject("response","UserName Alredy taken");
			mv.setViewName("reset");
		}else {
			mv.addObject("response1","Password do not match Condition");
			mv.addObject("response2","Should contain at least 1 Capital letter");
			mv.addObject("response3","Should contain at least 1 special case @#$%^&+=");
			mv.addObject("response4","Should contain at least 1 digit");
			mv.setViewName("reset");
		}
	}else {
		mv.addObject("response","PASSWORDS DO NOT MATCH");
		mv.setViewName("reset");
	}
		return mv;
}
}
