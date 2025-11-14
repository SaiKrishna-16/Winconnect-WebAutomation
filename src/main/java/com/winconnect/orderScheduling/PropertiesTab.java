package com.winconnect.orderScheduling;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.winconnect.Baseclass;
import static com.winconnect.Baseclass.waitAfterAction;

public class PropertiesTab
{
   WebDriver driver;

    // Property Details Tab 

    @FindBy(xpath="//input[@placeholder='Search address or create manually']")
    private WebElement SearchpropertyAddress;

    @FindBy(xpath="//span[normalize-space()='123 Main Street, San Francisco, California, USA']")
    private WebElement selectpropertyaddress;

    @FindBy(xpath="//button[normalize-space()='Create Address Manually']")
    private WebElement createAddressManuallyButton;

    @FindBy(xpath="//input[@id='addressLine1']")
    private WebElement addressLine1Input1;

   @FindBy(xpath="//input[@id='city']")
    private WebElement cityInput;

    @FindBy(xpath="//button[@role='combobox'  and .//span[text()='Select']]")
    private WebElement stateInput;

    @FindBy(xpath="//div[@role='option' and .//span[text()='CA']]")
    private WebElement stateInputValue;

    @FindBy(xpath="//input[@id='zipCode']")
    private WebElement zipCodeInput;

    @FindBy(xpath="//input[@id='country']") 
    private WebElement countryInput;

    @FindBy(xpath="//div[@role='option' and .//span[text()='USA']]")
    private WebElement countryInputValue;

    @FindBy(xpath="//input[@id='propertyPurchasePrice']")
    private WebElement propertyPurchase;

    @FindBy(xpath="//button[normalize-space()='Save Address']")
    private WebElement saveAdress;

    public PropertiesTab(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickCreateAddressManuallyButton() {
        createAddressManuallyButton.click();
    }

    
    public void enterPropertyDetails() {
        addressLine1Input1.sendKeys("123 Main Street");
        cityInput.sendKeys("Chicago");
        Baseclass.waitAfterAction(10000);
        stateInput.click();
        stateInputValue.click();
        zipCodeInput.sendKeys("60601");
        // countryInput.click();
        // countryInputValue.click();
        propertyPurchase.sendKeys("100000");
        saveAdress.click();
        
    }

    public void enterInspectionAddress() {
           waitAfterAction(1000); 
        SearchpropertyAddress.click();
        SearchpropertyAddress.sendKeys("123 Main Street CA");
         waitAfterAction(1000); 
        selectpropertyaddress.click();
        waitAfterAction(5000); // Example: wait 1 second after clicking login
       
    }

    

}



