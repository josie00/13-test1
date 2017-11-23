package com.obs.databean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Employee {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String title;
    private String userName;
    private String password;
    private String email;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<Transaction> transactions;
    
    protected Employee() {}

	public Employee(String firstName, String lastName, String title, String userName, String password, String email,
			Set<Transaction> transactions) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.title = title;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName + ", title="
				+ title + ", userName=" + userName + ", password=" + password + ", email=" + email + ", transactions="
				+ transactions + "]";
	}
	
}
