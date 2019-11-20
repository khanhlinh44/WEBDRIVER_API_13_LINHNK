package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Topic_06_Webrowser_WebElement {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@BeforeMethod
	public void navigateToLoginPage() {
		driver.get("http://live.guru99.com/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
	}

	@Test
	public void TC_01_verifyUrlLoginPage() {
		String loginPageUrl = driver.getCurrentUrl();
		Assert.assertEquals(loginPageUrl, "http://live.demoguru99.com/index.php/customer/account/login/");
	}

	@Test
	public void TC_01_verifyUrlRegisterPage() {
		driver.findElement(By.xpath("//a//span[text()='Create an Account']")).click();
		String registerPageUrl = driver.getCurrentUrl();
		Assert.assertEquals(registerPageUrl, "http://live.demoguru99.com/index.php/customer/account/create/");
	}

	@Test
	public void TC_02_verifyTitleLoginPage() {
		String loginPageTitle = driver.getTitle();
		Assert.assertEquals(loginPageTitle, "Customer Login");
	}

	@Test
	public void TC_02_verifyTitleRegisterPage() {
		driver.findElement(By.xpath("//a//span[text()='Create an Account']")).click();
		String loginPageTitle = driver.getTitle();
		Assert.assertEquals(loginPageTitle, "Create New Customer Account");
	}

	@Test
	public void TC_03_navigateFunction() {
		driver.findElement(By.xpath("//a//span[text()='Create an Account']")).click();
		String registerPageUrl = driver.getCurrentUrl();
		Assert.assertEquals(registerPageUrl, "http://live.demoguru99.com/index.php/customer/account/create/");

		driver.navigate().back();
		String loginPageUrl = driver.getCurrentUrl();
		Assert.assertEquals(loginPageUrl, "http://live.demoguru99.com/index.php/customer/account/login/");

		driver.navigate().forward();
		String registerPageTitle = driver.getTitle();
		Assert.assertEquals(registerPageTitle, "Create New Customer Account");
	}

	@Test
	public void TC_04_getPageSourceCode() {
		// verify Login Page chứa text "Login or Create an Account"
		String loginPageSource = driver.getPageSource();
		Assert.assertTrue(loginPageSource.contains("Login or Create an Account"));

		// verify Register Page chứa text "Create an Account"
		driver.findElement(By.xpath("//a//span[text()='Create an Account']")).click();
		String registerPageSource = driver.getPageSource();
		Assert.assertTrue(registerPageSource.contains("Create an Account"));

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}