package testCases.test;

import org.testng.annotations.Test;

import com.baseclass.Base_Class;

public class DummyClass extends Base_Class {
	
	@Test
	public void dummytest(){
//		String title = getDriver().getTitle();
		String title = getDriver().getTitle();
		System.out.println("Application title : "+title);
		assert title.equals("OrangeHRM"):"Test Failed - Title is Not Matching";
		System.out.println("Test Passed - Title is Matching");
	}

}
