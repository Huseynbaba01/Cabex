package com.arif.cabex.model;

public class User {
	private String name;
	private String email;
	private int age;
	private boolean isDriver = false;

	public User(){

	}

	public User(String name, String email) {
		this.name = name;
		this.email = email;
	}

	public User(String name, String email, int age, boolean isDriver) {
		this.name = name;
		this.email = email;
		this.age = age;
		this.isDriver = isDriver;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isDriver() {
		return isDriver;
	}

	public void setDriver(boolean driver) {
		isDriver = driver;
	}
}
