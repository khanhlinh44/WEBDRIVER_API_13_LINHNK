package webdriver;

import java.util.List;
import java.util.Set;
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

public class Topic_11_12_Excersise_Popup_Irame {

	WebDriver driver;
	JavascriptExecutor js;
	String projectPath = System.getProperty("user.dir");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", projectPath + "/libraries/chromedriver");
		System.out.println("project path =" + projectPath);
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--lang=vi");
		driver = new ChromeDriver(options);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_Popup_Iframe() {
		driver.get("https://kyna.vn/");

		// close popup if have
		List<WebElement> fancyPopup = driver.findElements(By.xpath("//div[@class='fancybox-inner']"));

		if (fancyPopup.size() > 0) {
			Assert.assertTrue(fancyPopup.get(0).isDisplayed());
			driver.findElement(By.cssSelector(".fancybox-close")).click();
		}

		// switch to iframe and verify likes
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='face-content']//iframe")));
		String facekbookLikes = driver.findElement(By.xpath("//a[@title='Kyna.vn']/parent::*/following-sibling::div"))
				.getText();
		Assert.assertEquals(facekbookLikes, "170K likes");

		// login to dashboard
		driver.findElement(By.className("button-login")).click();
		driver.findElement(By.id("user-login")).sendKeys("automationfc.vn@gmail.com");
		driver.findElement(By.id("user-passwor")).sendKeys("automationfc.vn@gmail.com");
	}

	@Test
	public void TC_02_WindowTab() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		// Tra về ID của tab hiện tại(đang active)
		String parentID = driver.getWindowHandle();

		// Tra về ID của all tab
//		Set<String> allID = driver.getWindowHandles();

		driver.findElement(By.xpath("//label/following-sibling::a[text()='GOOGLE']")).click();
		switchToWindowByTitle("Google");
		Assert.assertEquals(driver.getTitle(), "Google");

		switchToWindowByTitle("SELENIUM WEBDRIVER FORM DEMO");
		driver.findElement(By.xpath("//label/following-sibling::a[text()='FACEBOOK']")).click();
		switchToWindowByTitle("Facebook - Đăng nhập hoặc đăng ký");

		closeAllTabWithoutParent(parentID);
		Assert.assertEquals(driver.getTitle(), "SELENIUM WEBDRIVER FORM DEMO");

	}

	@Test
	public void TC_03_WindowTab() throws InterruptedException {
		driver.get("https://kyna.vn/");

		// close popup if have
		List<WebElement> fancyPopup = driver.findElements(By.xpath("//div[@class='fancybox-inner']"));
		if (fancyPopup.size() > 0) {
			Assert.assertTrue(fancyPopup.get(0).isDisplayed());
			driver.findElement(By.cssSelector(".fancybox-close")).click();
			Thread.sleep(3000);
		}

		String parentID = driver.getWindowHandle();

		clickElementByJS("//div[@class='app-col']//a//img[@alt='apple-app-icon']");
		switchToWindowByTitle("‎KYNA on the App Store");
		Assert.assertEquals(driver.getTitle(), "‎KYNA on the App Store");

		switchToWindowByTitle("Kyna.vn - Học online cùng chuyên gia");
		clickElementByJS("//div[@class='app-col']//a//img[@alt='android-app-icon']");

		switchToWindowByTitle("KYNA - Học online cùng chuyên gia - Ứng dụng trên Google Play");
		Assert.assertEquals(driver.getTitle(), "KYNA - Học online cùng chuyên gia - Ứng dụng trên Google Play");

		closeAllTabWithoutParent(parentID);
	}

	@Test
	public void TC_04_WindowTab() throws InterruptedException {
		// Step 01 - open url
		driver.get("http://live.demoguru99.com/index.php/");
		String parenTitle = driver.getTitle();

		// Step 02 - click on mobile tab
		driver.findElement(By.xpath("//ol//a[text()='Mobile']")).click();

		// Step 03 - add Sony Xperia
		clickOnButton(
				"//h2[@class='product-name']//a[text()='Sony Xperia']/ancestor::div[1]//a[text()='Add to Compare']");
		Assert.assertTrue(driver.findElement(By.xpath(
				"//li[@class='success-msg']//span[text()='The product Sony Xperia has been added to comparison list.']"))
				.isDisplayed());

		// Step 04 - add Samsung Galaxy
		clickOnButton(
				"//h2[@class='product-name']//a[text()='Samsung Galaxy']/ancestor::div[1]//a[text()='Add to Compare']");
		Assert.assertTrue(driver.findElement(By.xpath(
				"//li[@class='success-msg']//span[text()='The product Samsung Galaxy has been added to comparison list.']"))
				.isDisplayed());

		// Step 05 - click on compare button
		driver.findElement(By.xpath("//button//span[text()='Compare']")).click();

		// Step 06 - switch to new window
		String title = "Products Comparison List - Magento Commerce";
		switchToWindowByTitle(title);
		Assert.assertEquals(driver.getTitle(), title);

		// Step 07 - close the tab and switch parent window
		closeAllTabWithoutParent(parenTitle);
	}

	public void switchToWinDowByID(String parentID) {
		Set<String> allWinDows = driver.getWindowHandles();

		for (String termID : allWinDows) {
			if (!termID.equals(parentID)) {
				driver.switchTo().window(parentID);
				break;
			}
		}
	}

	public void switchToWindowByTitle(String title) {
		Set<String> allWinDows = driver.getWindowHandles();
		for (String runWindow : allWinDows) {
			driver.switchTo().window(runWindow);
			String currentWin = driver.getTitle();
			if (currentWin.equals(title)) {
				break;
			}
		}
	}

	public void closeAllTabWithoutParent(String parentWindow) {
		Set<String> allWinDows = driver.getWindowHandles();

		for (String runWindow : allWinDows) {
			driver.switchTo().window(runWindow);
			if (!runWindow.equals(parentWindow)) {
				driver.close();
			}
		}
		driver.switchTo().window(parentWindow);
	}

	public void srollToViewElement(String xpath) {

	}

	public void clickElementByJS(String locator) {
		WebElement element = driver.findElement(By.xpath(locator));
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);

	}

	public void clickOnButton(String locator) {
		driver.findElement(By.xpath(locator)).click();
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}