package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "DIAGNOSTIC")
public class Diagnostic {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "diagnostic_id")
	@SequenceGenerator(sequenceName = "diagnostic_sequence",allocationSize = 1,initialValue = 1,name="diagnostic_id")
	@Column(name = "DIAGNOSTIC_ID")
	private int testcId;

	@Column(name = "DIAGNOSTIC_NAME")
	private String testName;

	public Diagnostic() {
	}

	public Diagnostic(int testcId, String testName) {
		super();
		this.testcId = testcId;
		this.testName = testName;
	}
	

	public Diagnostic(String testName) {
		super();
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
