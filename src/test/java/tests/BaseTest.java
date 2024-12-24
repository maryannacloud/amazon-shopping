package tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.HomePage;
import pages.SearchResultsPage;
import utils.DriverUtils;
import utils.PropertyReader;

import java.lang.reflect.Method;

public class BaseTest {

    HomePage homePage;
    SearchResultsPage searchResultPage;

    @BeforeMethod
    public void setUp(Method test) {
        PropertyReader.initProperty();
        DriverUtils.createDriver(test);

        DriverUtils.getDriver().get(PropertyReader.getProperty("application.url"));
        homePage = new HomePage();
        searchResultPage = new SearchResultsPage();
    }

    @AfterMethod
    public void cleanUp() {
        if (DriverUtils.getDriver() != null) {
            DriverUtils.getDriver().quit();
            DriverUtils.driver = null;
        }
    }
}
