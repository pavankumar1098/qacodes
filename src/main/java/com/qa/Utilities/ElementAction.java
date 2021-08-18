package com.qa.Utilities;

/**
 * @author pbhattacharjee
 *
 */

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.MainFunctions.DriverCalling;
import com.qa.MainFunctions.GlobalConstant;


public class ElementAction extends DriverCalling {

	// Functions to click a button

	public boolean clickButton(WebElement ele, String message) {
		boolean status = false;
		try {
			if (ele.isDisplayed()) {
				// Scroll(ele);
				ele.click();
				Extent_Reporting.Log_Message(message + " is clicked", test, driver);
				status = true;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("clickButton() failed", e.getMessage(), test, driver);

		}
		return status;
	}

	public boolean JSclickButton(WebElement ele, String message) {
		boolean status = false;
		try {
			if (ele.isDisplayed()) {
				Scroll(ele);
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", ele);
				Extent_Reporting.Log_Message(message + " is clicked", test, driver);
				status = true;
			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("JSclickButton() failed", e.getMessage(), test, driver);

		}
		return status;
	}

	public boolean ActionclickButton(WebElement ele, String message) {
		boolean status = false;
		try {
			if (ele.isDisplayed()) {
				Scroll(ele);
				Actions builder = new Actions(driver);
				builder.moveToElement(ele).build().perform();
				Extent_Reporting.Log_Message(message + " is clicked", test, driver);
				status = true;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("ActionclickButton() failed", e.getMessage(), test, driver);

		}
		return status;
	}

	// Functions to enter text

	public boolean inputText(WebElement ele, String text, String message) {
		boolean status = false;
		try {
			if (ele.isDisplayed()) {
				ele.sendKeys(text);
				Extent_Reporting.Log_Pass("Input", message + " is entered", test, driver);
				status = true;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("inputText() failed", e.getMessage(), test, driver);

		}
		return status;
	}

	public boolean clearInputText(WebElement ele, String text, String message) {
		boolean status = false;
		try {
			if (ele.isDisplayed()) {
				ele.clear();
				ele.sendKeys(text);
				Extent_Reporting.Log_Pass("Input", message + " is entered", test, driver);
				status = true;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("inputText() failed", e.getMessage(), test, driver);

		}
		return status;
	}

	public boolean JSinputText(WebElement ele, String text, String message) {
		boolean status = false;
		try {
			if (ele.isDisplayed()) {
				Scroll(ele);
				JavascriptExecutor jse = (JavascriptExecutor) driver;
				jse.executeScript("arguments[0].value='" + text + "';", ele);
				Extent_Reporting.Log_Message(message + " is entered", test, driver);
				status = true;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("JSinputText() failed", e.getMessage(), test, driver);

		}
		return status;
	}

	public boolean ActioninputText(WebElement ele, String text, String message) {
		boolean status = false;
		try {
			if (ele.isDisplayed()) {
				Scroll(ele);
				Actions builder = new Actions(driver);
				builder.moveToElement(ele).sendKeys(text).build().perform();
				Extent_Reporting.Log_Message(message + " is entered", test, driver);
				status = true;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("ActioninputText() failed", e.getMessage(), test, driver);

		}
		return status;
	}

	// Action Class functions
	public void mouseHover(WebElement ele) {
		try {
			if (ele.isDisplayed()) {
				Scroll(ele);
				Actions builder = new Actions(driver);
				builder.moveToElement(ele).build().perform();
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("mouseHover() failed", e.getMessage(), test, driver);

		}
	}
	// Alert functions

	public boolean alertIsPresent() {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, GlobalConstant.Global_Wait);
			if (wait.until(ExpectedConditions.alertIsPresent()) != null) {
				status = true;
				Extent_Reporting.Log_Message("Alert is present", test, driver);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("alertIsPresent() failed", e.getMessage(), test, driver);

		}
		return status;
	}

	public void acceptAlert() {
		try {
			boolean status = alertIsPresent();
			if (status) {
				driver.switchTo().alert().accept();
				Extent_Reporting.Log_Message("Alert is accepted", test, driver);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("acceptAlert() failed", e.getMessage(), test, driver);

		}

	}

	public void rejectAlert() {
		try {
			boolean status = alertIsPresent();
			if (status) {
				driver.switchTo().alert().dismiss();
				Extent_Reporting.Log_Message("Alert is rejected", test, driver);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("rejectAlert() failed", e.getMessage(), test, driver);

		}

	}

	public String getTextAlert() {
		String alerttext = null;
		try {
			alerttext = null;
			boolean status = alertIsPresent();
			if (status) {
				alerttext = driver.switchTo().alert().getText();
				Extent_Reporting.Log_Message("Text from alert " + alerttext, test, driver);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("getTextAlert() failed", e.getMessage(), test, driver);

		}

		return alerttext;
	}

	public void sendTextAlert(String text) {

		try {
			boolean status = alertIsPresent();
			if (status) {
				driver.switchTo().alert().sendKeys(text);
				Extent_Reporting.Log_Message("Text sent to alert " + text, test, driver);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("sendTextAlert() failed", e.getMessage(), test, driver);

		}

	}

	// Select dropdown functions

	public void selectDDByText(WebElement ele, String text) {
		try {
			WaitUntilDisplayed(ele);
			if (ele.isDisplayed()) {
				Select selectEle = new Select(ele);
				selectEle.selectByVisibleText(text);
				Extent_Reporting.Log_Message("Drop down option with visible text " + text + " is selected", test,
						driver);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("selectDDByText() failed", e.getMessage(), test, driver);
		}
	}

	public void selectDDByValue(WebElement ele, String value) {
		try {
			WaitUntilDisplayed(ele);
			if (ele.isDisplayed()) {
				Select selectEle = new Select(ele);
				selectEle.selectByValue(value);
				Extent_Reporting.Log_Message("Drop down option with value " + value + " is selected", test, driver);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("selectDDByValue() failed", e.getMessage(), test, driver);
		}
	}

	public void selectDDByIndex(WebElement ele, int Index) {
		try {
			WaitUntilDisplayed(ele);
			if (ele.isDisplayed()) {
				Select selectEle = new Select(ele);
				selectEle.selectByIndex(Index);
				Extent_Reporting.Log_Message("Drop down option at the position " + Index + " is selected", test,
						driver);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("selectDDByIndex() failed", e.getMessage(), test, driver);
		}
	}

	public boolean isMultipleSelect(WebElement ele) {
		boolean multiple = false;
		try {
			WaitUntilDisplayed(ele);
			if (ele.isDisplayed()) {
				Select selectEle = new Select(ele);
				if (selectEle.isMultiple()) {
					multiple = true;
					Extent_Reporting.Log_Message("Multiple selection is allowed", test, driver);
				}
			} else {
				throw new Exception();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("isMultipleSelect() failed", e.getMessage(), test, driver);
		}
		return multiple;
	}

	public void deselectDDByText(WebElement ele, String text) {
		try {
			WaitUntilDisplayed(ele);
			if (ele.isDisplayed()) {
				Select selectEle = new Select(ele);
				selectEle.deselectByVisibleText(text);
				Extent_Reporting.Log_Message("Drop down option with visible text " + text + " is deselected", test,
						driver);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("deselectDDByText() failed", e.getMessage(), test, driver);
		}
	}

	public void deselectDDByValue(WebElement ele, String value) {
		try {

			WaitUntilDisplayed(ele);
			if (ele.isDisplayed()) {
				Select selectEle = new Select(ele);
				selectEle.deselectByValue(value);
				Extent_Reporting.Log_Message("Drop down option with value " + value + " is deselected", test, driver);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("deselectDDByValue() failed", e.getMessage(), test, driver);
		}
	}

	public void deselectDDByIndex(WebElement ele, int Index) {
		try {
			WaitUntilDisplayed(ele);
			if (ele.isDisplayed()) {
				Select selectEle = new Select(ele);
				selectEle.selectByIndex(Index);
				Extent_Reporting.Log_Message("Drop down option at the position " + Index + " is deselected", test,
						driver);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("deselectDDByIndex() failed", e.getMessage(), test, driver);
		}
	}

	public void deselectDDAll(WebElement ele) {
		try {
			WaitUntilDisplayed(ele);
			if (ele.isDisplayed()) {
				Select selectEle = new Select(ele);
				selectEle.deselectAll();
				Extent_Reporting.Log_Message("All the dropdown option is deselected", test, driver);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("deselectDDAll() failed", e.getMessage(), test, driver);
		}
	}

	// Window switching

	public void windowSwitch(WebElement TargetEle, String message) {
		try {
			Set<String> allWindowHandles = driver.getWindowHandles();

			for (String handle : allWindowHandles) {
				driver.switchTo().window(handle);
				if (elementDisplayed(TargetEle)) {
					Extent_Reporting.Log_Message(message + " found and switched to ", test, driver);
					break;
				} else {
					throw new Exception();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("windowSwitch() failed", e.getMessage(), test, driver);

		}
	}

	// Frame switching
	public void frameSwitch(WebElement FrameEle, String message) {
		try {
			WaitUntilDisplayed(FrameEle);
			driver.switchTo().frame(FrameEle);
			Extent_Reporting.Log_Message(message + " found and switched to ", test, driver);
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("frameSwitch() failed", e.getMessage(), test, driver);

		}
	}

	public void defaultSwitch(String message) {

		try {
			driver.switchTo().defaultContent();
			Extent_Reporting.Log_Message(message + " found and switched to ", test, driver);
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("defaultSwitch() failed", e.getMessage(), test, driver);

		}
	}

	// Element functions
	public String elementGetText(WebElement ele, String message) {
		String text = null;
		try {
			WaitUntilDisplayed(ele);
			text = ele.getText();
			Extent_Reporting.Log_Message("Text of the element : " + text, test, driver);

		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("elementGetText() failed", e.getMessage(), test, driver);

		}
		return text;
	}

	public String elementGetAttribute(WebElement ele, String attribute, String message) {
		String text = null;
		try {
			WaitUntilDisplayed(ele);
			text = ele.getAttribute(attribute).trim();
			Extent_Reporting.Log_Message("Attribute for " + attribute + " is : " + text, test, driver);

		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("elementGetAttribute() failed", e.getMessage(), test, driver);

		}
		return text;
	}

	// Generic functions

	public void Scroll(WebElement ele) {
		try {
			WaitUntilDisplayed(ele);
			JavascriptExecutor j = (JavascriptExecutor) driver;
			j.executeScript("arguments[0].scrollIntoView();", ele);
			Extent_Reporting.Log_Message("Scrolled successfully", test, driver);
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("Scroll() failed", e.getMessage(), test, driver);

		}
	}

	public void ScrollToTop() {
		try {
			JavascriptExecutor j = (JavascriptExecutor) driver;
			j.executeScript("window.scrollTo(0, 0)");
			Extent_Reporting.Log_Message("Scrolled successfully", test, driver);
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("Scroll() failed", e.getMessage(), test, driver);

		}
	}

	public void ScrollToBottom() {
		try {
			JavascriptExecutor j = (JavascriptExecutor) driver;
			j.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Extent_Reporting.Log_Message("Scrolled successfully", test, driver);
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("Scroll() failed", e.getMessage(), test, driver);

		}
	}

	public boolean waitForPageLoad() {
		boolean status = false;
		try {
			ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
				}
			};
			WebDriverWait wait = new WebDriverWait(driver, GlobalConstant.Global_Wait);
			wait.until(pageLoadCondition);
			status = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Extent_Reporting.Log_Fail("waitForPageLoad() failed", e.getMessage(), test, driver);
		}
		return status;
	}

	public boolean WaitForElement(WebElement ele, String message) {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, GlobalConstant.Global_Wait);
			wait.until(ExpectedConditions.visibilityOf(ele));
			Extent_Reporting.Log_Message(message + " is visible", test, driver);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("WaitForElement() failed", e.getMessage(), test, driver);

		}
		return status;
	}

	public boolean waitForElementClickable(WebElement ele, String message) {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, GlobalConstant.Global_Wait);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			Extent_Reporting.Log_Message(message + " is clickable", test, driver);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("waitForElementClickable() failed", e.getMessage(), test, driver);

		}
		return status;
	}

	public boolean WaitUntilDisplayed(WebElement ele) {
		boolean status = false;
		try {

			for (int i = 0; i < 5; i++) {
				if (elementDisplayed(ele)) {
					status = true;
					// Extent_Reporting.Log_Message("Wait successful Element is displayed",
					// test,driver);
					// break;
				} else {
					driver.manage().timeouts().implicitlyWait(GlobalConstant.Global_Wait, TimeUnit.SECONDS);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("WaitUntilDisplayed() failed", e.getMessage(), test, driver);

		}
		return status;
	}

	public boolean elementDisplayed(WebElement ele) {
		boolean status = false;

		try {
			WebDriverWait wait = new WebDriverWait(driver, GlobalConstant.Global_Wait);
			wait.until(ExpectedConditions.visibilityOf(ele));
			wait.until(ExpectedConditions.elementToBeClickable(ele));
			status = true;
			// ele.isDisplayed();

		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("elementDisplayed() failed", e.getMessage(), test, driver);

		}

		return status;
	}

	public boolean isElementDisplayed(WebElement ele, String name) {
		boolean status = false;

		try {
			status = ele.isDisplayed();
			Extent_Reporting.Log_Pass("Element Pressence", name + " is displayed", test, driver);

		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("isElementDisplayed() failed", e.getMessage(), test, driver);

		}

		return status;
	}

	public boolean l(WebElement ele, String name) {
		boolean status = false;
		Scroll(ele);
		try {
			status = ele.isEnabled();
			Extent_Reporting.Log_Pass("Element enabled", name + " is enabled", test, driver);
		} catch (Exception e) {
			e.printStackTrace();
			Extent_Reporting.Log_Fail("isElementEnabled() failed", e.getMessage(), test, driver);

		}

		return status;
	}

}
