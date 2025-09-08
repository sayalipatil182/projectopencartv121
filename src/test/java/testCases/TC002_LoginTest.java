package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {
	@Test(groups= {"sanity","master"})
	public void verify_login() {
        logger.info("***** Starting TC002_LoginTest *****");
        
        try {
        HomePage hp= new HomePage(driver);
        hp.clickMyAccount();
        hp.clickLogin();
        
        logger.info("Clicked on Login");
        
        // Create LoginPage object
        LoginPage lp = new LoginPage(driver);
        logger.info("Filling login form");
        
        lp.setEmail("jpatil58@gmail.com");
        lp.setPassword("ppddddd");
        lp.clickLoginButton();
        
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        
         boolean targetPage=  myAccountPage.isMyAccountPageExists();
        
        Assert.assertTrue(targetPage);  }  
        
	catch (Exception e) {
		logger.error("Error during login test: " + e.getMessage());
		logger.debug("Debug logs", e);
		Assert.fail("Test failed due to exception: " + e.getMessage());
	}
	logger.info("***** Finished TC002_LoginTest *****");

}}
