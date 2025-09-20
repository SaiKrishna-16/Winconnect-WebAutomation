package com.winconnect;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Baseclass 
{
    protected static WebDriver driver;

    public static void setupDriver() 
    {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void url() {
        driver.get("https://pre-main.d3534e457mjqbf.amplifyapp.com/login/");
    }

    public void login() {
      //  driver.findElement(By.id("email")).sendKeys("ssharma@winhomeoffice.com");
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("ssharma@winhomeoffice.com");
        driver.findElement(By.id("password")).sendKeys("Admin@123");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    public void logout() {
        driver.findElement(By.xpath("//button[normalize-space()='Logout']")).click();
    }
}
