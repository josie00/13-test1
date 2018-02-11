package com.cfs.formbean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class CreateEmployeeForm {

	@NotEmpty
	@NotNull
	private String userName;
	
	@NotEmpty
	@Size(min = 3,max = 20)
    private String password;
	
	@NotEmpty
	@Size(min = 1,max = 20)
    private String firstName;
	
	@NotEmpty
	@Size(min = 1,max = 20)
    private String lastName;
	
	private String button;
	
    public CreateEmployeeForm() {
		
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
    
	public String getButton() {
        return button;
    }
    
    public void setButton(String button) {
        this.button = button;
    }
}
