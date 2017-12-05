package myprojects.automation.assignment5;


import com.google.common.base.Predicate;
import myprojects.automation.assignment5.model.ProductData;
import myprojects.automation.assignment5.utils.logging.CustomReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;

    //Main Page variables
    private By mobileMenu = By.id("_mobile_cart");


    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public void openPage (String url){
        driver.get(url);
        waitForContentLoad();
    }

    public boolean isMobileVersion(){
        return driver.findElement(mobileMenu).isDisplayed();
    }

    public void openRandomProduct() {

        // TODO implement logic to open random product before purchase
        throw new UnsupportedOperationException();
    }

    /**
     * Extracts product information from opened product details page.
     *
     * @return
     */
    public ProductData getOpenedProductInfo() {
        CustomReporter.logAction("Get information about currently opened product");
        // TODO extract data from opened page
        throw new UnsupportedOperationException();
    }

    public void waitForContentLoad() {
        Predicate<WebDriver> driverPredicate = new Predicate<WebDriver>() {
            @Override
            public boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        wait.until(driverPredicate);
    }
}
