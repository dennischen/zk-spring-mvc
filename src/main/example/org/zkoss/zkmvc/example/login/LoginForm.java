package org.zkoss.zkmvc.example.login;

public class LoginForm {

	String name;
	String password;
	public LoginForm() {
		
	}
	public LoginForm(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
