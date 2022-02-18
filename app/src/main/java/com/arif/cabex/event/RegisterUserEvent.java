package com.arif.cabex.event;

public class RegisterUserEvent {
	private String userName, password;

	public RegisterUserEvent(String userName, String password) {
		this.userName = userName;
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}
}
