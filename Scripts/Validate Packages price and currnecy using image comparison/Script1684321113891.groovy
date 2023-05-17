import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
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
import com.kms.katalon.core.testobject.ConditionType as ConditionType
import java.awt.image.BufferedImage as BufferedImage
import javax.imageio.ImageIO as ImageIO



String KatalonProjecthome = System.getProperty('user.dir')
System.out.println('The katalon home is the following ' + KatalonProjecthome)
//create a folder upon runtime if it does not exist where the screenshots checkpoints will reside to be compared with the baseline
File Folder = new File(KatalonProjecthome+"/Checkpoints");
if (!Folder.exists()){
	Folder.mkdirs();
}

System.out.println("Folder is created successfully");

TestObject Countryname = new TestObject().addProperty('xpath', ConditionType.EQUALS, ('//a[@id=\'country-btn\']/div/span'))

for (int i=0;i<GlobalVariable.Listofcountries.size();i++) {
	switch(GlobalVariable.Listofcountries.get(i)) {
		
		case "الكويت":
		/*Compare if the selected country is KW if not we will select KW*/
		String ActualCountryname= WebUI.getText(Countryname)

		if (WebUI.verifyEqual(ActualCountryname,GlobalVariable.Listofcountries.get(i),FailureHandling.OPTIONAL)==true) {
			System.out.println("We are in the correct country "+ GlobalVariable.Listofcountries.get(i))
		}else {
			System.out.println("We are in not the correct country, we are in "+ ActualCountryname+" while we should be in " +GlobalVariable.Listofcountries.get(i))
			WebUI.click(Countryname)
			TestObject CountrySelect = new TestObject().addProperty('xpath', ConditionType.EQUALS, ('//div[@class=\'country-select\']/a[@id=\'kw\']'))
			WebUI.click(CountrySelect)
									WebUI.waitForElementPresent(Countryname, 10)

		}
		
		//take screenshot of the home screen for this coountry 
		WebUI.takeScreenshot(KatalonProjecthome+'\\Checkpoints\\screenshot'+GlobalVariable.Listofcountries.get(i)+'.png')
		def baselineimg = KatalonProjecthome+'/Baseline/KWhomescreen.png'
		def checkpointimg = KatalonProjecthome+'/Checkpoints/screenshot'+GlobalVariable.Listofcountries.get(i)+'.png'
		String Result= CustomKeywords.'countryflagscomparison.imagecomparison.Compareimg'(baselineimg, checkpointimg)
		WebUI.verifyEqual(Result, "Images are identical")
		
		break;
		case "السعودية":
		
		String ActualCountryname1= WebUI.getText(Countryname)
		
							if (WebUI.verifyEqual(ActualCountryname1,GlobalVariable.Listofcountries.get(i),FailureHandling.OPTIONAL)==true) {
								System.out.println("We are in the correct country "+ GlobalVariable.Listofcountries.get(i))
							}else {
								System.out.println("We are in not the correct country, we are in "+ ActualCountryname1+"while we should be in " +GlobalVariable.Listofcountries.get(i))
								WebUI.click(Countryname)
								TestObject CountrySelect = new TestObject().addProperty('xpath', ConditionType.EQUALS, ('//div[@class=\'country-select\']/a[@id=\'sa\']'))
								WebUI.click(CountrySelect)
														WebUI.waitForElementPresent(Countryname, 10)

							}
							//take screenshot of the home screen for this coountry
							WebUI.takeScreenshot(KatalonProjecthome+'\\Checkpoints\\screenshot'+GlobalVariable.Listofcountries.get(i)+'.png')
							def baselineimg = KatalonProjecthome+'/Baseline/KSAhomescreen.png'
							def checkpointimg = KatalonProjecthome+'/Checkpoints/screenshot'+GlobalVariable.Listofcountries.get(i)+'.png'
							String Result= CustomKeywords.'countryflagscomparison.imagecomparison.Compareimg'(baselineimg, checkpointimg)
							WebUI.verifyEqual(Result, "Images are identical")
							
		break;
		case "البحرين":
		
			String ActualCountryname2= WebUI.getText(Countryname)
		
							if (WebUI.verifyEqual(ActualCountryname2,GlobalVariable.Listofcountries.get(i),FailureHandling.OPTIONAL)==true)
							{
								System.out.println("We are in the correct country "+ GlobalVariable.Listofcountries.get(i))
		
							}else {
								System.out.println("We are in not the correct country, we are in "+ ActualCountryname2+"while we should be in " +GlobalVariable.Listofcountries.get(i))
								WebUI.click(Countryname)
								TestObject CountrySelect = new TestObject().addProperty('xpath', ConditionType.EQUALS, ('//div[@class=\'country-select\']/a[@id=\'bh\']'))
								WebUI.click(CountrySelect)
								WebUI.waitForElementPresent(Countryname, 10)
								
							}
							//take screenshot of the home screen for this coountry
							WebUI.takeScreenshot(KatalonProjecthome+'\\Checkpoints\\screenshot'+GlobalVariable.Listofcountries.get(i)+'.png')
							def baselineimg = KatalonProjecthome+'/Baseline/BHhomescreen.png'
							def checkpointimg = KatalonProjecthome+'/Checkpoints/screenshot'+GlobalVariable.Listofcountries.get(i)+'.png'
							String Result= CustomKeywords.'countryflagscomparison.imagecomparison.Compareimg'(baselineimg, checkpointimg)
							WebUI.verifyEqual(Result, "Images are identical")
		break;
	}




}