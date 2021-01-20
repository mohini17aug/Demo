package uidemo;

import java.io.IOException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class SelCommonUtil {

	private static WebDriver driver;
	private static String ID_LOCATOR = "ID";
	private static String LINKTEXT_LOCATOR = "LINKTEXT";
	private static String XPATH_LOCATOR = "XPATH";
	private static String PARTIALLINKTEXT_LOCATOR = "PARTIALLINKTEXT";

	public static void initialize() {
		System.out.println("Inside Webdrive class");
		// System.setProperty("webdriver.chrome.driver",
		// System.getProperty("user.dir")+"//src/main/resources/chromedriver.exe");
		WebDriverManager.chromedriver().setup();
		driver = (WebDriver) new ChromeDriver();

	}

	/**
	 * launches the client
	 *
	 * @throws IOException
	 */
	public static void launchClient(String URL) throws Exception {
		driver.get(URL);
		driver.manage().window().maximize();

	}

	public static void scrollIntoView(String[] uiObject) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = findElement(uiObject);
		js.executeScript("arguments[0].scrollIntoView();", element);

	}

	public static WebElement findElement(String[] uiObject) throws NoSuchElementException {
		String locator = uiObject[0];
		String locatorValue = uiObject[1];
		WebElement element = null;
		if (locator.equalsIgnoreCase(ID_LOCATOR)) {
			element = driver.findElement(By.id(locatorValue));
			return element;
		}
		if (locator.equalsIgnoreCase(LINKTEXT_LOCATOR)) {
			element = driver.findElement(By.linkText(locatorValue));
			return element;
		}
		if (locator.equalsIgnoreCase(XPATH_LOCATOR)) {
			element = driver.findElement(By.xpath(locatorValue));
			return element;
		}
		if (locator.equalsIgnoreCase(PARTIALLINKTEXT_LOCATOR)) {
			element = driver.findElement(By.partialLinkText(locatorValue));
			return element;
		}
		return element;

	}

	public static boolean isDisplayed(String[] uiObject) {
		boolean displayed = false;
		String locator = uiObject[0];
		String locatorValue = uiObject[1];
		WebElement element;
		try {
			switch (locator) {
			case "LINKTEXT":
				element = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.presenceOfElementLocated(By.linkText(locatorValue)));
				break;
			case "ID":
				element = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.presenceOfElementLocated(By.id(locatorValue)));
				break;
			case "XPATH":
				element = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locatorValue)));
				break;
			case "PARTIALLINKTEXT":
				element = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.presenceOfElementLocated(By.partialLinkText(locatorValue)));
				break;
			default:
				System.out.println("PLEASE Provide correct locator");
				throw new Exception("Wrong or no Locator provided");
			}
			if (element.isEnabled()) {
				displayed = true;
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return displayed;
	}

	public static boolean isClickable(String[] uiObject) {
		boolean isclickable = false;
		String locator = uiObject[0];
		String locatorValue = uiObject[1];
		WebElement element;
		try {
			switch (locator) {
			case "LINKTEXT":
				element = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.elementToBeClickable(By.linkText(locatorValue)));
				break;

			case "ID":
				element = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.elementToBeClickable(By.id(locatorValue)));
				break;
			case "XPATH":
				element = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.elementToBeClickable(By.xpath(locatorValue)));
				break;
			case "PARTIALLINKTEXT":
				element = (new WebDriverWait(driver, 10))
						.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(locatorValue)));
				break;
			default:
				System.out.println("PLEASE Provide correct locator");
				throw new Exception("Wrong or no Locator provided");
			}
			if (element.isEnabled()) {
				isclickable = true;
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isclickable;

	}

	public static boolean click(String[] uiObject) throws Exception {
		if (isDisplayed(uiObject)) {
			WebElement element = findElement(uiObject);
			element.click();
			return true;
		} else {
			throw new Exception("cannnot click on object: " + uiObject);
		}

	}

	public static void acceptAlert() {
		driver.switchTo().alert().accept();
	}

	public static boolean waitTillExists(String[] uiObject) {
		boolean displayed = false;
		String locator = uiObject[0];
		String locatorValue = uiObject[1];
		try {
			if (locator.equalsIgnoreCase("ID")) {
				WebElement element = (new WebDriverWait(driver, 60))
						.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
				if (element.isDisplayed()) {
					displayed = true;
				}

			}
			if (locator.equalsIgnoreCase("XPATH")) {
				WebElement element = (new WebDriverWait(driver, 60))
						.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
				if (element.isEnabled()) {
					displayed = true;
				}
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return displayed;
	}

	public static void setText(String[] uiObject, String value) throws Exception {
		WebElement element = findElement(uiObject);
		element.click();
		element.sendKeys(value);
	}

	public static void waitForAlert() {
		int i = 0;
		while (i++ < 5) {
			try {
				Alert alert = driver.switchTo().alert();
				break;
			} catch (NoAlertPresentException e) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {

					e1.printStackTrace();
				}
				continue;
			}
		}
	}

	public static String getText(String[] uiObject) {
		WebElement element = findElement(uiObject);
		String text = element.getText();
		return text;
	}

	public static void wait(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void quit() {
		driver.quit();
	}
}