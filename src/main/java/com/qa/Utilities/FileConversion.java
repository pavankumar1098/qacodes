package com.qa.Utilities;

import java.util.*;
import org.apache.poi.ss.usermodel.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.qa.MainFunctions.DriverCalling;

public class FileConversion extends DriverCalling {

	public static WebDriver webDriver = driver;
	static CSVFileCompare csvfilecomp = new CSVFileCompare(webDriver);
	static String fie = null;
	@SuppressWarnings("unused")
	private static String TC_ID;

	public FileConversion(WebDriver driver, String TC_ID) {
		FileConversion.driver = driver;
		FileConversion.TC_ID = TC_ID;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String CSVToExcelConverter(String filename, String xlsxfile) {
		try {
			ArrayList arList = null;
			ArrayList al = null;

			String fName = filename;
			String thisLine;
			@SuppressWarnings("resource")
			BufferedReader brTest = new BufferedReader(new FileReader(fName));

			arList = new ArrayList();
			while ((thisLine = brTest.readLine()) != null) {
				al = new ArrayList();
				String strar[] = thisLine.split(",");
				for (int j = 0; j < strar.length; j++) {
					al.add(strar[j]);
				}
				arList.add(al);

			}

			XSSFWorkbook hwb = new XSSFWorkbook();
			XSSFSheet sheet = hwb.createSheet("newsheet");
			for (int k = 0; k < arList.size(); k++) {
				ArrayList ardata = (ArrayList) arList.get(k);
				XSSFRow row = sheet.createRow((short) 0 + k);
				for (int p = 0; p < ardata.size(); p++) {
					XSSFCell cell = row.createCell((short) p);
					String data = ardata.get(p).toString();
					if (data.startsWith("=")) {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						data = data.replaceAll("\"", "");
						data = data.replaceAll("=", "");
						cell.setCellValue(data);
					} else if (data.startsWith("\"")) {
						data = data.replaceAll("\"", "");
						cell.setCellType(Cell.CELL_TYPE_STRING);
						cell.setCellValue(data);
					} else {
						data = data.replaceAll("\"", "");
						cell.setCellType(Cell.CELL_TYPE_NUMERIC);
						cell.setCellValue(data);
					}
					// */
					// cell.setCellValue(ardata.get(p).toString());
				}
				System.out.println();
			}

			fie = xlsxfile;
			FileOutputStream fileOut = new FileOutputStream(fie);
			hwb.write(fileOut);
			System.out.println("Output File Name :" + hwb);
			fileOut.close();
			System.out.println("Your excel file has been generated");
		} catch (Exception ex) {
			ex.printStackTrace();
		} // main method ends
		return fie;

	}

	public static String convertExcelToCSV(Sheet sheet, String sheetName, String csvfile) {
		StringBuffer data = new StringBuffer();
		try {

			fie = csvfile;
			FileWriter pw = new FileWriter(fie, true);
			Cell cell;
			Row row;

			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					cell = cellIterator.next();

					int type = cell.getCellType();

					if (type == Cell.CELL_TYPE_BOOLEAN) {
						data.append(cell.getBooleanCellValue() + ",");

					} else if (type == Cell.CELL_TYPE_NUMERIC) {

						data.append((int) cell.getNumericCellValue() + ",");
					} else if (type == Cell.CELL_TYPE_STRING) {
						data.append(cell.getStringCellValue() + ",");
					} else if (type == Cell.CELL_TYPE_BLANK) {
						data.append("" + ",");
					} else {
						data.append(cell + ",");
					}
				}
				data.append('\n');
				System.out.println(data);
			}
			pw.append(data);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fie;
	}

	public static String ExcelToCSVConverter(String filename, String csvfile) {
		String fileN = null;
		String lastString = null;
		InputStream inp = null;
		String firstcsvfile = System.getProperty("user.dir") + "\\FileConversion\\convertedFile.csv";
		String ddcsvfile = System.getProperty("user.dir") + "\\FileConversion\\deleteduplicate.csv";
		try {
			inp = new FileInputStream(filename);
			Workbook wb = WorkbookFactory.create(inp);

			for (int i = 0; i < wb.getNumberOfSheets(); i++) {
				System.out.println(wb.getSheetAt(i).getSheetName());
				fileN = removeLastDelimiter(deletingDuplcateLines(
						convertExcelToCSV(wb.getSheetAt(i), wb.getSheetAt(i).getSheetName(), firstcsvfile), ddcsvfile),
						csvfile);
				lastString = fileN.substring((fileN.lastIndexOf('\\')) + 1);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				inp.close();
				deleteFiles(firstcsvfile);
				deleteFiles(ddcsvfile);
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		return lastString;

	}

	public static String removeLastDelimiter(String filename, String finalcsv) {
		try {
			String thisLine;
			@SuppressWarnings("resource")
			BufferedReader brTest = new BufferedReader(new FileReader(filename));
			fie = finalcsv;
			FileOutputStream fos = new FileOutputStream(fie);
			while ((thisLine = brTest.readLine()) != null) {
				thisLine = thisLine.replaceAll(",$", "\n");
				fos.write(thisLine.getBytes());
			}
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} // main method ends
		return fie;

	}

	public static String deletingDuplcateLines(String filename, String ddfile) throws IOException {
		String input = null;
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(new File(filename));
		fie = ddfile;
		@SuppressWarnings("resource")
		FileWriter writer = new FileWriter(fie);
		// Instantiating the Set class
		Set<String> set = new HashSet<String>();
		while (sc.hasNextLine()) {
			input = sc.nextLine();
			if (set.add(input)) {
				writer.append(input + "\n");
			}
		}
		writer.flush();
		System.out.println("Contents added............");
		return fie;
	}

	public static void deleteFiles(String filename) {
		try {
			String deleFile = filename;
			Files.deleteIfExists(Paths.get(deleFile));
		} catch (NoSuchFileException e) {
			System.out.println("No such file/directory exists");
		} catch (DirectoryNotEmptyException e) {
			System.out.println("Directory is not empty.");
		} catch (IOException e) {
			System.out.println("Invalid permissions.");
		}

		System.out.println("Deletion successful.");
	}

}
