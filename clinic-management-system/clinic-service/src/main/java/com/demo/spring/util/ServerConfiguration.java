package com.demo.spring.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class ServerConfiguration {

	private String patientServer = "http://localhost:8181";

	public String getPatientServer() {
		return patientServer;
	}

	public void setPatientServer(String patientServer) {
		this.patientServer = patientServer;
	}

}