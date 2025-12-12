package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class AmazonTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(50));
    }

    @Test
    public void testAmazonBasicsSearchAndAddToCart() {
        // Step 1: Navigate to Amazon
        driver.get("https://www.amazon.com/");

        // Step 2: Search for "amazon basics"
        WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
        searchBox.sendKeys("amazon basics");
        searchBox.submit();

        // Step 3: Verify results header
        WebElement resultsHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//span[contains(text(), 'results for \"amazon basics\"')]")
        ));
        Assert.assertTrue(resultsHeader.isDisplayed(), "Search results header not visible");

        // Step 4: Select Amazon Brands filter
        WebElement brandsFilter = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//li[@aria-label='Amazon Brands']//i")
        ));
        brandsFilter.click();

        // Step 5: Select the specific product
        WebElement productLink = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[contains(text(), 'Amazon Basics Freezer Gallon Bags, 90 Count (Previously Solimo)')]")
        ));
        productLink.click();

        // Step 6: Verify product page
        WebElement productTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productTitle")));
        Assert.assertTrue(productTitle.getText().contains("Freezer Gallon Bags"), "Not on product page");

        // Step 7: Verify size selection
        WebElement sizeSelected = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//span[contains(text(), 'Gallon (90 Count)') and contains(@class, 'selection')]")
        ));
        Assert.assertTrue(sizeSelected.isDisplayed(), "Correct size not selected");

        // Step 8: Add to cart
        WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
        addToCartBtn.click();

        // Step 9: Verify item added
        WebElement addedConfirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[@id='NATC_SMART_WAGON_CONF_MSG_SUCCESS']//span")
        ));
        Assert.assertTrue(addedConfirmation.getText().contains("Added to Cart"), "Add to cart failed");

        // Step 10: Verify cart subtotal
        WebElement cartSubtotal = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//span[contains(text(), 'Subtotal (1 item)')]")
        ));
        Assert.assertTrue(cartSubtotal.getText().contains("1 item"), "Cart count mismatch");

        System.out.println("All steps completed successfully!");
    }


}