package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import utils.Methods;

public class HomePage {
    private WebDriver driver;
    private Methods methods;
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    private By searchBox = By.xpath("//input[@placeholder='Ürün, Marka Arayın']");
    private By closeGenderButton = By.xpath("//button[@class='o-modal__closeButton bwi-close']");
    private By searchButton = By.id("search-button");
    private By rejectAllHandler = By.id("onetrust-reject-all-handler");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.methods = new Methods(driver);
    }

    public void searchFor(String text) {
        driver.findElement(searchBox).sendKeys(text);
    }
    public void closeModals() {
        methods.waitForElement(rejectAllHandler).click();
       // methods.waitForElement(closeGenderButton).click();
    }

    public void clearSearchBox() {
        driver.findElement(searchBox).clear();
    }

    public void pressEnter() {
        driver.findElement(searchBox).sendKeys(Keys.ENTER);
        logger.info("Enter key pressed.");
    }

    public void clickSearchButton() {
        driver.findElement(searchButton).click();
    }
}