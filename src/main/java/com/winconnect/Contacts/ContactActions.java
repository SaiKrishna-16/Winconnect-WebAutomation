package com.winconnect.Contacts;

import static com.winconnect.Baseclass.waitAfterAction;

public class ContactActions {
    private final ContactPage contactPage;

    public ContactActions(ContactPage contactPage) {
        this.contactPage = contactPage;
    }

    public void createContact(String firstName, String lastName, String primaryEmail, String contactType) {
        contactPage.clickAddContact();
        waitAfterAction(3000);


        contactPage.enterFirstName(firstName);
        waitAfterAction(1000);

        contactPage.enterLastName(lastName);
        waitAfterAction(1000);

        contactPage.enterPrimaryEmail(primaryEmail);
        waitAfterAction(1000);

        contactPage.enterContactType(contactType);
        waitAfterAction(1000);
        
        contactPage.clickSaveContact();
        waitAfterAction(1000);
    }
}
