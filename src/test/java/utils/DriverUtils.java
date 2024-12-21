package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.lang.reflect.Method;
import java.net.URL;
import java.time.Duration;

public class DriverUtils {

    public static WebDriver driver;

    public static void createDriver(Method test) {
        String host = PropertyReader.getProperty("application.host");
        if (host.equalsIgnoreCase("local")) {
            createLocalDriver();
        } else if (host.equalsIgnoreCase("saucelabs")) {
            createSauceLabsDriver(test);
        } else if (host.equalsIgnoreCase("browserstack")) {
            createBrowserStackDriver(test);
        } else {
            throw new RuntimeException("Unsupported host: " + host);
        }

        if (driver != null) {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
        }
    }

    private static void createLocalDriver() {
        String browser = PropertyReader.getProperty("application.browser");
        switch (browser.toLowerCase()) {
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            }
            case "safari" -> {
                WebDriverManager.safaridriver().setup();
                driver = new SafariDriver();
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }
            default -> {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
        }
    }

    private static void createSauceLabsDriver(Method test) {
        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("username", PropertyReader.getProperty("sauce.username"));
        sauceOptions.setCapability("accessKey", PropertyReader.getProperty("sauce.accessKey"));

        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("browserName", PropertyReader.getProperty("sauce.browserName"));
        capabilities.setCapability("browserVersion", PropertyReader.getProperty("sauce.browserVersion"));
        capabilities.setCapability("platformName", PropertyReader.getProperty("sauce.platformName"));
        capabilities.setCapability("sauce:options", sauceOptions);

        try {
            driver = new RemoteWebDriver(new URL(PropertyReader.getProperty("sauce.urlWest")), capabilities);
            ((JavascriptExecutor) driver).executeScript("sauce:job-name=" + test.getName());
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize SauceLabs WebDriver", e);
        }
    }

    private static void createBrowserStackDriver(Method test) {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", PropertyReader.getProperty("browserstack.browser"));
        capabilities.setCapability("browserVersion", PropertyReader.getProperty("browserstack.browser.version"));

        MutableCapabilities bstackOptions = new MutableCapabilities();
        bstackOptions.setCapability("os", PropertyReader.getProperty("browserstack.os"));
        bstackOptions.setCapability("osVersion", PropertyReader.getProperty("browserstack.os.version"));
        bstackOptions.setCapability("projectName", PropertyReader.getProperty("browserstack.project"));
        bstackOptions.setCapability("buildName", PropertyReader.getProperty("browserstack.build"));
        bstackOptions.setCapability("sessionName", test.getName());
        bstackOptions.setCapability("browserstack.user", PropertyReader.getProperty("browserstack.username"));
        bstackOptions.setCapability("browserstack.key", PropertyReader.getProperty("browserstack.access.key"));

        capabilities.setCapability("bstack:options", bstackOptions);

        try {
            driver = new RemoteWebDriver(new URL(PropertyReader.getProperty("browserstack.hub.url")), capabilities);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize BrowserStack WebDriver", e);
        }
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            throw new RuntimeException("Driver is not initialized. Call createDriver() first.");
        }
        try {
            driver.getCurrentUrl(); // Verify the session is active
        } catch (Exception e) {
            throw new RuntimeException("Driver session is no longer active. Reinitialize the driver.");
        }
        return driver;
    }
}
