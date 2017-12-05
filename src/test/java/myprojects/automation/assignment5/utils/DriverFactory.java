package myprojects.automation.assignment5.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    private static DesiredCapabilities capabilities;
    /**
     *
     * @param browser Driver type to use in tests.
     * @return New instance of {@link WebDriver} object.
     */
    public static WebDriver initDriver(String browser) {
        switch (browser) {
            case "firefox":
                System.setProperty(
                        "webdriver.gecko.driver",
                        new File(DriverFactory.class.getResource("/geckodriver.exe").getFile()).getPath());
                return new FirefoxDriver();
            case "ie":
            case "internet explorer":
            case "iexplorer":
                System.setProperty(
                        "webdriver.ie.driver",
                        new File(DriverFactory.class.getResource("/IEDriverServer.exe").getFile()).getPath());
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
                capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
                return new InternetExplorerDriver(capabilities);
            case "android":
                System.setProperty(
                        "webdriver.chrome.driver",
                        new File(DriverFactory.class.getResource("/chromedriver.exe").getFile()).getPath());

                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "Galaxy S5");
                Map<String, Object> mobileEmulationOptions = new HashMap<>();
                mobileEmulationOptions.put("mobileEmulation", mobileEmulation);

                capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability(ChromeOptions.CAPABILITY, mobileEmulationOptions);
                return new ChromeDriver(capabilities);
            case "phantomjs":
                System.setProperty(
                        "phantomjs.binary.path",
                        new File(DriverFactory.class.getResource("/phantomjs.exe").getFile()).getPath());
                return new PhantomJSDriver();
            case "chrome":
            default:
                System.setProperty(
                        "webdriver.chrome.driver",
                        new File(DriverFactory.class.getResource("/chromedriver.exe").getFile()).getPath());
                return new ChromeDriver();
        }
    }

    /**
     *
     * @param browser Remote driver type to use in tests.
     * @param gridUrl URL to Grid.
     * @return New instance of {@link RemoteWebDriver} object.
     */
    public static WebDriver initDriver(String browser, String gridUrl) throws MalformedURLException {
        switch (browser) {
            case "firefox":
                capabilities = DesiredCapabilities.firefox();
            case "ie":
            case "internet explorer":
            case "iexplorer":
                capabilities = DesiredCapabilities.internetExplorer();
                capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
                capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
            case "android":
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", "Galaxy S5");
                Map<String, Object> mobileEmulationOptions = new HashMap<>();
                mobileEmulationOptions.put("mobileEmulation", mobileEmulation);

                capabilities = DesiredCapabilities.chrome();
                capabilities.setCapability(ChromeOptions.CAPABILITY, mobileEmulationOptions);
            case "phantomjs":
                capabilities = DesiredCapabilities.phantomjs();
            case "chrome":
            default:
                capabilities = DesiredCapabilities.chrome();
        }
        return new RemoteWebDriver(new URL(gridUrl), capabilities);
    }
}
