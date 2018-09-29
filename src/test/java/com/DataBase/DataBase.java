package com.DataBase;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.GenericFunctions.GenericFunctions;
import com.Reports.Reports;
import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class DataBase extends Reports{
	public static Fillo fillo;
	public static Connection connection;
	public static String crrntClass;
	public static String crrntTest;
	
//database connection
	public static void dataBaseConnection(String excelPath){
		fillo = new Fillo();
		try {
			connection = fillo.getConnection(excelPath);
			System.out.println("database connection established successfully");
		} catch (FilloException e) {
			System.out.println("excel path is incorrect");
			e.printStackTrace();
		}
	}
	
	
//get common test data
	public static String getCommonTestData(String column){
		String query = "select "+column+" from CommonTestData where Test='TestPreferences'";
		String fetched = "";
		try {
			Recordset recordset = connection.executeQuery(query);
			while(recordset.next()){
				fetched = recordset.getField(column);
				break;
			}
			recordset.close();
		} catch (FilloException e) {
			System.out.println("data not fetched from commontest data sheet of column "+column);
			e.printStackTrace();
		}
		return fetched;
	}
	
	
//get crrnt test data
	public static String getCrrntTestData(String column, int itr){
		String query = "select "+column+" from "+crrntClass+" where TestCase='"+crrntTest+"' and Iteration="+itr;
		String fetched = "";
		try {
			Recordset recordset = connection.executeQuery(query);
			while(recordset.next()){
				fetched = recordset.getField(column);
				break;
			}
			recordset.close();
		} catch (FilloException e) {
			System.out.println("data not fetched from "+crrntClass+" sheet for testcase "+crrntTest+" under column "+column+" and iteration="+itr);
			e.printStackTrace();
		}
		return fetched;
	}
	
//close database connection
	public static void closeDataBaseConnection(){
		connection.close();
	}
	
//log failed test
	public static void logFailedTest(){
		String query = "INSERT into Results(TestCase,Module,Status) VALUES('"+crrntTest+"','"+crrntClass+"','failed')";
		try {
			connection.executeUpdate(query);
		} catch (FilloException e) {
			System.out.println("failed test "+crrntTest+" not logged into results sheet");
			e.printStackTrace();
		}
	}
	
//log skipped test	
	public static void logSkippedTest(){
		String query = "INSERT into Results(TestCase,Module,Status) VALUES('"+crrntTest+"','"+crrntClass+"','skipped')";
		try {
			connection.executeUpdate(query);
		} catch (FilloException e) {
			System.out.println("skipped test "+crrntTest+" not logged into results sheet");
			e.printStackTrace();
		}
	}
	
//update commontest data
	public static void updateCommonTestData(String column, String data){
		String query = "update CommonTestData set "+column+"='"+data+"' where Test='TestPreferences'";
		try {
			connection.executeUpdate(query);
		} catch (FilloException e) {
			System.out.println("common test data not updated with column "+column+"="+data);
			e.printStackTrace();
		}
	}
	
//get test credentials
	public static String getTestCredentials(String Environment, String column){
		String query = "select "+column+" from TestCredentials where Environment='"+Environment+"'";
		String fetched = "";
		try {
			Recordset recordset = connection.executeQuery(query);
			while(recordset.next()){
				fetched = recordset.getField(column);
				break;
			}
			recordset.close();
		} catch (FilloException e) {
			System.out.println("test credentials not fetched for "+Environment);
			e.printStackTrace();
		}
		return fetched;		
	}
	
	
//set test preferences
	public static void setTestPreferences(){
		System.out.println("enter test preferences(Environment,Browser,TestType,TestPriyority,Module):");
		Scanner sc = new Scanner(System.in);
		String preferences = sc.nextLine().trim().toUpperCase();
		String[] preferencesArr = preferences.split(",");
		
		updateCommonTestData("Environment", preferencesArr[0].trim());
		updateCommonTestData("Browser", preferencesArr[1].trim());
		updateCommonTestData("TestType", preferencesArr[2].trim());
		updateCommonTestData("TestPriyority", preferencesArr[3].trim());
		updateCommonTestData("Module", preferencesArr[4].trim());
		
		updateCommonTestData("Url", getTestCredentials(preferencesArr[0].trim(), "Url"));
		updateCommonTestData("UserName", getTestCredentials(preferencesArr[0].trim(), "UserName"));
//		GenericFunctions.launchBrowser();
//		GenericFunctions.driver.get(System.getProperty("user.dir")+"\\props\\index.html");
//		WebDriverWait wait = new WebDriverWait(GenericFunctions.driver, 60);
//		wait.until(GenericFunctions.driver.findElement(By.xpath("")).)
		
	}
	
//clear results
	public static void clearResults(){
		String query = "delete from Results";
		try {
			connection.executeUpdate(query);
		} catch (FilloException e) {
			System.out.println("results sheet is not cleared");
			e.printStackTrace();
		}
	}

	
//get regression preferences
	public static String getRegressionPreferences(String column){
		String query = "select "+column+" from CommonTestData where Test='RegressionPreferences'";
		String fetched = "";
		try {
			Recordset recordset = connection.executeQuery(query);
			while(recordset.next()){
				fetched = recordset.getField(column);
				break;
			}
			recordset.close();
		} catch (FilloException e) {
			System.out.println("regression data not fetched");
			e.printStackTrace();
		}
		return fetched;
	}
	
	
//set regression test preferences
	public static void setRegressionTestPreferences(){
		updateCommonTestData("Environment", getRegressionPreferences("Environment"));
		updateCommonTestData("Browser", getRegressionPreferences("Browser"));
		updateCommonTestData("TestType", getRegressionPreferences("TestType"));
		updateCommonTestData("TestPriyority", getRegressionPreferences("TestPriyority"));
		updateCommonTestData("Module", getRegressionPreferences("Module"));
		
		updateCommonTestData("Url", getTestCredentials(getRegressionPreferences("Environment"), "Url"));
		updateCommonTestData("UserName", getTestCredentials(getRegressionPreferences("Environment"), "UserName"));
	}

}
