package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_15_Wait {

	WebDriver driver;
	WebDriverWait explicitWait;
	FluentWait<WebDriver> fluentWait;
	WebElement element;
	String projectPath = System.getProperty("user.dir");

	String url1 = "http://the-internet.herokuapp.com/dynamic_loading/2";
	String url2 = "https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx";
	By resultText = By.xpath("//div[@id='finish']//h4[text()='Hello World!']");
	By loadingIcon = By.xpath("//div[@id='loading']//img");
	By dateTimePicker = By.xpath("//div[@class='calendarContainer']");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "/libraries/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		driver.manage().window().maximize();
	}

//	@Test
	public void TC_01_FluentWait() {
		driver.get("");

		// Tong thoi gian can check
		fluentWait.withTimeout(15, TimeUnit.SECONDS)
				// Tan so. neu find k thay element se bo qua
				.ignoring(NoSuchElementException.class);

	}

//	@Test
	public void TC_02_ImplicitWait() {
		driver.get(url1);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		clickBtnWithLabel("Start");
		Assert.assertTrue(driver.findElement(resultText).isDisplayed());
	}

//	@Test
	public void TC_03_ExplicitWait_Invisible() {
		String actualSelectedDate = "";
		String expectedSelectedDate = "No Selected Dates to display.";
		driver.get(url1);
		explicitWait = new WebDriverWait(driver, 5);

		clickBtnWithLabel("Start");

		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(loadingIcon));
		Assert.assertTrue(driver.findElement(resultText).isDisplayed());

		actualSelectedDate = driver.findElement(By.xpath("//div[@class='datesContainer']//span")).getText();
		Assert.assertEquals(actualSelectedDate, expectedSelectedDate);

	}

//	@Test
	public void TC_04_ExplicitWait_Visible() {
		driver.get(url1);
		explicitWait = new WebDriverWait(driver, 5);

		clickBtnWithLabel("Start");
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(loadingIcon));
		Assert.assertTrue(driver.findElement(resultText).isDisplayed());

	}

	@Test
	public void TC_05_ExplicitWait() {
		String actualSelectedDate = "";
		String unSelectedDate = "No Selected Dates to display.";
		String selectedDate = "Wednesday, January 15, 2020";
		// Step 01 - open url
		driver.get(url2);

		// Step 02 - Wait
		explicitWait = new WebDriverWait(driver, 10);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(dateTimePicker));

		// Step 03 - verify selected date
		actualSelectedDate = driver.findElement(By.xpath("//div[@class='datesContainer']//span")).getText();
		Assert.assertEquals(actualSelectedDate, unSelectedDate);

		// Step 04 - Select current date
		clickElement("//div[@class='calendarContainer']//tbody//td//a[text()='15']");

		// Step 05 - wait until loading icon displayed
		explicitWait.until(ExpectedConditions
				.invisibilityOfElementLocated(By.xpath("//div[not(@style='display:none;')]/div[@class='raDiv']")));

		// Step 06 - current date = selected date
		actualSelectedDate = driver.findElement(By.xpath("//div[@class='datesContainer']//span")).getText();
		Assert.assertEquals(actualSelectedDate, selectedDate);
		System.out.println("Selected date" + actualSelectedDate);

	}

	public void clickBtnWithLabel(String label) {
		driver.findElement(By.xpath("//div//button[text()='" + label + "']")).click();
	}

	public void clickElement(String xpath) {
		driver.findElement(By.xpath(xpath)).click();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}