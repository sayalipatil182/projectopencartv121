package pageObjects;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyAccountPage extends BasePage {

    public MyAccountPage(WebDriver driver) {
        super(driver);
    }

    // Actual page heading after successful login
    @FindBy(xpath = "//h2[normalize-space()='My Account']")
    private WebElement msgHeading;

    @FindBy(xpath = "//a[normalize-space()='Logout']")
    private WebElement lnkLogout;

    /**
     * Verify if the My Account page is displayed
     * @return true if heading is visible, false otherwise
     */
    public boolean isMyAccountPageExists() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOf(msgHeading));
            return msgHeading.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickLogout() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.elementToBeClickable(lnkLogout)).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
