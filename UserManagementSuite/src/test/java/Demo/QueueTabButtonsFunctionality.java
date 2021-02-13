package Demo;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class QueueTabButtonsFunctionality {
	private WebDriver driver;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testQueueTabButtomsFunctionality() throws Exception {
		// login into WebOps as test/test
		driver.get("http://185.13.87.17:6011/webops/index.html?uid=testOps1");
		driver.findElement(By.id("usernameBox")).click();
		driver.findElement(By.id("usernameBox")).clear();
		driver.findElement(By.id("usernameBox")).sendKeys("test");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("test");
		driver.findElement(By.id("passLoginBut")).click();
		// check if client is configured
		assertEquals(isElementPresent(By.className("btn")), false);
		// check if user login/pwd are correct
		assertEquals(isElementPresent(By.id("errMsg")), false);
		// handle chrome pwd alert
		if (isAlertPresent()) {
			System.out.println(closeAlertAndGetItsText());
		}
		
		//find and navigate to the queue tab
		driver.findElement(By.id("textarea.textField")).click();
		driver.findElement(By.id("textarea.textField")).clear();
		driver.findElement(By.id("textarea.textField")).sendKeys("queue");
		driver.findElement(By.xpath("//div[@id='div.menuItems']/div")).click();
		//check if tab is present
		assertEquals(driver.findElement(By.xpath("//div[@id='div.menuItems']/div")), true);
		
		//choose first item from the queue and replay it
		driver.findElement(By.xpath("//div[@id='__upisComponents.DataGrid110.tableHolder']/div/table/tbody/tr/td[2]"))
				.click();
		driver.findElement(By.id("button.replayButton")).click();
		
		//choose same item and cancel it
		driver.findElement(By.xpath("//div[@id='__upisComponents.DataGrid110.tableHolder']/div/table/tbody/tr/td[5]"))
				.click();
		driver.findElement(By.id("button.cancelButton")).click();
		
		//download played announcement
		driver.findElement(
				By.xpath("//div[@id='__upisComponents.DataGrid110.tableHolder']/div/table/tbody/tr/td[5]/span"))
				.click();
		driver.findElement(By.xpath("//a[@id='a.downloadLink']/span")).click();
		// Autoit script will be added later
		driver.close();
		
		//export queue items
		driver.findElement(By.xpath("//div[@id='div.contentHolder']/queue-tab/div/floating-controls/div/button[3]/i"))
				.click();
		driver.findElement(By.id("input.elm[1]")).clear();
		driver.findElement(By.id("input.elm[1]")).sendKeys("13-02-2021");
		driver.findElement(By.id("input.elm")).clear();
		driver.findElement(By.id("input.elm")).sendKeys("13-01-2021");
		driver.findElement(By.xpath("//label/div")).click();
		driver.findElement(By.xpath("//div[4]/label/div")).click();
		driver.findElement(By.id("input.elm")).clear();
		driver.findElement(By.id("input.elm")).sendKeys("13-01-2021");
		driver.findElement(By.id("input.elm[1]")).clear();
		driver.findElement(By.id("input.elm[1]")).sendKeys("13-02-2021");
		driver.findElement(By.xpath("//button[2]/i")).click();
		driver.findElement(By.id("downloadLink")).click();
		// autoit script will be added later
		driver.close();
		// hide expot window
		driver.findElement(By.xpath("//div[2]/div[2]/button")).click();
	}

	@AfterClass(alwaysRun = true)
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
