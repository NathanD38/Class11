package Challenges;

import java.io.*;
import java.time.Duration;

import Utilities.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class JsonReadTest {
    private static WebDriver driver;
    private static Gson gson;
    private static File jsonPath;
    private static BufferedReader br;
    private static JsonObject obj;

    public JsonReadTest() throws FileNotFoundException {
        gson = new Gson();
        jsonPath = new File("C:\\Users\\Nate\\IdeaProjects\\Class11\\src\\main\\resources\\data.json");
        br = new BufferedReader(
                new FileReader(jsonPath));
        //convert the json string back to object
        obj = gson.fromJson(br, JsonObject.class);
    }

    @BeforeClass
    public static void runOnceBeforeClass() {
        JsonElement browser1 = obj.getAsJsonArray("browser").get(0);
        System.out.println(browser1.getAsString());
        if (browser1.getAsString().equals("Chrome")) {
            System.setProperty("webdriver.chrome.driver", Constants.CHROMEDRIVER_PATH);
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();

        } else if (browser1.getAsString().equals("Firefox")) {
            System.setProperty("webdriver.firefox.driver", Constants.FIREFOXDRIVER_PATH);
            driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().window().maximize();
        }
    }

    @Test
    public void test01_challenge05_readFromJson() {
        JsonElement link2 = obj.getAsJsonArray("url").get(1);
        JsonElement link3 = obj.getAsJsonArray("url").get(2);

        driver.get(getURLFromJson());
        driver.navigate().to(link2.getAsString());
        driver.navigate().to(link3.getAsString());
    }

    @Test
    public void test02_challenge06_readFromConfig() {
        Config config = new Config(obj.getAsJsonArray("url").get(1));
        driver.get(config.getUrl());

    }

    @AfterClass
    public static void tearDown() {
//        driver.quit();
    }

    protected String getURLFromJson() {
        JsonElement link1 = obj.getAsJsonArray("url").get(0);
        return link1.getAsString();
    }
}