package com.actions;

import java.time.Duration;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.baseclass.Base_Class;
import com.utilities.ExtentManager;

public class ActionsClass {

	private WebDriver driver;
	private WebDriverWait wait;
	public static final Logger logger = Base_Class.logger;

	// Constructor
	public ActionsClass(WebDriver driver) {
		this.driver = driver;
		int explicitlyWait = Integer.parseInt(Base_Class.getProp().getProperty("explicitWait"));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitlyWait));
		logger.info("WebDriver instance is created!");
//		System.out.println("WebDriver instance is created!");
	}
	
	// Wait for the page to load
	public void waitForPageLoad(int timeOutInSec){
		try {
			wait.withTimeout(Duration.ofSeconds(timeOutInSec))
			.until(WebDriver -> ((JavascriptExecutor) WebDriver)
			.executeScript("return document.readyState").equals("complete"));
		}catch(Exception e) {
			System.out.println("Page did not load within: "+timeOutInSec+"seconds.Exception: "+e.getMessage());
		}
	}
	
	// Scroll to element
	public void scrollToElement(By by) {
		try {
			JavascriptExecutor js = (JavascriptExecutor)driver;
			WebElement element = driver.findElement(by);
			js.executeScript("arguments[0], scrollIntoView(true);", element);
		} catch (Exception e) {
			logger.error("Unable to locate element"+e.getMessage());
		}
	}

	// Wait for element to be clickable
	private void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			logger.error("Element is not clickable: " + e.getMessage());
		}
	}

	// Wait for element to be visible
	private void waitForElementToBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
		} catch (Exception e) {
			logger.error("Element is not visible: " + e.getMessage());
		}
	}

	// Method to click on an element
	public void click(By by) {
		String elementDescription = getElementDescription(by);
		try {
			waitForElementToBeClickable(by);
			driver.findElement(by).click();
//			ExtentManager.logStep("Clicked an element!");
			logger.info("Clicked an element-->"+elementDescription);
		} catch (Exception e) {
			System.out.println("Unable to click the element" + e.getMessage());
//			ExtentManager.logFailure(,"Unable to click element");
			logger.error("Unable to click element!");
		}
	}

	// Method to enter text into input field
	public void enterText(By by, String value) {
		try {
			waitForElementToBeVisible(by);
			driver.findElement(by).clear();
			driver.findElement(by).sendKeys(value);
			logger.info("Enter text on :"+getElementDescription(by)+" "+value);
		} catch (Exception e) {
			logger.error("Unable to enter the value into input box" + e.getMessage());
		}
	}

	// Method to get text from input field
	public String getText(By by) {
		try {
			waitForElementToBeVisible(by);
			return driver.findElement(by).getText();
		} catch (Exception e) {
			logger.error("Unable to get the text: " + e.getMessage());
			return "";
		}
	}

	// Method to check if an element is displayed
	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			boolean isDisplayed = driver.findElement(by).isDisplayed();
			if (isDisplayed) {
				logger.info("Element is displayed: "+getElementDescription(by));
//				ExtentManager.logStep("Element is displayed");
				return isDisplayed;
			} else {
				return isDisplayed;
			}
		} catch (Exception e) {
			logger.error("Element is not displayed");
			return false;
		}
	}

	// method to compare two text
	public boolean compareTwoText(By by, String expectedText) {
		try {
			waitForElementToBeVisible(by);
			String actualText = driver.findElement(by).getText();
			if (expectedText.equals(actualText)) {
				logger.info("Text are matching :" + actualText + " equals " + expectedText);
//				ExtentManager.logStepWithScreenshot(driver, "Compare text", "Text verified successfully!");
				return true;
			} else {
				logger.error("Text are not matching :" + actualText + " not equals " + expectedText);
				return false;
			}
		} catch (Exception e) {
			logger.error("Unable to compare the text :" + e.getMessage());
		}
		return false;
	}
	
	// Method to get the description of an element using by locator
	public String getElementDescription(By locator) {
		// Check for null driver or locator to avoid null pointer exception
		if(driver == null) {
			return "driver is null";
		}
		if(locator == null) {
			return "Locator is null";
		}
		
		try {
			// Find the element using the locator
			WebElement element = driver.findElement(locator);
			
			// Get element description
			String name = element.getDomAttribute("name");
			String id = element.getDomAttribute("id");
			String text = element.getText();
			String className = element.getDomAttribute("class");
			String placeHolder = element.getDomAttribute("placeholder");
			
			//Return the description based on element attribute
			if(isNotEmpty(name)) {
				return "Element with name:"+name;
			}else if(isNotEmpty(name)) {
				return "Element with id:"+id;
			}else if(isNotEmpty(text)) {
				return "Element with text:"+truncate(text, 50);
			}else if(isNotEmpty(className)) {
				return "Element with className:"+className;
			}else if(isNotEmpty(placeHolder)) {
				return "Element with placeholder:"+placeHolder;
			}
		} catch (Exception e) {
			logger.error("Unable to describe the element:"+e.getMessage());
		}
		return "Unable to describe the element";
	}
	
	//Utility method to check a String is not null or empty
	private boolean isNotEmpty(String value) {
		return value != null && value.isEmpty();
	}
	
	// utility method to truncate long string
	private String truncate(String value, int maxLength) {
		if(value == null || value.length() <= maxLength) {
			return value;
		}
		return value.substring(0, maxLength)+"...";
	}

}
