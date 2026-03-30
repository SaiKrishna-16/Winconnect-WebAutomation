package com.winconnect;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Baseclass 
{
    protected static WebDriver driver;
  

    public static void setupDriver() 
    {
        driver = new ChromeDriver();
        driver.manage()
            .timeouts()
            .implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    // Helper method to wait after actions
    public static void waitAfterAction(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void url() {
      //  driver.get("https://pre-main.d3534e457mjqbf.amplifyapp.com/login/");
        driver.get("https://staging.d3eops0zc6p92l.amplifyapp.com/login/");
        waitAfterAction(2000); // Example: wait 2 seconds after navigation
    }

    public void login() {

        // corporate login
        // driver.findElement(By.xpath("//input[@type='email']")).sendKeys("admin@wini.com");
        // waitAfterAction(500); // Example: wait 0.5 seconds after entering email
        // driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Admin@123");
        // waitAfterAction(500); // Example: wait 0.5 seconds after entering password
        // driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
        // waitAfterAction(1000); // Example: wait 1 second after clicking login
        // driver.findElement(By.xpath("//button[@role='combobox' and .//span[text()='Select Strategic Partner']]")).click();
        // driver.findElement(By.xpath("//input[@placeholder='Search by company name or company key...']")).sendKeys("099");
        // waitAfterAction(1000); // Example: wait 1 second after clicking login
        // driver.findElement(By.xpath("//span[text()='win-e099']")).click();
        
        //SP Login
        driver.findElement(By.xpath("//input[1]")).sendKeys("0");
        waitAfterAction(500); // Example: wait 0.5 seconds after entering email

        driver.findElement(By.xpath("//input[2]")).sendKeys("9");
        waitAfterAction(500); // Example: wait 0.5 seconds after entering password
    
        driver.findElement(By.xpath("//input[3]")).sendKeys("9");
        waitAfterAction(500); // Example: wait 0.5 seconds after entering password


         //driver.findElement(By.xpath("//input[@type='email']")).sendKeys("admin@wini.com");
         driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/form[1]/div[1]/div[2]/input[1]")).sendKeys("rvierra");
        waitAfterAction(500); // Example: wait 0.5 seconds after entering email
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Admin@123");
        waitAfterAction(500); // Example: wait 0.5 seconds after entering password
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();

        System.err.println("Login successful");

        // POPUP

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

    try {
        WebElement gotIt = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[text()='Got It!']")));
        gotIt.click();

        WebElement scrollInsidePopUp = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//li[text()='Corrected the inspection location display in map view.']")));

        ((JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block: 'center'});", scrollInsidePopUp);

        WebElement secondPopup = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@class='flex justify-center']/child::button")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", secondPopup);

        System.out.println("Popup handled");

    } catch (Exception e) {
        System.out.println("Popup not displayed");
    }

    }

    public void logout() {
        driver.findElement(By.xpath("//button[normalize-space()='Logout']")).click();
        waitAfterAction(1000); // Example: wait 1 second after logout
    }

    // Helper method to scroll the page by pixels
    public static void scrollBy(int x, int y) {
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(arguments[0], arguments[1]);", x, y);
        }
    }

    // Helper method to scroll to the bottom of the page
    public static void scrollToBottom() {
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        }
    }

    // Helper method to scroll to a specific element
    public static void scrollToElement(By locator) {
        if (driver instanceof JavascriptExecutor) {
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);",
                driver.findElement(locator)
            );
        }
    }
}
