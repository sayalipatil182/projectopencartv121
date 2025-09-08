package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	 
	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txtEmail;
	
	@FindBy(xpath = "//input[@id='input-password']")
	WebElement txtPassword;
	
	@FindBy(xpath= "//button[normalize-space()='Login']")
	WebElement btnLogin;

	public void setEmail(String email) {
		// Implement method to set email
		txtEmail.clear();
		txtEmail.sendKeys(email);
		
		
		
	}

	public void setPassword(String password) {
		// Implement method to set password
		txtPassword.clear();
		txtPassword.sendKeys(password);
		
	}
	
	public void clickLoginButton() {
		// Implement method to click login button
		btnLogin.click();

	}
	

}
