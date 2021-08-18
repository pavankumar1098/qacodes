package com.qa.Utilities;

/**
 * @author pbhattacharjee
 *
 */

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileFilter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.io.filefilter.WildcardFileFilter;

public class JavaUtilities {

	public static File getTheNewestFile(String filePath, String ext) {
		File theNewestFile = null;
		File dir = new File(filePath);
		FileFilter fileFilter = new WildcardFileFilter("*." + ext);
		File[] files = dir.listFiles(fileFilter);

		theNewestFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (theNewestFile.lastModified() < files[i].lastModified()) {
				theNewestFile = files[i];
				
			}
		}

		return theNewestFile;

	}

	public static void deleteFiles(String filePath, String ext) {
		File dir = new File(filePath);
		FileFilter fileFilter = new WildcardFileFilter("*." + ext);
		File[] files = dir.listFiles(fileFilter);
		for (File file : files) {
			file.delete();
		}

	}

	public static File getTheOldestFile(String filePath, String ext) {
		File theOldestFile = null;
		File dir = new File(filePath);
		FileFilter fileFilter = new WildcardFileFilter("*." + ext);
		File[] files = dir.listFiles(fileFilter);

		theOldestFile = files[0];

		for (int i = 1; i < files.length; i++) {
			if (theOldestFile.lastModified() > files[i].lastModified()) {
				theOldestFile = files[i];

			}
		}

		return theOldestFile;

	}

	public static void uploadFile(File filename) throws AWTException, InterruptedException {
		try {
			String path = filename.getAbsolutePath();
			String Path = path.replace("//", "/");
			StringSelection ss = new StringSelection(Path);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

			// imitate mouse events like ENTER, CTRL+C, CTRL+V
			Robot robot = new Robot();

			robot.keyPress(KeyEvent.VK_ENTER);

			robot.keyRelease(KeyEvent.VK_ENTER);

			robot.keyPress(KeyEvent.VK_CONTROL);

			robot.keyPress(KeyEvent.VK_V);

			robot.keyRelease(KeyEvent.VK_V);

			robot.keyRelease(KeyEvent.VK_CONTROL);

			robot.keyPress(KeyEvent.VK_ENTER);

			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public static String datetime(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);

		Date date = new Date();
		String todate = dateFormat.format(date);

		return todate;
	}

	public static String dateconversion(String format, String targetformat, String enterdate) throws ParseException {
		DateFormat inputFormat = new SimpleDateFormat(format);
		Date input = inputFormat.parse(enterdate);
		DateFormat outputFormat = new SimpleDateFormat(targetformat, Locale.ENGLISH);
		String finaldate = outputFormat.format(input);

		return finaldate;
	}

	public static String dateconversionwithoutsec(String format, String targetformat, String enterdate)
			throws ParseException {
		DateFormat inputFormat = new SimpleDateFormat(format);
		Date input = inputFormat.parse(enterdate);
		Calendar c = new GregorianCalendar();
		c.setTime(input);

		if (c.get(Calendar.SECOND) >= 30)
			c.add(Calendar.MINUTE, 1);
		input = c.getTime();
		DateFormat outputFormat = new SimpleDateFormat(targetformat, Locale.ENGLISH);
		String finaldate = outputFormat.format(input);

		return finaldate;
	}

	public static String getNextDate(String curDate, String format) {
		String nextDate = "";
		try {
			Calendar today = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat(format);
			Date date = dateFormat.parse(curDate);
			today.setTime(date);
			today.add(Calendar.DAY_OF_YEAR, 2);
			nextDate = dateFormat.format(today.getTime());
		} catch (Exception e) {
			return nextDate;
		}
		return nextDate;
	}

	public static String getPreviousDate(String curDate, String format) {
		String nextDate = "";
		try {
			Calendar today = Calendar.getInstance();
			DateFormat dateFormat = new SimpleDateFormat(format);
			Date date = dateFormat.parse(curDate);
			today.setTime(date);
			today.add(Calendar.DAY_OF_YEAR, -1);
			nextDate = dateFormat.format(today.getTime());
		} catch (Exception e) {
			return nextDate;
		}
		return nextDate;
	}

	public static Date string2Date(String sdate, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date1 = null;
		try {
			date1 = dateFormat.parse(sdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date1;
	}

	public static void clickAlertToggle(int xCoordinate, int yCoordinate) throws Exception {
		try {

			Thread.sleep(1000);
			Robot robot = new Robot();
			robot.mouseMove(xCoordinate, yCoordinate); // x cord- 231 , y cord – 232.
			robot.mousePress(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
			Thread.sleep(1000);
			robot.mouseRelease(java.awt.event.InputEvent.BUTTON1_DOWN_MASK);
			Thread.sleep(1000);

		} catch (Throwable e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());

		}
	}

	public static void zoomChoice(String choice) {

		try {
			Robot robot = new Robot();
			if (choice.equalsIgnoreCase("IN")) {
				for (int i = 0; i < 3; i++) {
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_ADD);
					robot.keyRelease(KeyEvent.VK_ADD);
					robot.keyRelease(KeyEvent.VK_CONTROL);
				}
			} else if (choice.equalsIgnoreCase("OUT")) {
				for (int i = 0; i < 4; i++) {
					robot.keyPress(KeyEvent.VK_CONTROL);
					robot.keyPress(KeyEvent.VK_SUBTRACT);
					robot.keyRelease(KeyEvent.VK_SUBTRACT);
					robot.keyRelease(KeyEvent.VK_CONTROL);
				}
			}

		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
