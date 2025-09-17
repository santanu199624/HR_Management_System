package com.baseclass;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.apache.hc.core5.util.Timeout;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class Base_Class {

	protected Properties prop;
	protected WebDriver driver;
	FileInputStream fis;

	@BeforeMethod
	public void setUp() throws IOException, InterruptedException {
		// load configuration
		prop = new Properties();
		fis = new FileInputStream("src/main/resources/config.properties");
		prop.load(fis);

		// initialize the browser based on the properties file
		String browserName = prop.getProperty("browser");
		if (browserName.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else {
			throw new IllegalArgumentException("Browser not supported!" + browserName);
		}

		// Implicit wait
		int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

		// maximize the driver
		driver.manage().window().maximize();

		// navigate the url
		driver.get(prop.getProperty("url"));
		Thread.sleep(3000);


	}
	

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	// Driver getter method
		public WebDriver getDriver(){
			return driver;
		}
		
		// static wait for pause
		public void staticWait(int seconds) {
			LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
		}

}
