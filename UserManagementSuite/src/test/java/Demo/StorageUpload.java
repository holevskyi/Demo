package Demo;

import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class StorageUpload {
	private WebDriver driver;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testStorageUpload() throws Exception {
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

		// navigate to the Storage tab
		driver.findElement(By.id("textarea.textField")).click();
		driver.findElement(By.id("textarea.textField")).clear();
		driver.findElement(By.id("textarea.textField")).sendKeys("storage");
		driver.findElement(By.xpath("//div[@id='div.menuItems[54]']/div/div")).click();
		// check if tab exists
		assertEquals(isElementPresent(By.xpath("//div[@id='div.menuItems[54]']/div/div")), true);

		// click on the upload and choose file button
		driver.findElement(By.id("button.uploadButton")).click();
		driver.findElement(By.id("label.fileInputLabel")).click();

		Runtime.getRuntime().exec(/* path to the executable */"");
		// Autoit executable script will be added
		// check if file exists
		assertEquals(Runtime.getRuntime().exec(/* path to the executable */"") != null, true);
		//saving the file
		driver.findElement(By.xpath("//div[2]/button[2]")).click();

		// adding alias to the uploaded element
		driver.findElement(By.id("input.input")).click();
		driver.findElement(By.id("input.input")).clear();
		driver.findElement(By.id("input.input")).sendKeys("Grisha");
		driver.findElement(
				By.xpath("//div[@id='__upisComponents.StorageTab87.editState']/div/floating-controls/div/button"))
				.click();

		// choosing uploaded file and delete it
		driver.findElement(By.xpath("//div[@id='__upisComponents.PojoListItem113.closedState']/div/div")).click();
		driver.findElement(
				By.xpath("//div[@id='__upisComponents.StorageTab87.editState']/div/floating-controls/div/button[2]"))
				.click();
		driver.findElement(By.id("button.elmBtnOk")).click();
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
