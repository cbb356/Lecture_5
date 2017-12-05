package myprojects.automation.assignment5;


import com.google.common.base.Predicate;
import myprojects.automation.assignment5.model.ProductData;
import myprojects.automation.assignment5.utils.DataConverter;
import myprojects.automation.assignment5.utils.logging.CustomReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;

    //Main Page variables
    private By mobileMenu = By.id("_mobile_cart");
    private By allProducts = By.className("all-product-link");

    //All Goods Page variables
    private By productLinks = By.cssSelector(".product-title > a");

    //Product Page variables
    private By productNameOnProductPage = By.xpath("//h1[@itemprop='name']");
    private By productPriceOnProductPage = By.xpath("//span[@itemprop='price']");
    private By productAmountOnProductPage = By.cssSelector(".product-quantities>span");


    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public void openMainPage (String url){
        driver.get(url);
        waitForContentLoad();
    }

    public boolean isMobileVersion(){
        return driver.findElement(mobileMenu).isDisplayed();
    }

    public void openRandomProduct() {
        driver.findElement(allProducts).click();
        List<WebElement> productsOnPage = driver.findElements(productLinks);
        WebElement productClick = productsOnPage.get(new Random().nextInt(productsOnPage.size()));
        productClick.click();
        waitForContentLoad();
    }

    /**
     * Extracts product information from opened product details page.
     *
     * @return
     */
    public ProductData getOpenedProductInfo() {
        CustomReporter.logAction("Get information about currently opened product");
        String productName = driver.getTitle();
        String productAmount = driver.findElement(productAmountOnProductPage).getText();
        String productPrice = driver.findElement(productPriceOnProductPage).getAttribute("content");
        return new ProductData(productName,
                DataConverter.parseStockValue(productAmount), DataConverter.parsePriceValue(productPrice));
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
