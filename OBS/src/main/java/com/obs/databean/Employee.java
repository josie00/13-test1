package com.obs.databean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String title;
    private String userName;
    private String password;
    private String email;

 
    
    protected Employee() {}

	public Employee(String firstName, String lastName, String title, String userName, String password, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.userName = userName;
		this.password = password;
		this.email = email;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName + ", title="
				+ title + ", userName=" + userName + ", password=" + password + ", email=" + email + 
				 "]";
	}
	
}
