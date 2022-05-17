package Utilities;

//Thus is our Constants class, where we create constants for our drivers and one for the timeNow function
public class Constants {
    //ChromeDriver constant - to run the tests on Chrome
    public static final String CHROMEDRIVER_PATH = "src/test/java/drivers/chromedriver.exe";
    //ChromeDriver constant - to run the tests on Firefox
    public static final String FIREFOXDRIVER_PATH = "src/test/java/drivers/geckodriver.exe";
    //For every "Element not found", a screenshot is generated with the filename of the current time
    public static final String TIMENOW = String.valueOf(System.currentTimeMillis());

}
