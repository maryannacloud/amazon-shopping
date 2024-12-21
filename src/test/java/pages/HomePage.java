package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class HomePage extends BasePage {

    // Define WebElements
    @FindBy(xpath = "//input[@id = 'twotabsearchtextbox']")
    WebElement searchBox;

    @FindBy(xpath = "//input[@id = 'nav-search-submit-button']")
    WebElement searchButton;

    @FindBy(xpath = "//span[@class='a-color-state a-text-bold' and contains(text(), 'laptop')]")
    WebElement searchResultsCountText;

    // Define methods
    public void searchForProduct(String product){
        searchBox.sendKeys(product);
        searchButton.click();
    }

    public void validateSearchResultsTextAndCount(){
        String actualText = searchResultsCountText.getText();
        Assert.assertTrue(actualText.contains("laptop"), "The search results text does not contain 'laptop'.");

    }
}
