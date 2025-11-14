package com.winconnect;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

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
        driver.get("https://staging.d3eops0zc6p92l.amplifyapp.com/admin/login/");
        waitAfterAction(2000); // Example: wait 2 seconds after navigation
    }

    public void login() {

        // corporate login
        driver.findElement(By.xpath("//input[@type='email']")).sendKeys("admin@wini.com");
        waitAfterAction(500); // Example: wait 0.5 seconds after entering email
        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Admin@123");
        waitAfterAction(500); // Example: wait 0.5 seconds after entering password
        driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
        waitAfterAction(1000); // Example: wait 1 second after clicking login
        driver.findElement(By.xpath("//button[@role='combobox' and .//span[text()='Select Strategic Partner']]")).click();
        driver.findElement(By.xpath("//input[@placeholder='Search by company name or company key...']")).sendKeys("099");
        waitAfterAction(1000); // Example: wait 1 second after clicking login
        driver.findElement(By.xpath("//span[text()='win-e099']")).click();
        
        //SP Login
        //  driver.findElement(By.xpath("//input[@type='email']")).sendKeys("admin@wini.com");
        // waitAfterAction(500); // Example: wait 0.5 seconds after entering email
        // driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Admin@123");
        // waitAfterAction(500); // Example: wait 0.5 seconds after entering password
        // driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();

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
