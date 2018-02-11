package com.cfs.service;

import org.springframework.stereotype.Service;

@Service
public class ButtonService {
    public boolean invalidButton(String buttonInput) {
        if (buttonInput.matches(".*[<>\"].*")) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean buttonMismatch(String buttonInput, String buttonAction) {
        if (!buttonInput.equals(buttonAction)) {
            return true;
        } else {
            return false;
        }
    }
}
