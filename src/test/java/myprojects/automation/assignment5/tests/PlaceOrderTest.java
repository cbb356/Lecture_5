package myprojects.automation.assignment5.tests;

import myprojects.automation.assignment5.BaseTest;
import myprojects.automation.assignment5.model.ProductData;
import myprojects.automation.assignment5.utils.Properties;
import org.testng.Assert;
import org.testng.annotations.Test;

import static myprojects.automation.assignment5.utils.logging.CustomReporter.log;

public class PlaceOrderTest extends BaseTest {

    @Test
    public void checkSiteVersion() {
        actions.openMainPage(Properties.getBaseUrl());
        Assert.assertEquals(actions.isMobileVersion(), isMobileTesting, "The non-correct version of site opened");
        log("The version of site is correct");
    }

    @Test
    public void createNewOrder() {
        // open random product
        actions.openMainPage(Properties.getBaseUrl());
        actions.openRandomProduct();
        log("Random Product opened");

        // save product parameters
        ProductData productDataFirst = actions.getOpenedProductInfo();
        String productURL = driver.getCurrentUrl();
        log("Product Parameters saved");
        log("Product URL:" + productURL);

        // add product to Cart and validate product information in the Cart
        actions.getProductIntoCart();
        log("Cart opened");

        ProductData productDataCart = actions.validateProductIntoCart();
        Assert.assertEquals(productDataCart.getName(), productDataFirst.getName(), "Product Name in Cart is non-correct");
        Assert.assertEquals(productDataCart.getQty(), 1, "Product Amount in Cart is non-correct");
        Assert.assertEquals(productDataCart.getPrice(), productDataFirst.getPrice(), "Product Price in Cart is non-correct");
        log("Product in Cart verified");

        // proceed to order creation, fill required information
        actions.orderCompletion();
        log("Order completed");

        // place new order and validate order summary
        Assert.assertTrue(actions.orderConfirmationMessage(), "Order is not confirmed");
        log("Order confirmed");
        Assert.assertEquals(actions.orderPositionsQuantity(), 1, "There are more than one position in Order");
        ProductData productDataOrder = actions.validateProductIntoFinalOrder();
        Assert.assertTrue(productDataOrder.getName().startsWith(productDataFirst.getName()),
                "Product Name in Order Confirmation is non-correct");
        Assert.assertEquals(productDataOrder.getQty(), 1, "Product Amount in Order Confirmation is non-correct");
        Assert.assertEquals(productDataOrder.getPrice(), productDataFirst.getPrice(),
                "Product Price in Order Confirmation is non-correct");
        log("Order validated");

        // check updated In Stock value
        int newAmounntOfProduct = actions.getNewAmountProduct (productURL);
        Assert.assertEquals(newAmounntOfProduct, productDataFirst.getQty() - 1, "THe final Amount of Products is non-correct");
        log("Product Amount verified");
    }

}
