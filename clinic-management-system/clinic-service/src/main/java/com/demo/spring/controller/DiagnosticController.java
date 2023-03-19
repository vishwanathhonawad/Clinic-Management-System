package com.demo.spring.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.dto.DiagnosticDTO;
import com.demo.spring.entity.Diagnostic;
import com.demo.spring.exception.DiagnosticTestExistsException;
import com.demo.spring.exception.DiagnosticTestNotExistsException;
import com.demo.spring.exception.NoDiagnosticTestExistsException;
import com.demo.spring.service.DiagnosticService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@RequestMapping("/clinic/diagnostic")
@OpenAPI30
public class DiagnosticController {

	private Logger logger = LogManager.getLogger(this.getClass().getName());
	
	@Autowired
	DiagnosticService diagnosticService;

	/**
	 * this method will return list of all diagnostic
	 * 
	 * @return
	 * @throws NoDiagnosticTestExistsException
	 */
	@GetMapping(path = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "request.list.diagnostic")
	public ResponseEntity<List<Diagnostic>> listAllDiagnostic() throws NoDiagnosticTestExistsException {
		logger.info("This list all diagnostic method had call to service list");
		return ResponseEntity.ok(diagnosticService.listAllDiagnosticTest());
	}

	/**
	 * this method will save a diagnostic
	 * 
	 * @param diagnosticDTO
	 * @return
	 * @throws DiagnosticTestExistsException
	 */
	@PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "request.save.diagnostic")
	public ResponseEntity<Message> saveDiagnostic(@RequestBody DiagnosticDTO diagnosticDTO)
			throws DiagnosticTestExistsException {
		Diagnostic diagnostic = new Diagnostic(diagnosticDTO.getTestcId(), diagnosticDTO.getTestName());
		logger.info("This save diagnostic method had call to save diagnostic service ");
		return ResponseEntity.ok(diagnosticService.saveDiagnosticTest(diagnostic));
	}

	/**
	 * this method will delete a existing diagnostic
	 * 
	 * @param testId
	 * @return
	 * @throws DiagnosticTestNotExistsException
	 */
	@DeleteMapping(path = "/delete/{testId}")
	@Timed(value = "request.delete.diagnostic")
	public ResponseEntity<Message> deleteDiagnostic(@PathVariable("testId") Integer testId)
			throws DiagnosticTestNotExistsException {
		logger.info("This delete diagnostic method had call to delete diagnostic service ");
		return ResponseEntity.ok(diagnosticService.deleteDiagnosticTest(testId));
	}

}
