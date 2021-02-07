package Demo;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class UserCRUD {
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
	public void testUserCRUD() throws Exception {
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

		// find and navigate to the Users tab
		driver.findElement(By.id("textarea.textField")).clear();
		driver.findElement(By.id("textarea.textField")).sendKeys("users");
		driver.findElement(By.xpath("//div[@id='div.menuItems[26]']/div/div")).click();
		// check if tab exists
		assertEquals(isElementPresent(By.xpath("//div[@id='div.menuItems[26]']/div/div")),true);

		// adding and saving a new user
		driver.findElement(By.xpath("(//button[@type='success'])[2]")).click();
		driver.findElement(By.id("input.input")).click();
		driver.findElement(By.id("input.input")).clear();
		driver.findElement(By.id("input.input")).sendKeys("0new");
		driver.findElement(
				By.xpath("//div[@id='__upisComponents.UsersTab59.editState']/div/floating-controls/div/button"))
				.click();
		// check if the user is unique
		assertEquals(isElementPresent(By.xpath("//*[@id=\"span.validationMessageHolder\"]")),false);

		// refresh users tab
		driver.findElement(By.xpath("//div[@id='div.menuItems[26]']/div/div")).click();

		// adding a password to the created user
		driver.findElement(By.xpath("//*[@id=\"__upisComponents.DataList137.tableHolder\"]/div[1]")).click();
		// current user pwd
		driver.findElement(By.id("button.passwordEditor")).click();
		driver.findElement(By.xpath("//input[@type='password']")).clear();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("test");
		// new pwd
		driver.findElement(By.xpath("(//input[@type='password'])[2]")).click();
		driver.findElement(By.xpath("(//input[@type='password'])[2]")).clear();
		driver.findElement(By.xpath("(//input[@type='password'])[2]")).sendKeys("new");
		// confirm pwd
		driver.findElement(By.xpath("(//input[@type='password'])[3]")).click();
		driver.findElement(By.xpath("(//input[@type='password'])[3]")).clear();
		driver.findElement(By.xpath("(//input[@type='password'])[3]")).sendKeys("new");
		driver.findElement(By.xpath("//div[3]/button[2]")).click();
		// check if pwds are ok
		assertEquals(isElementPresent(By.className("ui error message password-change_error-box")), false);
		// save user changes
		driver.findElement(
				By.xpath("//div[@id='__upisComponents.UsersTab59.editState']/div/floating-controls/div/button"))
				.click();
		System.out.println("123 " + driver.findElement(By.xpath("//*[@id=\"__upisComponents.DataList137.tableHolder\"]/div[1]")).getText());
		
		// logout
		driver.findElement(By.xpath("//div[@id='div.asideHolder']/div[4]/div[5]/i")).click();

		// login as a newly created user
		driver.findElement(By.id("usernameBox")).click();
		driver.findElement(By.id("usernameBox")).clear();
		driver.findElement(By.id("usernameBox")).sendKeys("0new");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("new");
		driver.findElement(By.name("password")).sendKeys(Keys.ENTER);
		// check if user login/pwd are correct
		assertEquals(isElementPresent(By.id("errMsg")), false);
		// handle chrome pwd alert
		if (isAlertPresent()) {
			System.out.println(closeAlertAndGetItsText());
		}

		// logout
		driver.findElement(By.xpath("//div[@id='div.asideHolder']/div[4]/div[5]/i")).click();

		// login as test/test
		driver.findElement(By.id("usernameBox")).click();
		driver.findElement(By.id("usernameBox")).clear();
		driver.findElement(By.id("usernameBox")).sendKeys("test");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("test");
		driver.findElement(By.id("passLoginBut")).click();
		// check if user login/pwd are correct
		assertEquals(isElementPresent(By.id("errMsg")), false);
		// handle chrome pwd alert
		if (isAlertPresent()) {
			System.out.println(closeAlertAndGetItsText());
		}
		
		//navigate to the users tab
		driver.findElement(By.id("textarea.textField")).clear();
		driver.findElement(By.id("textarea.textField")).sendKeys("users");
		driver.findElement(By.id("div.menuItems[26]")).click();
		
		//edit and save newly created user
		driver.findElement(By.xpath("//*[@id=\"__upisComponents.DataList137.tableHolder\"]/div[1]")).click();
		driver.findElement(By.id("input.input")).click();
		driver.findElement(By.id("input.input")).clear();
		driver.findElement(By.id("input.input")).sendKeys("00new");
		driver.findElement(
				By.xpath("//div[@id='__upisComponents.UsersTab59.editState']/div/floating-controls/div/button"))
				.click();
		
		//delete newly created user
		driver.findElement(By.xpath("//*[@id=\"__upisComponents.DataList137.tableHolder\"]/div[1]")).click();
		driver.findElement(
				By.xpath("//div[@id='__upisComponents.UsersTab59.editState']/div/floating-controls/div/button[2]"))
				.click();
		driver.findElement(By.id("button.elmBtnOk")).click();
		//don't forget to add assertion for deletion
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
