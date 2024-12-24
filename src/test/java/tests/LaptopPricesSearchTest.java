package tests;

import org.testng.annotations.Test;

public class LaptopPricesSearchTest extends BaseTest {

    @Test
    public void verifyUserCanSearchForLaptop() {

        homePage.searchForProduct("Laptop");
        searchResultPage.scrollToBrandsFilterSection();
        searchResultPage.selectBrand("HP");
        searchResultPage.getLaptopPrices();
    }
}
