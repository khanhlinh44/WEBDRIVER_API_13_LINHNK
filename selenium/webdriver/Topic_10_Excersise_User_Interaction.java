package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_10_Excersise_User_Interaction {

	WebDriver driver;
	Actions action;
	Alert alert;

	@BeforeClass
	public void beforeClass() {
		// capability: config browser
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("dom.webnotifications.enabled", false);

		driver = new FirefoxDriver(profile);
		System.out.println("driver" + driver.toString());

		action = new Actions(driver);

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC_01_hoverMouseToElement() {
		driver.get("http://myntra.com");
		action.moveToElement(driver.findElement(By.xpath("//div[@class='desktop-navLink']//a[text()='Men']")))
				.perform();
		driver.findElement(By.xpath("//div[@class='desktop-navLink']//a[text()='Boxers']")).click();
		Assert.assertEquals(getText("//div[@class='title-container']//h1"), "Boxers For Men");
	}

	@Test
	public void TC_02_clickAndHoldElement() throws InterruptedException {
		driver.get("https://jqueryui.com/resources/demos/selectable/display-grid.html");

		List<WebElement> numbers = driver.findElements(By.xpath("//ol[@id='selectable']//li"));

		// release mouse
		action.clickAndHold(numbers.get(0)).moveToElement(numbers.get(7)).release().perform();
		Thread.sleep(3000);

		List<WebElement> selectedNumber = driver.findElements(
				By.xpath("//ol[@id='selectable']//li[@class='ui-state-default ui-selectee ui-selected']"));
		Assert.assertEquals(selectedNumber.size(), 8);
	}

	@Test
	public void TC_03_clickAndHoldRandom() throws InterruptedException {
		driver.get("https://jqueryui.com/resources/demos/selectable/display-grid.html");

		List<WebElement> numbers = driver.findElements(By.xpath("//ol[@id='selectable']//li"));
		int numberSize = numbers.size();
		System.out.println("number size before click:" + numberSize);

		action.keyDown(Keys.CONTROL).perform();
		action.click(numbers.get(0)).click(numbers.get(2)).click(numbers.get(6)).perform();

		action.keyUp(Keys.CONTROL).perform();
		Thread.sleep(3000);

		List<WebElement> selectedNumber = driver.findElements(
				By.xpath("//ol[@id='selectable']//li[@class='ui-state-default ui-selectee ui-selected']"));
		Assert.assertEquals(selectedNumber.size(), 3);
	}

	@Test
	public void TC_04_doubleClick() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		action.doubleClick(findByXpath("//button[text()='Double click me']")).perform();
		;
		Assert.assertTrue(findByXpath("//button[text()='Double click me']/following-sibling::p").isDisplayed());
	}

	@Test
	public void TC_05_rightClick() throws InterruptedException {
		driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");

		action.contextClick(findByXpath("//span[text()='right click me']")).perform();

		action.moveToElement(
				findByXpath("//ul[@class='context-menu-list context-menu-root']//li[child::span[text()='Quit']]//span"))
				.perform();
		Thread.sleep(3000);
		Assert.assertTrue(findByXpath(
				"//ul[@class='context-menu-list context-menu-root']//li[@class='context-menu-item context-menu-icon context-menu-icon-quit context-menu-visible']")
						.isDisplayed());

		action.click(findByXpath("//ul[@class='context-menu-list context-menu-root']//li[child::span[text()='Quit']]"))
				.perform();

		Assert.assertEquals(driver.switchTo().alert().getText(), "clicked: quit");
	}

	@Test
	public void TC_06_dragAnDrop() {
		driver.get("https://demos.telerik.com/kendo-ui/dragdrop/angular");

		WebElement sourceCirle = findByXpath("//div[@id='draggable']");
		WebElement targetCirle = findByXpath("//div[@id='droptarget']");

		action.dragAndDrop(sourceCirle, targetCirle).perform();
	}

	public WebElement findByXpath(String locator) {
		return driver.findElement(By.xpath(locator));
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