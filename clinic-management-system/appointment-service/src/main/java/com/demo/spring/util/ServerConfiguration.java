package com.demo.spring.util;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class ServerConfiguration  {
	
	private String patientServer = "http://localhost:8181";
	private String appointmentServer = "http://localhost:8183";
	private String clinicServer = "http://localhost:8185";
	
	public String getPatientServer() {
		return patientServer;
	}
	public void setPatientServer(String patientServer) {
		this.patientServer = patientServer;
	}
	public String getAppointmentServer() {
		return appointmentServer;
	}
	public void setAppointmentServer(String appointmentServer) {
		this.appointmentServer = appointmentServer;
	}
	public String getClinicServer() {
		return clinicServer;
	}
	public void setClinicServer(String clinicServer) {
		this.clinicServer = clinicServer;
	}
}