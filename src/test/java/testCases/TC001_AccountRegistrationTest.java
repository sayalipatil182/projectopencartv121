package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
    
    @Test(groups = {"regression", "master"})
    void verify_account_registration() {
        
        logger.info("***** Starting TC001_AccountRegistrationTest *****");

        try { 
            // Navigate from home to registration
            HomePage homePage = new HomePage(driver);
            homePage.clickMyAccount();
            logger.info("Clicked on My Account");
            
            homePage.clickRegister();   // ✅ ensure HomePage has this method
            logger.info("Clicked on Register");
            
            // Fill registration form
            AccountRegistrationPage accountRegistrationPage = new AccountRegistrationPage(driver);
            logger.info("Filling registration form");
            
            accountRegistrationPage.setFirstName(randomString().toUpperCase());
            accountRegistrationPage.setLastName(randomString().toUpperCase());
            accountRegistrationPage.setEmail(randomString() + "@example.com");
            accountRegistrationPage.setPassword(randomNumber());
            
            accountRegistrationPage.setPrivacyPolicy();
            accountRegistrationPage.clickContinue();
            
            logger.info("Validation: expected message is 'Your Account Has Been Created!'");
            
            // Validation
            String confirmationMessage = accountRegistrationPage.getConfirmationMessage();
            System.out.println("Confirmation Message: " + confirmationMessage);
            
            Assert.assertEquals(confirmationMessage, "Your Account Has Been Created!",
                    "Account Registration Failed!");
            
            System.out.println("Account registration test passed!");
        
        } catch (Exception e) {
            logger.error("Error during account registration test: " + e.getMessage());
            logger.debug("Debug logs", e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

        logger.info("***** Finished TC001_AccountRegistrationTest *****");
    }
}
