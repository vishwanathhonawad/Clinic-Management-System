package com.demo.spring.dto;

public class Credentials {

	private String userName;
	private String password;
	private String passwordTwo;
	
	public Credentials(String userName, String password, String passwordTwo) {
		super();
		this.userName = userName;
		this.password = password;
		this.passwordTwo = passwordTwo;
	}

	public String getPasswordTwo() {
		return passwordTwo;
	}

	public void setPasswordTwo(String passwordTwo) {
		this.passwordTwo = passwordTwo;
	}

	public Credentials() {
	}

	public Credentials(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
