package com.demo.spring.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.entity.Diagnostic;
import com.demo.spring.repository.DiagnosticRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DiagnosticControllerTest {

	@Autowired
	MockMvc mvc;

	@Autowired
	TestRestTemplate testRestTemplate;
	
	@MockBean
	DiagnosticRepository diagnosticRepository;

	
	@Test
	void testListAllDiagnosticSuccess() throws Exception {
		Diagnostic diagnostic=new Diagnostic(1,"nurology");
		ObjectMapper mapper=new ObjectMapper();
		List<Diagnostic> listdiagnostic=new ArrayList<>();
		listdiagnostic.add(diagnostic);
		String diagnosticJson=mapper.writeValueAsString(listdiagnostic);
		
		when(diagnosticRepository.findAll()).thenReturn(listdiagnostic);
		
		mvc.perform(get("/clinic/diagnostic/list")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().json(diagnosticJson));
	}
	@Test
	void testListAllDiagnosticFailure() throws Exception {
		when(diagnosticRepository.findAll()).thenReturn(new ArrayList<>());
		
		mvc.perform(get("/clinic/diagnostic/list")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("There are No Diagnostic Tests exists"));
	}

	@Test
	void testSaveDiagnosticSuccess() throws Exception {
		Diagnostic diagnostic=new Diagnostic(1,"nurology");
		ObjectMapper mapper=new ObjectMapper();
		String diagnosticJson=mapper.writeValueAsString(diagnostic);
		
		when(diagnosticRepository.findByDiagnosticName("nurology")).thenReturn(new ArrayList<>());
		
		mvc.perform(post("/clinic/diagnostic/save").content(diagnosticJson)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Test Saved Successfully"));
	}
	
	@Test
	void testSaveDiagnosticFailure() throws Exception {
		Diagnostic diagnostic=new Diagnostic(1,"nurology");
		ObjectMapper mapper=new ObjectMapper();
		String diagnosticJson=mapper.writeValueAsString(diagnostic);
		List<Diagnostic> listDiagnostic=new ArrayList<>();
		listDiagnostic.add(diagnostic);
		
		when(diagnosticRepository.findByDiagnosticName("nurology")).thenReturn(listDiagnostic);
		
		mvc.perform(post("/clinic/diagnostic/save").content(diagnosticJson)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Diagnostic test alredy exists"));
	}

	@Test
	void testDeleteDiagnosticSuccess() throws Exception {
		when(diagnosticRepository.existsById(1)).thenReturn(true);
		
		mvc.perform(delete("/clinic/diagnostic/delete/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Test deleted Successfully"));
	}
	@Test
	void testDeleteDiagnosticFailure() throws Exception {
		when(diagnosticRepository.existsById(1)).thenReturn(false);
		
		mvc.perform(delete("/clinic/diagnostic/delete/1")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Diagnostic test not exists"));
	}

}
