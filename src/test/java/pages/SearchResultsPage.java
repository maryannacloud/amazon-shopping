package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsPage extends BasePage {

    @FindBy(xpath = "//span[@class='a-color-state a-text-bold' and contains(text(), 'laptop')]")
    WebElement searchResultsCountText;

    @FindBy(xpath = "//*[@aria-label='See more, Brands']")
    WebElement seeMoreBrandsBtn;

    @FindBy(xpath = "//div[@data-component-type='s-search-result']//span[@class='a-price-whole']")
    WebElement productPrice;

    @FindBy(xpath = "//div[@id='brandsRefinements']")
    WebElement brandsFilterSection;

    public void validateSearchResultsTextAndCount(){
        String actualText = searchResultsCountText.getText();
        Assert.assertTrue(actualText.contains("laptop"),
                "The search results text does not contain 'laptop'.");
    }

    public void scrollToBrandsFilterSection() {
        scrollToElement(brandsFilterSection);
    }

    public void expandBrandsList(){
        seeMoreBrandsBtn.click();
    }

    public WebElement getBrandCheckbox(String brandName) {
        String xpath = "//div[@id='brandsRefinements']//span[contains(text(), '" + brandName + "')]/ancestor::li//input[@type='checkbox']";
        return driver.findElement(By.xpath(xpath));
    }

    public void selectBrand(String brandName) {
        WebElement brandCheckbox = getBrandCheckbox(brandName);
        clickElement(brandCheckbox);
    }

    public List<String> getLaptopPrices() {
        // XPath for all prices within product containers
        String priceXpath = "//div[@data-component-type='s-search-result']//span[@class='a-price-whole']";
        // Find all price elements
        List<WebElement> priceElements = driver.findElements(By.xpath(priceXpath));
        // Create a list to store extracted prices
        List<String> prices = new ArrayList<>();
        // Loop through each element and extract text
        for (WebElement priceElement : priceElements) {
            String priceText = priceElement.getText(); // Get the price text
            prices.add(priceText); // Add it to the list
        }
        // Return the list of prices
        return prices;
    }
}
