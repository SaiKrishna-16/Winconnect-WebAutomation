package com.winconnect;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class OrdersScheduling 
{
   WebDriver driver;


    @FindBy(xpath = "//button[normalize-space()='Create Order']")
    private WebElement createOrderButton;

    @FindBy(xpath="(//button[@role='combobox'][1])[1]")
    private WebElement clientTypeDropdown;


    @FindBy(xpath="(//button[@role='combobox'][1])[2]")
    private WebElement clientContact;

    @FindBy(xpath = "//div[@class='flex items-center space-x-3 px-3 py-2 cursor-pointer border-b border-gray-100 transition-colors hover:primary/10'][2]")
    private Select selectclientcontact;

    @FindBy(xpath="//button[normalize-space()='Save and Continue to Next Step']")
    private WebElement saveandcontinue;

    @FindBy(xpath="//button[normalize-space()='Cancel']")
    private WebElement cancelButton;


    // Property Details Tab 

    @FindBy(xpath="//input[@placeholder='Search address to see suggestions, or fill out the form manually']")
    private WebElement SearchpropertyAddress;

    @FindBy(xpath="//span[normalize-space()='Cannery Row, Monterey, CA, USA']")
    private WebElement selectpropertyaddress;

    @FindBy(xpath="//button[normalize-space()='Create Address Manually']")
    private WebElement createAddressManuallyButton;

    @FindBy(xpath="//input[@id='addressLine1']")
    private WebElement addressLine1Input1;

   @FindBy(xpath="//input[@id='city']")
    private WebElement cityInput;

    @FindBy(xpath="")
    private WebElement stateInput;

    @FindBy(xpath="//input[@id='zipCode']")
    private WebElement zipCodeInput;

    @FindBy(xpath="//input[@id='country']") 
    private WebElement countryInput;

    @FindBy(xpath="//input[@id='propertyPurchasePrice']")
    private WebElement propertyPurchase;

    @FindBy(xpath="//button[normalize-space()='Save Address']")
    private WebElement saveAdress;


















    public OrdersScheduling(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickCreateOrderButton() {
        createOrderButton.click();
    }

    public void clientTypeDropdown() {
        clientTypeDropdown.click();
         // Assuming dropdownElement is your <select> WebElement
         // XPATH = "//select//option[@value='BUYER']";

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

    public void clickSelectPropertyAddress() {
        SearchpropertyAddress.sendKeys("cannery");
        selectpropertyaddress.click();
    }

    public void clickCreateAddressManuallyButton() {
        createAddressManuallyButton.click();
    }
    public void enterPropertyDetails(String addressLine1, String city,  String zipCode,  String purchasePrice) {
        addressLine1Input1.sendKeys("123 Main Street");
        cityInput.sendKeys("Chicago");
       // stateInput.sendKeys(state);
        zipCodeInput.sendKeys("60601");
      //  countryInput.sendKeys(country);
        propertyPurchase.sendKeys("100000");
        saveAdress.click();
    }


    
    

   

    

}
