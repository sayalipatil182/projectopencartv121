package utilities;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import testBase.BaseClass;

public class ExtendReportManager implements ITestListener {

    private ExtentSparkReporter sparkReporter;
    private ExtentReports extent;
    private ExtentTest test;
    private String reportName;
    private String reportPath;

    // Executes before any test starts
    @Override
    public void onStart(ITestContext context) {

        // Create timestamped report name
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        reportName = "Test-Report-" + timeStamp + ".html";

        // Ensure report folder exists
        String reportDir = System.getProperty("user.dir") + "/report/";
        File dir = new File(reportDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        reportPath = reportDir + reportName;
        sparkReporter = new ExtentSparkReporter(reportPath);

        sparkReporter.config().setDocumentTitle("Opencart Automation Report");
        sparkReporter.config().setReportName("Opencart Functional Testing Report");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Add system info
        extent.setSystemInfo("Application", "Opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");

        String os = context.getCurrentXmlTest().getParameter("os");
        if (os != null) extent.setSystemInfo("OS", os);

        String browser = context.getCurrentXmlTest().getParameter("browser");
        if (browser != null) extent.setSystemInfo("Browser", browser);

        List<String> includedGroups = Arrays.asList(context.getIncludedGroups());
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Included Groups", includedGroups.toString());
        }
    }

    // Executes when each test starts
    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getName());
    }

    // Executes when a test passes
    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, "Test Passed: " + result.getName());
        test.assignCategory(result.getMethod().getGroups());
    }

    // Executes when a test fails
    @Override
    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());

        test.log(Status.FAIL, "Test Failed: " + result.getName());
        if (result.getThrowable() != null) {
            test.log(Status.INFO, result.getThrowable().getMessage());
        }

        try {
            String imgPath = new BaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Executes when a test is skipped
    @Override
    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, "Test Skipped: " + result.getName());

        if (result.getThrowable() != null) {
            test.log(Status.INFO, result.getThrowable().getMessage());
        }
    }

    // Executes after all tests finish
    @Override
    public void onFinish(ITestContext context) {
        extent.flush(); // Write everything to the report
        try {
            File extentReportFile = new File(reportPath);
            if (extentReportFile.exists()) {
                Desktop.getDesktop().browse(extentReportFile.toURI());
            } else {
                System.err.println("Report file not found: " + reportPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
