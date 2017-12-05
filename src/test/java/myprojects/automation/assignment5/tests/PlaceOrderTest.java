package myprojects.automation.assignment5.tests;

import myprojects.automation.assignment5.BaseTest;
import myprojects.automation.assignment5.utils.Properties;
import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static myprojects.automation.assignment5.utils.logging.CustomReporter.log;

public class PlaceOrderTest extends BaseTest {

    @Test
    public void checkSiteVersion() {
        actions.openPage(Properties.getBaseUrl());
        Assert.assertEquals("The uncorrect version of site opened", isMobileTesting, actions.isMobileVersion());

        // TODO open main page and validate website version
    }

    @Test
    public void createNewOrder() {
        // TODO implement order creation test

        // open random product

        // save product parameters

        // add product to Cart and validate product information in the Cart

        // proceed to order creation, fill required information

        // place new order and validate order summary

        // check updated In Stock value
    }

}
