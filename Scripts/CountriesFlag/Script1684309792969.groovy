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

//WebUI.openBrowser("https://subscribe.stctv.com/sa-ar")
//WebUI.maximizeWindow()
//	TestObject CountryFlag = new TestObject().addProperty('xpath', ConditionType.EQUALS, ('//div[@id=\'flag\']/img'))
//String imgSrc=WebUI.getAttribute(CountryFlag, "src")
//System.out.println("The image source url is the following " +imgSrc)
WebUI.openBrowser(GlobalVariable.WebUrl1)
WebUI.takeScreenshot(KatalonProjecthome+'\\BenchmarkData\\screenshot.png')


def baselineimg = KatalonProjecthome+'/Baseline/KSAhomescreen.png'
def checkpointimg = KatalonProjecthome+'/BenchmarkData/screenshot.png'
String Result= CustomKeywords.'countryflagscomparison.imagecomparison.Compareimg'(baselineimg, checkpointimg)
WebUI.verifyEqual(Result, "Images are identical")

/*C:\Users\User\Desktop\finalrun\stctvassignment1

// How to convert SVG file into PNG format in Java using default options
Converter converter = new Converter('https://cdn.jawwy.tv/3/KSA.svg')

ImageConvertOptions options = new ImageConvertOptions()

options.setFlipMode(ImageFlipModes.FlipY) // Flip Vertically or Horizontally

options.setBrightness(50) // Set Brightness

options.setContrast(50) // Set Contrast

options.setGamma(0.5) // Set Gamma

options.setGrayscale(true) // Set Grayscale

options.setRotateAngle(45) // Set Rotation

options.setFormat(ImageFileType.Png)


// Add watermark to JPG when converted from SVG format
WatermarkTextOptions watermark = new WatermarkTextOptions("Watermark");
watermark.setColor(Color.BLUE);
watermark.setBackground(false);
watermark.setRotationAngle(-45);
watermark.setTop(50);
watermark.setLeft(50);
watermark.setTransparency(0.2);
watermark.setWidth(450);
watermark.setHeight(450);
options.setWatermark(watermark);
*/
//converter.convert('path/svg-to-png.png', options)


