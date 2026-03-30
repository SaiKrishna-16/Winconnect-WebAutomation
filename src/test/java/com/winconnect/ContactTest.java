package com.winconnect;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.winconnect.Contacts.ContactActions;
import com.winconnect.Contacts.ContactPage;

public class ContactTest extends Baseclass {

    @BeforeClass
    public void setup() {
        Baseclass.setupDriver();
        Baseclass base = new Baseclass();
        base.url();
        base.login();
    }

    @Test
    public void testCreateContact() {
        Homescreen homeScreen = new Homescreen(driver);
        homeScreen.clickExpandOption(); 
         waitAfterAction(1000);

        homeScreen.clickAccountsAndContacts();
         waitAfterAction(1000);
       

        ContactPage contactPage = new ContactPage(driver);
        ContactActions contactActions = new ContactActions(contactPage);

        contactActions.createContact("John", "Doe", "john.doe@example1.com", "1234567890");
        


    //     String successText = contactPage.getSuccessText();
    //     Assert.assertTrue(successText.toLowerCase().contains("success"), "Expected success message on contact creation");

    //     waitAfterAction(1000);
    // 
    }
}
