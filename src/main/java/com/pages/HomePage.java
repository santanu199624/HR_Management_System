package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.actions.ActionsClass;
import com.baseclass.Base_Class;

public class HomePage {
	private ActionsClass actionClass;

	// Initialize the action class object by passing the webdriver instance
//		public HomePage(WebDriver driver) {
//			this.actionClass =  new ActionsClass(driver);
//		}

	public HomePage(WebDriver driver) {
		this.actionClass = Base_Class.getActionDriver();
	}

	// Define locators using By class
	private By adminTab = By.xpath("//span[text()='Admin']");
	private By userIdButton = By.className("//p[@class='oxd-userdropdown-name']");
	private By logoutBtn = By.xpath("Logout");
	private By logo = By.xpath("//div[@class='oxd-brand-banner']//img");

	// method to verify if admin tab is visible
	public boolean isAdminTabVisible() {
		return actionClass.isDisplayed(adminTab);
	}

	// verify the logo
	public boolean verifyLogo() {
		return actionClass.isDisplayed(logo);
	}

	// method to perform logout operation
	public void logout() {
		actionClass.click(userIdButton);
		actionClass.click(logoutBtn);
	}
}
