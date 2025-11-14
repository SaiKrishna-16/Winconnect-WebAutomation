package com.winconnect.orderScheduling;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ServicesTab
{
   WebDriver driver;

    // Services Tab 

    @FindBy(xpath="//span[normalize-space()='Services']")
    private WebElement ServicesTab;

    @FindBy(xpath="//div[contains(@role,'radiogroup')]//div//h4[normalize-space()='Home Inspection']")
    private WebElement HomeInspection;

    @FindBy(xpath="//body[1]/div[1]/div[2]/div[2]/main[1]/div[1]/div[1]/form[1]/div[1]/div[4]/div[1]/div[2]/div[1]/div[5]/div[2]/div[2]/div[8]/div[1]")
    private WebElement BoatLiftandDockInspection;

    @FindBy(xpath="//body[1]/div[1]/div[2]/div[2]/main[1]/div[1]/div[1]/form[1]/div[1]/div[4]/div[1]/div[2]/div[1]/div[5]/div[3]/div[2]/div[2]/div[1]")
    private WebElement MoldTestInspection;

    @FindBy(xpath="//button[normalize-space()='Pick a date']")
    private WebElement pickadate;

    @FindBy(xpath="//button[normalize-space()='24']")
    private WebElement selectdate;

    @FindBy(xpath="//button[normalize-space()='Pick a time']")
    private WebElement pickatime;

    @FindBy(xpath="//div[normalize-space()='5:30 AM']")
    private WebElement selecttime;

    @FindBy(xpath="//button[contains(text(),'✕')]")
    private WebElement closecartview;

    @FindBy(xpath="//body[1]/div[1]/div[2]/div[2]/main[1]/div[1]/div[1]/form[1]/div[1]/div[4]/div[1]/div[2]/div[1]/div[11]/div[2]/div[2]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[2]/div[1]/button[1]")
    private WebElement Selectinspector;

    @FindBy(xpath="//button[normalize-space()='Review Order']")
    private WebElement ReviewOrderButton;

   






    

    




  

    public ServicesTab(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickServicesTab() {
        ServicesTab.click();
    }

    public void HomeInspection() {
        HomeInspection.click();
    }
    public void BoatLiftandDockInspection() {
        BoatLiftandDockInspection.click();
    }   

    public void MoldTestInspection() {
        MoldTestInspection.click();
    }

    public void pickadate() {
        pickadate.click();      
    }

    public void selectdate() {

        pickadate.click();
        selectdate.click();  
        pickatime.click();
        selecttime.click();   
    }       

     public void closecartviewO() {
        closecartview.click();
    }   

    public void Selectinspector() {
        Selectinspector.click();
    }   

public void ReviewOrderButton() {
        ReviewOrderButton.click();
    }

    


// 1. Open the calendar if needed (adjust the locator as per your UI)
// driver.findElement(By.xpath("//button[@aria-label='Open calendar']")).click();

// 2. (Optional) Navigate to the correct month
// driver.findElement(By.xpath("//button[@name='next-month']")).click(); // for next month
// driver.findElement(By.xpath("//button[@name='previous-month']")).click(); // for previous month

// 3. Select the desired day, e.g., 25
// driver.findElement(By.xpath("//button[@name='day' and text()='25']")).click();
    

}



