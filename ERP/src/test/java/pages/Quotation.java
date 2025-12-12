package pages;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;


public class Quotation {

	
	WebDriver driver;
	ExtentTest test2;
	public static String qt_no;

	
//	Object repository
	
	By quotation_valid_upto=By.id("expdate");
	By quo_no=By.id("quotation-no");
	By billing_address_dropdown=By.id("billingaddress");
	By description = By.xpath("//textarea[@rows='3' and contains(@class,'textarea')]");
	By item_description=By.xpath("(//textarea[contains(@class,'textarea')])[1]");
	By rate=By.xpath("(//input[contains(@class,'form-control') and contains(@class,'number') and @type='text'])[1]");
	By subsidy=By.xpath("/html/body/div[1]/div[1]/div[1]/form/div/div[2]/div/div/div[1]/div/div[16]/div/table/tbody/tr/td[4]/input");
	By quotation_save_btn=By.xpath("//button[.//i[contains(@class,'ri-save-line')]]");
	By quotation_confirm_btn=By.xpath("/html/body/div[21]/div/div[3]/button[1]");
	
	By edit_quotation=By.xpath("/html/body/div[1]/div[1]/div[1]/div/div[1]/div[2]/button[2]");
    
	By viewBtn = By.xpath("//a[.//i[contains(@class,'ri-eye-line')]]");

	By followupBtn = By.xpath("//button[contains(., 'Followup') and .//i[contains(@class,'ri-printer-line')]]");
	By followupStatus = By.cssSelector("select.form-control.d-flex");
	By qtn_description = By.id("short-description");
	By saveBtn = By.xpath("//button[@type='submit' and contains(normalize-space(.), 'Save')]");

		
	public Quotation(WebDriver driver, ExtentTest test2) {
		this.driver=driver;
		this.test2=test2;
		
	}
//	Methods
	public void quotation()throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;

		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(quotation_valid_upto));
		
		
		LocalDate today = LocalDate.now();

		// Get last day of current month
		LocalDate lastDay = today.withDayOfMonth(today.lengthOfMonth());

		// Format it as dd-MM-yyyy
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String formattedDate = lastDay.format(fmt);

		// Pass to the field
		driver.findElement(quotation_valid_upto).clear();
		driver.findElement(quotation_valid_upto).sendKeys(formattedDate);		
//		WebElement Billing_address=driver.findElement(billing_address_dropdown);
		
		WebElement Quo_no=driver.findElement(quo_no);
		Actions act=new Actions(driver);
        act.click(Quo_no).perform();
        act.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
        act.keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).perform();
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        qt_no = (String) clipboard.getData(DataFlavor.stringFlavor);

        System.out.println("Copied text: " + qt_no);

		
		if (qt_no.length()>0)
		{
			test2.log(Status.PASS, "Quotation no : "+qt_no);
		}
		else
		{
			test2.log(Status.FAIL, "The quotation number is not present");
		}
		
	
		
		Thread.sleep(2000);
		WebElement Description = driver.findElement(description);
	   ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", Description);

		Actions act_desc=new Actions(driver);
		act_desc.click(Description).perform();
		act_desc.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
		act_desc.keyDown(Keys.CONTROL).sendKeys("c").keyUp(Keys.CONTROL).perform();
		
		String desc_text = (String) clipboard.getData(DataFlavor.stringFlavor);
		

        System.out.println("Text in the textarea: " + desc_text);
		
		if(desc_text.length()>0)
		{
			test2.log(Status.INFO, "Description already entered : "+desc_text);
		}
		else
		{
			Description.sendKeys("test description");
			test2.log(Status.INFO, "The description field is empty and value passed");
		}
		
		
		Thread.sleep(2000);
		
//		WebElement Item_Description = driver.findElement(item_description);
//		String Item_desc_script = "return arguments[0].value;";
//        String Item_Desc = (String) js.executeScript(Item_desc_script, Item_Description);
//
//        System.out.println("Text in the textarea: " + Item_Desc);
//		
//		if(Item_Desc.length()>0)
//		{
//			test2.log(Status.INFO, "Item Description already entered : "+Item_Desc);
//		}
//		else
//		{
//			Item_Description.sendKeys("test description");
//			test2.log(Status.INFO, "The item description field is empty and value passed");
//		}

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", driver.findElement(rate));

		driver.findElement(rate).clear();
		driver.findElement(rate).sendKeys("210000");
		
		List<WebElement> Subsidy=driver.findElements(subsidy);
		
//		if(Subsidy.size()>0)
//		{
//			WebElement subsidy_field=Subsidy.get(0);
//			subsidy_field.sendKeys("78000");
//			test2.log(Status.PASS, "Subsidy field found and value passed");
//		}
//		else {
//			test2.log(Status.INFO, "The subsidy field is not present");
//		}
		
		js.executeScript("arguments[0].scrollIntoView();", driver.findElement(quotation_save_btn));
		Actions act1=new Actions(driver);
		act1.moveToElement(driver.findElement(quotation_save_btn)).click().perform();
		
		
		test2.log(Status.PASS, "Quotation created successfully");
		
		
	}
	
	public void edit_quotation() throws Exception {
		
		
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
	                
        wait.until(ExpectedConditions.visibilityOfElementLocated(edit_quotation)).click();
        
        Thread.sleep(2000);
        JavascriptExecutor js=(JavascriptExecutor)driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", driver.findElement(quotation_save_btn));
		Actions act=new Actions(driver);
		act.moveToElement(driver.findElement(quotation_save_btn)).click().perform();
		

		test2.log(Status.PASS, "Quotation edited successfully");
	}
	
	public void view_quotation()throws Exception {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(viewBtn));
		// Store the current tab
		String mainTab = driver.getWindowHandle();

		// Click the view button
		driver.findElement(viewBtn).click();

		// Wait for new tab to appear
		wait.until(driver -> driver.getWindowHandles().size() > 1);

		// Switch to new tab
		for (String tab : driver.getWindowHandles()) {
		    if (!tab.equals(mainTab)) {
		        driver.switchTo().window(tab);
		        break;
		    }
		}

		// Print success
		System.out.println("New tab opened successfully");

		// Hold for 3 seconds
		Thread.sleep(3000);

		// Close the new tab
		driver.close();

		// Switch back to main tab
		driver.switchTo().window(mainTab);
		System.out.println("Returned to main tab");

	}
	
	public void quotation_followup() throws Exception {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.visibilityOfElementLocated(followupBtn)).click();

		Thread.sleep(2000);

		WebElement dropdown = driver.findElement(followupStatus);
		Select select = new Select(dropdown);

		for (WebElement option : select.getOptions()) {
		    if (option.getText().contains("Won")) {
		        option.click();
		        break;
		    }
		}

		WebElement description_field = driver.findElement(qtn_description);
		description_field.sendKeys("Followup done");

		Thread.sleep(2000);
		driver.findElement(saveBtn).click();

		test2.log(Status.PASS, "Quotation followup saved successfully");
	}
}
