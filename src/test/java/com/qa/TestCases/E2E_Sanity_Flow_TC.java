/**
 * 
 */
package com.qa.TestCases;

import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import com.qa.BusinessLogic.General_Process_BusinessLogic;
import com.qa.MainFunctions.DriverCalling;
import com.qa.MainFunctions.GlobalConstant;
import com.qa.Utilities.Extent_Reporting;

/**
 * @author pbhattacharjee
 *
 */
public class E2E_Sanity_Flow_TC extends DriverCalling {

	static XSSFWorkbook wb;
	static XSSFSheet sheet;
	static File file;
	static FileInputStream fis;
	Long StartTime;
	Long EndTime;

	public static WebDriver webDriver = null;
	public static String tcID = null;
	public static String prior = null;
	String loginPerson = null;
	General_Process_BusinessLogic GP = null;
	
	@Test
	public void TC_ActivityLog() throws Throwable {
		try {
			file = new File(GlobalConstant.ExcelPath);
			fis = new FileInputStream(file);
			wb = new XSSFWorkbook(fis);
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				sheet = wb.getSheetAt(i);
				for (int j = 1; j <= sheet.getLastRowNum(); j++) {
					if ((sheet.getRow(j).getCell(2).toString()).equalsIgnoreCase(this.getClass().getSimpleName())
							&& (sheet.getRow(j).getCell(5).toString()).equalsIgnoreCase("Y")) {
						StartTime = System.currentTimeMillis();
						try {
							tcID = sheet.getRow(j).getCell(1).toString();
							prior = sheet.getRow(j).getCell(3).toString();
							webDriver = launchbrowser(tcID, prior);
							GP = new General_Process_BusinessLogic(webDriver, tcID);

							loginPerson = GP.Salesken_Login();
							GP.Salesken_Logout();
							GP.Salesken_MgrLogin();
							EndTime = System.currentTimeMillis();
							EndExcelReport(EndTime, StartTime, tcID, prior);
							EndReport();
							ExcelErrorReport(tcID, prior);

						} catch (Exception e) {
							EndTime = System.currentTimeMillis();
							EndExcelReport(EndTime, StartTime, tcID, prior);

							EndReport();
							ExcelErrorReport(tcID, prior);
						}
					}

				}
			}

			// TODO Test Case Step Function
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	@AfterTest
	public void tearDown() {
		if (webDriver != null) {
			webDriver.quit();
		}
	}


}
