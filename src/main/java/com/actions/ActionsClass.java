package com.actions;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionsClass {

	private WebDriver driver;
	private WebDriverWait wait;

	public ActionsClass(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// Wait for element to be clickable
	private void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			System.out.println("Element is not clickable: " + e.getMessage());
		}
	}

	// Wait for element to be visible
	private void waitForElementToBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
		} catch (Exception e) {
			System.out.println("Element is not visible: " + e.getMessage());
		}
	}

	// Method to click on an element
	public void click(By by) {
		try {
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
		} catch (Exception e) {
			System.out.println("Unable to click the element"+e.getMessage());
		}
	}
	
	// Method to enter text into input field
	public void enterText(By by, String value) {
		try {
			waitForElementToBeVisible(by);
			driver.findElement(by).clear();
			driver.findElement(by).sendKeys(value);
		} catch (Exception e) {
			System.out.println("Unable to enter the value into input box"+e.getMessage());
		}
	}
	
	// Method to get text from input field
	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			return driver.findElement(by).getText();
		} catch (Exception e) {
			System.out.println("Unable to get the text: "+e.getMessage());
			return "";
		}
	}
	
	// Method to check if an element is displayed
	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			boolean isDisplayed = driver.findElement(by).isDisplayed();
			if(isDisplayed) {
				System.out.println("Element is visible");
				return isDisplayed;
			}else {
				return isDisplayed;
			}
		} catch (Exception e) {
			System.out.println("Element is not displayed");
			return false;
		}
	}
	
	// method to compare two text
	public boolean compareTwoText(By by, String expectedText) {
		try {
			waitForElementToBeVisible(by);
			String actualText = driver.findElement(by).getText();
			if(expectedText.equals(actualText)) {
				System.out.println("Text are matching :"+actualText+" equals "+expectedText);
				return true;
			}else {
				System.out.println("Text are not matching :"+actualText+" not equals "+expectedText);
				return false;
			}
		} catch (Exception e) {
			System.out.println("Unable to compare the text :"+e.getMessage());
		}
		return false;
	}

}
