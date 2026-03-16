package testCases.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.baseclass.Base_Class;
import com.pages.HomePage;
import com.pages.LoginPage;

import com.baseclass.Base_Class;
import com.pages.HomePage;
import com.pages.LoginPage;
import com.utilities.DataProviders;
import com.utilities.ExtentManager;



public class Home_Page_Test extends Base_Class {
	
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setUpPages(){
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}
	
	//dataProvider="validLoginData", dataProviderClass = DataProviders.class
	@Test()
	public void verifyOrrangeHRMLogo(){
//		ExtentManager.startTest("Home page logo test"); -- This will be implemented in TestListener
//		ExtentManager.logStep("Navigating to Login Page entering username and password");
		loginPage.login("Admin", "admin123");
//		ExtentManager.logStep("Verifying logo is visible or not");
		Assert.assertTrue(homePage.verifyLogo(), "Logo is not visible");
//		ExtentManager.logStep("Validation Successful");
		homePage.logout();
//		ExtentManager.logStep("Logged out successful");
	}

}
