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

public class Topic_04_Exercise_Xpath_CSS {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://live.guru99.com/");
	}

	@Test
	public void loginEmpty() { 
		driver.findElement(By.xpath("//*[@id='header-account']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys(" ");
		driver.findElement(By.xpath("//input[@id='pass']")).sendKeys(" ");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='advice-required-entry-email']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//div[@id='advice-required-entry-pass']")).isDisplayed());
	}
	
	@Test
	public void loginWithInvalidEmail () {
		driver.findElement(By.xpath("//*[@id='header-account']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("linhnk@123");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'Please enter a valid email address. For example johndoe@domain.com.')]")).isDisplayed());
		}
	
	@Test
	public void loginWithPassLessThanSixCharacters() {
		driver.findElement(By.xpath("//*[@id='header-account']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("linhnk@gmail.com");
		driver.findElement(By.xpath("//input[@id='pass']")).sendKeys("12345");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'Please enter 6 or more characters without leading or trailing spaces.')]")).isDisplayed());
	}
	
	@Test
	public void loginWitncorrectPassword() {
		driver.findElement(By.xpath("//*[@id='header-account']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("automation@gmail.com");
		driver.findElement(By.xpath("//input[@id='pass']")).sendKeys("12312312");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//*[@class='error-msg']//span[contains(text(),'Invalid login or password.')]")).isDisplayed());
	}

}