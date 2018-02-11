package com.cfs.formbean;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class ChangePasswordForm {
    @NotEmpty
    @Size(min = 3,max = 20)
    private String currentPassword;
    @NotEmpty
    @Size(min = 3,max = 20)
    private String newPassword;
    @NotEmpty
    @Size(min = 3,max = 20)
    private String confirmPassword;
    @NotEmpty
    private String button;
    
    public ChangePasswordForm() {
        
    }
    
    public String getCurrentPassword() {
        return currentPassword;
    }
    
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }
    
    public String getNewPassword() {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword.trim();
    }
    
    public String getConfirmPassword() {
        return confirmPassword;
    }
    
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword.trim();
    }
    
    public String getButton() {
        return button;
    }
    
    public void setButton(String button) {
        this.button = button;
    }
}
