package com.qa.MainFunctions;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.File;
import java.net.URL;

/**
 * @author pbhattacharjee
 *
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.qa.Utilities.ElementAction;
import com.qa.Utilities.ExcelHandling;
import com.qa.Utilities.ExcelUtil;
import com.qa.Utilities.Extent_Reporting;
import com.qa.Utilities.JavaUtilities;
import com.qa.Utilities.SendEmailwithAttachment;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import atu.testrecorder.ATUTestRecorder;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class DriverCalling extends ExcelHandling {

	public static WebDriver driver;
	public static AndroidDriver mdriver;
	public static ExtentTest test;

	static String Browser = "";
	static String URL = "";
	static String VideoRecording = "";
	static String DocReport = "";
	static String PdfReport = "";

	//static String errormsg;
	public static String TestCase;
	public static String Priority;

	public static ExtentReports report;
	public static ATUTestRecorder recorder;

	public static Logger log = Logger.getLogger("BaseClass");

	@BeforeSuite
	public void SetExtentReportAndLogger() throws Exception {

		ExcelUtil.CreateFile();
		report = new ExtentReports(GlobalConstant.ExtentReport_Path, false);
		PropertyConfigurator.configure(GlobalConstant.Log4j_Path);

	}

	public static WebDriver launchbrowser(String testname, String Prior) {

		try {

			test = report.startTest(testname);
			TestCase = testname;
			Priority = Prior;
			VideoRecording = ExcelHandling.GetExcelData(testname, "Video_Report");
			DocReport = ExcelHandling.GetExcelData(testname, "Doc_Report");
			PdfReport = ExcelHandling.GetExcelData(testname, "Pdf_Report");

			if (VideoRecording.equalsIgnoreCase("Y")) {
				recorder = new ATUTestRecorder(GlobalConstant.Video_Path,
						testname + "_" + JavaUtilities.datetime("ddMMhhmmss"), false);
				recorder.start();
			}
			if (DocReport.equalsIgnoreCase("Y")) {
				Extent_Reporting.createDocxFile(testname);
			}
			if (PdfReport.equalsIgnoreCase("Y")) {
				Extent_Reporting.createPdfFile(testname);
			}

			Browser = ExcelHandling.GetExcelData(testname, "BrowserType");
			URL = ExcelHandling.GetExcelData(testname, "URL");
			driver = InitateDriver(Browser);
			driver.get(URL);
			Extent_Reporting.Log_Pass("Launch", "User Is Navigated To Site : " + URL, test, driver);
			driver.manage().timeouts().pageLoadTimeout(GlobalConstant.Global_Wait, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(GlobalConstant.Global_Wait, TimeUnit.SECONDS);
		} catch (Exception e) {
		}

		return driver;
	}

	public static WebDriver InitateDriver(String browsername) {

		if (browsername.equalsIgnoreCase("chrome")) {
			driver = InitalizeChromeBrowser();
		} else if (browsername.equalsIgnoreCase("ie")) {
			driver = InitalizeIEBrowser();
		} else if (browsername.equalsIgnoreCase("firefox")) {
			driver = InitalizeFireFoxBrowser();
		} else {
			throw new NoSuchSessionException("********* Browser Is Not Specified ***********");
		}
		return driver;

	}

	public static AndroidDriver androidInitalize(String testname, String priority) {
		try {
			DesiredCapabilities cap = new DesiredCapabilities();
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");// "platformName"
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11");
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel_4_XL");
			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
			cap.setCapability(MobileCapabilityType.UDID, "emulator-5554");
			cap.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
			String appUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
					+ File.separator + "resources" + File.separator + "com" + File.separator + "qa" + File.separator
					+ "Apps" + File.separator + "app-release.apk";

			cap.setCapability(MobileCapabilityType.APP, appUrl);
			URL url = new URL("http://0.0.0.0:4723/wd/hub");
			mdriver = new AndroidDriver(url, cap);

			mdriver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
		} catch (Exception e) {
		}

		return mdriver;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static WebDriver InitalizeChromeBrowser() {

		Map prefsMap = new HashMap();
		System.setProperty("webdriver.chrome.driver", GlobalConstant.ChromedriverPath);
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("start-maximized"); // https://stackoverflow.com/a/26283818/1689770
		chromeOptions.addArguments("force-device-scale-factor=1.00");
		chromeOptions.addArguments("high-dpi-support=1.00");

		chromeOptions.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
		chromeOptions.addArguments("--no-sandbox"); // https://stackoverflow.com/a/50725918/1689770
		chromeOptions.addArguments("--disable-infobars"); // https://stackoverflow.com/a/43840128/1689770
		chromeOptions.addArguments("--disable-dev-shm-usage"); // https://stackoverflow.com/a/50725918/1689770
		chromeOptions.addArguments("--disable-browser-side-navigation"); // https://stackoverflow.com/a/49123152/1689770
		chromeOptions.addArguments("--disable-gpu");
		// chromeOptions.addArguments("user-data-dir=/some/path/allow-camera");

		chromeOptions.addArguments("use-fake-device-for-media-stream");
		chromeOptions.addArguments("use-fake-ui-for-media-stream");
		prefsMap.put("profile.default_content_setting_values.media_stream_mic", 1);
		prefsMap.put("profile.default_content_setting_values.media_stream_camera", 1);
		prefsMap.put("profile.default_content_setting_values.geolocation", 1);
		prefsMap.put("profile.default_content_setting_values.notifications", 1);

		prefsMap.put("profile.default_content_settings.popups", 0); // Handle download notification bar
		prefsMap.put("download.default_directory", System.getProperty("user.dir") + "\\Downloads"); // sets the path of
																									// downloaded file.
		chromeOptions.setExperimentalOption("prefs", prefsMap);
		driver = new ChromeDriver(chromeOptions);
		driver.manage().deleteAllCookies();
		// driver.manage().window().maximize();
		return driver;
	}

	public static WebDriver InitalizeFireFoxBrowser() {
		System.setProperty("webdriver.gecko.driver", GlobalConstant.FireFoxdriverPath);
		FirefoxOptions options = new FirefoxOptions();
		options.addPreference("browser.helperapps.neverAsk.saveToDisk",
				"application/octet-stream;application/pdf;application/excel");
		options.addPreference("browser.helperApps.alwaysAsk.force", false);
		options.addPreference("browser.download.manager.showWhenStarting", false);
		options.addPreference("browser.download.folderList", 2);
		options.addPreference("browser.download.dir", System.getProperty("user.dir") + "\\Downloads\\savedPDFs");
		options.addPreference("browser.download.manager.closeWhenDone", false);
		driver = new FirefoxDriver(options);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		return driver;
	}

	public static WebDriver InitalizeIEBrowser() {
		System.setProperty("webdriver.ie.driver", GlobalConstant.IEdriverPath);
		InternetExplorerOptions options = new InternetExplorerOptions();
		options.disableNativeEvents();// Define to ignore
		options.setCapability("enablePersistentHover", true);
		options.setCapability("disable-popup-blocking", true);//
		options.setCapability("ignoreProtectedModeSettings", true); // Define to ignore protected mode settings during
																	// start of IE driver.
		options.setCapability("ignoreZoomSetting", true);// Capability that defines ignore browser zoom settings
		driver = new InternetExplorerDriver(options);
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		return driver;

	}

	/*
	 * public static void EndExcelReport(long EndTime, long StartTime, String
	 * Status, String testcasename) throws Exception { long total = EndTime -
	 * StartTime; ExcelUtil.WriteInExcel("Status", Status, testcasename);
	 * ExcelUtil.WriteInExcel("Start_Time", ExcelUtil.GetTime(StartTime),
	 * testcasename); ExcelUtil.WriteInExcel("End_Time", ExcelUtil.GetTime(EndTime),
	 * testcasename); ExcelUtil.WriteInExcel("TC_Time",
	 * ExcelUtil.timeDifference(total), testcasename); }
	 */

	@SuppressWarnings("null")
	public static void ExcelErrorReport(String testcasename, String priority) throws Exception {
		//String[] error = new String[10];
		String errormsg = "";
		driver = InitalizeChromeBrowser();
		String url = "file:///" + GlobalConstant.ExtentReport_Path;
		driver.get(url);
		Thread.sleep(5000);
		driver.manage().timeouts().pageLoadTimeout(GlobalConstant.Global_Wait, TimeUnit.SECONDS);
		List<WebElement> elements = driver
				.findElements(By.xpath("//ul[@id='test-collection']//following::span[@class='test-name']"));
		for (int i = 1; i <= elements.size(); i++) {
			WebElement testname = driver.findElement(
					By.xpath("(//ul[@id='test-collection']//following::span[@class='test-name'])[" + i + "]"));
			// System.out.println(testname.getText().trim());
			// System.out.println(testcasename);
			//&& (test.getRunStatus().toString().toUpperCase().equals("FAIL"))
			if (((testname.getText().trim()).equals(testcasename)&& (test.getRunStatus().toString().toUpperCase().equals("FAIL")))) {
				// System.out.println("Here");
				driver.findElement(By.xpath(
						"(//ul[@id='test-collection']//following::span[@class='test-name'])[" + i + "]//ancestor::li"))
						.click();
				driver.manage().timeouts().pageLoadTimeout(GlobalConstant.Global_Wait, TimeUnit.SECONDS);
				JavascriptExecutor j = (JavascriptExecutor) driver;
				j.executeScript("window.scrollTo(0, document.body.scrollHeight)");

				List<WebElement> fails = driver
						.findElements(By.xpath("(//div[contains(@class,'details-container')]//td[@title = 'fail'])"));
				for (int s = 1; s <= fails.size(); s++) {
					System.out.println(s);
					WebElement fail = driver
							.findElement(By.xpath("(//div[contains(@class,'details-container')]//td[@title = 'fail'])["
									+ s + "]//following::td[@class = 'step-name' or @class = 'step-details']"));
					
					errormsg = errormsg+","+fail.getText().trim();

				}
			

			}

		}
		
		ExcelUtil.WriteInExcel("ErrorMsg", errormsg, testcasename, priority);

		driver.quit();

	}

	public static void EndExcelReport(long EndTime, long StartTime, String testcasename, String priority)
			throws Exception {
		ExcelUtil.WriteInExcel("Status", test.getRunStatus().toString().toUpperCase(), TestCase, Priority);
		long total = EndTime - StartTime;
		ExcelUtil.WriteInExcel("Start_Time", ExcelUtil.GetTime(StartTime), testcasename, Priority);
		ExcelUtil.WriteInExcel("End_Time", ExcelUtil.GetTime(EndTime), testcasename, Priority);
		ExcelUtil.WriteInExcel("TC_Time", ExcelUtil.timeDifference(total), testcasename, Priority);
	}

	public void EndReport() {
		try {
			if (test != null) {
				report.endTest(test);
				report.flush();
				if (VideoRecording.equalsIgnoreCase("Y")) {
					recorder.stop();
				}
				if (DocReport.equalsIgnoreCase("Y")) {
					Extent_Reporting.addEndTimeToDocx();
				}
				if (PdfReport.equalsIgnoreCase("Y")) {
					Extent_Reporting.addEndTimetoPDF();
					Extent_Reporting.closePdf();
				}

				driver.quit();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterSuite
	public void SendMail() {
		try {
			String comment = ExcelUtil.EndExcel();
			SendEmailwithAttachment.sendEmailReport(comment);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception{
			 ExcelErrorReport("E2E_Flow_AgentCall_NoResponse", "Medium");
		}

}
