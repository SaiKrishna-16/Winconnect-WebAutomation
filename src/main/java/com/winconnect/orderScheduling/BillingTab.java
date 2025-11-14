package com.winconnect.orderScheduling;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BillingTab 
{
  WebDriver driver;

  @FindBy(xpath="//button[normalize-space()='Review Order']")
    private WebElement ReviewOrderButton;

    @FindBy(xpath="//button[normalize-space()='Confirm & Schedule']")
    private WebElement ConfirmandScheduleButton;

     public BillingTab(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickReviewOrderButton() 
    {
        ReviewOrderButton.click();
    }
    public void clickConfirmandScheduleButton() 
    {
        ConfirmandScheduleButton.click();
    }   

}
