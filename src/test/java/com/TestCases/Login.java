package com.TestCases;

import org.testng.annotations.Test;

import com.GenericFunctions.GenericFunctions;

public class Login extends GenericFunctions{
	
	@Test(groups={"sanity","bat","p2"})
	public static void TC01(){
		System.out.println("TC01 executed");
		System.out.println(getCrrntTestData("Email", 1));
		System.out.println(getCrrntTestData("Email", 2));
	}
	
	@Test(groups={"bat","smoke","all"})
	public static void TC02(){
		System.out.println("TC02 executed");
		System.out.println(getCrrntTestData("Email", 1));
		System.out.println(getCrrntTestData("Email", 2));
	}

	@Test(groups={"sanity","p2"})
	public static void TC09(){
		System.out.println("TC09 executed");
		System.out.println(getCrrntTestData("FirstName", 1));
		System.out.println(getCrrntTestData("FirstName", 2));
	}
	
	@Test(groups={"bat","all"})
	public static void TC10(){
		System.out.println("TC10 executed");
		System.out.println(getCrrntTestData("FirstName", 1));
		System.out.println(getCrrntTestData("FirstName", 2));
	}
	
}
