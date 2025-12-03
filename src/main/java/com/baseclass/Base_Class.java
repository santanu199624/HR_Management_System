package com.baseclass;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.hc.core5.util.Timeout;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.actions.ActionsClass;
import com.utilities.ExtentManager;
import com.utilities.LoggerManager;

public class Base_Class {

	protected static Properties prop;
//	protected static WebDriver driver;
//	private static ActionsClass actionClass;
	
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
	private static ThreadLocal<ActionsClass> actionClass = new ThreadLocal<>();
	
	public static final Logger logger = LoggerManager.getLogger(Base_Class.class);
	FileInputStream fis;


	@BeforeSuite
	public void loadConfig() throws IOException {
		// load the configuration file
		prop = new Properties();
		fis = new FileInputStream("src/main/resources/config.properties");
		prop.load(fis);
		logger.info("config.properties file loaded!");
	}

	@BeforeMethod
	public void setUp() throws IOException, InterruptedException {
		System.out.println("Setting webdriver for:" + this.getClass().getSimpleName());
		launchBrowser();
		configureBrowser();
		staticWait(5);
		
		logger.info("WebDriver initialized and Borwser maximized!");
		logger.trace("This is a Trace message");
		logger.error("This is a Error message");
		logger.debug("This is a Debug message");
		logger.fatal("This is a Fatal message");
		logger.warn("This is a Fatal message");
		
		// Initialize the action Driver only once
//		if(actionClass == null) {
//			actionClass = new ActionsClass(driver);
//			System.out.println("ActionClass instance is created.");
//			logger.info("Action driver instance is created!"+Thread.currentThread().getId());
//		}
		
		// Initialize action driver for the current thread
		actionClass.set(new ActionsClass(getDriver()));
		logger.info("ActionDriver initialized for thread: "+Thread.currentThread().getId());

	}

	private void launchBrowser() {
		// initialize the browser based on the config.properties file
		String browserName = prop.getProperty("browser");
		if (browserName.equalsIgnoreCase("chrome")) {
//			driver = new ChromeDriver();
			driver.set(new ChromeDriver());
			logger.info("ChromeDriver instance is created!!");
		} else if (browserName.equalsIgnoreCase("edge")) {
//			driver = new EdgeDriver();
			driver.set(new EdgeDriver());
			logger.info("EdgeDriver instance is created!!");
		} else if (browserName.equalsIgnoreCase("firefox")) {
//			driver = new FirefoxDriver();
			driver.set(new FirefoxDriver());
			logger.info("FirefoxDriver instance is created!!");
		} else {
			throw new IllegalArgumentException("Browser not supported!" + browserName);
		}
	}

	// Configure browser settings such as implicit wait, max browser and navigate to
	// the url
	private void configureBrowser() {
		// Implicit wait
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

		// maximize the driver
		getDriver().manage().window().maximize();

		// navigate the url
		try {
			getDriver().get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("Failed to navigate to the url:"+e.getMessage());
		}
	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			try {
				getDriver().quit();
			} catch (Exception e) {
				System.out.println("Unable to quit the driver:"+e.getMessage());
			}
		}
		logger.info("WebDriver instance is closed.");
		driver.remove();
		actionClass.remove();
//		driver = null;
//		actionClass = null;
	}
	
	// Getter method for prop
	public static Properties getProp() {
		return prop;
	}
	
//	// Driver getter method
//	public WebDriver getDriver() {
//		return driver;
//	}
	
	// Getter method for WebDriver
	public static WebDriver getDriver() {
		if(driver.get() == null) {
		System.out.println("WebDriver is not initialized.");
		throw new IllegalStateException("WebDriver is not initialized");
	}
		return driver.get();
	}
	
	// Getter method for action driver
		public static ActionsClass getActionDriver() {
			if(actionClass.get() == null) {
			System.out.println("Action driver is not initialized.");
			throw new IllegalStateException("Action driver is not initialized");
		}
			return actionClass.get();
		}
	
	// Driver setter method
	public void setDriver(ThreadLocal<WebDriver> driver) {
		this.driver = driver;
	}

	// static wait for pause
	public void staticWait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
	}

}
