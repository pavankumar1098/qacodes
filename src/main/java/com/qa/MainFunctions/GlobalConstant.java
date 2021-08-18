package com.qa.MainFunctions;

/**
 * @author pbhattacharjee
 *
 */

import com.qa.Utilities.JavaUtilities;

public class GlobalConstant {

	static String dateval = JavaUtilities.datetime("ddMMhhmmss");
	public static final String ChromedriverPath=System.getProperty("user.dir")+"\\driver\\chromedriver.exe";
	
    public static final String FireFoxdriverPath=System.getProperty("user.dir")+"\\driver\\geckodriver.exe";
    
    public static final String IEdriverPath=System.getProperty("user.dir")+"\\driver\\IEDriverServer.exe";
    
    public static final long Global_Wait=5;
    
    public static final String DownloadPath = System.getProperty("user.dir")+"\\Downloads";
//	
	public static final String ExcelPath=System.getProperty("user.dir")+"\\src\\test\\resources\\com\\qa\\TestData\\DataSheet.xlsx";
		
	public static final String Log4j_Path=System.getProperty("user.dir")+"\\src\\main\\java\\com\\qa\\log4j\\log4j.properties";
	
	public static final String ExtentReport_Path=System.getProperty("user.dir") + "\\src\\test\\resources\\com\\qa\\Reports\\HTML\\TestCasesResults_"+dateval+".html";

	public static final String ScreenShot_Path=System.getProperty("user.dir") + "\\src\\test\\resources\\com\\qa\\Reports\\Screenshots\\";

	public static final String ExtentReportPDF_Path=System.getProperty("user.dir") + "\\src\\test\\resources\\com\\qa\\Reports\\PDF\\";

	public static final String ExtentReportDoc_Path=System.getProperty("user.dir") + "\\src\\test\\resources\\com\\qa\\Reports\\Doc\\";

	public static final String Video_Path=System.getProperty("user.dir") + "\\src\\test\\resources\\com\\qa\\Reports\\Video\\";
	
	public static final String Recording=System.getProperty("user.dir") + "\\Recording\\";
	
	public static final String TestReport = System.getProperty("user.dir")+"\\Reports\\TestResults_"+dateval+".xlsx";
	
	
	   
//    public static final String DownloadPath = System.getProperty("user.dir")+"\\Downloads";
//	
//	public static final String ExcelPath=System.getProperty("user.dir")+"\\TestData\\DataSheet.xlsx";
//		
//	public static final String Log4j_Path=System.getProperty("user.dir")+"\\log4j\\log4j.properties";
//	
//	public static final String ExtentReport_Path=System.getProperty("user.dir") + "\\Reports\\HTML\\TestCasesResults_"+dateval+".html";
//
//	public static final String ScreenShot_Path=System.getProperty("user.dir") + "\\Reports\\Screenshots\\";
//
//	public static final String ExtentReportPDF_Path=System.getProperty("user.dir") + "\\Reports\\PDF\\";
//
//	public static final String ExtentReportDoc_Path=System.getProperty("user.dir") + "\\Reports\\Doc\\";
//
//	public static final String Video_Path=System.getProperty("user.dir") + "\\Reports\\Video\\";

	
}
