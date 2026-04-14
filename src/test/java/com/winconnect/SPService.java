package com.winconnect;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v123.backgroundservice.model.ServiceName;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SPService {
    public WebDriver driver;
    WebDriverWait wait;

    @BeforeSuite
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://staging.d3eops0zc6p92l.amplifyapp.com/login");

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    @BeforeClass
    public void preConditionFlow() {

        // LOGIN
        driver.findElement(By.xpath("(//input[@type='number'])[1]")).sendKeys("0");
        driver.findElement(By.xpath("(//input[@type='number'])[2]")).sendKeys("9");
        driver.findElement(By.xpath("(//input[@type='number'])[3]")).sendKeys("9");

        driver.findElement(By.xpath("//label[text()='Enter Username/Email']/following-sibling::input"))
                .sendKeys("rvierra");

        driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Admin@123");

        driver.findElement(By.xpath("//button[text()='Login']")).click();

        System.out.println("Login Success");

        // POPUP
        try {
            WebElement gotIt = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[text()='Got It!']")));
            gotIt.click();

            WebElement scrollInsidePopUp = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//li[text()='Corrected the inspection location display in map view.']")));

            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView({block: 'center'});", scrollInsidePopUp);

            WebElement secondPopup = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class='flex justify-center']/child::button")));

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", secondPopup);

            System.out.println("Popup handled");

        } catch (Exception e) {
            System.out.println("Popup not displayed");
        }

        // NAVIGATION

        WebElement serviceManagement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='Service Management']/parent::button")));

        serviceManagement.click();

        WebElement serviceCatalog = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[text()='Service Catalog']/parent::a")));

        serviceCatalog.click();

        // State Filter
        WebElement serviceStateFilterDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@data-testid='service-state-filter-trigger']")));
        serviceStateFilterDropdown.click();

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@role='option'][.//span[text()='CA']]")));
        option.click();
    }

    @Test(priority = 1)
    public void verifyTheStatusFilterIsActiveByDefault() {

        String serviceStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@data-testid='service-status-filter-trigger']"))).getText();

        try {

            Assert.assertEquals(serviceStatus, "Active");

            System.out.println("\u001B[32mService Status is Active\u001B[0m");

        } catch (AssertionError e) {

            System.out.println("\u001B[31mService Status is not Active\u001B[0m");
            throw e;
        }

    }

    @Test(priority = 2)
    public void serviceEditValueValidations() throws InterruptedException {

        Thread.sleep(5000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // ---------- LOCATORS ----------
        By editBtnLocator = By.xpath("(//button[@data-testid='service-edit-btn'])[1]");
        By serviceNameLocator = By
                .xpath("//button[@data-testid='service-dialog-serviceName-trigger']/span/child::div/span");
        By descriptionLocator = By.xpath("//input[@name='description']");
        By listPriceLocator = By.xpath("//input[@data-testid='service-dialog-listPrice-input']");
        By addOnPriceLocator = By.xpath("//input[@data-testid='service-dialog-addOnPrice-input']");
        By durationLocator = By.xpath("//input[@data-testid='service-dialog-duration-input']");
        By standaloneLocator = By.xpath("//label[text()='Standalone']/preceding-sibling::button");
        By thirdPartyLocator = By.xpath("//label[text()='Third Party']/preceding-sibling::button");
        By onlineVisibilityLocator = By.xpath("//label[text()='Online Visibility']/preceding-sibling::button");
        By licenseRequiredLocator = By.xpath("//label[text()='License Required']/following-sibling::input");
        By primaryServiceLocator = By.xpath("//label[text()='Primary Service']/following-sibling::input");
        By saveBtnLocator = By.xpath("//button[@type='submit']");

        // ---------- CLICK EDIT ----------
        WebElement editBtn = wait.until(ExpectedConditions.elementToBeClickable(editBtnLocator));
        editBtn.click();

        // ---------- GET SERVICE NAME ----------
        String serviceName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(serviceNameLocator)).getText();

        // ---------- GET DESCRIPTION ----------
        WebElement descriptionField = wait.until(
                ExpectedConditions.elementToBeClickable(descriptionLocator));

        descriptionField.sendKeys("Test Service : " + serviceName);

        // ---------- UPDATE LIST PRICE ----------
        WebElement listPriceField = wait.until(
                ExpectedConditions.elementToBeClickable(listPriceLocator));
        listPriceField.click();
        listPriceField.sendKeys(Keys.chord(Keys.COMMAND, "a"));
        listPriceField.sendKeys("720");
        String listPrice = listPriceField.getAttribute("value");

        // ---------- UPDATE ADD ON PRICE ----------
        WebElement addOnPriceField = wait.until(
                ExpectedConditions.elementToBeClickable(addOnPriceLocator));
        addOnPriceField.click();
        addOnPriceField.sendKeys(Keys.chord(Keys.COMMAND, "a"));
        addOnPriceField.sendKeys("342");
        String addOnPrice = addOnPriceField.getAttribute("value");

        // ---------- UPDATE DURATION ----------
        WebElement durationField = wait.until(
                ExpectedConditions.elementToBeClickable(durationLocator));
        durationField.click();
        durationField.sendKeys(Keys.chord(Keys.COMMAND, "a"));
        durationField.sendKeys("240");
        String duration = durationField.getAttribute("value");

        // ---------- STANDALONE TOGGLE ----------
        WebElement standaloneToggle = wait.until(
                ExpectedConditions.elementToBeClickable(standaloneLocator));

        String standaloneState = standaloneToggle.getAttribute("data-state");
        String standaloneValue;

        if (standaloneState.equalsIgnoreCase("checked")) {

            standaloneToggle.click(); // turning OFF
            standaloneValue = "No";

        } else {

            standaloneToggle.click(); // turning ON
            standaloneValue = "Yes";
        }
        // ---------- THIRD PARTY TOGGLE ----------
        WebElement thirdPartyToggle = wait.until(
                ExpectedConditions.elementToBeClickable(thirdPartyLocator));

        String thirdPartyState = thirdPartyToggle.getAttribute("data-state");
        String thirdPartyValue;

        if (thirdPartyState.equalsIgnoreCase("checked")) {

            thirdPartyToggle.click();
            thirdPartyValue = "No";

        } else {

            thirdPartyToggle.click();
            thirdPartyValue = "Yes";
        }

        // ---------- ONLINE VISIBILITY TOGGLE ----------
        WebElement onlineToggle = wait.until(
                ExpectedConditions.elementToBeClickable(onlineVisibilityLocator));

        String onlineState = onlineToggle.getAttribute("data-state");
        String onlineVisibilityValue;

        if (onlineState.equalsIgnoreCase("checked")) {

            onlineToggle.click();
            onlineVisibilityValue = "No";

        } else {

            onlineToggle.click();
            onlineVisibilityValue = "Yes";
        }

        // ---------- LICENSE REQUIRED ----------
        WebElement licenseField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(licenseRequiredLocator));

        String licenseRequired = licenseField.getAttribute("value");

        // ---------- PRIMARY SERVICE ----------
        WebElement primaryServiceField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(primaryServiceLocator));

        String primaryService = primaryServiceField.getAttribute("value");

        // ---------- CLICK SAVE ----------
        WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(saveBtnLocator));
        saveBtn.click();

        // ---------- WAIT FOR TABLE LOAD ----------
        Thread.sleep(4000);

        // ---------- TABLE LOCATORS USING SERVICE NAME ----------
        By serviceNameTableLocator = By.xpath("//div[text()='" + serviceName + "']");
        By listPriceTableLocator = By
                .xpath("(//div[text()='" + serviceName + "']/parent::td/following-sibling::td//div)[2]");
        By addOnPriceTableLocator = By
                .xpath("(//div[text()='" + serviceName + "']/parent::td/following-sibling::td//div)[3]");
        By durationTableLocator = By
                .xpath("(//div[text()='" + serviceName + "']/parent::td/following-sibling::td//span)[2]");
        By licRequiredTableLocator = By
                .xpath("(//div[text()='" + serviceName + "']/parent::td/following-sibling::td//span)[1]");
        By thirdPartyTableLocator = By
                .xpath("(//div[text()='" + serviceName + "']/parent::td/following-sibling::td//div)[4]");
        By primaryServiceTableLocator = By
                .xpath("(//div[text()='" + serviceName + "']/parent::td/following-sibling::td//span)[3]");
        By onlineVisibilityTableLocator = By
                .xpath("//div[text()='" + serviceName + "']/parent::td/following-sibling::td[9]//div");
        By standaloneTableLocator = By
                .xpath("(//div[text()='" + serviceName + "']/parent::td/following-sibling::td/div)[6]");

        // ---------- FETCH TABLE VALUES ----------
        String serviceNameTable = wait.until(ExpectedConditions.visibilityOfElementLocated(serviceNameTableLocator))
                .getText();
        String listPriceTable = driver.findElement(listPriceTableLocator).getText();
        String addOnPriceTable = driver.findElement(addOnPriceTableLocator).getText();
        String durationTable = driver.findElement(durationTableLocator).getText();
        String licRequiredTable = driver.findElement(licRequiredTableLocator).getText();
        String thirdPartyTable = driver.findElement(thirdPartyTableLocator).getText();
        String primaryServiceTable = driver.findElement(primaryServiceTableLocator).getText();
        String onlineVisibilityTable = driver.findElement(onlineVisibilityTableLocator).getText();
        String standaloneTable = driver.findElement(standaloneTableLocator).getText();

        // System.out.println("Service Name: " + serviceNameTable);
        // System.out.println("List Price: " + listPriceTable);
        // System.out.println("Add-On Price: " + addOnPriceTable);
        // System.out.println("Duration: " + durationTable);
        // System.out.println("License Required: " + licRequiredTable);
        // System.out.println("Third Party: " + thirdPartyTable);
        // System.out.println("Primary Service: " + primaryServiceTable);
        // System.out.println("Online Visibility: " + onlineVisibilityTable);
        // System.out.println("Standalone: " + standaloneTable);

        // ---------- NORMALIZE PRICE ----------
        double popupListPrice = Double.parseDouble(listPrice);
        double tableListPrice = Double.parseDouble(listPriceTable.replace("$", "").trim());

        double popupAddOn = Double.parseDouble(addOnPrice);
        double tableAddOn = Double.parseDouble(addOnPriceTable.replace("$", "").trim());

        // ---------- DURATION CONVERSION ----------
        int popupDurationMinutes = Integer.parseInt(duration);

        // Split duration by space
        String[] durationParts = durationTable.split(" ");

        // Initialize hours and minutes
        int hours = 0;
        int minutes = 0;

        for (String part : durationParts) {
            if (part.endsWith("h")) {
                hours = Integer.parseInt(part.replace("h", ""));
            } else if (part.endsWith("m")) {
                minutes = Integer.parseInt(part.replace("m", ""));
            }
        }

        // Convert total duration to minutes
        int tableDurationMinutes = (hours * 60) + minutes;

        // Validation
        if (popupDurationMinutes == tableDurationMinutes) {
            System.out.println("Duration matches: " + tableDurationMinutes + " mins");
        } else {
            System.out
                    .println("Duration mismatch! Popup: " + popupDurationMinutes + " | Table: " + tableDurationMinutes);
        }

        // ---------- VALIDATION RESULTS ----------
        boolean allValidationsPassed = true;

        System.err.println("-------------- Service Table Validation --------------");

        // ---------- SERVICE NAME ----------
        if (serviceNameTable.equals(serviceName)) {
            System.out.println("\u001B[32mService Name Validation Passed\u001B[0m");
        } else {
            System.out.println("\u001B[31mService Name Validation Failed\u001B[0m");
            allValidationsPassed = false;
        }
        // ---------- LIST PRICE ----------
        if (tableListPrice == popupListPrice) {
            System.out.println("\u001B[32mList Price Validation Passed\u001B[0m");
        } else {
            System.out.println("\u001B[31mList Price Validation Failed\u001B[0m");
            allValidationsPassed = false;
        }

        // Exact validation for AddOn Price
        if (tableAddOn == popupAddOn) {
            System.out.println("\u001B[32mAddOn Price Validation Passed\u001B[0m");
        } else {
            System.out.println("\u001B[31mAddOn Price Validation Failed\u001B[0m");
            allValidationsPassed = false;
        }

        // ---------- DURATION ----------
        if (tableDurationMinutes == popupDurationMinutes) {
            System.out.println("\u001B[32mDuration Validation Passed\u001B[0m");
        } else {
            System.out.println("\u001B[31mDuration Validation Failed\u001B[0m");
            allValidationsPassed = false;
        }

        // ---------- STANDALONE ----------
        if (standaloneTable.equalsIgnoreCase(standaloneValue)) {
            System.out.println("\u001B[32mStandalone Validation Passed\u001B[0m");
        } else {
            System.out.println("\u001B[31mStandalone Validation Failed\u001B[0m");
            allValidationsPassed = false;
        }

        // ---------- THIRD PARTY ----------
        if (thirdPartyTable.equalsIgnoreCase(thirdPartyValue)) {
            System.out.println("\u001B[32mThird Party Validation Passed\u001B[0m");
        } else {
            System.out.println("\u001B[31mThird Party Validation Failed\u001B[0m");
            allValidationsPassed = false;
        }

        // ---------- ONLINE VISIBILITY ----------
        if (onlineVisibilityTable.equalsIgnoreCase(onlineVisibilityValue)) {
            System.out.println("\u001B[32mOnline Visibility Validation Passed\u001B[0m");
        } else {
            System.out.println("\u001B[31mOnline Visibility Validation Failed\u001B[0m");
            allValidationsPassed = false;
        }

        // ---------- LICENSE REQUIRED ----------
        if (licRequiredTable.equalsIgnoreCase(licenseRequired)) {
            System.out.println("\u001B[32mLicense Required Validation Passed\u001B[0m");
        } else {
            System.out.println("\u001B[31mLicense Required Validation Failed\u001B[0m");
            allValidationsPassed = false;
        }

        // ---------- PRIMARY SERVICE ----------
        if (primaryServiceTable.equalsIgnoreCase(primaryService)) {
            System.out.println("\u001B[32mPrimary Service Validation Passed\u001B[0m");
        } else {
            System.out.println("\u001B[31mPrimary Service Validation Failed\u001B[0m");
            allValidationsPassed = false;
        }

        // ---------- FINAL ASSERT ----------
        Assert.assertTrue(allValidationsPassed, "One or more validations failed. Check logs above.");

    }

    @Test(priority = 3)
    public void addServiceSelection() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // ---------- CLICK ADD SERVICE ----------
        By addServiceBtn = By.xpath("//button[@data-testid='header-btn-add-service']");
        wait.until(ExpectedConditions.elementToBeClickable(addServiceBtn)).click();

        // ---------- SELECT STATE ----------
        By stateDropdown = By.xpath("//button[@data-testid='service-dialog-state-trigger']");
        wait.until(ExpectedConditions.elementToBeClickable(stateDropdown)).click();

        String stateName = "AK";

        WebElement state = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@role='option']//span[text()='" + stateName + "']")));

        state.click();

        System.out.println("Selected State : " + stateName);

        // ---------- SERVICE NAME DROPDOWN ----------
        By serviceDropdown = By.xpath("//button[@data-testid='service-dialog-serviceName-trigger']");
        wait.until(ExpectedConditions.elementToBeClickable(serviceDropdown)).click();

        List<WebElement> services = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//div[@role='option']")));

        String selectedService = "";

        for (WebElement service : services) {

            String serviceName = service.findElement(By.xpath(".//span")).getText().trim();

            // Check if "Added" div exists inside this option
            List<WebElement> addedTag = service.findElements(By.xpath(".//div[contains(text(),'Added')]"));

            if (addedTag.size() == 0) { // means NOT added

                selectedService = serviceName;

                service.click();
                break;
            }
        }

        // ---------- PRINT SELECTED SERVICE ----------
        System.out.println("Selected Service Name : " + selectedService);

        // ---------- PRINT SELECTED VALUES ----------
        System.out.println("Selected Service Name : " + selectedService);
    }

}

// @Ignore
// @Test(priority = 5)
// public void updateServicePricingForEmptyFields() {

// // State Filter
// WebElement serviceStateFilterDropdown =
// wait.until(ExpectedConditions.elementToBeClickable(
// By.xpath("//label[text()='State']/following-sibling::button")));
// serviceStateFilterDropdown.click();

// WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
// By.xpath("//div[@role='option'][.//span[text()='CA']]")));
// option.click();

// WebElement listPriceSorting =
// wait.until(ExpectedConditions.elementToBeClickable(
// By.xpath("//div[text()='List Price']")));
// listPriceSorting.click();

// int listPrice = 300;
// int addOnPrice = 200;
// int editIndex = 0;

// for (int i = 0; ; i++) { // 🔥 infinite loop with break

// List<WebElement> naList =
// wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
// By.xpath("//span[@data-state='closed']/ancestor::tr/td/div[text()='N/A']")));

// // ✅ Stop when no more rows
// if (i >= naList.size()) break;

// // ✅ Only 1,3,5...
// if (i % 2 == 0) {

// // Re-fetch edit buttons
// List<WebElement> editButtons =
// wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
// By.xpath("//span[@data-state='closed']/child::button")));

// WebElement editBtn = wait.until(ExpectedConditions.elementToBeClickable(
// editButtons.get(editIndex)));
// editBtn.click();

// // List Price
// WebElement listPriceField =
// wait.until(ExpectedConditions.visibilityOfElementLocated(
// By.xpath("//label[contains(text(),'List Price')]/following::input[1]")));

// listPriceField.sendKeys(Keys.chord(Keys.COMMAND, "a"));
// listPriceField.sendKeys(String.valueOf(listPrice));

// // Add-On Price
// WebElement addOnField =
// wait.until(ExpectedConditions.visibilityOfElementLocated(
// By.xpath("//label[contains(text(),'Add-On Price')]/following::input[1]")));

// addOnField.sendKeys(Keys.chord(Keys.COMMAND, "a"));
// addOnField.sendKeys(String.valueOf(addOnPrice));

// // Submit
// WebElement submitBtn = wait.until(ExpectedConditions.elementToBeClickable(
// By.xpath("//button[@type='submit']")));
// submitBtn.click();

// wait.until(ExpectedConditions.invisibilityOf(submitBtn));

// listPrice += 50;
// addOnPrice += 50;

// editIndex++;
// }
// }
// }
