package testCases.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.baseclass.Base_Class;
import com.pages.HomePage;
import com.pages.LoginPage;
import com.utilities.ExtentManager;



public class Home_Page_Test extends Base_Class {
	
	private LoginPage loginPage;
	private HomePage homePage;
	
	@BeforeMethod
	public void setUpPages(){
		loginPage = new LoginPage(getDriver());
		homePage = new HomePage(getDriver());
	}
	
	@Test
	public void verifyOrrangeHRMLogo(){
		loginPage.login("Admin", "admin123");
		Assert.assertTrue(homePage.verifyLogo(), "Logo not visible");
	}

}
