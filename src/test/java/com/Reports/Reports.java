package com.Reports;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.DataBase.DataBase;
import com.GenericFunctions.GenericFunctions;
import com.TestListeners.TestListeners;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class Reports {
	public static ExtentReports extent;
	public static ExtentTest test;
	
//screen shot
	public static String screenShot(){
		TakesScreenshot scr = (TakesScreenshot)GenericFunctions.driver;
		String shot = scr.getScreenshotAs(OutputType.BASE64);
		return "data:image/png;base64,"+shot;
	}
	

//time stamp
	public static String timeStamp(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String stamp = sdf.format(date).replaceAll("/", "");
		return stamp;
	}
	
//result folder
	public static String resultFolder(){
		File f = new File(System.getProperty("user.dir")+"\\Results\\"+timeStamp()+"\\"+TestListeners.crrntClass);
		String path = System.getProperty("user.dir")+"\\Results\\"+timeStamp()+"\\"+TestListeners.crrntClass;
		if(!f.exists())
			f.mkdirs();
		return path;
	}
	
//initialize report
	public static void initializeReport(String htmlReport){
		extent = new ExtentReports(resultFolder()+"\\"+htmlReport+".html");
		extent.addSystemInfo("Environment", DataBase.getCommonTestData("Environment")).addSystemInfo("Browser", DataBase.getCommonTestData("Browser")).addSystemInfo("TestType", DataBase.getCommonTestData("TestType")).addSystemInfo("TestPriyority", DataBase.getCommonTestData("TestPriyority"));
		extent.loadConfig(new File("extent.xml"));
	}
	
//flush report
	public static void flushReport(){
		extent.flush();
	}
	
	
//start test 
	public static void startTest(String testToStart){
		test = extent.startTest(testToStart);
	}
	
//end test
	public static void endTest(){
		extent.endTest(test);
	}

}
