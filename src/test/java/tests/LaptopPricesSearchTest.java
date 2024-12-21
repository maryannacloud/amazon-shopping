package tests;

import org.testng.annotations.Test;

public class LaptopPricesSearchTest extends BaseTest {

    @Test
    public void verifyUserCanSearchForLaptop() {
        //homePage.openUrl();
        homePage.searchForProduct("Laptop");
        homePage.validateSearchResultsTextAndCount();
        searchResultPage.expandBrandsList();
        searchResultPage.getBrandCheckbox("HP").click();
        searchResultPage.getLaptopPrices();
    }
}
