package com.winconnect;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/**TestCases
 1. Validate Fixed Discount Calculation
 2.Validate Percentage Discount Calculation
 3. Validate Manual Pricing (None Discount)
 4.Validate Package Update with Services
 5. Validate Package Creation with Services and Percentage Discount Calculation

*/
public class SpPackages {

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
            .sendKeys("bvierra@wini.com");

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

    WebElement packages = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//span[text()='Service Packages']/parent::a")));
    packages.click();
    

    // State Filter
    WebElement serviceStateFilterDropdown = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//button[@id='state-filter']")));
    serviceStateFilterDropdown.click();

    WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//div[@role='option'][.//span[text()='CA']]")));
    option.click();
}
   


/**
 * Test Case 1:
 * Validate Fixed Discount Calculation
 *
 * Steps:
 * 1. Click on Edit Package
 * 2. Select "Fixed" from discount dropdown
 * 3. Enter fixed discount value
 * 4. Trigger calculation
 * 5. Capture popup total price
 * 6. Click on "Update Package"
 * 7. Capture table price
 * 8. Validate popup price equals table price
 *
 * Expected Result:
 * Popup price and table price should match exactly
  * @throws InterruptedException 
  */
 
 
 @Test(priority = 1)
 public void totalPriceValidationFixedDiscount() throws InterruptedException {

    Thread.sleep(5000);

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

    By editBtnLocator = By.xpath("(//span[@data-state='closed']/child::button)[1]");
    By fieldLocator = By.xpath("//input[contains(@id,'form-item')]");
    By popupPriceLocator = By.xpath("//td[contains(@class,'font-bold')]");
    By updateBtnLocator = By.xpath("//button[text()='Update Package']");
    By overlayLocator = By.xpath("//div[@data-state='open']");

    // ---------- CLICK EDIT ----------
    WebElement editBtn = wait.until(ExpectedConditions.elementToBeClickable(editBtnLocator));
    editBtn.click();

    // ---------- GET PACKAGE NAME ----------
    By packageNameLocator = By.xpath("(//button[@data-testid='package-form-name-trigger']/descendant::span)[2]");

    String packageName = wait.until(
            ExpectedConditions.visibilityOfElementLocated(packageNameLocator)).getText();

    // ---------- SELECT DISCOUNT DROPDOWN ----------
    WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("(//button[@role='combobox'])[5]")));
    dropdown.click();

    // ---------- SELECT FIXED ----------
    WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@role='option'][.//span[text()='Fixed']]")));
    option.click();

    // ---------- ENTER FIXED VALUE ----------
    WebElement fixedDiscountfield = wait.until(ExpectedConditions.elementToBeClickable(fieldLocator));
    fixedDiscountfield.click();
    fixedDiscountfield.sendKeys(Keys.chord(Keys.COMMAND, "a"));

    ((JavascriptExecutor) driver).executeScript(
            "const element = arguments[0];" +
                    "const value = '54';" +
                    "const nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                    "nativeInputValueSetter.call(element, value);" +
                    "element.dispatchEvent(new Event('input', { bubbles: true }));",
            fixedDiscountfield
    );

    // Trigger calculation
    fixedDiscountfield.sendKeys(Keys.TAB);

    // ---------- WAIT FOR POPUP PRICE ----------
    wait.until(driver -> {
        String text = driver.findElement(popupPriceLocator).getText();
        return text != null && !text.trim().isEmpty();
    });

    String fixedDiscountFinalPrice = driver.findElement(popupPriceLocator).getText();

    // ---------- CLICK UPDATE ----------
    WebElement updateBtn = wait.until(ExpectedConditions.elementToBeClickable(updateBtnLocator));
    updateBtn.click();

    // ---------- WAIT POPUP CLOSE ----------
    wait.until(ExpectedConditions.invisibilityOfElementLocated(overlayLocator));

    // ---------- GET TABLE PRICE USING PACKAGE NAME (DYNAMIC) ----------
    By tablePriceLocator = By.xpath(
            "//div[contains(text(),'" + packageName + "')]/ancestor::tr//span[contains(@class,'text-green-600')]"
    );

    String fixedDiscountTotalPrice = wait.until(
            ExpectedConditions.visibilityOfElementLocated(tablePriceLocator)).getText();

    // ---------- PRINT RESULTS ----------
    System.err.println("-------------- Test Case 1 -------------- ");
    System.out.println("Package Name : " + packageName);
    System.err.println("Fixed Discount Popup Price: " + fixedDiscountFinalPrice);
    System.err.println("Fixed Discount Table Price: " + fixedDiscountTotalPrice);

    // ---------- NORMALIZE PRICE ----------
    String normalizedPopup = fixedDiscountFinalPrice.replace("$", "").trim();
    String normalizedTable = fixedDiscountTotalPrice.replace("$", "").trim();

    if (normalizedPopup.endsWith(".00")) {
        normalizedPopup = normalizedPopup.replace(".00", "");
    }

    if (normalizedTable.endsWith(".00")) {
        normalizedTable = normalizedTable.replace(".00", "");
    }

    // ---------- VALIDATION ----------
    try {

        Assert.assertEquals(normalizedTable, normalizedPopup);

        System.out.println("\u001B[32mFixed Discount Validation Passed\u001B[0m");

    } catch (AssertionError e) {

        System.out.println("\u001B[31mFixed Discount Validation Failed\u001B[0m");

        throw e;
    }
}
   
/**
 * Test Case 2:
 * Validate Percentage Discount Calculation
 *
 * Steps:
 * 1. Click on Edit Package
 * 2. Select "Percent" from dropdown
 * 3. Enter percentage value
 * 4. Capture popup total price
 * 5. Click on "Update Package"
 * 6. Capture table price
 * 7. Validate both prices are equal
 *
 * Expected Result:
 * Popup price and table price should be the same
  * @throws InterruptedException 
  */
 
 
 @Test(priority = 2)
 public void totalPriceValidationPercentage() throws InterruptedException {

    Thread.sleep(5000);

    By editBtnLocator = By.xpath("(//span[@data-state='closed']/child::button)[3]");

    // Wait for overlay to disappear
    wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.xpath("//div[@data-state='open' and contains(@class,'fixed')]")));

    // ---------- CLICK EDIT ----------
    WebElement editBtn = wait.until(ExpectedConditions.elementToBeClickable(editBtnLocator));
    editBtn.click();

    // ---------- GET PACKAGE NAME ----------
    By packageNameLocator = By.xpath("(//button[@data-testid='package-form-name-trigger']/descendant::span)[2]");

    String packageName = wait.until(
            ExpectedConditions.visibilityOfElementLocated(packageNameLocator)).getText();

    // ---------- SELECT DISCOUNT DROPDOWN ----------
    WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("(//button[@role='combobox'])[5]")));
    dropdown.click();

    // ---------- SELECT PERCENT ----------
    WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@role='option'][.//span[text()='Percent']]")));
    option.click();

    // ---------- ENTER PERCENT VALUE ----------
    WebElement percentageField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[contains(@id,'form-item')]")));

    percentageField.click();
    percentageField.sendKeys(Keys.chord(Keys.COMMAND, "a"));

    ((JavascriptExecutor) driver).executeScript(
            "const element = arguments[0];" +
                    "const value = '65';" +
                    "const nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                    "nativeInputValueSetter.call(element, value);" +
                    "element.dispatchEvent(new Event('input', { bubbles: true }));",
            percentageField
    );

    // ---------- WAIT FOR POPUP PRICE TO STABILIZE ----------
    By popupPriceLocator = By.xpath("//td[contains(@class,'font-bold')]");

    String previous = "";
    String current = "";

    for (int i = 0; i < 10; i++) {

        previous = driver.findElement(popupPriceLocator).getText();

        try { Thread.sleep(500); } catch (Exception e) {}

        current = driver.findElement(popupPriceLocator).getText();

        if (previous.equals(current)) {
            break;
        }
    }

    String finalPrice = current;

    // ---------- CLICK UPDATE ----------
    driver.findElement(By.xpath("//button[text()='Update Package']")).click();

    // ---------- WAIT FOR POPUP CLOSE ----------
    wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.xpath("//div[@data-state='open' and contains(@class,'fixed')]")));

    // ---------- FETCH TABLE PRICE USING PACKAGE NAME ----------
    By tablePriceLocator = By.xpath(
            "//div[contains(text(),'" + packageName + "')]/ancestor::tr//span[contains(@class,'text-green-600')]"
    );

    previous = "";
    current = "";

    for (int i = 0; i < 10; i++) {

        previous = driver.findElement(tablePriceLocator).getText();

        try { Thread.sleep(500); } catch (Exception e) {}

        current = driver.findElement(tablePriceLocator).getText();

        if (previous.equals(current)) {
            break;
        }
    }

    String totalPrice = current;

    // ---------- PRINT ----------
    System.err.println("-------------- Test Case 2 -------------- ");
    System.out.println("Package Name : " + packageName);
    System.err.println("Percentage Popup Price: " + finalPrice);
    System.err.println("Percentage Table Price: " + totalPrice);

    // ---------- NORMALIZE ----------
    String normalizedPopup = finalPrice.replace("$", "").trim();
    String normalizedTable = totalPrice.replace("$", "").trim();

    if (normalizedPopup.endsWith(".00")) {
        normalizedPopup = normalizedPopup.replace(".00", "");
    }

    if (normalizedTable.endsWith(".00")) {
        normalizedTable = normalizedTable.replace(".00", "");
    }

    // ---------- VALIDATION ----------
    try {

        Assert.assertEquals(normalizedTable, normalizedPopup);

        System.out.println("\u001B[32mPercentage Validation Passed\u001B[0m");

    } catch (AssertionError e) {

        System.out.println("\u001B[31mPercentage Validation Failed\u001B[0m");

        throw e;
    }
}

    /**
 * Test Case 3:
 * Validate Manual Pricing (None Discount)
 *
 * Steps:
 * 1. Click on Edit Package
 * 2. Select "None" from dropdown
 * 3. Enter values in all manual fields
 * 4. Wait for calculation
 * 5. Capture popup total price
 * 6. Click on "Update Package"
 * 7. Capture table price
 * 8. Validate both prices match
 *
 * Expected Result:
 * Popup price and table price should be equal
      * @throws InterruptedException 
  */
 
 
   
     @Test(priority = 3)
     public void totalPriceValidationNone() throws InterruptedException {

        Thread.sleep(5000);

        By popupPriceLocator = By.xpath("//td[contains(@class,'font-bold')]");
        By overlayLocator = By.xpath("//div[@data-state='open']");

        // ------------------ DYNAMIC EDIT BUTTON ------------------
        By packageNameLocator = By.xpath("//div[text()='Essential']");
        String packageName = wait.until(ExpectedConditions.visibilityOfElementLocated(packageNameLocator)).getText();

        By editBtnLocator = By.xpath("//div[text()='"+packageName+"']/ancestor::tr//span[@data-state='closed']/child::button");
        WebElement editBtn = wait.until(ExpectedConditions.elementToBeClickable(editBtnLocator));
        editBtn.click();

        // SELECT NONE
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//button[@role='combobox'])[5]")));
        dropdown.click();
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@role='option'][.//span[text()='None']]")));
        option.click();

        // ENTER VALUES IN MANUAL FIELDS
        List<WebElement> manualDiscountFields = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
                By.xpath("(//input[@type='number'])")));
        int value = 37;
        for (WebElement field : manualDiscountFields) {
            field.click();
            field.sendKeys(Keys.chord(Keys.COMMAND, "a"));
            field.sendKeys(String.valueOf(value));
            value++;
        }

    Thread.sleep(4000);
       // WAIT UNTIL POPUP PRICE STABILIZES
String finalPrice = wait.until(driver -> {
    String prev = driver.findElement(popupPriceLocator).getText();
    try { Thread.sleep(500); } catch (InterruptedException e) {}
    String curr = driver.findElement(popupPriceLocator).getText();
    if (prev.equals(curr)) return curr; // stable
    else return null; // keep waiting
});

        driver.findElement(By.xpath("//button[text()='Update Package']")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(overlayLocator));

        // GET TABLE PRICE
        By tablePriceLocator = By.xpath(
                "//div[text()='"+packageName+"']/ancestor::tr//span[contains(@class,'text-green-600')]");
        String totalPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(tablePriceLocator)).getText();

        System.err.println("-------------- Test Case 3 -------------- ");
        System.out.println("Package Name : " + packageName);
        System.err.println("None Popup Price: " + finalPrice);
        System.err.println("None Table Price: " + totalPrice);

        String normalizedPopup = finalPrice.replace("$", "").trim();
        String normalizedTable = totalPrice.replace("$", "").trim();

        if (normalizedPopup.endsWith(".00")) normalizedPopup = normalizedPopup.replace(".00", "");
        if (normalizedTable.endsWith(".00")) normalizedTable = normalizedTable.replace(".00", "");

        Assert.assertEquals(normalizedTable, normalizedPopup, "None Discount Validation Failed");
        System.out.println("\u001B[32mNone Validation Passed\u001B[0m");
    }


/**
 * Test Case 4:
 * Validate Package Update with Services
 *
 * Steps:
 * 1. Click on Edit Package
 * 2. Check if Primary Service exists
 * 3. If available, add one Primary Service
 * 4. Add one Non-Primary Service
 * 5. Capture popup total price
 * 6. Click on "Update Package"
 * 7. Capture table price
 * 8. Validate popup price equals table price
 *
 * Expected Result:
 * Updated package total price should match in popup and table
  * @throws InterruptedException 
  */
 
 
 @Test(priority = 4) //1979 - Ticket pending 
 public void updatePackageWithServiceValidation() throws InterruptedException {

    Thread.sleep(5000);

    // Edit Package
    By editBtnLocator = By.xpath("(//span[@data-state='closed']/child::button)[8]");
    By packageNameLocator = By.xpath("(//button[@data-testid='package-form-name-trigger']/descendant::span)[2]");

    wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.xpath("//div[@data-state='open' and contains(@class,'fixed')]")));

    WebElement editBtn = wait.until(ExpectedConditions.refreshed(
            ExpectedConditions.elementToBeClickable(editBtnLocator)));
    editBtn.click();

    String packageName = wait.until(
            ExpectedConditions.visibilityOfElementLocated(packageNameLocator)).getText();

    // ---------------- PRIMARY SERVICE ----------------

    List<WebElement> primarySections = wait.until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//div[text()='Primary Service']")));

    boolean primaryAdded = false;

    if (!primarySections.isEmpty()) {

        List<WebElement> primaryAddBtns = driver.findElements(
                By.xpath("//div[text()='Primary Service']/ancestor::div[contains(@class,'flex items-center justify-between')]/descendant::button"));

        for (WebElement btn : primaryAddBtns) {

            if (btn.isDisplayed() && btn.isEnabled()) {
                btn.click();
                primaryAdded = true;
                break;
            }
        }
    }

    // ---------------- NON PRIMARY SERVICE ----------------

    List<WebElement> nonPrimaryBtns = wait.until(
            ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//button[contains(@data-testid,'package-form-add-service-toggle')]")));

    for (WebElement btn : nonPrimaryBtns) {

        if (btn.isDisplayed() && btn.isEnabled()) {
            btn.click(); // add one service
            break;
        }
    }

    // ---------------- FINAL TOTAL (POPUP) ----------------

    By popupPriceLocator = By.xpath("//td[contains(@class,'font-bold')]");

    String previous = "";
    String current = "";

    for (int i = 0; i < 10; i++) {

        previous = driver.findElement(popupPriceLocator).getText();

        try { Thread.sleep(500); } catch (Exception e) {}

        current = driver.findElement(popupPriceLocator).getText();

        if (previous.equals(current)) break;
    }

    String finalPrice = current;

    // ---------------- UPDATE PACKAGE ----------------

    WebElement updateBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[text()='Update Package']")));

    updateBtn.click();

    wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.xpath("//div[@data-state='open' and contains(@class,'fixed')]")));

    // ---------------- TABLE PRICE USING PACKAGE NAME ----------------

    By tablePriceLocator = By.xpath(
            "//div[contains(text(),'" + packageName + "')]/ancestor::tr//span[contains(@class,'text-green-600')]"
    );

    String totalPrice = wait.until(
            ExpectedConditions.visibilityOfElementLocated(tablePriceLocator)).getText();

    // ---------------- VALIDATION ----------------

    System.err.println("-------------- Test Case 4 -------------- ");
    System.out.println("Package Name : " + packageName);
    System.err.println("Popup Price: " + finalPrice);
    System.err.println("Table Price: " + totalPrice);

    String normalizedPopup = finalPrice.replace("$", "").trim();
    String normalizedTable = totalPrice.replace("$", "").trim();

    if (normalizedPopup.endsWith(".00")) {
        normalizedPopup = normalizedPopup.replace(".00", "");
    }

    if (normalizedTable.endsWith(".00")) {
        normalizedTable = normalizedTable.replace(".00", "");
    }

    try {

        Assert.assertEquals(normalizedTable, normalizedPopup);

        System.out.println("\u001B[32mService Update Validation Passed\u001B[0m");

    } catch (AssertionError e) {

        System.out.println("\u001B[31mService Update Validation Failed\u001B[0m");

        throw e;
    }
}




/**
 * Test Case 5:
 * Validate Package Creation with Services and Percentage Discount Calculation
 *
 * Steps:
 * 1. Click on "Create Package"
 * 2. Select State from dropdown (AK)
 * 3. Select Package Name (Essential Plus)
 * 4. Enter Package Description
 * 5. Enable Primary Service
 * 6. Add 3 additional services
 * 7. Select "Percent" from discount dropdown
 * 8. Enter percentage discount value (51)
 * 9. Wait for price calculation to stabilize
 * 10. Capture popup total price
 * 11. Click on "Create Package"
 * 12. Verify success toast message
 * 13. Apply State filter (AK)
 * 14. Capture package price from table using package name
 * 15. Normalize popup price and table price
 * 16. Validate popup price equals table price
 *
 * Expected Result:
 * Package should be created successfully and
 * popup price and table price should match exactly.
 */

@Test(priority = 5)
public void createPackageWithServicesAndPercentageValidation() throws Exception {

    String state = "AK";   // COMMON VARIABLE FOR STATE
    String packageNameOption = "HMC Essential Plus";

    // ---------- CLICK CREATE PACKAGE ----------
    WebElement createPackageBtn = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[text()='Create Package']")));
    createPackageBtn.click();

    wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//button[@data-testid='package-form-state-trigger']")));

    // ---------- SELECT STATE IN POPUP ----------
    WebElement stateDropdown = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[@data-testid='package-form-state-trigger']")));
    stateDropdown.click();

    WebElement stateOption = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@role='option'][.//span[text()='" + state + "']]")));
    stateOption.click();


    // ---------- SELECT PACKAGE ----------
    WebElement packageDropdown = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[@data-field='packageName']")));
    packageDropdown.click();

    WebElement packageOption = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//span[text()='"+packageNameOption +"']")));
    packageOption.click();

    By packageNameLocator = By.xpath("(//button[@data-testid='package-form-name-trigger']//span)[2]");
    String packageName = wait.until(ExpectedConditions.visibilityOfElementLocated(packageNameLocator)).getText();


    // ---------- DESCRIPTION ----------
    WebElement description = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//textarea[@name='description']")));

    description.clear();
    description.sendKeys("Includes all essential home inspection services to provide complete peace of mind to buyers.");


    // ---------- ADD PRIMARY SERVICE ----------
    WebElement primaryToggle = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[text()='Primary Service']/ancestor::div[contains(@class,'flex items-center justify-between')]//button[@data-testid='package-form-add-service-toggle']")));

    primaryToggle.click();


    // ---------- ADD 3 OTHER SERVICES ----------
    List<WebElement> services = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
            By.xpath("//button[@data-testid='package-form-add-service-toggle']")));

    int added = 0;

    for (WebElement service : services) {

        if (service.isDisplayed() && service.isEnabled()) {

            service.click();
            added++;

            if (added == 3) {
                break;
            }
        }
    }


    // ---------- SELECT DISCOUNT TYPE ----------
    WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("(//button[@role='combobox'])[5]")));
    dropdown.click();

    WebElement option = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@role='option'][.//span[text()='Percent']]")));
    option.click();


    // ---------- ENTER PERCENTAGE ----------
    WebElement percentageField = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[contains(@id,'form-item')]")));

    percentageField.click();
    percentageField.sendKeys(Keys.chord(Keys.COMMAND, "a"));

    ((JavascriptExecutor) driver).executeScript(
            "const element = arguments[0];" +
                    "const value = '54';" +
                    "const nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;" +
                    "nativeInputValueSetter.call(element, value);" +
                    "element.dispatchEvent(new Event('input', { bubbles: true }));",
            percentageField
    );


    // ---------- WAIT FOR POPUP PRICE ----------
    By popupPriceLocator = By.xpath("//td[contains(@class,'font-bold')]");

    String previous = "";
    String current = "";

    for (int i = 0; i < 10; i++) {

        previous = driver.findElement(popupPriceLocator).getText();
        Thread.sleep(500);
        current = driver.findElement(popupPriceLocator).getText();

        if (previous.equals(current)) {
            break;
        }
    }

    String finalPrice = current;


    // ---------- CLICK CREATE ----------
    driver.findElement(By.xpath("//button[@form='package-form']")).click();


    // ---------- VALIDATE PACKAGE CREATED ----------
    WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(text(),'created')]")));

    Assert.assertTrue(toastMessage.isDisplayed(), "Package not created");


    // ---------- WAIT POPUP CLOSE ----------
    wait.until(ExpectedConditions.invisibilityOfElementLocated(
            By.xpath("//div[@data-state='open' and contains(@class,'fixed')]")));


    // ---------- APPLY STATE FILTER ----------
    WebElement serviceStateFilterDropdown = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//button[@id='state-filter']")));
    serviceStateFilterDropdown.click();

    WebElement filterOption = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//div[@role='option'][.//span[text()='" + state + "']]")));
    filterOption.click();


    // ---------- FETCH TABLE PRICE USING PACKAGE NAME ----------
    By tablePriceLocator = By.xpath(
            "//div[contains(text(),'" + packageName + "')]/ancestor::tr//span[contains(@class,'text-lg font-semibold')]"
    );

    String totalPrice = wait.until(ExpectedConditions.visibilityOfElementLocated(tablePriceLocator)).getText();


    // ---------- PRINT ----------
    System.err.println("-------------- Test Case 5 -------------- ");
    System.out.println("Package Name : " + packageName);
    System.err.println("Popup Price: " + finalPrice);
    System.err.println("Table Price: " + totalPrice);


    // ---------- NORMALIZE ----------
    String normalizedPopup = finalPrice.replace("$", "").trim();
    String normalizedTable = totalPrice.replace("$", "").trim();

    if (normalizedPopup.endsWith(".00")) {
        normalizedPopup = normalizedPopup.replace(".00", "");
    }

    if (normalizedTable.endsWith(".00")) {
        normalizedTable = normalizedTable.replace(".00", "");
    }


    // ---------- PRICE VALIDATION ----------
    Assert.assertEquals(
            normalizedTable,
            normalizedPopup,
            "Package created but pricing is not matching. Popup Price: "
                    + normalizedPopup + " | Table Price: " + normalizedTable
    );

    System.out.println(" Package created and pricing matched successfully");
}

}


