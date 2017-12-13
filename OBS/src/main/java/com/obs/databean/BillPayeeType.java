package com.obs.databean;
import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class BillPayeeType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private long billPayeeTypeId;
	private String name;
	
	@OneToMany(mappedBy = "billPayeeType", cascade = CascadeType.ALL)
	private Set<BillPayee> billPayees;
	
	protected BillPayeeType() {}

	public BillPayeeType(String name, Set<BillPayee> billPayees) {
		this.name = name;
		this.billPayees = billPayees;
	}


	@Override
	public String toString() {
		return name;
	}
	
}
