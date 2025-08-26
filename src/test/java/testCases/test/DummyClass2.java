package testCases.test;

import org.testng.annotations.Test;

import com.baseclass.Base_Class;

public class DummyClass2 extends Base_Class {
	
	@Test
	public void dummytest(){
		String title = driver.getTitle();
		System.out.println("Application title : "+title);
	}

}
