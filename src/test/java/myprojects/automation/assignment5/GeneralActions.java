package myprojects.automation.assignment5;


import com.google.common.base.Predicate;
import myprojects.automation.assignment5.model.ProductData;
import myprojects.automation.assignment5.utils.DataConverter;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;

import static myprojects.automation.assignment5.utils.logging.CustomReporter.logAction;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor javascriptExecutor;

    //Main Page variables
    private By mobileMenu = By.id("_mobile_cart");
    private By allProducts = By.className("all-product-link");

    //All Goods Page variables
    private By productLinks = By.cssSelector(".product-title > a");

    //Product Page variables
    private By productDetails = By.xpath("//a[@href='#product-details']");
    private By productPriceOnProductPage = By.xpath("//span[@itemprop='price']");
    private By productAmountOnProductPage = By.cssSelector(".product-quantities>span");
    private By addToCartBtn = By.className("add-to-cart");
    private By addToCartConfirmation = By.id("myModalLabel");
    private By addToCartConfirmationBtn = By.cssSelector("a.btn-primary");

    //Cart variables
    private By productNameInCart = By.cssSelector(".product-line-info>a");
    private By productAmountInCart = By.className("js-cart-line-product-quantity");
    private By productPriceInCart = By.xpath("//div[@class = 'product-line-info'][2]/span");
    private By orderConfirmationBtn = By.cssSelector(".checkout > div > a");

    //Order Completion variables
    private By firstNameInputField = By.name("firstname");
    private By lastNameInputField = By.name("lastname");
    private By emailInputField = By.name("email");
    private By nextSectionBtn1 = By.name("continue");
    private By addressInputField = By.name("address1");
    private By postCodeInputField = By.name("postcode");
    private By cityInputField = By.name("city");
    private By nextSectionBtn2 = By.name("confirm-addresses");
    private By nextSectionBtn3 = By.name("confirmDeliveryOption");
    private By paymentConfirmationBtn = By.cssSelector("#payment-confirmation > div > button");
    private By paymentOptionRadioBtn = By.id("payment-option-1");
    private By conditionsCheckBox = By.id("conditions_to_approve[terms-and-conditions]");

    //Order Confirmation variables
    private By orderConfirmationMsg = By.cssSelector("h3.h1.card-title");
    private By orderPositionsQty = By.className("order-line");
    private By productNameOrderConfirmation = By.cssSelector("div.row > div.details");
    private By productAmountOrderConfirmation = By.cssSelector("div.row > div.qty > div.row > div.col-xs-2");
    private By productPriceOrderConfirmation = By.cssSelector("div.row > div.qty > div.row > div.text-xs-left");

    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
        javascriptExecutor = (JavascriptExecutor) driver;
    }

    public void openMainPage (String url){
        logAction("Open Main Page");
        driver.get(url);
        waitForContentLoad();
    }

    public boolean isMobileVersion(){
        return driver.findElement(mobileMenu).isDisplayed();
    }

    public void openRandomProduct() {
        logAction("Open Random Product");
        driver.findElement(allProducts).click();
        List<WebElement> productsOnPage = driver.findElements(productLinks);
        WebElement productClick = productsOnPage.get(new Random().nextInt(productsOnPage.size()));
        scrollTo(productClick).click();
        waitForContentLoad();
    }

    /**
     * Extracts product information from opened product details page.
     *
     * @return
     */
    public ProductData getOpenedProductInfo() {
        logAction("Get information about currently opened product");
        scrollTo(driver.findElement(productDetails)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(productAmountOnProductPage));
        String productName = driver.getTitle();
        String productAmount = driver.findElement(productAmountOnProductPage).getText();
        String productPrice = driver.findElement(productPriceOnProductPage).getText();
        return new ProductData(productName,
                DataConverter.parseStockValue(productAmount), DataConverter.parsePriceValue(productPrice));
    }

    public void getProductIntoCart() {
        logAction("Get Product into Cart");
        driver.findElement(addToCartBtn).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(addToCartConfirmation));
        driver.findElement(addToCartConfirmationBtn).click();
        waitForContentLoad();
    }

    public ProductData validateProductIntoCart() {
        logAction("Validate Product into Cart");
        String productName = driver.findElement(productNameInCart).getText();
        String productAmount = driver.findElement(productAmountInCart).getAttribute("value");
        String productPrice = driver.findElement(productPriceInCart).getText();
        return new ProductData(productName,
                Integer.valueOf(productAmount), DataConverter.parsePriceValue(productPrice));
    }

    public void orderCompletion () {
        String firstName = "Ivan";
        String lastName = "Petrov";
        String email = "ipetrov1965@gmail.com";
        String address = "23, Sidorova str.";
        String zipCode = "12345";
        String city = "Dnipro";

        scrollTo(driver.findElement(orderConfirmationBtn)).click();
        waitForContentLoad();

        logAction("Filling Fields in Order Completion Step 1");
        driver.findElement(firstNameInputField).sendKeys(firstName);
        scrollTo(driver.findElement(lastNameInputField)).sendKeys(lastName);
        scrollTo(driver.findElement(emailInputField)).sendKeys(email);
        scrollTo(driver.findElement(nextSectionBtn1)).click();
        waitForContentLoad();

        logAction("Filling Fields in Order Completion Step 2");
        wait.until(ExpectedConditions.visibilityOfElementLocated(addressInputField));
        driver.findElement(addressInputField).sendKeys(address);
        scrollTo(driver.findElement(postCodeInputField)).sendKeys(zipCode);
        scrollTo(driver.findElement(cityInputField)).sendKeys(city);
        scrollTo(driver.findElement(nextSectionBtn2)).click();
        waitForContentLoad();

        logAction("Confirm Delivery Options");
        wait.until(ExpectedConditions.visibilityOfElementLocated(nextSectionBtn3));
        scrollTo(driver.findElement(nextSectionBtn3)).click();
        waitForContentLoad();

        logAction("Confirm Payment");
        wait.until(ExpectedConditions.visibilityOfElementLocated(paymentConfirmationBtn));
        scrollTo(driver.findElement(paymentOptionRadioBtn)).click();
        scrollTo(driver.findElement(conditionsCheckBox)).click();
        scrollTo(driver.findElement(paymentConfirmationBtn)).click();
        waitForContentLoad();
    }


    public boolean orderConfirmationMessage() {
        logAction("Check Order Confirmation Message");
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderConfirmationMsg));
        return driver.findElement(orderConfirmationMsg).isDisplayed();
    }

    public int orderPositionsQuantity() {
        logAction("Check Product Positions Quantity");
        List<WebElement> quantity = driver.findElements(orderPositionsQty);
        return quantity.size();
    }

    public ProductData validateProductIntoFinalOrder() {
        logAction("Validate Product into Final Order");
        String productName = driver.findElement(productNameOrderConfirmation).getText();
        String productAmount = driver.findElement(productAmountOrderConfirmation).getText();
        String productPrice = driver.findElement(productPriceOrderConfirmation).getText();
        return new ProductData(productName,
                Integer.valueOf(productAmount), DataConverter.parsePriceValue(productPrice));
    }

    public int getNewAmountProduct(String productURL) {
        logAction("Validate Product Final Amount");
        driver.get(productURL);
        waitForContentLoad();
        scrollTo(driver.findElement(productDetails)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(productAmountOnProductPage));
        String productAmount = driver.findElement(productAmountOnProductPage).getText();
        return DataConverter.parseStockValue(productAmount);
    }

    public WebElement scrollTo(WebElement element){
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
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
