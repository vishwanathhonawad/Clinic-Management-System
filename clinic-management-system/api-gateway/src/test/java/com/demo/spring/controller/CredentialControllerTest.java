package com.demo.spring.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.demo.spring.entity.Credentials;
import com.demo.spring.util.Message;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CredentialControllerTest {
	
	@LocalServerPort
	int port;
	
	@Autowired
	TestRestTemplate testRestTemplate;
	
	@Test
	void testFinduserSuccess() throws Exception {
		Credentials credentials = new Credentials("hi", "hi");
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Credentials> req = new HttpEntity<>(credentials, headers);
		ResponseEntity<Message> resp2 = testRestTemplate.exchange("http://localhost:"+port+"/find",HttpMethod.POST,req,Message.class);
		Assertions.assertEquals("User Not Found",resp2.getBody().getStatus());
	}
	@Test
	void testFinduserFailure() throws Exception {
		Credentials credentials = new Credentials("admin", "Ge4@feh5");
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Credentials> req = new HttpEntity<>(credentials, headers);
		ResponseEntity<Message> resp2 = testRestTemplate.exchange("http://localhost:"+port+"/find",HttpMethod.POST,req,Message.class);
		Assertions.assertEquals("User Found",resp2.getBody().getStatus());
	}
	@Test
	void testUpdateUserFailure() throws Exception {
		Credentials credentials = new Credentials("hi", "hi");
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Credentials> req = new HttpEntity<>(credentials, headers);
		ResponseEntity<Message> resp2 = testRestTemplate.exchange("http://localhost:"+port+"/update",HttpMethod.PATCH,req,Message.class);
		Assertions.assertEquals("User Not Found",resp2.getBody().getStatus());
	}
	@Test
	void testUpdateUserSuccess() throws Exception {
		Credentials credentials = new Credentials("admin", "Ge4@feh5");
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Credentials> req = new HttpEntity<>(credentials, headers);
		ResponseEntity<Message> resp2 = testRestTemplate.exchange("http://localhost:"+port+"/update",HttpMethod.PATCH,req,Message.class);
		Assertions.assertEquals("User Updated",resp2.getBody().getStatus());
	}
	@Test
	void testCreateUserSuccess() throws Exception {
		Credentials credentials = new Credentials("admin11", "Ge4@feh5");
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Credentials> req = new HttpEntity<>(credentials, headers);
		ResponseEntity<Message> resp2 = testRestTemplate.exchange("http://localhost:"+port+"/save",HttpMethod.POST,req,Message.class);
		Assertions.assertEquals("User Saved",resp2.getBody().getStatus());
	}
	@Test
	void testCreateUserfailure() throws Exception {
		Credentials credentials = new Credentials("admin", "Ge4@feh5");
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Credentials> req = new HttpEntity<>(credentials, headers);
		ResponseEntity<Message> resp2 = testRestTemplate.exchange("http://localhost:"+port+"/save",HttpMethod.POST,req,Message.class);
		Assertions.assertEquals("User Exists",resp2.getBody().getStatus());
	}
	@Test
	void testCreateUserfailure2() throws Exception {
		Credentials credentials = new Credentials("admin5", "admin");
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Credentials> req = new HttpEntity<>(credentials, headers);
		ResponseEntity<Message> resp2 = testRestTemplate.exchange("http://localhost:"+port+"/save",HttpMethod.POST,req,Message.class);
		Assertions.assertEquals("Invalid Password",resp2.getBody().getStatus());
	}
}
