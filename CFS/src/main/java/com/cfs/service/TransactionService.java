package com.cfs.service;

import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    public boolean numberError(String numberInput) {
        try {
          Double.parseDouble(numberInput);
        } catch(NumberFormatException e) {
            return true;
        }
        if (Double.parseDouble(numberInput) <= 0 || Double.parseDouble(numberInput) > 100000) {
            return true;
        } else {
            return false;
        }
        
    }
         
}
