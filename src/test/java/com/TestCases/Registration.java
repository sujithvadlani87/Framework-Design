package com.TestCases;

import org.testng.annotations.Test;

import com.GenericFunctions.GenericFunctions;

public class Registration extends GenericFunctions{
	
	@Test(groups={"sanity","p1","p2"})
	public static void TC11(){
		System.out.println("TC11 executed");
		System.out.println(getCrrntTestData("LastName", 1));
		System.out.println(getCrrntTestData("LastName", 2));
	}
	
	@Test(groups={"sanity","smoke","p1","p2"})
	public static void TC12(){
		System.out.println("TC12 executed");
		System.out.println(getCrrntTestData("LastName", 1));
		System.out.println(getCrrntTestData("LastName", 2));
	}
	
	@Test(groups={"smoke","p1","p2"})
	public static void TC13(){
		System.out.println("TC13 executed");
		System.out.println(getCrrntTestData("LastName", 1));
	}

}
