package myprojects.automation.assignment5.tests;

import myprojects.automation.assignment5.BaseTest;
import myprojects.automation.assignment5.model.ProductData;
import myprojects.automation.assignment5.utils.Properties;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static myprojects.automation.assignment5.utils.logging.CustomReporter.log;

public class PlaceOrderTest extends BaseTest {

    @Test
    public void checkSiteVersion() {
        actions.openMainPage(Properties.getBaseUrl());
        Assert.assertEquals("The non-correct version of site opened", isMobileTesting, actions.isMobileVersion());
        log("The version of site is correct");
    }

    @Test
    public void createNewOrder() {
        actions.openMainPage(Properties.getBaseUrl());
        actions.openRandomProduct();
        log("Random Product opened");

        ProductData productData = actions.getOpenedProductInfo();
        log("Product Parameters saved");

        // add product to Cart and validate product information in the Cart

        // proceed to order creation, fill required information

        // place new order and validate order summary

        // check updated In Stock value
    }

}
