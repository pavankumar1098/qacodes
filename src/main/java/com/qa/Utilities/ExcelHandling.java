package com.qa.Utilities;

/**
 * @author pbhattacharjee
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.qa.MainFunctions.GlobalConstant;

public class ExcelHandling {

	static XSSFWorkbook wb;
	static XSSFSheet sheet;
	static File file;
	static FileInputStream fis;
	static int lastRowNum;
	static int lastCellNum;
	static String data = null;

	public static void WriteInExcel(String columnname, String data, String testcasename) {
		try {
			file = new File(GlobalConstant.ExcelPath);
			fis = new FileInputStream(file);
			wb = new XSSFWorkbook(fis);
			outerloop: for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				sheet = wb.getSheetAt(i);
				lastRowNum = sheet.getLastRowNum();
				for (int j = 1; j <= lastRowNum; j++) {
					for (int k = 1; k < (sheet.getRow(0).getLastCellNum()); k++) {
						if (sheet.getRow(0).getCell(k).getStringCellValue().trim().equalsIgnoreCase(columnname.trim())
								&& sheet.getRow(j).getCell(1).getStringCellValue().trim()
										.equalsIgnoreCase(testcasename.trim())) {
							sheet.getRow(j).getCell(k).setCellValue(data);
							break outerloop;
							// fis.close();
						}
					}

				}
			}

			FileOutputStream fos = new FileOutputStream(file);
			wb.write(fos);
			fos.close();
			fos.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String GetExcelData(String testname, String columnname) {
		try {
			file = new File(GlobalConstant.ExcelPath);
			fis = new FileInputStream(file);
			wb = new XSSFWorkbook(fis);

			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				sheet = wb.getSheetAt(i);
				lastRowNum = sheet.getLastRowNum();
				for (int j = 0; j < lastRowNum; j++) {
					HashMap<String, String> datamap = new HashMap<String, String>();

					for (int k = 0; k < (sheet.getRow(0).getLastCellNum()); k++) {

						datamap.put(sheet.getRow(0).getCell(k).toString(), sheet.getRow(j + 1).getCell(k).toString());

					}

					if (datamap.get("Runmode").equals("Y") && datamap.get("TestCaseName").equals(testname)) {
						data = datamap.get(columnname).toString();
					}

				}

			}

		} catch (Exception ex) {
			
		}

		return data;

	}

	public static boolean setCellData(String filepath, String sheetName, int colNum, int rowNum, String data) {
		try {

			if (rowNum <= 0)
				return false;
			wb = new XSSFWorkbook(OPCPackage.open(new FileInputStream(filepath)));
			sheet = wb.getSheet(sheetName);
			XSSFRow row = sheet.getRow(0);

			if (colNum == -1)
				return false;
			// sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);
			XSSFCell cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);
			cell.setCellValue(data);
			FileOutputStream fileOut = new FileOutputStream(filepath);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static HashMap<String, String> readCellData(String filepath) {
		HashMap<String, String> datamap = null;
		try {

			File file = new File(filepath);
			FileInputStream inputStream = new FileInputStream(file);

			wb = new XSSFWorkbook(inputStream);
			for (int k = 0; k < wb.getNumberOfSheets(); k++) {
				sheet = wb.getSheetAt(k);
				int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
				for (int i = 0; i < rowCount; i++) {

					datamap = new HashMap<String, String>();
					Row row = sheet.getRow(i);
					for (int j = 0; j < row.getLastCellNum(); j++) {
						if (((sheet.getRow(i + 1).getCell(j, org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK)
								.getStringCellValue().toString()).equals("null"))) {
							datamap.put(sheet.getRow(0).getCell(j).toString(), "null");
						} else
							datamap.put(sheet.getRow(0).getCell(j).toString(),
									sheet.getRow(i + 1).getCell(j).toString());

					}
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return datamap;

	}

}
