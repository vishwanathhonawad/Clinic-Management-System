package com.demo.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ClinicManagementUiApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(ClinicManagementUiApplication.class, args);
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		
		registry.addViewController("/home").setViewName("home/home");
		registry.addViewController("/error").setViewName("error");
		
		registry.addViewController("/reset").setViewName("reset");
		registry.addViewController("/signUp").setViewName("signUp");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/intro-contact").setViewName("intro-contact");
		registry.addViewController("/intro-aboutUs").setViewName("intro-aboutUs");
		registry.addViewController("/").setViewName("index");
		
		registry.addViewController("/patient").setViewName("patient/patient");
		registry.addViewController("/patient-Diagnostic").setViewName("patient/patient-Diagnostic");
		registry.addViewController("/patient-findId").setViewName("patient/patient-findId");
		registry.addViewController("/patient-findName").setViewName("patient/patient-findName");
		registry.addViewController("/patient-register").setViewName("patient/patient-register");
		registry.addViewController("/patient-updatedetails").setViewName("patient/patient-updatedetails");
		
		registry.addViewController("/doctor").setViewName("doctor/doctor");
		registry.addViewController("/doctor-add").setViewName("doctor/doctor-add");
		registry.addViewController("/doctor-remove").setViewName("doctor/doctor-remove");
		registry.addViewController("/doctor-find").setViewName("doctor/doctor-find");

		registry.addViewController("/appointment").setViewName("appointment/appointment");
		registry.addViewController("/appointment-add").setViewName("appointment/appointment-add");
		registry.addViewController("/appointment-patient").setViewName("appointment/appointment-patient");
		registry.addViewController("/appointment-doctor").setViewName("appointment/appointment-doctor");
		
		
		registry.addViewController("/speciality").setViewName("speciality/speciality");
		registry.addViewController("/speciality-find").setViewName("speciality/speciality-find");
		
		
		registry.addViewController("/diagnostic").setViewName("diagnostic/diagnostic");
		registry.addViewController("/diagnostic-add").setViewName("diagnostic/diagnostic-add");
		registry.addViewController("/diagnostic-remove").setViewName("diagnostic/diagnostic-remove");
		
		
	}
	
	@Bean
	 RestTemplate restTemplate() {
		return new RestTemplateBuilder().build();
	}

}
