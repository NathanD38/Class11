package Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

//This class initialize the reporter that is used to generate the report and its path.
public class ExtentReportFactory {

    //Globally initializing the two objects responsible for creating the report.
    public static ExtentReports extent;
    public static ExtentSparkReporter htmlReporter;

    //A method to call the extent reporter which creates the report.
    public static synchronized ExtentReports getReporter() {
        if (extent == null) {
            extent = new ExtentReports();
            htmlReporter = new ExtentSparkReporter("C:\\Users\\Nate\\IdeaProjects\\Class11\\src\\test\\java\\extent.html");
            extent.attachReporter(htmlReporter);
        }
        return extent;
    }
}
