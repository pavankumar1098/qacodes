package com.qa.Utilities;

/**
 * @author pbhattacharjee
 *
 */

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.qa.MainFunctions.DriverCalling;
import com.qa.MainFunctions.GlobalConstant;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;

public class Extent_Reporting extends DriverCalling {

	static String path = null;
	static String path1 = null;
	static Document doc;
	static String dateval = JavaUtilities.datetime("ddMMhhmmss");
	public static String startTime;
	public static String endTime;
	static XWPFDocument document;
	static XWPFParagraph tmpParagraph;
	static XWPFRun tmpRun;
	static String docfileName = null;
	static FileOutputStream fos = null;

	static Font passed = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new CMYKColor(0, 59, 168, 93));
	static Font header = FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD, new CMYKColor(255, 0, 0, 0));
	static Font failed = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Font.BOLD, new CMYKColor(0, 209, 49, 74));
	static Font blackFont = FontFactory.getFont(FontFactory.COURIER, 12, Font.BOLD, new CMYKColor(0, 0, 0, 255));
	static Font normal = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL, new CMYKColor(0, 0, 0, 255));

	public static void createPdfFile(String testcasename) throws FileNotFoundException, DocumentException {

		String fileName = GlobalConstant.ExtentReportPDF_Path + testcasename + "_" + dateval + ".pdf";
		FileOutputStream fos = new FileOutputStream(fileName);
		doc = new Document();
		PdfWriter.getInstance(doc, fos);
		doc.open();
		addMetaData(testcasename);
	}

	public static void addMetaData(String text) throws DocumentException {
		startTime = JavaUtilities.datetime("dd/MM/yyyy HH:mm:ss");
		Paragraph para = new Paragraph(text, header);
		para.setAlignment(Element.ALIGN_CENTER);
		doc.add(para);
		doc.add(new Paragraph());
		para = new Paragraph(startTime, blackFont);
		para.setAlignment(Element.ALIGN_CENTER);
		doc.add(para);
	}

	public static void createDocxFile(String text) throws DocumentException, IOException {
		startTime = JavaUtilities.datetime("dd/MM/yyyy HH:mm:ss");
		docfileName = GlobalConstant.ExtentReportDoc_Path + text + "_" + dateval + ".docx";
		XWPFDocument document = new XWPFDocument();
		XWPFParagraph paragraph = document.createParagraph();
		// paragraph.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun run = paragraph.createRun();
		run.setText(text);
		run.setFontSize(20);
		run.setBold(true);
		run.setColor("f70f02");
		FileOutputStream out = new FileOutputStream(docfileName);
		document.write(out);
		out.close();
		addtime();
	}

	public static void addtime() {
		try {
			InputStream fs = new FileInputStream(docfileName);
			XWPFDocument doc = new XWPFDocument(OPCPackage.open(fs));
			List<XWPFParagraph> paragraphs = doc.getParagraphs();
			XWPFParagraph paragraph = paragraphs.get(paragraphs.size() - 1);
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun runText = paragraph.createRun();
			runText.addBreak();
			runText.setText(startTime);
			runText.setFontFamily("COURIER");
			runText.setFontSize(10);
			runText.setBold(true);
			runText.setColor("0a0a0a");
			FileOutputStream out = new FileOutputStream(docfileName);
			doc.write(out);
			out.close();
		} catch (Exception e) {
		}
	}

	public static void addEndTimeToDocx() {
		try {
			endTime = JavaUtilities.datetime("dd/MM/yyyy HH:mm:ss");
			InputStream fs = new FileInputStream(docfileName);
			XWPFDocument doc = new XWPFDocument(OPCPackage.open(fs));
			List<XWPFParagraph> paragraphs = doc.getParagraphs();
			XWPFParagraph paragraph = paragraphs.get(paragraphs.size() - 1);
			paragraph.setAlignment(ParagraphAlignment.CENTER);
			XWPFRun runText = paragraph.createRun();
			runText.addBreak();
			runText.setText(endTime);
			runText.setFontFamily("COURIER");
			runText.setFontSize(10);
			runText.setBold(true);
			runText.setColor("0a0a0a");
			FileOutputStream out = new FileOutputStream(docfileName);
			doc.write(out);
			out.close();
		} catch (Exception e) {
		}
	}

	public static void addToDocx(String text) throws DocumentException, InvalidFormatException, IOException {
		InputStream fs = new FileInputStream(docfileName);
		XWPFDocument doc = new XWPFDocument(OPCPackage.open(fs));
		List<XWPFParagraph> paragraphs = doc.getParagraphs();
		XWPFParagraph paragraph = paragraphs.get(paragraphs.size() - 1);
		paragraph.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun runText = paragraph.createRun();
		runText.addBreak();
		runText.setText(text);
		runText.setFontSize(12);
		runText.setFontFamily("TIMES_ROMAN");
		runText.setColor("0a0a0a");
		FileOutputStream out = new FileOutputStream(docfileName);
		doc.write(out);
		out.close();

	}

	public static void addParagraph(String text) throws DocumentException {
		Paragraph para = new Paragraph(text, normal);
		para.setAlignment(Element.ALIGN_LEFT);
		doc.add(para);

	}

	public static void addEndTimetoPDF() throws DocumentException {

		try {
			endTime = JavaUtilities.datetime("dd/MM/yyyy HH:mm:ss");
			Paragraph para = new Paragraph(endTime, blackFont);
			para.setAlignment(Element.ALIGN_CENTER);
			doc.add(para);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void closePdf() {
		try {
			// addEndTimetoPDF();
			doc.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void addScreenShotPDF(String filename) {
		try {
			Image ss = Image.getInstance(filename);
			ss.scaleToFit(PageSize.A4.getWidth() / 2, PageSize.A4.getHeight() / 2);
			doc.add(ss);
			doc.add(new Paragraph(" "));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void addScreenShotDocx(String filename) {
		try {
			InputStream pic = new FileInputStream(filename);
			InputStream fs = new FileInputStream(docfileName);
			XWPFDocument doc = new XWPFDocument(OPCPackage.open(fs));
			List<XWPFParagraph> paragraphs = doc.getParagraphs();
			XWPFParagraph paragraph = paragraphs.get(paragraphs.size() - 1);
			XWPFRun runText = paragraph.createRun();
			runText.addBreak();
			runText.addPicture(pic, XWPFDocument.PICTURE_TYPE_JPEG, filename, Units.toEMU(400), Units.toEMU(250));
			pic.close();
			FileOutputStream out = new FileOutputStream(docfileName);
			doc.write(out);
			out.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String capture(String methodname, WebDriver wdriver) {
		TakesScreenshot sc = (TakesScreenshot) wdriver;
		File src = sc.getScreenshotAs(org.openqa.selenium.OutputType.FILE);
		String filename = new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date());
		String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
		String path = GlobalConstant.ScreenShot_Path + date + "\\" + methodname + "_" + filename + ".png";
		try {
			FileUtils.copyFile(src, new File(path));
		} catch (IOException e) {
			log.info("Screenshot captured failed");
		}
		return path;

	}

	public static String captureScreenShot(String methodname) {
		Robot robot;
		try {
			robot = new Robot();
			BufferedImage image = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			String filename = new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date());
			String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
			path = GlobalConstant.ScreenShot_Path + "/" + date + "/" + methodname + "_" + filename + ".png";
			ImageIO.write(image, "png", new File(path));
			return path;
		} catch (AWTException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void Log_Pass(String screenshotname, String message, ExtentTest test, WebDriver wdriver) {

		try {
			path = capture(screenshotname, wdriver);
			test.log(LogStatus.PASS, message, test.addScreenCapture(path));
			ExcelUtil.WriteInExcel("Status", "Pass", TestCase, Priority);
			if (ExcelHandling.GetExcelData(TestCase, "Doc_Report").equalsIgnoreCase("Y")) {
				addToDocx(message);
				addScreenShotDocx(path);
			}
			if (ExcelHandling.GetExcelData(TestCase, "Pdf_Report").equalsIgnoreCase("Y")) {
				addParagraph(message);
				addScreenShotPDF(path);
			}

			log.info(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void Log_Fail(String screenshotname, String message, ExtentTest test, WebDriver wdriver) {
		try {
			path = capture(screenshotname, wdriver);
			test.log(LogStatus.FAIL, message, test.addScreenCapture(path));
			ExcelUtil.WriteInExcel("Status", "Fail", TestCase, Priority);
			if (ExcelHandling.GetExcelData(TestCase, "Doc_Report").equalsIgnoreCase("Y")) {
				addToDocx(message);
				addScreenShotDocx(path);
			}
			if (ExcelHandling.GetExcelData(TestCase, "Pdf_Report").equalsIgnoreCase("Y")) {
				addParagraph(message);
				addScreenShotPDF(path);
			}
			log.info(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void Log_Info(String screenshotname, String message, ExtentTest test, WebDriver wdriver) {
		try {
			path = capture(screenshotname, wdriver);
			test.log(LogStatus.INFO, message, test.addScreenCapture(path));
			if (ExcelHandling.GetExcelData(TestCase, "Doc_Report").equalsIgnoreCase("Y")) {
				addToDocx(message);
				addScreenShotDocx(path);
			}
			if (ExcelHandling.GetExcelData(TestCase, "Pdf_Report").equalsIgnoreCase("Y")) {
				addParagraph(message);
				addScreenShotPDF(path);
			}
			log.info(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void Log_Message(String message, ExtentTest test, WebDriver wdriver) {
		try {
			test.log(LogStatus.INFO, message);
			if (ExcelHandling.GetExcelData(TestCase, "Doc_Report").equalsIgnoreCase("Y")) {
				addToDocx(message);
			}
			if (ExcelHandling.GetExcelData(TestCase, "Pdf_Report").equalsIgnoreCase("Y")) {
				addParagraph(message);
			}

			log.info(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void Log_FailMessage(String message, ExtentTest test, WebDriver wdriver) {
		try {
			test.log(LogStatus.FAIL, message);
			if (ExcelHandling.GetExcelData(TestCase, "Doc_Report").equalsIgnoreCase("Y")) {
				addToDocx(message);
			}
			if (ExcelHandling.GetExcelData(TestCase, "Pdf_Report").equalsIgnoreCase("Y")) {
				addParagraph(message);
			}
			log.info(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void Log_PassMessage(String message, ExtentTest test, WebDriver wdriver) {
		try {

			test.log(LogStatus.PASS, message);
			if (ExcelHandling.GetExcelData(TestCase, "Doc_Report").equalsIgnoreCase("Y")) {
				addToDocx(message);
			}
			if (ExcelHandling.GetExcelData(TestCase, "Pdf_Report").equalsIgnoreCase("Y")) {
				addParagraph(message);
			}
			log.info(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}