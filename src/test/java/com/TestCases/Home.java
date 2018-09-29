package com.TestCases;

import org.testng.annotations.Test;

import com.GenericFunctions.GenericFunctions;

public class Home extends GenericFunctions{
	
	@Test(groups={"smoke","all"})
	public static void TC03(){
		System.out.println("TC03 executed");
		System.out.println(getCrrntTestData("Email", 1));
		System.out.println(getCrrntTestData("Email", 2));
	}
	
	@Test(groups={"sanity","p1","p3"})
	public static void TC04(){
		System.out.println("TC04 executed");
		System.out.println(getCrrntTestData("Email", 1));
		System.out.println(getCrrntTestData("Email", 2));
	}
	
	@Test(groups={"bat","p1","p3"})
	public static void TC07(){
		System.out.println("TC07 executed");
		System.out.println(getCrrntTestData("Password", 1));
		//System.out.println(getCrrntTestData("Password", 2));
	}
	
	@Test(groups={"smoke","p4","p3"})
	public static void TC08(){
		System.out.println("TC08 executed");
		System.out.println(getCrrntTestData("Password", 1));
		System.out.println(getCrrntTestData("Password", 2));
	}
	
	

}
