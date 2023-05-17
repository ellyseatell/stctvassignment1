import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import java.nio.file.Files as Files
import java.nio.file.Path as Path
import java.nio.file.Paths as Paths
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
import com.kms.katalon.keyword.excel.ExcelKeywords as ExcelKeywords
import org.apache.poi.hssf.usermodel.HSSFWorkbook as HSSFWorkbook
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellReference as CellReference
import org.apache.poi.ss.util.WorkbookUtil as WorkbookUtil
import org.apache.poi.xssf.usermodel.XSSFWorkbook as XSSFWorkbook
import org.apache.xmlbeans.impl.values.XmlValueDisconnectedException as XmlValueDisconnectedException

CustomKeywords.'benchmark.benchmarkhelper.CreateBenchmarkforcountries'(GlobalVariable.Listofcountries)

