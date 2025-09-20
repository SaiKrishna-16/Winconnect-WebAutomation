package com.winconnect;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class LoginTest extends Baseclass {
   
    @BeforeClass
    public void setUp() {
        Baseclass.setupDriver();
    }
   
   
    @Test
    public void CreateOrder() 
    {
        Homescreen home = new Homescreen(driver);
        home.url();
        home.login();
        home.clickExpandOption();
        home.clickOrdersScheduling();   

        OrdersScheduling order = new OrdersScheduling(driver);
        order.clickCreateOrderButton();
        order.clientTypeDropdown();
        order.clientContactdropdwon();
        order.clickSaveAndContinue();
    //  order.clickCancelButton();
   //    order.clickSelectPropertyAddress();
   //order.clickCreateAddressManuallyButton();

   // Declare and initialize property details
   String addressLine1 = "123 Main St";
   String city = "SampleCity";

   String zipCode = "12345";

   String purchasePrice = "500000";

   //order.enterPropertyDetails(addressLine1, city, zipCode,  purchasePrice);
 




    }

    // @AfterSuite
    // public void tearDown() {
    //     Baseclass app = new Baseclass();
    //     app.logout();
    //     app.driver.quit(); // Ensure the driver is closed after tests
    // }
}



