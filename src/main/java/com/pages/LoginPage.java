package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.actions.ActionsClass;

public class LoginPage {
	
	private ActionsClass actionClass;
	
	
	
	// Define locators using By class
	private By userNameField = By.name("username");
	private By passwordField = By.name("password");
	private By loginBtn = By.xpath("//button[text()=' Login ']");
	private By errorMessage = By.xpath("//p[text()='Invalid credentials']");
	
	// Initialize the action class object by passing the webdriver instance
	public LoginPage(WebDriver driver) {
		this.actionClass =  new ActionsClass(driver);
	}
	
	// Method to perform login
	public void login(String username, String password) {
		actionClass.enterText(userNameField, "Admin");
		actionClass.enterText(passwordField, "admin123");
		actionClass.click(loginBtn);
	}
	
	// Method to check if error message is dispalyed
	public Boolean isErrorMessageDisplayed() {
		return actionClass.isDisplayed(errorMessage);
	}
	
	// Method to get text from the error message
	public String getErrorMessage() {
		return actionClass.getText(errorMessage);
	}
	
	// Verify if error is correct or not
	public void verifyErrorMessage(String expectedError) {
		actionClass.compareTwoText(errorMessage, expectedError);
	}

}
