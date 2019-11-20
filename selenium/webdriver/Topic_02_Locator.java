package webdriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Topic_02_Locator {
	WebDriver driver;
	
	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	@Test
	public void TC_01() {
		driver.get("http://live.demoguru99.com/index.php/customer/account/login/");
		
		//ID
		driver.findElement(By.id("email")).sendKeys("id@mailtothis.com");
		driver.findElement(By.id("pass")).sendKeys("12345");		
		
		//NAME
		driver.findElement(By.name("send")).click();
		
		//CLASS
		driver.findElement(By.className("//*[@class='input-text required-entry validate-email']")).sendKeys("className");
		
		//TAGNAME (Tìm ra page có bao nhiêu đường link) 
		List <WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println("count = " + links.size());
		for(WebElement link: links) {
			System.out.println("Value = " + link.getAttribute("href"));	
		}
	}
	
	
	
	
	
	
}