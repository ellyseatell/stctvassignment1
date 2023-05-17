package benchmark

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.keyword.excel.ExcelKeywords
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellReference
import org.apache.poi.ss.util.WorkbookUtil
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.xmlbeans.impl.values.XmlValueDisconnectedException
import com.kms.katalon.core.annotation.Keyword

public class benchmarkhelper {

	@Keyword
	public void CreateBenchmarkforcountries (List <String> countries) {

		//excel keywords are here:https://plugin-docs.katalon.com/docs/excel-custom-keywords/com/kms/katalon/keyword/excel/ExcelKeywords.html
		/*Get the Katalon home directory to create a folder, and a file to contain the data that we will be testing against. This is more dynamic
		 * */
		String KatalonProjecthome = System.getProperty('user.dir')
		System.out.println("The katalon home is the following "+KatalonProjecthome)

		/*Create a folder upon runtime, if it does not exist, this is where our data will reside. in an excel file
		 * */
		File Folder = new File(KatalonProjecthome+"/BenchmarkData");
		if (!Folder.exists()){
			Folder.mkdirs();
		}

		System.out.println("Folder is created successfully");

		GlobalVariable.dataFilePath = new File(KatalonProjecthome + '/BenchmarkData/Data.xlsx/')
		/*Using the excel keywords, to create an excel file, workbook and sheets for each country defined on the profile level, the countries are stored in a list*/
		(new com.kms.katalon.keyword.excel.ExcelKeywords()).createExcelFile(GlobalVariable.dataFilePath.toString())

		Workbook workbook = ExcelKeywords.getWorkbook(GlobalVariable.dataFilePath.toString())
		/*The list of countries that we will be validating is a global variable, its a list of strings, we will iterate it and for each one create a sheet containing the currency and prices of plans LITE,*/
		for (int i=0;i<=countries.size()-1;i++) {

			(new com.kms.katalon.keyword.excel.ExcelKeywords()).createExcelSheet(workbook, countries.get(i))
			ExcelKeywords.saveWorkbook(GlobalVariable.dataFilePath.toString(), workbook)
			Sheet Sheet = workbook.getSheet(countries.get(i))
			/*Filling the data inside each sheet*/
			switch (countries.get(i)) {
				case "السعودية":
				//Filling data in the sheet for SA,Price of LITE in A1, price of classic in B1, price of premium in C1 and currency in A2
					(new com.kms.katalon.keyword.excel.ExcelKeywords()).setValueToCellByAddress(Sheet,"A1",15)
					(new com.kms.katalon.keyword.excel.ExcelKeywords()).setValueToCellByAddress(Sheet,"B1",25)
					(new com.kms.katalon.keyword.excel.ExcelKeywords()).setValueToCellByAddress(Sheet,"C1",60)
					(new com.kms.katalon.keyword.excel.ExcelKeywords()).setValueToCellByAddress(Sheet,"A2",'ريال سعودي')
				//save the workbook
					ExcelKeywords.saveWorkbook(GlobalVariable.dataFilePath.toString(), workbook)

					break;

				case "البحرين":
				//Filling data in the sheet for BH,Price of LITE in A1, price of classic in B1, price of premium in C1 and currency in A2
					(new com.kms.katalon.keyword.excel.ExcelKeywords()).setValueToCellByAddress(Sheet,"A1",2)
					(new com.kms.katalon.keyword.excel.ExcelKeywords()).setValueToCellByAddress(Sheet,"B1",3)
					(new com.kms.katalon.keyword.excel.ExcelKeywords()).setValueToCellByAddress(Sheet,"C1",6)
					(new com.kms.katalon.keyword.excel.ExcelKeywords()).setValueToCellByAddress(Sheet,"A2",'دينار بحريني')
				//save the workbook
					ExcelKeywords.saveWorkbook(GlobalVariable.dataFilePath.toString(), workbook)

					break;

				case "الكويت":
				//Filling data in the sheet for KW,Price of LITE in A1, price of classic in B1, price of premium in C1 and currency in A2
					(new com.kms.katalon.keyword.excel.ExcelKeywords()).setValueToCellByAddress(Sheet,"A1",1.2)
					(new com.kms.katalon.keyword.excel.ExcelKeywords()).setValueToCellByAddress(Sheet,"B1",2.5)
					(new com.kms.katalon.keyword.excel.ExcelKeywords()).setValueToCellByAddress(Sheet,"C1",4.8)
					(new com.kms.katalon.keyword.excel.ExcelKeywords()).setValueToCellByAddress(Sheet,"A2",'دينار كويتي')
				//save the workbook
					ExcelKeywords.saveWorkbook(GlobalVariable.dataFilePath.toString(), workbook)

					break;

			}
			ExcelKeywords.saveWorkbook(GlobalVariable.dataFilePath.toString(), workbook)
			System.out.println('Benchmark data is saved for the country/sheet ' +countries.get(i))
		}

	}}
