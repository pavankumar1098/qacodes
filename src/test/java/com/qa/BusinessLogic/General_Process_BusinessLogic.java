package com.qa.BusinessLogic;

/**
 * @author pbhattacharjee

 *
 */

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import com.qa.Utilities.Extent_Reporting;
import com.qa.Utilities.ExcelHandling;
import com.qa.Utilities.ElementAction;
import com.qa.PageObjects.General_Process_PageObjects;

public class General_Process_BusinessLogic extends Extent_Reporting {

	ElementAction action = new ElementAction();
	String TC_ID = null;
	WebDriver driver;
	General_Process_PageObjects general_elements;
	String key, value;
	long total = 0;

	public General_Process_BusinessLogic(WebDriver driver, String TC_ID) {
		this.driver = driver;
		this.TC_ID = TC_ID;
		general_elements = new General_Process_PageObjects(driver);
	}

	public String Salesken_Login() throws Throwable {
		String loginedin = null;
		try {
			action.WaitUntilDisplayed(general_elements.EmailAdd);
			action.inputText(general_elements.EmailAdd, ExcelHandling.GetExcelData(TC_ID, "LoginEmailId"),
					"Email Address");
			action.inputText(general_elements.Pwd, ExcelHandling.GetExcelData(TC_ID, "LoginPassword"), "Password");
			action.clickButton(general_elements.LoginBtn, "Login button");
			action.WaitUntilDisplayed(general_elements.LoginedInPerson);
			loginedin = action.elementGetText(general_elements.LoginedInPerson, "Person logged in ");
			String[] fielddata = loginedin.split("\\r?\\n", 2);
			loginedin = fielddata[0];

		}

		catch (Exception e) {
			Extent_Reporting.Log_FailMessage("Salesken_Login() failed " + e.getLocalizedMessage(), test, driver);
			driver.quit();
			throw new Exception(e.getLocalizedMessage());

		}
		return loginedin;

	}

	public void Salesken_MgrLogin() throws Throwable {
		try {
			action.WaitUntilDisplayed(general_elements.EmailAdd);
			action.inputText(general_elements.EmailAdd, ExcelHandling.GetExcelData(TC_ID, "Mgr_Login"),
					"Email Address");
			action.inputText(general_elements.Pwd, ExcelHandling.GetExcelData(TC_ID, "Mgr_Password"), "Password");
			action.clickButton(general_elements.LoginBtn, "Login button");
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		}

		catch (Exception e) {
			Extent_Reporting.Log_FailMessage("Salesken_MgrLogin() failed " + e.getLocalizedMessage(), test, driver);
			driver.quit();
			throw new Exception(e.getLocalizedMessage());

		}

	}

	public void Salesken_Logout() throws Throwable {

		try {
			action.WaitUntilDisplayed(general_elements.MyAccDDOptions);
			action.clickButton(general_elements.MyAccDDOptions, "Dropdown options");
			action.WaitUntilDisplayed(general_elements.Logout);
			Extent_Reporting.Log_Pass("tologout", "Log out option displayed", test, driver);
			action.clickButton(general_elements.Logout, "Sign Out");
			action.WaitUntilDisplayed(general_elements.EmailAdd);
			Extent_Reporting.Log_Pass("LoggedOut", "User logged out successfully", test, driver);

		}

		catch (Exception e) {
			Extent_Reporting.Log_FailMessage("Salesken_Logout() failed " + e.getLocalizedMessage(), test, driver);
			driver.quit();
			throw new Exception(e.getLocalizedMessage());

		}

	}

}
