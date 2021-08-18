package com.qa.Utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;

import com.qa.MainFunctions.DriverCalling;

public class CSVFileCompare extends DriverCalling {

	static WebDriver driver = null;
	String fie = null;

	public CSVFileCompare(WebDriver driver) {
		CSVFileCompare.driver = driver;
	}

	public ArrayList<String> readCSVtoArrayList(String filename) {
		ArrayList<String> valuesInSheetOne = new ArrayList<String>();
		String line;
		try (BufferedReader br = new BufferedReader(
				new FileReader(System.getProperty("user.dir") + "\\src\\" + filename))) {
			while ((line = br.readLine()) != null) {

				valuesInSheetOne.add(line);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return valuesInSheetOne;
	}

	public void sortingArrayList(ArrayList<String> valuesInSheet) {
		Collections.sort(valuesInSheet);
		/*
		 * for(String ele : valuesInSheet) { System.out.println(ele); }
		 */
	}

	public String fileCompare(ArrayList<String> valuesInSheetOne, ArrayList<String> valuesInSheetTwo) {
		ArrayList<String> newAddedElementsList = new ArrayList<String>();
		ArrayList<String> removedElementsList = new ArrayList<String>();
		for (String ele : valuesInSheetOne) {
			if (valuesInSheetTwo.contains(ele)) {
				continue;
			} else {
				removedElementsList.add(ele);
			}
		}
		for (String ele : valuesInSheetTwo) {
			if (valuesInSheetOne.contains(ele)) {
				continue;
			} else {
				newAddedElementsList.add(ele);
			}
		}

		// System.out.println(removedElementsList);
		// System.out.println(newAddedElementsList);

		try {
			String dateval = JavaUtilities.datetime("ddMMhhmmss");
			fie = System.getProperty("user.dir") + "\\src\\OutputFile_" + dateval + ".csv";
			System.out.println(fie);
			FileWriter writer = new FileWriter(fie);
			for (String s : newAddedElementsList) {
				String[] split2 = s.split(",");
				writer.write(Arrays.asList(split2).stream().collect(Collectors.joining(",")));
				writer.write("\n"); // newline
			}

			for (String s : removedElementsList) {
				String[] split2 = s.split(",");
				writer.write(Arrays.asList(split2).stream().collect(Collectors.joining(",")));
				writer.write("\n"); // newline
			}

			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fie;

	}

}
