package webdriver;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_11_12_Excersise_Popup_Irame {

	WebDriver driver;
	JavascriptExecutor js;

	@BeforeClass
	public void beforeClass() {
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("dom.webnotifications.enabled", false);

		driver = new FirefoxDriver(profile);
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

		// click on App store
		clickElementByJS("//div[@class='app-col']//a//img[@alt='apple-app-icon']");
		switchToWindowByTitle("‎KYNA on the App Store");
		String title = driver.getTitle();
		Assert.assertEquals(title, "‎KYNA on the App Store");

		switchToWindowByTitle("Kyna.vn - Học online cùng chuyên gia");
		clickElementByJS("//div[@class='app-col']//a//img[@alt='android-app-icon']");
		switchToWindowByTitle("KYNA - Học online cùng chuyên gia - Ứng dụng trên Google Play");
		Assert.assertEquals(driver.getTitle(), "KYNA - Học online cùng chuyên gia - Ứng dụng trên Google Play");

		closeAllTabWithoutParent(parentID);

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

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}