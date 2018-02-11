package com.cfs.formbean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class CreateCustomerForm {

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
	
	@NotEmpty
    private String addrLine1;
    private String addrLine2;
    
    @NotEmpty
    private String city;
    
    @NotEmpty
    private String state;
    
    @NotEmpty
    private String zip;
    
    
    private String button;
	
    public CreateCustomerForm() {
		
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

	public String getAddrLine1() {
		return addrLine1;
	}

	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	public String getAddrLine2() {
		return addrLine2;
	}

	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
    
	public String getButton() {
        return button;
    }
    
    public void setButton(String button) {
        this.button = button;
    }
	
}
