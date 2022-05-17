import Pages.BasePage;
import Utilities.Constants;
import Utilities.DriverSingleton;
import Utilities.ExtentReportFactory;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

//This is our MainTest class, where we run our test suite. It inherits from BasePage the method getData.
public class MainTest extends BasePage {
    /*
    Globally calling our ExtentReportFactory and its reporter method;
    as well as the ExtentTest with assignment of our own test suite details.
    */
    private static ExtentReports extent = ExtentReportFactory.getReporter();
    private static ExtentTest test = extent.createTest("Class11 Homework", "Sanity test");
    private static WebDriver driver;
    private static WebDriverWait wait;


    //Locally instantiating this class with its own local instance of driver.
    public MainTest() throws Exception {
        driver = DriverSingleton.getDriverInstance();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    //Our @BeforeClass calls the URL1 for our suite, located in the xml config file.
    @BeforeClass
    public static void runOnceBeforeClass() throws Exception {
        driver.get(getData("URL1"));
        test.info("This is @BeforeClass which opens the browser, Chrome, \n" +
                "on URL1, Daniel's Navigation page, from our xml file.");
    }


    //1 - switching to iframe, printing the text within it, and switching back to the default page.
    @Test
    public void test01_printIFrame() {
        try {
            driver.switchTo().frame("my-frame");
            System.out.println(getElement(By.id("iframe_container")).getText());
            driver.switchTo().defaultContent();
            test.pass("Printing iframe text was successful!");
        } catch (Exception e) {
            e.printStackTrace();
            test.fail("Printing iframe text was not successful! " + e.getMessage());
        }
    }

    /*
    2 - navigating to Google Translate, taking a screenshot of it and logging it,
    pressing the translation field and passing our company name to it.
     */
    @Test
    public void test02_googleTranslateLog() {
        try {
            driver.navigate().to(getData("URL2"));
            test.info("URL2, Google Translate, screenshot!", MediaEntityBuilder.createScreenCaptureFromPath(takeScreenShot(driver, Constants.TIMENOW)).build());

            clickElement(By.className("er8xn"));

            extent.setSystemInfo("Environment", "Paramount+");
            test.pass("Navigating to Google Translate, taking a screenshot \n" +
                    "clicking the translation box and setting system info was successful!");
        } catch (Exception e) {
            e.printStackTrace();
            test.fail("Navigating to Google Translate, taking a screenshot \n" +
                    "clicking the translation box and setting system info was not successful! " + e.getMessage());
        }
    }

    /*
    3 - navigating to Walla, taking a screenshot of it and logging it,
    pressing the translation field and passing our company name to it.
     */
    @Test
    public void test03_googleTranslateLog() {
        try {
            driver.navigate().to(getData("URL3"));

            String path = "C:\\Users\\Nate\\IdeaProjects\\Class11\\src\\main\\resources\\data.xml";
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(path);

            Node URL3 = doc.getElementsByTagName("URL3").item(0);
            URL3.setTextContent("https://www.ynet.co.il");

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);

            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

            driver.navigate().to(getData("URL3"));

            test.pass("Navigating to Walla, changing URL3 to Ynet in our XML \n" +
                    "and navigating to it was successful!");

        } catch (Exception e) {
            e.printStackTrace();
            test.fail("Navigating to Walla, changing URL3 to Ynet in our XML \n" +
                    "and  navigating to it was not successful! " + e.getMessage());
        }
    }



    /*
    4 - navigating back to URL1, pressing "show alert" and printing alert's text;
    pressing "show prompt", filling my name and asserting result;
    pressing "show confirm box", choosing one button, and asserting text of the result;
    pressing "open new tab" and going back to the main tab;
    pressing "open new window" and going back to the main window.
     */
    @Test
    public void test04_backToNavigation() {
        try {
            driver.navigate().to(getData("URL1"));

            //"show alert"
            clickElement(By.id("MyAlert"));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            System.out.println(alert.getText());
            alert.dismiss();

            //"show prompt"
            clickElement(By.id("MyPrompt"));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert prompt = driver.switchTo().alert();
            prompt.sendKeys("Netanel");
            prompt.accept();
            String myName = "Netanel";
            String enteredName = getElement(By.id("output")).getText();
            Assert.assertEquals(myName, enteredName);

            //"show confirm box"
            clickElement(By.id("MyConfirm"));
            wait.until(ExpectedConditions.alertIsPresent());
            Alert confirm = driver.switchTo().alert();
            confirm.accept();
            System.out.println(getElement(By.id("output")).getText());

            //"open new tab"
            clickElement(By.id("openNewTab"));
            ArrayList<String> handles = new ArrayList<>(driver.getWindowHandles());
            //switch to new tab
            driver.switchTo().window(handles.get(1));
            //switch back to original
            driver.switchTo().window(handles.get(0));

            //"open new window"
            clickElement(By.cssSelector("a[href='newWindow.html']"));
            //set current window name
            String mainWindow = driver.getWindowHandle();
            //create a set of all opened windows
            Set<String> windows = driver.getWindowHandles();
            //use iterator to iterate between them
            //switch to new window
            for (String newWindow : windows) {
                if (!mainWindow.equals(newWindow)) {
                    driver.switchTo().window(newWindow);
                    String newWindowTitle = driver.switchTo().window(newWindow).getTitle();
                    System.out.println(newWindowTitle);

                    driver.close();
                }
                //switch back to main window
                driver.switchTo().window(mainWindow);
            }

            test.pass("All navigation tasks were successful!");


        } catch (Exception e) {
            e.printStackTrace();
            test.fail("All navigation tasks were not successful! " + e.getMessage());
        }
    }


    /*
    Challenges: 5 - reading from JSON file, using "JSON In Java" maven library.

     */
    @Test
    public void test05_challenge01_readFromJson() {


    }


    /*
    Our @AfterClass is meant to run after all tests have been completed.
    It calls our extentReport and with the flush() method generates our extent report.
    It is also meant to exit the driver (i.e. close the browse). This part is in comment for my own sanity alone.
    */
        @AfterClass
        public static void tearDown () {
            test.info("This is @AfterClass which runs after all tests are completed, \n" +
                    "and generates our extent report.");
            extent.flush();
//        driver.quit();
        }
    }
