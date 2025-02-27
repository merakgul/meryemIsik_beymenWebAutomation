package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import utils.Methods;

import java.util.List;

public class CartPage {
    private WebDriver driver;
    private Methods methods;
    private static final Logger logger = LogManager.getLogger(CartPage.class);

    private By cartItemPrice = By.xpath("//li[@class='m-orderSummary__item -grandTotal']//*[contains(@class,'m-orderSummary__value')]");
    private By quantityInput = By.id("quantitySelect0-key-0");
    private By removeButton = By.id("removeCartItemBtn0-key-0");
    private By emptyCartMessage = By.xpath("//div[@id='emtyCart']//strong[@class='m-empty__messageTitle']");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.methods = new Methods(driver);
    }

    public String getCartItemPrice() {
        methods.waitForElement(cartItemPrice);
        return driver.findElement(cartItemPrice).getText();
    }

    public boolean isCartEmpty() {
        return driver.findElement(emptyCartMessage).isDisplayed();
    }

    public boolean increaseQuantity() {
        WebElement quantityElement = driver.findElement(quantityInput);
        Select select = new Select(quantityElement);
        List<WebElement> options = select.getOptions();

        if (!options.isEmpty()) {
            WebElement firstOption = options.get(0); // İlk seçeneği al
            String currentValue = firstOption.getAttribute("value");

            if (!currentValue.equals("1")) {
                select.selectByValue("2");
                return true;
            } else {
                System.out.println("Value is only 1. It couldn't be increased.");
                return false;
            }
        } else {
            System.out.println("No options found in the select element.");
            return false;
        }
    }

    public String getQuantity() {
        return driver.findElement(quantityInput).getAttribute("value");
    }

    public void removeItem() {
        driver.findElement(removeButton).click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.error("Error during wait: " + e.getMessage());
        }
    }

    public String standardizePrice(String price) {
        return price.trim()
                .replace(".", "")
                .replace(",", ".")
                .replace(" TL", "");
    }
}