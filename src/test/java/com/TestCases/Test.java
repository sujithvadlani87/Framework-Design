package com.TestCases;

import com.GenericFunctions.GenericFunctions;

public class Test extends GenericFunctions{
	
	public static void main(String[]args){
		dataBaseConnection(System.getProperty("user.dir")+"\\DataBase\\TestData.xlsx");
		launchApplication();
	}

}
