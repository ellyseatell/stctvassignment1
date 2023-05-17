package customhelper.com

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import com.kms.katalon.core.testobject.ConditionType as ConditionType

import org.openqa.selenium.WebDriver as WebDriver
import org.openqa.selenium.WebElement as WebElement
import org.openqa.selenium.interactions.Actions as Actions
import com.kms.katalon.core.webui.common.WebUiCommonHelper as WebUiCommonHelper
import com.kms.katalon.core.webui.driver.DriverFactory as DriverFactory
import org.openqa.selenium.JavascriptExecutor as JavascriptExecutor
import org.openqa.selenium.chrome.ChromeDriver as ChromeDriver
import org.openqa.selenium.By as By
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import com.kms.katalon.keyword.excel.ExcelKeywords
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellReference
import org.apache.poi.ss.util.WorkbookUtil
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.xmlbeans.impl.values.XmlValueDisconnectedException
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class CustomHelper {
	@Keyword
	public void browseweburl (String weburl, String language) {

		WebUI.openBrowser(weburl)
		WebUI.maximizeWindow()

		switch (language) {
			case "English":
				System.out.println("Language is " +language)
				TestObject Translationbtn = new TestObject().addProperty('xpath', ConditionType.EQUALS, ('//a[@id=\'translation-btn\' and contains(text(),\'' + language +'\')]'))
				WebUI.waitForElementClickable(Translationbtn, 20)
				WebUI.click(Translationbtn)
				break;

			case "Arabic":
				System.out.println("Language is " +language)
			//no action because the default language is arabic
				break;

			default:
				System.out.println("Language is " +language)
			//no action because the default language is arabic

		}
	}


	@Keyword
	public void ValidatePlansForCountry(List<String> countries) {

		TestObject Countryname = new TestObject().addProperty('xpath', ConditionType.EQUALS, ('//a[@id=\'country-btn\']/div/span'))
		/*When the web page loads, get the currently selected country*/
		/*Iterate the list of countries (of countries to be validated)*/
		for (int i=0;i<countries.size();i++) {

			System.out.println("The country that is being validated is the following " +countries.get(i))

			switch (countries.get(i)) {
				/*Handling the country KW*/
				case "الكويت":
				/*Compare if the selected country is KW if not we will select KW*/
					String ActualCountryname= WebUI.getText(Countryname)

					if (WebUI.verifyEqual(ActualCountryname,countries.get(i),FailureHandling.OPTIONAL)==true) {
						System.out.println("We are in the correct country "+ countries.get(i))
					}else {
						System.out.println("We are in not the correct country, we are in "+ ActualCountryname+" while we should be in " +countries.get(i))
						WebUI.click(Countryname)
						TestObject CountrySelect = new TestObject().addProperty('xpath', ConditionType.EQUALS, ('//div[@class=\'country-select\']/a[@id=\'kw\']'))
						WebUI.click(CountrySelect)
						WebUI.delay(30)
					}

					DriverFactory factory1 = new DriverFactory()

					WebDriver driver1= factory1.getWebDriver()
				/*Create an object for the plan names upon runtime*/
					WebElement Plans1 = driver1.findElement(By.xpath('(//div[@class=\'plan-names\'])[1]'))
				/*all the plans will be stored in the below list*/
					List<String> allplans1 = Plans1.findElements(By.xpath('(//div[@class=\'plan-names\'])[1]/div'))

					System.out.print('the number of plans is ' + allplans1.size() + 'plans')

					Iterator<WebElement> iter1 = allplans1.iterator()
					List<String> ActualPlans = new LinkedList<String>(Arrays.asList())
				/*Iterate the list of plans to validate the currency and price later*/

					while (iter1.hasNext()) {
						WebElement we1 = iter1.next()

						WebElement Plan1 = we1.findElement(By.tagName('strong'))

						System.out.println(Plan1.getText())

						ActualPlans.add(Plan1.getText())
					}
					System.out.println('The actual plans are' + ActualPlans)
					WebUI.verifyEqual(ActualPlans, GlobalVariable.ExpectedPlansAr,FailureHandling.STOP_ON_FAILURE)
				/*
				 if (ActualPlans == GlobalVariable.ExpectedPlansAr) {
				 KeywordUtil.markPassed('test passed')
				 } else {
				 KeywordUtil.markPassed('test failed')
				 }*/

					for (int j=0;j<ActualPlans.size();j++) {
						/*Get the currency and price of each plan*/
						TestObject PlanCurrency = new TestObject().addProperty('xpath', ConditionType.EQUALS, ('//div[@id=\'currency-'+ ActualPlans.get(j) +'\']/i'))
						String[] currencypermoth = WebUI.getText(PlanCurrency).split("/");
						String currency=currencypermoth[0]

						System.out.println("the actual currency is the following "+currencypermoth[0])
						System.out.println("The currency of the plan " +ActualPlans.get(j)+ " is the following " +currencypermoth[0])

						Workbook workbook = (new com.kms.katalon.keyword.excel.ExcelKeywords()).getWorkbook(GlobalVariable.dataFilePath.toString())
						String c2=(new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"A2"))
						/*Assert the currency with the excel file containingthe benchmark, we are accessing the sheet of the corresponding country*/
						WebUI.verifyEqual(currency, c2,FailureHandling.STOP_ON_FAILURE)

						TestObject PlanPrice = new TestObject().addProperty('xpath', ConditionType.EQUALS, ('//div[@id=\'currency-' + ActualPlans.get(j) +'\']/b'))

						switch(ActualPlans.get(j)) {

							case "لايت":
								System.out.println("Validating the prices of the plan " +ActualPlans.get(j)+ " for the country " +countries.get(i))
								WebUI.verifyEqual(WebUI.getText(PlanPrice), (new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"A1")),FailureHandling.STOP_ON_FAILURE)
								break;
							case "الأساسية":
								System.out.println("Validating the prices of the plan " +ActualPlans.get(j)+ " for the country " +countries.get(i))
								WebUI.verifyEqual(WebUI.getText(PlanPrice), (new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"B1")),FailureHandling.STOP_ON_FAILURE)

								break;
							case "بريميوم":
								System.out.println("Validating the prices of the plan " +ActualPlans.get(j)+ " for the country " +countries.get(i))
								System.out.println("The actual  price  is "+WebUI.getText(PlanPrice)+ "the expected price is " +(new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"C1")))
								WebUI.verifyEqual(WebUI.getText(PlanPrice), (new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"C1")),FailureHandling.STOP_ON_FAILURE)

								break;
						}
					}

					break;


				case "السعودية":
					String ActualCountryname1= WebUI.getText(Countryname)

					if (WebUI.verifyEqual(ActualCountryname1,countries.get(i),FailureHandling.OPTIONAL)==true) {
						System.out.println("We are in the correct country "+ countries.get(i))
					}else {
						System.out.println("We are in not the correct country, we are in "+ ActualCountryname1+"while we should be in " +countries.get(i))
						WebUI.click(Countryname)
						TestObject CountrySelect = new TestObject().addProperty('xpath', ConditionType.EQUALS, ('//div[@class=\'country-select\']/a[@id=\'sa\']'))
						WebUI.click(CountrySelect)
						WebUI.delay(30)
					}

					DriverFactory factory2 = new DriverFactory()

					WebDriver driver2 = factory2.getWebDriver()

					WebElement Plans2 = driver2.findElement(By.xpath('(//div[@class=\'plan-names\'])[1]'))

					List<String> allplans2 = Plans2.findElements(By.xpath('(//div[@class=\'plan-names\'])[1]/div'))

					System.out.println(allplans2.size())

					System.out.print(('the number of plans is ' + allplans2.size()) + ' plans')

					Iterator<WebElement> iter2 = allplans2.iterator()
					List<String> ActualPlansforKSA = new LinkedList<String>(Arrays.asList())

					while (iter2.hasNext()) {
						WebElement we2 = iter2.next()

						WebElement Plan2 = we2.findElement(By.tagName('strong'))

						System.out.println(Plan2.getText())

						ActualPlansforKSA.add(Plan2.getText())
					}
					System.out.println()
					System.out.println('The actual plans are' + ActualPlansforKSA)

					WebUI.verifyEqual(ActualPlansforKSA, GlobalVariable.ExpectedPlansAr,FailureHandling.STOP_ON_FAILURE)


				// for each plan, need to verify the currency
					for (int q=0;q<ActualPlansforKSA.size();q++) {

						/*Get the currency and price of each plan*/
						TestObject PlanCurrency = new TestObject().addProperty('xpath', ConditionType.EQUALS, ('//div[@id=\'currency-'+ ActualPlansforKSA.get(q) +'\']/i'))
						String[] currencypermoth = WebUI.getText(PlanCurrency).split("/");
						String currency=currencypermoth[0]

						System.out.println("the actual currency is the following "+currencypermoth[0])
						System.out.println("The currency of the plan " +ActualPlansforKSA.get(q)+ " is the following " +currencypermoth[0])

						Workbook workbook = (new com.kms.katalon.keyword.excel.ExcelKeywords()).getWorkbook(GlobalVariable.dataFilePath.toString())
						String c1=(new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"A2"))
						/*Assert the currency with the excel file containingthe benchmark, we are accessing the sheet of the corresponding country*/
						WebUI.verifyEqual(currency, c1,FailureHandling.STOP_ON_FAILURE)

						TestObject PlanPrice = new TestObject().addProperty('xpath', ConditionType.EQUALS, ('//div[@id=\'currency-' + ActualPlansforKSA.get(q) +'\']/b'))

						switch(ActualPlansforKSA.get(q)) {
							case "لايت":
								System.out.println("Validating the prices of the plan " +ActualPlansforKSA.get(q)+ " for the country " +countries.get(i))
								System.out.println("The actual  price  is "+WebUI.getText(PlanPrice)+ "the expected price is " +(new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"A1")))

								WebUI.verifyEqual(WebUI.getText(PlanPrice), (new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"A1")),FailureHandling.STOP_ON_FAILURE)
								break;

							case "الأساسية":
								System.out.println("Validating the prices of the plan " +ActualPlansforKSA.get(q)+ " for the country " +countries.get(i))
								System.out.println("The actual  price  is "+WebUI.getText(PlanPrice)+ "the expected price is " +(new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"B1")))
								WebUI.verifyEqual(WebUI.getText(PlanPrice), (new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"B1")),FailureHandling.STOP_ON_FAILURE)

								break;
							case "بريميوم":
								System.out.println("Validating the prices of the plan " +ActualPlansforKSA.get(q)+ " for the country " +countries.get(i))
								System.out.println("The actual  price  is "+WebUI.getText(PlanPrice)+ "the expected price is " +(new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"C1")))
								WebUI.verifyEqual(WebUI.getText(PlanPrice), (new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"C1")),FailureHandling.STOP_ON_FAILURE)

								break;
						}

					}

					break;

				case "البحرين":
					String ActualCountryname2= WebUI.getText(Countryname)

					if (WebUI.verifyEqual(ActualCountryname2,countries.get(i),FailureHandling.OPTIONAL)==true)
					{
						System.out.println("We are in the correct country "+ countries.get(i))

					}else {
						System.out.println("We are in not the correct country, we are in "+ ActualCountryname2+"while we should be in " +countries.get(i))
						WebUI.click(Countryname)
						TestObject CountrySelect = new TestObject().addProperty('xpath', ConditionType.EQUALS, ('//div[@class=\'country-select\']/a[@id=\'bh\']'))
						WebUI.click(CountrySelect)
					}

					DriverFactory factory = new DriverFactory()

					WebDriver driver = factory.getWebDriver()

					WebElement Plans = driver.findElement(By.xpath('(//div[@class=\'plan-names\'])[1]'))

					List<String> allplans = Plans.findElements(By.xpath('(//div[@class=\'plan-names\'])[1]/div'))

					System.out.println(allplans.size())

					System.out.print(('the number of plans is ' + allplans.size()) + ' plans')

					Iterator<WebElement> iter = allplans.iterator()
					List<String> ActualPlansforBH = new LinkedList<String>(Arrays.asList())

					while (iter.hasNext()) {
						WebElement we = iter.next()

						WebElement Plan = we.findElement(By.tagName('strong'))

						System.out.println(Plan.getText())

						ActualPlansforBH.add(Plan.getText())
					}
					System.out.println()
					System.out.println('The actual plans are' + ActualPlansforBH)
					WebUI.verifyEqual(ActualPlansforBH, GlobalVariable.ExpectedPlansAr,FailureHandling.STOP_ON_FAILURE)

				// for each plan, need to verify the currency
					for (int r=0;r<ActualPlansforBH.size();r++) {

						TestObject PlanCurrency = new TestObject().addProperty('xpath', ConditionType.EQUALS, ('//div[@id=\'currency-'+ ActualPlansforBH.get(r) +'\']/i'))
						TestObject PlanPrice = new TestObject().addProperty('xpath', ConditionType.EQUALS, ('//div[@id=\'currency-' + ActualPlansforBH.get(r) +'\']/b'))

						String[] currencypermoth = WebUI.getText(PlanCurrency).split("/");
						String currency=currencypermoth[0]
						System.out.println("the actual currency is" +currencypermoth[0])

						System.out.println("The currency of the plan" +ActualPlansforBH.get(r)+ "is the following" +currencypermoth[0])
						System.out.println("The price of the plan " +ActualPlansforBH.get(r)+ " is the following" +WebUI.getText(PlanPrice))

						Workbook workbook = (new com.kms.katalon.keyword.excel.ExcelKeywords()).getWorkbook(GlobalVariable.dataFilePath.toString())

						System.out.println("the expected currency is " +(new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"A2")))
						//String c= (new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"A2"))

						//WebUI.verifyEqual(currency, c)

						switch(ActualPlansforBH.get(r)) {
							case "لايت":
								System.out.println("Validating the prices of the plan " +ActualPlansforBH.get(r)+ " for the country " +countries.get(i))
								WebUI.verifyEqual(WebUI.getText(PlanPrice), (new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"A1")),FailureHandling.STOP_ON_FAILURE)
								System.out.println("The actual  price  is "+WebUI.getText(PlanPrice)+ "the expected price is " +(new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"A1")))

								break;
							case "الأساسية":
								System.out.println("Validating the prices of the plan " +ActualPlansforBH.get(r)+ " for the country " +countries.get(i))
								System.out.println("The actual  price  is "+WebUI.getText(PlanPrice)+ "the expected price is " +(new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"B1")))
								WebUI.verifyEqual(WebUI.getText(PlanPrice), (new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"B1")),FailureHandling.STOP_ON_FAILURE)

								break;
							case "بريميوم":
								System.out.println("Validating the prices of the plan " +ActualPlansforBH.get(r)+ " for the country " +countries.get(i))
								System.out.println("The actual  price  is "+WebUI.getText(PlanPrice)+ "the expected price is " +(new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"C1")))
								WebUI.verifyEqual(WebUI.getText(PlanPrice), (new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellValue((new com.kms.katalon.keyword.excel.ExcelKeywords()).getCellByAddress(workbook.getSheet(countries.get(i)),"C1")),FailureHandling.STOP_ON_FAILURE)

								break;
						}

					}
					break;

			}
		}
	}
}