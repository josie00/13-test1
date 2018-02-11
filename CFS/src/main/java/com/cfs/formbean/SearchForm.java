package com.cfs.formbean;

import org.hibernate.validator.constraints.NotEmpty;

public class SearchForm {

	@NotEmpty
	private String category;

	@NotEmpty
	private String input;
	
	private String button;

	public SearchForm() {
		
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	public String getButton() {
        return button;
    }
    
    public void setButton(String button) {
        this.button = button;
    }
	
}
