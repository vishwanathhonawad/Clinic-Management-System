package com.demo.spring.dto;

public class DiagnosticDTO {

	private int testcId;
	private String testName;

	public DiagnosticDTO() {
	}

	public DiagnosticDTO(int testcId, String testName) {
		super();
		this.testcId = testcId;
		this.testName = testName;
	}

	public int getTestcId() {
		return testcId;
	}

	public void setTestcId(int testcId) {
		this.testcId = testcId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

}
