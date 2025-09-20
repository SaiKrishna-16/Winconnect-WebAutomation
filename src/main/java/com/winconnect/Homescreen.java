package com.winconnect;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Homescreen extends Baseclass 

{
   WebDriver driver;


    @FindBy(xpath = "//button[contains(@class, 'gray-200 rounded-full shadow-md z-10')]")
    private WebElement expandOption;

    @FindBy(xpath = "//span[normalize-space()='Orders & Scheduling']")
    private WebElement ordersScheduling;

    @FindBy(xpath = "(//button[@role='combobox'])[1]")
    private WebElement clientTypeDropdown;

    public Homescreen(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickExpandOption() {
        expandOption.click();
    }

    public void clickOrdersScheduling() {
        ordersScheduling.click();
    }

    public void selectClientType(String clientType) {
        clientTypeDropdown.sendKeys(clientType);
    }
}