package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_Exercise_CustomDd {
	WebDriver driver;
	WebDriverWait waitExplicit;
	JavascriptExecutor javascript;
	String jqueryUrl = "https://jqueryui.com/resources/demos/selectmenu/default.html";
	String angularUrl = "https://ej2.syncfusion.com/angular/demos/?_ga=2.262049992.437420821.1575083417-524628264.1575083417#/material/drop-down-list/data-binding";
	String vueJsUrl = "https://mikerodham.github.io/vue-dropdowns/";
	String jqueryEditable = "http://indrimuska.github.io/jquery-editable-select/";

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		waitExplicit = new WebDriverWait(driver, 10);
		javascript = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void selectDd_Jquery() throws InterruptedException {
		String text = "19";
		driver.get(jqueryUrl);
		selectCustomDd("//span[@id='number-button']", "//ul[@id='number-menu']/li", text);
		Thread.sleep(2000);
		Assert.assertTrue(isElementDisplayed(
				"//span[@id='number-button']//span[@class='ui-selectmenu-text' and text()='" + text + "']"));
	}

	@Test
	public void selectDd_Angular() {
		String actualValue = "Basketball";
		driver.get(angularUrl);
		selectCustomDd("//ejs-dropdownlist[@id='games']", "//ul[@id='games_options']//li", actualValue);
		String expectedValue = getTextByJS("#games_hidden > option");
		System.out.println("Text = " + expectedValue);
		Assert.assertEquals(actualValue, expectedValue);
	}

	@Test
	public void selectDd_VueJS() throws InterruptedException {
		String actualValue = "Second Option";
		driver.get(vueJsUrl);
		selectCustomDd("//li[@class='dropdown-toggle']", "//ul[@class='dropdown-menu']//li", actualValue);
		Thread.sleep(2000);
		Assert.assertTrue(
				isElementDisplayed("//li[@class='dropdown-toggle' and normalize-space()='" + actualValue + "']"));
	}

	@Test
	public void selectDd_Jquer_EditableSelect() throws InterruptedException {
		String enteredValue = "Audi";
		driver.get(jqueryEditable);
		selectCustomEditableDd("(//div[text()='Into this']/following::input)[1]",
				"//div[@id='basic-place']//ul[@class='es-list']//li[@style='display: block;']", enteredValue);
		Thread.sleep(2000);

		String expect = getTextElementByJS("#basic-place li.es-visible");
		Assert.assertEquals(enteredValue, expect);
		System.out.println("Expected text =======" + expect);
	}

	public void selectCustomDd(String parentXpath, String allItemsXpath, String expectedText) {
		// 1- Click vào Dropdown
		driver.findElement(By.xpath(parentXpath)).click();

		// 2- Khai báo 1 list WebElement chứa all items bên trong
		List<WebElement> allItems = driver.findElements(By.xpath(allItemsXpath));

		// 3- Wait để tất cả các items hiển thị
		waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allItemsXpath)));

		// 4- Get text từng item & so sánh với item cần chọn & 5- click vào item
		for (WebElement item : allItems) {
			if (item.getText().equals(expectedText)) {
				item.click();
				break;
			}
		}
	}

	public void selectCustomEditableDd(String parentXpath, String allItemsXpath, String expectedText)
			throws InterruptedException {
		driver.findElement(By.xpath(parentXpath)).clear();
		driver.findElement(By.xpath(parentXpath)).sendKeys(expectedText);
		Thread.sleep(2000);

		waitExplicit.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(allItemsXpath)));
		List<WebElement> allItems = driver.findElements(By.xpath(allItemsXpath));

		for (WebElement item : allItems) {
			if (item.getText().equals(expectedText)) {
				item.click();
				break;
			}
		}
	}

	public boolean isElementDisplayed(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		if (element.isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}

	public String getTextByJS(String locator) {
		return (String) javascript.executeScript("return document.querySelector('" + locator + "').text");
	}

	public String getTextElementByJS(String locator) {
		return (String) javascript.executeScript("return document.querySelector('" + locator + "').textContent");
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}