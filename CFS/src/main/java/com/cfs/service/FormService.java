package com.cfs.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class FormService {
    public boolean invalid(String formInput) {
        String regex = "^[a-zA-Z0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(formInput);
        if (!matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean addressInvalid(String formInput) {
        String regex = "^[a-zA-Z0-9 ]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(formInput);
        if (!matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean nameInvalid(String nameInput) {
        if (Pattern.matches("[a-zA-Z]+", nameInput) == false) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean zipInvalid(String formInput) {
        String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(formInput);
        if (!matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
