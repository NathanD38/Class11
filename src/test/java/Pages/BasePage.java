package Pages;

import Utilities.Constants;
import Utilities.DriverSingleton;
import Utilities.ExtentReportFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;

/*
This is our BaseClass (Page Object Model) from which our various Pages inherit the functions
we use repeatedly throughout the test suite.
*/
public class BasePage {

    /*
    Globally calling our ExtentReportFactory and its reporter method;
    as well as the ExtentTest with assignment of our own test suite details.
    We also globally call our driver.
    */
    private static ExtentReports extent = ExtentReportFactory.getReporter();
    private static ExtentTest test = extent.createTest("Class11 Homework", "Sanity test");
    private static WebDriver driver;

    //Locally instantiating this class with its own local instance of driver.
    public BasePage() throws Exception {
        driver = DriverSingleton.getDriverInstance();
    }

    /*
    Our first method is to simply returning a WebElement. This is to avoid instantiating findElement every time we need it.
    It is wrapped between try-catch blocks based on the outcome of the test result.
     */
    public WebElement getElement(By locator) {
        try {
            test.pass("Element was found!");
            return getWebElement(locator);
        } catch (NoSuchElementException e) {
            test.fail("Element was not found!", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(driver, Constants.TIMENOW)).build());
        }
        return getWebElement(locator);
    }

    /*
    Our second method is to click a WebElement based on its locator.
    It is wrapped between try-catch blocks based on the outcome of the test result.
    */
    public void clickElement(By locator) {
        try{
            getWebElement(locator).click();
            test.pass("Element was found and clicked successfully!");
        } catch (NoSuchElementException e) {
            test.fail("Element was not found!", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(driver, Constants.TIMENOW)).build());
        }
    }

    /*
    Our second method is to send some text to a WebElement based on its locator (for example name or email in a form).
    It is wrapped between try-catch blocks based on the outcome of the test result.
    */
    public void sendToElement(By locator, String text) {
        try {
            getWebElement(locator).sendKeys(text);
            test.pass("Element was found and keys were sent to it successfully!");
        }catch (NoSuchElementException e) {
            test.fail("Element was not found!", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(driver, Constants.TIMENOW)).build());
        }
    }

    //Our private function of getWebElement that is used in the above methods.
    private WebElement getWebElement(By locator) {
        return driver.findElement(locator);
    }

    /*
    This method (protected but accessed by our singleton via inheritance) is to get the browserType from our xml file,
    a config file for our suite. It is also accessed in our MainTest @BeforeClass, to read the URL for this test suite.
    */
    protected static String getData(String keyName) throws Exception{
        File fXmlFile = new File("C:\\Users\\Nate\\IdeaProjects\\Class11\\src\\main\\resources\\data.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(keyName).item(0).getTextContent();
    }

    // This is a method which takes screenshots whenever an element is not found, and it is added to our extent report.
    protected static String takeScreenShot(WebDriver driver, String ImagesPath) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File screenShotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(ImagesPath + ".png");
        try {
            FileUtils.copyFile(screenShotFile, destinationFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return ImagesPath + ".png";
    }
}
