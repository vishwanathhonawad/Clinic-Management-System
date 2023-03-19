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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.dto.AppointmentDTO;

@Controller
public class AppointmentController {

	@Autowired
	RestTemplate restTemplate;

	@PostMapping(path = "/bookAppointment")
	public ModelAndView bookAppointment(AppointmentDTO appointmentDTO) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<AppointmentDTO> req = new HttpEntity<>(appointmentDTO, headers);

		String result = restTemplate.exchange("http://localhost:9091/appointment/", HttpMethod.POST, req, String.class)
				.getBody();
		if (result != null && result.equals("{\"status\":\"Appointment Booked..\"}")) {
			mv.addObject("responseS", "Appointment Booked..");
		} else if (result != null && result.equals("{\"status\":\"Patient Not Found\"}")){
			mv.addObject("response", "Patient Not Found");
		}else  if (result != null && result.equals("{\"status\":\"Doctor Not Found\"}")) {
			mv.addObject("response", "Doctor Not Found");
		}else {
			mv.addObject("response", result);

		}
		mv.setViewName("appointment/appointment-add");
		return mv;

	}

	@GetMapping(path = "/getDocAppointment")
	public ModelAndView bgetDocAppointment(AppointmentDTO appointmentDTO) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);
		String str = restTemplate.exchange(
				"http://localhost:9091/appointment/" + appointmentDTO.getDoctorId() + "/" + appointmentDTO.getDate(),
				HttpMethod.GET, req, String.class).getBody();
		if (str != null && str.equals("{\"status\":\"Doctor has no appointment on this date\"}")) {
			mv.addObject("response", "No Test Available");
		} else if (str != null && str.equals("{\"status\":\"Doctor Not Found\"}")) {
			mv.addObject("response", "Doctor Not exists");

		} else {
			ResponseEntity<List<AppointmentDTO>> response = restTemplate.exchange(
					"http://localhost:9091/appointment/" + appointmentDTO.getDoctorId() + "/"
							+ appointmentDTO.getDate(),
					HttpMethod.GET, req, new ParameterizedTypeReference<List<AppointmentDTO>>() {
					});
			mv.addObject("appointmentList", response.getBody());
		}
		mv.setViewName("appointment/appointment-doctor");
		return mv;
	}

	@GetMapping(path="/getPatientAppointment")
	public ModelAndView bgetPatientAppointment(AppointmentDTO appointmentDTO) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);
		String str = restTemplate.exchange(
				"http://localhost:9091/appointment/" + appointmentDTO.getPatientId(),
				HttpMethod.GET, req, String.class).getBody();
		if (str != null && str.equals("{\"status\":\"Patient Appointment Not Found..\"}")) {
			mv.addObject("response", "No Test Available");
		} else if (str != null && str.equals("{\"status\":\"Patient Not Found\"}")) {
			mv.addObject("response", "Patient Not exists");
		} else {
			ResponseEntity<List<AppointmentDTO>> response = restTemplate.exchange(
					"http://localhost:9091/appointment/" + appointmentDTO.getPatientId(),
					HttpMethod.GET, req, new ParameterizedTypeReference<List<AppointmentDTO>>() {
					});
			mv.addObject("appointmentList", response.getBody());
		}
		mv.setViewName("appointment/appointment-patient");
		return mv;
	}
	

}
