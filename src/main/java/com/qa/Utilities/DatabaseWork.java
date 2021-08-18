package com.qa.Utilities;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.Statement;

import org.openqa.selenium.WebDriver;

import com.qa.MainFunctions.DriverCalling;

public class DatabaseWork extends DriverCalling {

	private static Connection conn = null;
	private static boolean DBConnectionStatus = false;

	private static ResultSetMetaData rsmd = null;
	String file = null;
	String Data = null;
	String filename = null;
	public static FileOutputStream fileOut = null;
	public static String fileFullPath;
	public static String srcSheetName;
	public static String resultPath = "";
	public static String resultSheetName = "";
	public static String datevalfinal = "";

	static WebDriver driver = null;
	public String Status = null;
	@SuppressWarnings("unused")
	private static String TC_ID = null;

	public DatabaseWork(WebDriver driver, String TC_ID) {
		DatabaseWork.driver = driver;
		DatabaseWork.TC_ID = TC_ID;
	}

	public static boolean sqlDatabaseConnectionEstablish(String host, String port, String DatabaseName, String userName,
			String password) {

		// Set Class name for SQL
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e1) {
		}

		String url = "jdbc:sqlserver://" + host + ":" + port + ";DatabaseName=" + DatabaseName + ";user=" + userName
				+ ";password=" + password;

		for (int i = 0; i < 15; i++) {
			try {
				conn = DriverManager.getConnection(url);
				System.out.println("SQLDDatabase connected successfully.");
				DBConnectionStatus = true;
				break;
			} catch (Exception e) {

			}
		}
		if (!DBConnectionStatus) {
			System.out.println("Unable to connect to SQL Database!");
		}
		return DBConnectionStatus;
	}

	@SuppressWarnings("deprecation")
	public static boolean db2DatabaseConnectionEstablish(String host, String port, String DatabaseName, String userName,
			String password) {

		// Set Class name for SQL

		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String url = "jdbc:db2://" + host + ":" + port + "/" + DatabaseName;
		System.out.println("DB URL : " + url);

		for (int i = 0; i < 25; i++) {
			try {

				try {
					conn = DriverManager.getConnection(url, userName, password);
					System.out.println("Connected to DB2 Database successfully.");
					DBConnectionStatus = true;
					break;
				} catch (Exception e) {
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Unable to connect to DB2 DataBase...");
			}
		}

		if (!DBConnectionStatus) {
			System.out.println("Unable to connect to DB2 Database!");
		}
		return DBConnectionStatus;
	}

	public static boolean oracleDatabaseConnectionEstablish(String host, String port, String DatabaseName,
			String userName, String password) {

		// Set Class name for SQL
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e1) {
		}

		String url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + DatabaseName;

		for (int i = 0; i < 15; i++) {
			try {
				conn = DriverManager.getConnection(url, userName, password);
				System.out.println("Oracle Database connected successfully.");
				DBConnectionStatus = true;
				break;
			} catch (Exception e) {

			}
		}
		if (!DBConnectionStatus) {
			System.out.println("Unable to connect to Oracle Database!");
		}
		return DBConnectionStatus;
	}

	public static boolean postgresqlDatabaseConnectionEstablish(String address, String DatabaseName, String userName,
			String password) {

		// Set Class name for SQL
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
		}

		String url = "jdbc:postgresql://" + address + "/" + DatabaseName;

		for (int i = 0; i < 15; i++) {
			try {
				conn = DriverManager.getConnection(url, userName, password);
				System.out.println("Postgresql Database connected successfully.");
				DBConnectionStatus = true;
				break;
			} catch (Exception e) {

			}
		}
		if (!DBConnectionStatus) {
			System.out.println("Unable to connect to Postgresql Database!");
		}
		return DBConnectionStatus;
	}

	public static String sqlQueryExecution(String filename, String sqlQuery) {
		try {
			FileWriter fw = new FileWriter(filename);

			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(sqlQuery);
			rsmd = rs.getMetaData();

			int noOfColumns = rsmd.getColumnCount();

			System.out.println("No# of Columns in Table : " + noOfColumns);
			for (int i = 1; i <= noOfColumns; i++) {
				fw.append(rs.getMetaData().getColumnLabel(i));
				if (i < noOfColumns)
					fw.append(',');
				else
					fw.append('\n');
			}

			while (rs.next()) {

				for (int i = 1; i <= noOfColumns; i++) {
					fw.append(rs.getString(i));
					if (i < noOfColumns)
						fw.append(',');
				}
				fw.append('\n');
			}
			fw.flush();
			fw.close();
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
		}
		return filename;
	}

}
