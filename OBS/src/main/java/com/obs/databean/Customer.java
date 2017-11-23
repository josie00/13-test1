package com.obs.databean;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Customer {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long customerId;
	
	@Column(unique=true)
	private String userName;
	private String email;
	private String password;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private Date dateOfBirth;
    private String ssn;
    private String driverLicense;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Account> accounts;
    
    protected Customer() {}

	public Customer(String userName, String email, String password, String firstName, String lastName, String street,
			String city, String state, String zip, String phone, Date dateOfBirth, String ssn, String driverLicense,
			Set<Account> accounts) {
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
		this.dateOfBirth = dateOfBirth;
		this.ssn = ssn;
		this.driverLicense = driverLicense;
		this.accounts = accounts;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", userName=" + userName + ", email=" + email + ", password="
				+ password + ", firstName=" + firstName + ", lastName=" + lastName + ", street=" + street + ", city="
				+ city + ", state=" + state + ", zip=" + zip + ", phone=" + phone + ", dateOfBirth=" + dateOfBirth
				+ ", ssn=" + ssn + ", driverLicense=" + driverLicense + ", accounts=" + accounts + "]";
	}
    
}
