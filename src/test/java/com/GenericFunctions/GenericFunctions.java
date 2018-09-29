package com.GenericFunctions;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.TestListeners.TestListeners;

public class GenericFunctions extends TestListeners{
	public static WebDriver driver;
	
	
//launch browser
	public static void launchBrowser(){
		switch(getCommonTestData("Browser").toLowerCase()){
		case "chrome":
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\WebDrivers\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			break;
		case "firefox":
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\WebDrivers\\geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			break;
		}
	}
	
//launch application
	public static boolean launchApplication(){
		boolean status = false;
		launchBrowser();
		try {
			URL myUrl = new URL(getCommonTestData("Url"));
			HttpURLConnection connection = (HttpURLConnection)myUrl.openConnection();
			int responceCode = connection.getResponseCode();
			if(responceCode>=200&&responceCode<300){
				driver.get(getCommonTestData("Url"));
				System.out.println("Application "+driver.getTitle()+" is up and running");
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				status = true;
			}
		} catch (Exception e) {
			System.out.println("Application not launched");
			e.printStackTrace();
		} 
		return status;
	}
	
 
}
