package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {
	
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class,groups= {"regression"})
	public void verify_loginDDT(String email, String pwd, String exp) {
		try {
		logger.info("****starting TC_003_LooginDDT******* ");
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		
		LoginPage lp= new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLoginButton();
		
		MyAccountPage myAccountPage = new MyAccountPage(driver);
		boolean targetPage=  myAccountPage.isMyAccountPageExists();

			
		if (exp.equalsIgnoreCase("Valid")) {
			if (targetPage == true) {
				
				myAccountPage.clickLogout();
				Assert.assertTrue(true);
			
			} else {
				Assert.assertTrue(false);
			}
		}
		
		if (exp.equals("Invalid")) {   
			if (targetPage == true) {
				myAccountPage.clickLogout();
				Assert.assertTrue(false);
			} else {
				Assert.assertTrue(true);
			}
		}
	} catch (Exception e) {
		logger.error("Error during login DDT test: " + e.getMessage());
		logger.debug("Debug logs", e);
		Assert.fail("Test failed due to exception: " + e.getMessage());
	}
		 	logger.info("****Finished TC_003_LooginDDT******* ");
	}

}
