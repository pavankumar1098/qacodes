package com.qa.PageObjects;

/**
 * @author pbhattacharjee
 *
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.MainFunctions.DriverCalling;

public class General_Process_PageObjects extends DriverCalling {

	// LOGIN & LOGOUT

	@FindBy(xpath = "//input[@type='email']")
	public WebElement EmailAdd;

	@FindBy(xpath = "//input[@type='password']")
	public WebElement Pwd;

	@FindBy(xpath = "//button[contains(text(),'LOGIN')]")
	public WebElement LoginBtn;

	@FindBy(xpath = "//button[contains(text(),'Google')]")
	public WebElement GoogleSignIn;

	@FindBy(xpath = "//img[contains(@class,'header-image')]//following::button")
	public WebElement LoginedInPerson;

	@FindBy(xpath = "//i[contains(text(),'arrow_drop_down')]")
	public WebElement MyAccDDOptions;

	@FindBy(xpath = "//div[contains(text(),'Sign Out')]")
	public WebElement Logout;

	public General_Process_PageObjects(WebDriver driver) {
		PageFactory.initElements(driver, this);

	}
}
