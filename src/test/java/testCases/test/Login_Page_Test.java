package testCases.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.baseclass.Base_Class;
import com.pages.HomePage;
import com.pages.LoginPage;
import com.utilities.DataProviders;
import com.utilities.ExtentManager;



public class Login_Page_Test extends Base_Class{
	
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setUpPages(){
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}
	
	
	//dataProvider="validLoginData", dataProviderClass = DataProviders.class
	@Test()
	public void verifyValidLoginTest(){
//		ExtentManager.startTest("Valid login test");  -- This will be implemented in TestListener
//		ExtentManager.logStep("Navigating to login page entering username and password");
		loginPage.login("admin", "admin123");
//		ExtentManager.logStep("Verifying Admin tab is visible or not");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab should be visible after successfully login!");
//		ExtentManager.logStep("Validation successfully");
		homePage.logout();
//		ExtentManager.logStep("Logged out successfully!");
		staticWait(2);
	}
	
	//dataProvider="inValidLoginData", dataProviderClass = DataProviders.class
	@Test()
	public void verifyWithInvalidLoginTest(){
//		ExtentManager.startTest("In valid login test"); -- This will be implemented in TestListener
//		ExtentManager.logStep("Navigating to login page entering username and password");
		loginPage.login("admin", "admin1 ");
		String expectedErrorMsg = "Invalid credentials";
		Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMsg), "Text failed: Invalid Error message");
//		ExtentManager.logStep("Logged out successfully");
	}

}
