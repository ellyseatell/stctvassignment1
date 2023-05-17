package countryflagscomparison

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords

import internal.GlobalVariable

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.webui.exception.WebElementNotFoundException
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import com.kms.katalon.core.configuration.RunConfiguration


public class imagecomparison {
	@Keyword
	def public String  Compareimg(baselineimg, checkpointimg) //it handles only png i guess, need to try other formats, svg is not handled for sure
	{
		//def expectedImagePath = RunConfiguration.getProjectDir() + baselineimg
		//def actualImagePath = RunConfiguration.getProjectDir() + checkpointimg
		def expectedImagePath =  baselineimg
		def actualImagePath = checkpointimg
		def tolerance = 0

		def expectedImage = ImageIO.read(new File(expectedImagePath))
		def actualImage = ImageIO.read(new File(actualImagePath))


		def width = expectedImage.getWidth()
		def height = expectedImage.getHeight()

		def numDiffPixels = 0

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (expectedImage.getRGB(x, y) != actualImage.getRGB(x, y)) {
					numDiffPixels++
				}
			}
		}

		def diffPercent = numDiffPixels / (width * height)
		String msg
		if (diffPercent > tolerance) {
			msg='Images are Not identical'
			throw new Exception("Images are not similar. Number of different pixels: " + numDiffPixels + " (" + (diffPercent * 100) + "%)")
			System.out.println("Images are Not identical")
			KeywordUtil.markFailed("Images are Not identical")
		}else {
			msg='Images are identical'

			KeywordUtil.markPassed("Images are identical")
		}
		return msg
	}
}
