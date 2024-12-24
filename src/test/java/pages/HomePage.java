package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    // Define WebElements
    @FindBy(xpath = "//input[@id = 'twotabsearchtextbox']")
    WebElement searchBox;

    @FindBy(xpath = "//input[@id = 'nav-search-submit-button']")
    WebElement searchButton;

    // Define methods
    public void searchForProduct(String product){
        //waitForElementToBeVisible(searchBox);
        isPresent(searchBox);
        searchBox.sendKeys(product);
        searchButton.click();
    }
}
