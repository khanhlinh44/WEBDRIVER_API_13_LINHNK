package webdriver;

import java.util.Random;
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
	String validEmail = "automation" + randomNumber() + "@gmail.com";
	String validPassword = "123123";


	@BeforeClass
	//chay mot lan cho tat ca cac test ben duoi
	public void openBrowser() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@BeforeMethod
	//chay cho moi test ben duoi
	public void navigateToLoginPage() {
		driver.get("http://live.guru99.com/");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		}
	
	public int randomNumber() {
		Random rand = new Random();
		 int n = rand.nextInt(100000);
		 return n;
	}

	@Test
	public void loginWithEmptyEmailAndPassword() { // Dùng hàm assertEqual 
		String msgRequired = "This is a required field.";
		
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("");
		driver.findElement(By.xpath("//input[@id='pass']")).sendKeys("");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
	   
		String emaiErrorMsg  =  driver.findElement(By.xpath("//div[@class='validation-advice' and @id='advice-required-entry-email']")).getText();
		Assert.assertEquals(emaiErrorMsg, msgRequired);
		
		String passErrorMsg  =  driver.findElement(By.xpath("//div[@class='validation-advice' and @id='advice-required-entry-pass']")).getText();
		Assert.assertEquals(passErrorMsg, msgRequired);
				
	}
	
	@Test
	public void loginWithInvalidEmail () {    // Dùng hàm assertTrue 
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("linhnk@123");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'Please enter a valid email address. For example johndoe@domain.com.')]")).isDisplayed());
		}
	
	@Test
	public void loginWithPassLessThanSixCharacters() {
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("linhnk@gmail.com");
		driver.findElement(By.xpath("//input[@id='pass']")).sendKeys("12345");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//div[contains(text(),'Please enter 6 or more characters without leading or trailing spaces.')]")).isDisplayed());
	}
	
	@Test
	public void loginWitncorrectPassword() {
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("automation@gmail.com");
		driver.findElement(By.xpath("//input[@id='pass']")).sendKeys("12312312");
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//*[@class='error-msg']//span[contains(text(),'Invalid login or password.')]")).isDisplayed());
	}
	
	@Test
	public void loginWithValidEmailAndPassword() {
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys(validEmail);
		driver.findElement(By.xpath("//input[@id='pass']")).sendKeys(validPassword);
		driver.findElement(By.xpath("//button[@title='Login']")).click();
		
		Assert.assertTrue(driver.findElement(By.xpath("//*[@class='error-msg']//span[contains(text(),'Invalid login or password.')]")).isDisplayed());
	}
	
	@Test
	public void creatANewAccount() {
		
	   String firstName = "Linh";
	   String lastName = "Nguyen";
		
		driver.findElement(By.xpath("//a//span[text()='Create an Account']")).click();
		
		driver.findElement(By.xpath("//input[@id='firstname']")).sendKeys(firstName);
		driver.findElement(By.xpath("//input[@id='lastname']")).sendKeys(lastName);
		driver.findElement(By.xpath("//input[@id='email_address']")).sendKeys(validEmail);
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(validPassword);
		driver.findElement(By.xpath("//input[@id='confirmation']")).sendKeys(validPassword);
		driver.findElement(By.xpath("//button[@title='Register']")).click();
		
	}	
	
	@AfterClass
	public void afterClass() {
	driver.quit();
	}

}