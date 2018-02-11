package com.cfs.formbean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginForm {
	@NotEmpty
	@NotNull
	private String userName;
	
	@Size(min = 3,max = 20)
	private String password;
	
	@NotEmpty
	private String identity;
	
	@NotEmpty
	private String button;
	
	public LoginForm() {
		
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

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }
	
}
