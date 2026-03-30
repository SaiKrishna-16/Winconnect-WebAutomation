package com.winconnect.orderScheduling;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.winconnect.Baseclass.waitAfterAction;

public class ClientTab 
{
   WebDriver driver;


    @FindBy(xpath = "//button[normalize-space()='Create Order']")
    private WebElement createOrderButton;

    @FindBy(xpath="(//button[@role='combobox'][1])[1]")
    private WebElement clientTypeDropdown;


    @FindBy(xpath="(//button[@role='combobox'][1])[2]")
    private WebElement clientContact;

    @FindBy(xpath = "//div[@class='flex items-center space-x-3 px-3 py-2 cursor-pointer border-b border-gray-100 transition-colors hover:primary/10'][2]")
    private WebElement selectclientcontact;

    @FindBy(xpath="//button[normalize-space()='Save and Continue to Next Step']")
    private WebElement saveandcontinue;

    @FindBy(xpath="//button[normalize-space()='Cancel']")
    private WebElement cancelButton;

     @FindBy(xpath="//tbody/tr[4]//button[2]")
    private WebElement Editorderbutton;







    public ClientTab(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickCreateOrderButton() {
         waitAfterAction(5000);
        createOrderButton.click();
    }
    public void clientTypeDropdown() {
        clientTypeDropdown.click();
         
         try {
             Thread.sleep(1000); // Pause for 2 seconds to allow dropdown to render
         } catch (InterruptedException e) {
             Thread.currentThread().interrupt();
         }
//Select dropdown = ;
WebElement ClientTypedropdownvalue = driver
    .findElement(By.xpath("//div[@role='option' and .//span[text()='Buyer']]"));

// Or select by value attribute
ClientTypedropdownvalue.click();;

// Or select by index (0-based)
//dropdown.selectByIndex(1);
    }


    public void clientContactdropdwon() {
        clientContact.click();
        selectclientcontact.click();

    }

    public void clickSaveAndContinue() {
        try {
            Thread.sleep(2000); // Pause for 2 seconds to allow dropdown to render
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        saveandcontinue.click();
    }

    public void clickCancelButton() {
        cancelButton.click();
    }       

    public void Editorderbutton() {
        Editorderbutton.click();
    }

    

}
