package com.winconnect;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.winconnect.orderScheduling.BillingTab;
import com.winconnect.orderScheduling.ClientTab;
import com.winconnect.orderScheduling.PropertiesTab;
import com.winconnect.orderScheduling.ServicesTab;

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
        // home.clickExpandOption();
        // home.clickOrdersScheduling();   

         ClientTab order = new ClientTab(driver);
        
        order.clickCreateOrderButton();
        order.clientTypeDropdown();
        order.clientContactdropdwon();
        order.clickSaveAndContinue();
        waitAfterAction(1000);

       PropertiesTab propertyTab = new PropertiesTab(driver);
       propertyTab.enterInspectionAddress();
       order.clickSaveAndContinue();
// waitAfterAction(5000);
//     order.Editorderbutton();



   ServicesTab service = new ServicesTab(driver); 
    
   waitAfterAction(1000);
  // service.clickServicesTab();
   waitAfterAction(2000);
  // service.closecartviewO();
   Baseclass.scrollBy(0, 200);
    service.HomeInspection();       
   
    waitAfterAction(4000);
     Baseclass.scrollBy(0, 1000);
    service.selectdate();
    waitAfterAction(1000);
    Baseclass.scrollToBottom();
    service.Selectinspector();
    order.clickSaveAndContinue();

    BillingTab billing = new BillingTab(driver);
    waitAfterAction(2000);
    billing.clickReviewOrderButton();
    waitAfterAction(5000);
    billing.clickConfirmandScheduleButton();








    }

    // @AfterSuite
    // public void tearDown() {
    //     Baseclass app = new Baseclass();
    //     app.logout();
    //     app.driver.quit(); // Ensure the driver is closed after tests
    // }
}



