package com.obs.databean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long customerId;
	private String userName;
	private String email;
	private String password;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String zip;
    private String phone;
    private String dateOfBirth;
    private String ssn;
    private String driverLicense;
    
    
    protected Customer() {}

	public Customer(Long customerId, String userName, String email, String password, String firstName, String lastName,
			String street, String city, String zip, String phone, String dateOfBirth, String ssn,
			String driverLicense) {
		this.customerId = customerId;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.phone = phone;
		this.dateOfBirth = dateOfBirth;
		this.ssn = ssn;
		this.driverLicense = driverLicense;
	}


	@Override
	public String toString() {
		return "Customer [id=" + customerId + ", userName=" + userName + ", email=" + email + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", street=" + street + ", city=" + city
				+ ", zip=" + zip + ", phone=" + phone + ", dateOfBirth=" + dateOfBirth + ", ssn=" + ssn
				+ ", driverLicense=" + driverLicense + "]";
	}
	
	
    
  
    
    
    
}
