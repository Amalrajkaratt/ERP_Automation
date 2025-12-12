package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class Login {

	WebDriver driver;
	ExtentTest test1;
	
//	Object repository
	By company_code=By.xpath("/html/body/div[1]/div[1]/form/div/div/div/div/div[2]/div[1]/input");
	By username=By.xpath("/html/body/div[1]/div[1]/form/div/div/div/div/div[2]/div[2]/input");
	By password=By.xpath("/html/body/div[1]/div[1]/form/div/div/div/div/div[2]/div[3]/div[1]/input");
	By signin_btn=By.xpath("//*[@id=\"app\"]/div[1]/form/div/div/div/div/div[2]/div[4]/button");
	
//	Constructor
	public Login(WebDriver driver, ExtentTest test1) {
		this.driver=driver;
		this.test1=test1;
	}
	
	
	public void login()throws Exception {
		
		Thread.sleep(2000);
		WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(company_code)).click();
		
		driver.findElement(company_code).sendKeys("MWTTEST");
		driver.findElement(username).sendKeys("admin");
		driver.findElement(password).sendKeys("123");
		
		test1.log(Status.PASS, "Company code : MWTTEST");
		test1.log(Status.PASS, "Username : Admin");
		test1.log(Status.PASS, "Password : 123");
		
		Thread.sleep(1000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(signin_btn));
				
		driver.findElement(signin_btn).click();
		
		Thread.sleep(3000);
//		Login ends

		
	}
	
}
