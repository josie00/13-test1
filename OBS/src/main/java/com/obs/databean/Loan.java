package com.obs.databean;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Loan {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long loanId;
	private Date startDate;
	private Date nextDueDate;
	private String status;
	private double duePayment;
	private double totalAmount;
	
	@ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "loan_type_id")
	private LoanType loanType;
	
	@ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "customer_id")
	private Customer customer;
	
	
	
	
	@OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
	private Set<Transaction> transactions;
	
	protected Loan() {}

	
}
