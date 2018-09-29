package com.TestListeners;

import org.testng.IClassListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener2;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import com.DataBase.DataBase;

public class TestListeners extends DataBase implements IClassListener,ITestListener,IInvokedMethodListener2{

	
	@Override
	public void onStart(ITestContext context) {
		//database connection on start
		dataBaseConnection(System.getProperty("user.dir")+"\\DataBase\\TestData.xlsx");
		//setting up test preferences like environment,browser,testType,testPriyority by user through console
		setTestPreferences();
		/*clearing the results sheet from excel WB which contains listed test cases which were skipped and failed
		 * during last run
		*/ 
		clearResults();
	}
	
	
	
	@Override
	public void onBeforeClass(ITestClass testClass) {
		crrntClass = testClass.getName().substring(testClass.getName().lastIndexOf(".")+1).trim();
		String runclass = crrntClass.toLowerCase();
		String module = getCommonTestData("Module").trim().toLowerCase();
		if(module.contains(runclass)){
			initializeReport(crrntClass);
			System.out.println(crrntClass+" class is executing");
		}
		else if(module.contains("all")){
			initializeReport(crrntClass);
			System.out.println(crrntClass+" class is executing");
		}
		else{
			//skip class
		}	
	}
	
	
	@Override
	public void onTestStart(ITestResult result) {
		
		
	}
	

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
		/*
		 * in this method we start a test in html report.
		 * which ever test method contains appropriate testType and testPriyority in groups that are mentioned in method level 
		 * we start such test's html test and let the invocation happen, otherwise we don't unnecessarily start  
		 *  such method's test report in html file and we skip those methods.
		 */
		crrntTest = method.getTestMethod().getMethodName().trim();
		String[] groupsArr = method.getTestMethod().getGroups();
		String groups = "";
		for (int i = 0; i < groupsArr.length; i++) {
			groups = groups + groupsArr[i].trim().toLowerCase();
		}
		String TestType = getCommonTestData("TestType").trim().toLowerCase();
		String TestPriyority = getCommonTestData("TestPriyority").trim().toLowerCase();

		if (groups.contains(TestType)) {

			if (groups.contains(TestPriyority)) {
				startTest(crrntTest);
			} else if (TestPriyority.contains("all") || groups.contains("all")) {
				startTest(crrntTest);
			} else if (TestPriyority.contains("&")) {
				String[] testPriyorityArr = TestPriyority.split("&");
				for (int n = 0; n < testPriyorityArr.length; n++) {
					if (groups.contains(testPriyorityArr[n].trim())) {
						startTest(crrntTest);
						break;
					} else if (n >= testPriyorityArr.length - 1) {
						testResult.setAttribute("skip", "intended");
						throw new SkipException("skipped test=" + crrntTest);
					}
				}

			} else {
				testResult.setAttribute("skip", "intended");
				throw new SkipException("skipped test=" + crrntTest);
			}
		}

		else if (TestType.contains("all")) {
			if (TestPriyority.contains("all") || groups.contains("all")) {
				startTest(crrntTest);
			} else if (groups.contains(TestPriyority)) {
				startTest(crrntTest);
			} else if (TestPriyority.contains("&")) {
				String[] testPriyorityArr = TestPriyority.split("&");

				for (int n = 0; n < testPriyorityArr.length; n++) {
					if (groups.contains(testPriyorityArr[n].trim())) {
						startTest(crrntTest);
						break;
					}

					else if (n >= testPriyorityArr.length - 1) {
						testResult.setAttribute("skip", "intended");
						throw new SkipException("skipped test=" + crrntTest);
					}
				}

			} else {
				testResult.setAttribute("skip", "intended");
				throw new SkipException("skipped test=" + crrntTest);
			}
		} else if (TestType.contains("&")) {
			String[] TestTypeArr = TestType.split("&");
			for (int n = 0; n < TestTypeArr.length; n++) {
				if (groups.contains(TestTypeArr[n])) {
					if (groups.contains(TestPriyority)) {
						startTest(crrntTest);
						break;
					} else if (TestPriyority.contains("all") || groups.contains("all")) {
						startTest(crrntTest);
						break;
					} else if (TestPriyority.contains("&")) {
						String[] testPriyorityArr = TestPriyority.split("&");
						for (int i = 0; i < testPriyorityArr.length; i++) {
							if (groups.contains(testPriyorityArr[n].trim())) {
								startTest(crrntTest);
								break;
							} else if (i >= testPriyorityArr.length - 1) {
								testResult.setAttribute("skip", "intended");
								throw new SkipException("skipped test=" + crrntTest);
							}
						}
						break;
					} else {
						testResult.setAttribute("skip", "intended");
						throw new SkipException("skipped test=" + crrntTest);
					}
				} else if (n >= TestTypeArr.length - 1) {
					testResult.setAttribute("skip", "intended");
					throw new SkipException("skipped test=" + crrntTest);
				}
			}

		}

		else {
			testResult.setAttribute("skip", "intended");
			throw new SkipException("skipped test=" + crrntTest);
		}

	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
		//here we end the html test
		endTest();
	}
	
	
	@Override
	public void onAfterClass(ITestClass testClass) {
		//here we flush the present class html report file
		String runclass = crrntClass.toLowerCase();
		String module = getCommonTestData("Module").trim().toLowerCase();
		if(module.contains(runclass)){
			flushReport();
		}
		else if(module.contains("all")){
			flushReport();
		}
		else{
			System.out.println(crrntClass+" skipped");
		}
			
	}

	
	@Override
	public void onFinish(ITestContext context) {
		/*in this method we set default regression test preferences so that present test preferences wont effect the over night
		 * regression test runs
		 */
		setRegressionTestPreferences();
       //here we close database connection
		closeDataBaseConnection();
	}
	

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
  //here we log the tests which are all failed during run in results sheet in excel so it would be easy to get count of them
		logFailedTest();
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
/*
 * here we log the tests in results sheet which are supposed to run for current test preferences but got
 * skipped. and we dont log the test cases which are intended to get skipped			
 */
		if(result.getAttribute("skip").equals("intended")){
			System.out.println(crrntTest+" testCase skipped intentionally");
		}
		else{
			logSkippedTest();
		}
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	

	

	

	
	
	
	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		// TODO Auto-generated method stub
		
	}







	
}
