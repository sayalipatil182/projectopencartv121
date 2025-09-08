package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {

    public AccountRegistrationPage(WebDriver driver) {
        super(driver);
    }

    // ================== WebElements ==================
    @FindBy(xpath = "//input[@id='input-firstname']")
    WebElement txtFirstname;

    @FindBy(xpath = "//input[@id='input-lastname']")
    WebElement txtLastname;

    @FindBy(xpath = "//input[@id='input-email']")
    WebElement txtEmail;

    @FindBy(xpath = "//input[@id='input-password']")
    WebElement txtPassword;

    
    @FindBy(xpath = "//input[@name='agree']")
    WebElement chkPrivacyPolicy;

    @FindBy(xpath = "//button[normalize-space()=\'Continue\']")
    WebElement btnContinue;

    @FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
    WebElement msgConfirmation;

    // ================== Action Methods ==================
    public void setFirstName(String firstName) {
        txtFirstname.clear();
        txtFirstname.sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        txtLastname.clear();
        txtLastname.sendKeys(lastName);
    }

    public void setEmail(String email) {
        txtEmail.clear();
        txtEmail.sendKeys(email);
    }

    public void setPassword(String password) {
        txtPassword.clear();
        txtPassword.sendKeys(password);
    }

    
    public void setPrivacyPolicy() {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", chkPrivacyPolicy);
            chkPrivacyPolicy.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", chkPrivacyPolicy);
        }
    }

    public void clickContinue() {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btnContinue);
            btnContinue.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnContinue);
        }
    }

    public String getConfirmationMessage() {
        try {
            return msgConfirmation.getText();
        } catch (Exception e) {
            return null;
        }
    }
}
