package test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import pages.Enquiry;
import pages.Login;
import pages.Master_Page;
import pages.Quotation;

public class ERP_Test {

	WebDriver driver;
	ExtentHtmlReporter reporter;
	ExtentTest test;
	ExtentReports extent;

	@BeforeTest
	public void urlloading() {

		reporter = new ExtentHtmlReporter("./Reporter/ERP CRM Report.html");
		reporter.config().setDocumentTitle("Automationreport");
		reporter.config().setReportName("ERP CRM Report");
		reporter.config().setTheme(Theme.STANDARD);
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Hostname", "localhost");
		extent.setSystemInfo("OS", "windows11");
		extent.setSystemInfo("Tester Name", " ");
		extent.setSystemInfo("Browser Name", "Chrome");
		extent.setSystemInfo("Tool & Technology", "Selenium with POM Design");

		ChromeOptions options = new ChromeOptions();
//		options.addArguments("--remote-allow-origins=*");
//		options.addArguments("--disable-notifications");

		Map<String, Object> prefs = new HashMap<>();
		prefs.put("profile.default_content_setting_values.notifications", 1); // 1 = allow
		options.setExperimentalOption("prefs", prefs);
		driver = new ChromeDriver(options);

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://erptest.prog-biz.com/");

	}
	
	@Test(priority = 0)
	public void data_driven_testing() throws Exception {

		test = extent.createTest("Data Driven testing");
		Login ddt = new Login(driver, test);
		ddt.data_driven_test();
	}

	@Test(priority = 1)
	public void login_test() throws Exception {

		test = extent.createTest("Login with valid credentials");
		Login lg = new Login(driver, test);
		lg.login();

	}



	@AfterTest
	public void teardown() {
		extent.flush();
	}

	@AfterMethod
	public void browserclose(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {

			test.log(Status.FAIL, "Test case failed is : " + result.getName());
			test.log(Status.FAIL, "Failure explanation : " + result.getThrowable());

			String screenshotpath = ERP_Test.screenshotMethod(driver, result.getName());
			test.addScreenCaptureFromPath(screenshotpath);

		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, "Test case skipped is : " + result.getName());
			String screenshotpath = ERP_Test.screenshotMethod(driver, result.getName());
			test.addScreenCaptureFromPath(screenshotpath);

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, "Test case Passed is : " + result.getName());
//			String screenshotpath=ERP_Test.screenshotMethod(driver,result.getName());
//		    test.addScreenCaptureFromPath(screenshotpath);

		}

	}

	public static String screenshotMethod(WebDriver driver, String screenshotName) throws IOException {
		// Capture screenshot and return the path
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/Screenshot/" + screenshotName + ".png";

		File destFile = new File(destination);
		FileHandler.copy(src, destFile);
		return destination;
	}

}
