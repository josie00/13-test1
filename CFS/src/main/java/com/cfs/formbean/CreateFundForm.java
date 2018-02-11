package com.cfs.formbean;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class CreateFundForm {

	@NotEmpty
	@NotNull
	private String name;
	
	@NotEmpty
	@NotNull
    private String symbol;
	
	
    public CreateFundForm() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	
	
}
