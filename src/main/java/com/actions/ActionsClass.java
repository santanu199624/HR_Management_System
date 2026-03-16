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
	}
	
	
	
	// Method to click on an element
		public void click(By by) {
			String elementDescription = getElementDescription(by);
			try {
//				applyBorder(by, "green");
				waitForElementToBeClickable(by);
				driver.findElement(by).click();
//				ExtentManager.logStep("Clicked an element : "+elementDescription);
				logger.info("Clicked an element-->"+elementDescription);
			} catch (Exception e) {
				applyBorder(by, "red");
//				System.out.println("Unable to click the element" + e.getMessage());
//				ExtentManager.logFailure(Base_Class.getDriver(),"Unable to click element : ", elementDescription);
				logger.error("Unable to click element!");
			}
		}
		
		// Method to enter text into input field
		public void enterText(By by, String value) {
			try {
				waitForElementToBeVisible(by);
//				applyBorder(by, "green");
//				driver.findElement(by).clear();
//				driver.findElement(by).sendKeys(value);
				WebElement element = driver.findElement(by);
				element.clear();
				element.sendKeys(value);
				logger.info("Entered text on :"+getElementDescription(by)+"--> "+value);
			} catch (Exception e) {
				applyBorder(by,"red");
//				System.out.println("Unable to enter the value:"+e.getMessage());
				logger.error("Unable to enter the value into input box" + e.getMessage());
			}
		}
		
		// Method to get text from input field
		public String getText(By by) {
			try {
				waitForElementToBeVisible(by);
//				applyBorder(by, "green");
				return driver.findElement(by).getText();
			} catch (Exception e) {
//				applyBorder(by, "red");
				logger.error("Unable to get the text: " + e.getMessage());
//				System.out.println("Unable to get the text:"+e.getMessage());
				return "";
			}
		}
		
		
		// method to compare two text
		public boolean compareTwoText(By by, String expectedText) {
			try {
				waitForElementToBeVisible(by);
				String actualText = driver.findElement(by).getText();
				if (expectedText.equals(actualText)) {
//					System.out.println("Text are matching:" + actualText + " equals " + expectedText);
//					applyBorder(by, "green");
					logger.info("Text are matching :" + actualText + " equals " + expectedText);
//					ExtentManager.logStepWithScreenshot(Base_Class.getDriver(), "Compare text", "Text verified Successfully!"+actualText+" equals "+expectedText);
					return true;
				} else {
//					System.out.println("Text are not matching :" + actualText + " not equals " + expectedText);
//					applyBorder(by, "red");
					logger.error("Text are not matching :" + actualText + " not equals " + expectedText);
//					ExtentManager.logStepWithScreenshot(Base_Class.getDriver(),  "Text Comparison Failed!", "Text Text comparison failed"+actualText+" not equals "+expectedText);
					return false;
				}
			} catch (Exception e) {
//				System.out.println("Unable to compare the text :" + e.getMessage());
				logger.error("Unable to compare the text :" + e.getMessage());
			}
			return false;
		}
		
		// Method to check if an element is displayed
		public boolean isdisplayed(By by) {
			try {
				waitForElementToBeVisible(by);
				boolean isdisplayed = driver.findElement(by).isDisplayed();
				if (isdisplayed) {
					System.out.println("Element is Displayed");
//					applyBorder(by, "green");
					logger.info("Element is displayed: "+getElementDescription(by));
//					ExtentManager.logStep("Element is displayed"+getElementDescription(by));
//					ExtentManager.logStepWithScreenshot(Base_Class.getDriver(), "Element is displayed: ", "Element is displayed: "+getElementDescription(by));
					
					return isdisplayed;
				} else {
//					applyBorder(by, "red");
					return isdisplayed;
				}
			} catch (Exception e) {
//				System.out.println("Element is not displayed"+e.getMessage());
//				applyBorder(by, "red");
				logger.error("Element is not displayed"+e.getMessage());
//				ExtentManager.logFailure(Base_Class.getDriver(), "Element is not displayed","Element is not displayed: "+getElementDescription(by));
				return false;
			}
		}
		
		// Wait for the page to load
		public void waitForPageLoad(int timeOutInSec){
			try {
				wait.withTimeout(Duration.ofSeconds(timeOutInSec))
				.until(WebDriver -> ((JavascriptExecutor) WebDriver)
				.executeScript("return document.readyState").equals("complete"));
			}catch(Exception e) {
				logger.error("Page did not load within: "+timeOutInSec+"seconds.Exception: "+e.getMessage());
//				System.out.println("Page did not load within: "+timeOutInSec+"seconds.Exception: "+e.getMessage());
			}
		}
		
		// Scroll to element
		public void scrollToElement(By by) {
			try {
//				applyBorder(by, "green");
				JavascriptExecutor js = (JavascriptExecutor)driver;
				WebElement element = driver.findElement(by);
				js.executeScript("arguments[0].scrollIntoView(true);", element);
			} catch (Exception e) {
//				applyBorder(by, "red");
				logger.error("Unable to locate element"+e.getMessage());
//				System.out.println("Unable to locate element"+e.getMessage());
			}
		}
		

	// Wait for element to be clickable
	private void waitForElementToBeClickable(By by) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			logger.error("Element is not clickable: " + e.getMessage());
//			System.out.println("Element is not clickable: "+e.getMessage());
		}
	}
	
	

	// Wait for element to be visible
	private void waitForElementToBeVisible(By by) {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} catch (Exception e) {
			logger.error("Element is not visible: " + e.getMessage());
//			System.out.println("Element is not visible: "+e.getMessage());
		}
	}

	

	

	

	// Method to check if an element is displayed
	public boolean isDisplayed(By by) {
		try {
			waitForElementToBeVisible(by);
			boolean isDisplayed = driver.findElement(by).isDisplayed();
			if (isDisplayed) {
//				applyBorder(by, "green");
				logger.info("Element is displayed: "+getElementDescription(by));
//				ExtentManager.logStep("Element is displayed"+getElementDescription(by));
//				ExtentManager.logStepWithScreenshot(Base_Class.getDriver(), "Element is displayed: ", "Element is displayed: "+getElementDescription(by));
				
				return isDisplayed;
			} else {
//				applyBorder(by, "red");
				return isDisplayed;
			}
		} catch (Exception e) {
//			applyBorder(by, "red");
			logger.error("Element is not displayed"+e.getMessage());
//			ExtentManager.logFailure(Base_Class.getDriver(), "Element is not displayed","Element is not displayed: "+getElementDescription(by));
			return false;
		}
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
			String name = element.getDomProperty("name");
			String id = element.getDomProperty("id");
			String text = element.getText();
			String className = element.getDomProperty("class");
			String placeHolder = element.getDomProperty("placeholder");
			
			//Return the description based on element attribute
			if(isNotEmpty(name)) {
				return "Element with name:"+name;
			}else if(isNotEmpty(id)) {
				return "Element with id:"+id;
			}else if(isNotEmpty(text)) {
				return "Element with text:"+truncate(text, 50);
			}else if(isNotEmpty(className)) {
				return "Element with className:"+className;
			}else if(isNotEmpty(placeHolder)) {
				return "Element with placeholder:"+placeHolder;
			}else {
				return "Element located using:"+locator.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Unable to describe element due to error: "+e.getMessage());	
			
		}
		return "Unable to describe element due to error";
		
		
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
	
	// Utility method to border an element
	public void applyBorder(By by, String color) {
		try {
			// Locate the element
			WebElement element = driver.findElement(by);
			//Apply the border
			String script = "arguments[0].style.border='3px solid "+color+"'";
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript(script, element);
			logger.info("Applied the border with color "+color+" to element "+getElementDescription(by));
		} catch (Exception e) {
			logger.warn("Failed to apply the border to an element: "+getElementDescription(by), e);
		}
	}

}
