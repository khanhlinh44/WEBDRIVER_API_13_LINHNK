package webdriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_14_Upload_File {

	WebDriver driver;
	JavascriptExecutor jsExecutor;
	WebElement element;
	String projectPath = System.getProperty("user.dir"); // Lấy dir của folder hiện tại
	String image1Path = projectPath + "/Image/image1.jpg";
	String image2Path = projectPath + "/Image/image2.jpg";
	String image3Path = projectPath + "/Image/image3.jpeg";

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "/libraries/chromedriver");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--lang=vi");
		driver = new ChromeDriver(options);

		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_UploadFileBySendKey() throws InterruptedException {
		driver.get("http://blueimp.github.io/jQuery-File-Upload/");

		WebElement uploadFile = findElement("//span[text()='Add files...']/following-sibling::input");
		uploadFile.sendKeys(image1Path + "\n" + image2Path + "\n" + image3Path);
		Thread.sleep(4000);

		List<WebElement> startButtons = findElements("//button[@class='btn btn-primary start']//span[text()='Start']");
		for (WebElement startButton : startButtons) {
			startButton.click();
			Thread.sleep(2000);
		}

		Assert.assertTrue(findElement("//p[@class='name']//a[text()='image1.jpg']").isDisplayed());
		Assert.assertTrue(findElement("//p[@class='name']//a[text()='image2.jpg']").isDisplayed());
		Assert.assertTrue(findElement("//p[@class='name']//a[text()='image3.jpeg']").isDisplayed());

	}

	public WebElement findElement(String xpath) {
		return driver.findElement(By.xpath(xpath));
	}

	public List<WebElement> findElements(String xpath) {
		return driver.findElements(By.xpath(xpath));
	}

	public int randomNumber() {
		Random rand = new Random();
		int n = rand.nextInt(100000);
		return n;
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}