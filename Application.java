package tests;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Application {

	public static void main(String[] args) {
		WebDriver driver = null;

		try {
			// Step 1: Navigate to the FitPeo Homepage
			driver = new ChromeDriver();
			driver.manage().window().maximize(); // Maximize the browser window
			driver.get("https://www.fitpeo.com/home"); // Navigate to the target URL

			Thread.sleep(3000); // added wait to observe it on screen
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			// Step 2: Navigate to the Revenue Calculator Page
			WebElement revenueCalculator = driver.findElement(By.xpath("//div[text()='Revenue Calculator']"));
			Actions act = new Actions(driver);
			act.moveToElement(revenueCalculator).click().perform();

			// Step 3: Scroll Down to the Slider section:
			WebElement medicareSection = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//h4[text()='Medicare Eligible Patients']")));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView();", medicareSection);
			Thread.sleep(3000); // added wait to observe it on screen

			// Step 4: Adjust the Slider:
			WebElement sliderInput = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@data-index='0']")));
			act.dragAndDropBy(sliderInput, 94, 0).perform();
			Thread.sleep(3000);

			// Step 5: Update the Text Field:(560)
			WebElement textbox = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='number']")));
			act.sendKeys(textbox, Keys.BACK_SPACE.toString().repeat(3)).perform(); // Clear the textbox
			textbox.sendKeys("560"); // Enter the new value into the textbox
			Thread.sleep(3000); // added wait to observe it on screen

			// Step 6:Validate Slider Value:
			String currentTextboxValue = textbox.getAttribute("value");

			System.out.println("Current value in the textbox: " + currentTextboxValue);
			if (currentTextboxValue.equals("560")) {
				System.out.println(
						"The slider's value is correctly updated to match the textbox value.");
			} else {
				System.out.println("The slider's value does not match the textbox value.");
			}
			//Step 7:Select CPT Codes:
			WebElement moveToCheckBoxSection = wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='MuiBox-root css-1p19z09']")));
			js.executeScript("arguments[0].scrollIntoView();", moveToCheckBoxSection);

			// Click the checkboxes based on the displayed text values
			WebElement checkbox1 = driver
					.findElement(By.xpath("//label[.//span[contains(text(), '57')]]//input[@type='checkbox']"));
			checkbox1.click();

			WebElement checkbox2 = driver
					.findElement(By.xpath("//label[.//span[contains(text(), '19.19')]]//input[@type='checkbox']"));
			checkbox2.click();

			WebElement checkbox3 = driver
					.findElement(By.xpath("//label[.//span[contains(text(), '63')]]//input[@type='checkbox']"));
			checkbox3.click();

			WebElement checkbox4 = driver
					.findElement(By.xpath("//label[.//span[contains(text(), '15')]]//input[@type='checkbox']"));
			checkbox4.click();

			//Step 8:Validate Total Recurring Reimbursement:
			WebElement totalValueElement = wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//header//p[contains(text(),'Recurring')]/p")));
			String totalValue = totalValueElement.getText();
			System.out.println("Total Recurring Reimbursement for all Patients Per Month: " + totalValue);
			Thread.sleep(3000); // added wait to observe it on screen

		} catch (TimeoutException e) {
			System.out.println("Error: The element did not appear in time.");
		} catch (Exception e) {
			System.out.println("An unexpected error occurred: " + e.getMessage());
		} finally {
			// Ensure the browser is closed properly after execution
			if (driver != null) {
				driver.quit(); // Close the browser
			}
		}
	}

}
