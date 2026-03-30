package com.winconnect.Contacts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.winconnect.Baseclass;

public class ContactPage {
    private WebDriver driver;

    @FindBy(xpath = "//button[normalize-space()='Add New Contact']")
    private WebElement addNewContact;

   @FindBy(name="firstName")
    private WebElement firstName;

    @FindBy(name="lastName")
    private WebElement lastName;

    @FindBy(name="primaryEmail")
    private WebElement primaryEmail;

    @FindBy(xpath="/html[1]/body[1]/div[3]/div[2]/form[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[4]/div[2]/div[1]/div[1]/div[1]/div[1]/button[1]")
    private WebElement contactTypedropdown;

    @FindBy(xpath="//button[@type='submit']")
    private WebElement saveContactButton;

    @FindBy(xpath="//button[normalize-space()='Cancel']")
    private WebElement cancelButton;


    

    public ContactPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickAddContact() {
        addNewContact.click();
    }   

    public void enterFirstName(String firstName) {
        this.firstName.sendKeys(firstName);
    }
    
    public void enterLastName(String lastName) {
        this.lastName.sendKeys(lastName);
    }

    public void enterPrimaryEmail(String primaryEmail) {
        this.primaryEmail.sendKeys(primaryEmail);
    }

    public void enterContactType(String contactType) {

        Baseclass.scrollBy(500, 0);
      
        
        WebElement dropdownElement = this.contactTypedropdown; // Replace with the actual locator for the dropdown
        Select select = new Select(dropdownElement);
        select.selectByVisibleText("Builder");
         

         try {
             Thread.sleep(1000); // Pause for 2 seconds to allow dropdown to render
         } catch (InterruptedException e) {
             Thread.currentThread().interrupt();
         }

    
    }

    public void clickSaveContact() {
        this.saveContactButton.click();
    }

    public void clickCancelButton() {
        this.cancelButton.click();
    }

}
