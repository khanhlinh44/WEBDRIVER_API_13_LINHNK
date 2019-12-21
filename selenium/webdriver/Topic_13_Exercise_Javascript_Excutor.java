package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_13_Exercise_Javascript_Excutor {

	WebDriver driver;
	JavascriptExecutor jsExecutor;
	WebElement element;
	String projectPath = System.getProperty("user.dir");
	Topic_02_Locator topic02;

	// Locator TC_02
	By userIdInput = By.xpath("//input[@name='uid']");
	By passWordInput = By.xpath("//input[@name='password']");
	By loginButton = By.xpath("//input[@name='btnLogin']");
	By newCustomerLink = By.xpath("//a[@href='addcustomerpage.php']");
	By editCustomerLink = By.xpath("//a[@href='EditCustomer.php']");
	String customerNameInput = "//input[@name='name']";
	By maleRadioButton = By.xpath("//input[@name='rad1' and @value='m']");
	String birthDayInput = "//input[@id='dob']";
	String addressTextArea = "//textarea[@name='addr']";
	String cityInput = "//input[@name='city']";
	String stateInput = "//input[@name='state']";
	String pinInput = "//input[@name='pinno']";
	String mobileNumberInput = "//input[@name='telephoneno']";
	String emailInput = "//input[@name='emailid']";
	String passwordInput = "//input[@name='password']";
	String createCustomerSubmitButton = "//input[@name='sub']";
	String editCustomerSubmitButton = "//input[@name='AccSubmit']";

	// Data for create new customer TC_02
	String userName = "mngr236337";
	String passWord = "retUqEn";
	String customerName = "Nhung";
	String dateOfBirth = "04/04/1994";
	String address = "Ho Tung Mau";
	String city = "Ha Noi";
	String state = "Cau Giay";
	String pin = "123456";
	String mobileNumber = "0979161024";
	String email = "automation" + randomNumber() + "@gmail.com";
	String password = "123456";

	// Locator TC_03
	String firstNameXpath = "//input[@id='firstname']";
	String lastNameXpath = "//input[@id='lastname']";
	String emailAddXpath = "//input[@id='email_address']";
	String passwordXpath = "//input[@id='password']";
	String confirmPasswordXpath = "//input[@id='confirmation']";
	String registerBtnXpath = "//button//span[text()='Register']";

	// Data for create an account TC_03
	String firstName = "Nhung";
	String lastName = "DK";

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

//	@Test
//	public void TC_01_JS() {
//		navigateToUrlByJS("http://live.demoguru99.com/");
//
//		String liveDomain = (String) executeForBrowser("return document.domain"); // Ép kiểu
//		Assert.assertEquals(liveDomain, "live.demoguru99.com");
//
//		String currentUrl = (String) executeForBrowser("return document.URL;");
//		Assert.assertEquals(currentUrl, "http://live.demoguru99.com/");
//
//		clickToElementByJS("//a[text()='Mobile']");
//		clickToElementByJS(
//				"//a[text()='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//span[text()='Add to Cart']");
//		verifyTextInInnerText("Samsung Galaxy was added to your shopping cart.");
//
//		clickToElementByJS("//div[@class='links']//a[text()='Customer Service']");
//		scrollToElement("//div[@class='block-title']//span[text()='Newsletter']");
//		verifyTextInInnerText("Praesent ipsum libero, auctor ac, tempus nec, tempor nec, justo.");
//	}

//	@Test
//	public void TC_02_remove_attribute() throws InterruptedException {
//		String userName = "mngr238966";
//		String password = "emYmEqe";
//
//		navigateToUrlByJS("http://demo.guru99.com/v4/");
//		driver.findElement(By.name("uid")).sendKeys(userName);
//		driver.findElement(By.name("password")).sendKeys(password);
//		driver.findElement(By.name("frmLogin")).submit();
//
//		clickToElementByJS("//a[text()='New Customer']");
//		setAttributeToElementByJS(customerNameInput, customerName);
//		driver.findElement(maleRadioButton).click();
//
//		// Remove attribute
//		removeAttributeInDOM(birthDayInput, "type");
//		Thread.sleep(5000);
//
//		driver.findElement(By.xpath(birthDayInput)).sendKeys(dateOfBirth);
//		driver.findElement(By.xpath(addressTextArea)).sendKeys(address);
//		driver.findElement(By.xpath(cityInput)).sendKeys(city);
//		driver.findElement(By.xpath(stateInput)).sendKeys(state);
//		driver.findElement(By.xpath(pinInput)).sendKeys(pin);
//		driver.findElement(By.xpath(mobileNumberInput)).sendKeys(mobileNumber);
//		driver.findElement(By.xpath(emailInput)).sendKeys(email);
//		driver.findElement(By.xpath(passwordInput)).sendKeys(password);
//		driver.findElement(By.xpath(createCustomerSubmitButton)).click();
//	}

	@Test
	public void TC_03_ceateAnAccount() {
		navigateToUrlByJS("http://live.demoguru99.com/");

		clickToElementByJS("//div[@id='header-account']//a[text()='My Account']");
		clickToElementByJS("//a[@title='Create an Account']//span[text()='Create an Account']");

		sendKeyToElementByJS(firstNameXpath, firstName);
		sendKeyToElementByJS(lastNameXpath, lastName);
		sendKeyToElementByJS(emailAddXpath, email);
		sendKeyToElementByJS(passwordXpath, password);
		sendKeyToElementByJS(confirmPasswordXpath, password);
		clickToElementByJS(registerBtnXpath);
		verifyTextInInnerText("Thank you for registering with Main Website Store.");

	}

	// Browser
	public Object executeForBrowser(String javaSript) {
		return jsExecutor.executeScript(javaSript);
	}

	public boolean verifyTextInInnerText(String textExpected) {
		String textActual = (String) jsExecutor
				.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		System.out.println("Text actual = " + textActual);
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage() {
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(String url) {
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	// Element
	public void highlightElement(String locator) {
		element = driver.findElement(By.xpath(locator));
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				"border: 5px solid red; border-style: dashed;");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style",
				originalStyle);

	}

	public void clickToElementByJS(String locator) {
		element = driver.findElement(By.xpath(locator));
		jsExecutor.executeScript("arguments[0].click();", element);
	}

	public void scrollToElement(String locator) {
		element = driver.findElement(By.xpath(locator));
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void sendKeyToElementByJS(String locator, String value) {
		element = driver.findElement(By.xpath(locator));
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		element = driver.findElement(By.xpath(locator));
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", element);
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