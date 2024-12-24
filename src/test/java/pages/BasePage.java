package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverUtils;
import utils.PropertyReader;

import java.time.Duration;

public class BasePage {

    WebDriver driver;
    WebDriverWait wait;

    BasePage() {
        driver = DriverUtils.getDriver();
        PageFactory.initElements(driver, this);

        int explicitWaitTime = Integer.parseInt(PropertyReader
                .getProperty("explicit.wait"));
        wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWaitTime));

        int implicitWaitTime = Integer.parseInt(PropertyReader
                .getProperty("implicit.wait"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));

        int pageLoadTimeout = Integer.parseInt(PropertyReader
                .getProperty("page.load.timeout"));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
    }

    public void waitForElementToBeClickable(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitForElementToBeVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isPresent(WebElement element){
        try{
            element.isDisplayed();
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public void scrollToElement(WebElement element) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", element);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clickElement(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click()", element);
        }
    }

    public void enterText(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }
}
