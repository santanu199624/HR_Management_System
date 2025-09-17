package testCases.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.baseclass.Base_Class;
import com.pages.HomePage;
import com.pages.LoginPage;



public class Login_Page_Test extends Base_Class{
	
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setUpPages(){
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}
	
	@Test(priority = 2)
	public void verifyValidLoginTest(){
		loginPage.login("Admin", "admin123");
		Assert.assertTrue(homePage.isAdminTabVisible(), "Admin tab should be visible after successfully login!");
		homePage.logout();
		staticWait(2);
	}
	
	@Test(priority = 2)
	public void verifyWithInvalidLoginTest(){
		loginPage.login("admin", "admin");
		String expectedErrorMsg = "Invalid credentials";
		Assert.assertTrue(loginPage.verifyErrorMessage(expectedErrorMsg), "Test failed: Invalid Error message");
	}

}
