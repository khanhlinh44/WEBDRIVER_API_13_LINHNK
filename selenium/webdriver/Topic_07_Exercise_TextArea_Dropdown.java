package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_Exercise_TextArea_Dropdown {
	WebDriver driver;

	// Data
	String userID = "mngr234988";
	String password = "YzamabY";
	String customerName = "LinhNK";
	String gender = "female";
	String dob = "04-04-1996";
	String address = "814 Mission Street";
	String city = "Los Angeles";
	String state = "California";
	String pin = "900011";
	String mobileNumber = "4047957606";
	String email = "linhnk" + randomNumber() + "@mailtothis.com";

	// Locator
	By nameTextbox = By.name("name");
	By genderRadio = By.xpath("//input[@value='f']");
	By dobTextbox = By.name("dob");
	By addressTextArea = By.name("addr");
	By cityTextbox = By.name("city");
	By stateTextbox = By.name("state");
	By pinTextbox = By.name("pinno");
	By mobileNumberTextbox = By.name("telephoneno");
	By emailTextbox = By.name("emailid");
	By passwordTextbox = By.name("password");
	By submitBtn = By.name("sub");

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public int randomNumber() {
		Random rand = new Random();
		int n = rand.nextInt(100000);
		return n;
	}

	@Test
	public void loginToHomePage() {
		driver.get("http://demo.guru99.com/v4");
		driver.findElement(By.name("uid")).sendKeys(userID);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("frmLogin")).submit();

	}

	@Test
	public void verifyHomePage() {
		driver.get("http://demo.guru99.com/");
		driver.findElement(By.name("uid")).sendKeys(userID);
		driver.findElement(By.name("password")).sendKeys(password);
		driver.findElement(By.name("frmLogin")).submit();

		String welcomeText = driver.findElement(By.tagName("marquee")).getText();

		Assert.assertEquals(welcomeText, "Welcome To Manager's Page of Guru99 Bank");
		Assert.assertTrue(
				driver.findElement(By.xpath("//tr[@class='heading3']//td[text()='Manger Id: " + userID + "']"))
						.isDisplayed());
	}

	@Test
	public void TC_01_CreateANewCustomer() {
		// enter customer infor and submit
		driver.findElement(nameTextbox).sendKeys(customerName);
		driver.findElement(genderRadio).sendKeys(gender);
		driver.findElement(dobTextbox).sendKeys(dob);
		driver.findElement(addressTextArea).sendKeys(address);
		driver.findElement(cityTextbox).sendKeys(city);
		driver.findElement(stateTextbox).sendKeys(state);
		driver.findElement(pinTextbox).sendKeys(pin);
		driver.findElement(mobileNumberTextbox).sendKeys(mobileNumber);
		driver.findElement(emailTextbox).sendKeys(email);
		driver.findElement(passwordTextbox).sendKeys(password);
		driver.findElement(submitBtn).click();

		Assert.assertTrue(driver
				.findElement(By.xpath("//td//p[@class='heading3' and text()='Customer Registered Successfully!!!']"))
				.isDisplayed());

		// verify output is equal to input data
		Assert.assertEquals(customerName,
				driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText());
		Assert.assertEquals(gender,
				driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText());
		Assert.assertEquals(dob,
				driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText());
		Assert.assertEquals(address,
				driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText());
		Assert.assertEquals(city, driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText());
		Assert.assertEquals(state,
				driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText());
		Assert.assertEquals(pin, driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText());
		Assert.assertEquals(mobileNumber,
				driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText());
		Assert.assertEquals(mobileNumber,
				driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText());

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}