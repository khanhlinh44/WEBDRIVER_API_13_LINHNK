package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_09_Exercise_Button_RadioButton_Checkbox_Alert {

	WebDriver driver;
	JavascriptExecutor js;
	Actions action;
	Alert alert;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		action = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

//	@Test
//	public void TC_01_HandleButton() throws InterruptedException {
//		String registerPage = "";
//		String loginPage = "";
//		driver.get("http://live.demoguru99.com/");
//
//		clickElementByJS("//div[@class='footer']//a[@title='My Account']");
//		loginPage = driver.getCurrentUrl();
//		Assert.assertEquals(loginPage, "http://live.demoguru99.com/index.php/customer/account/login/");
//
//		clickElementByJS("//a//span[text()='Create an Account']");
//		registerPage = driver.getCurrentUrl();
//		Assert.assertEquals(registerPage, "http://live.demoguru99.com/index.php/customer/account/create/");
//		Thread.sleep(3000);
//
//	}

	@Test
	public void TC_04_AcceptAlert() {
		String alertText = "";
		driver.get("https://automationfc.github.io/basic-form/index.html");

		// click vào Btn Click for JS Alert
		clickElementByJS("//button[text()='Click for JS Alert']");

		// verify msg
		alert = driver.switchTo().alert();
		alertText = alert.getText();
		Assert.assertEquals(alertText, "I am a JS Alert");

		// Accept alert & verify msg
		alert.accept();
		Assert.assertEquals(getText("//h4[text()='Result:']/following-sibling::p"),
				"You clicked an alert successfully");
	}

	@Test
	public void TC_05_ConfirmAlert() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		clickElementByJS("//button[text()='Click for JS Confirm']");

		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");

		alert.dismiss();
		Assert.assertEquals(getText("//h4[text()='Result:']/following-sibling::p"), "You clicked: Cancel");
	}

	@Test
	public void TC_06_PromptAlert() throws InterruptedException {
		String value = "LinhNK";
		driver.get("https://automationfc.github.io/basic-form/index.html");

		clickElementByJS("//button[text()='Click for JS Prompt']");

		alert = driver.switchTo().alert();
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		alert.sendKeys(value);
		Thread.sleep(3000);

		alert.accept();
		Assert.assertEquals(getText("//h4[text()='Result:']/following-sibling::p"), "You entered: " + value + "");
	}

	@Test
	public void TC_07_AutheticationAlert() throws InterruptedException {
		String userName = "admin";
		String password = "admin";
		String autheticationUrl = "https://" + userName + ":" + password + "@the-internet.herokuapp.com/basic_auth";
		System.out.println(autheticationUrl);

		driver.get(autheticationUrl);
		Thread.sleep(3000);
		Assert.assertEquals(getText("//div[@id='content']//p"),
				"Congratulations! You must have the proper credentials.");
	}

	public void clickElementByJS(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);

	}

	public String getText(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		return element.getText();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}