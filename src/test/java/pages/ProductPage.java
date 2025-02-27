package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.Methods;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ProductPage {
    private WebDriver driver;
    private Methods methods;
    private static final Logger logger = LogManager.getLogger(HomePage.class);

    private By productName = By.xpath("//span[@class='o-productDetail__description']");
    private By productPrice = By.xpath("//*[contains(@class,'m-price ')]//*[@class='m-price__new' or contains(@class,'m-price__lastPrice')]");
    private By addToCartButton = By.id("addBasket");
    private By selectSize = By.xpath("//div[@class='m-variation']//span[(@class='m-variation__item') or (@class='m-variation__item -criticalStock') and not(contains(@class, 'm-variation__item -disabled'))]");
    private By cartIcon = By.xpath("//a[@title='Sepetim']");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.methods = new Methods(driver);
    }

    public String getProductName() {
        methods.waitForPageToLoad();
        return driver.findElement(productName).getText();
    }

    public String getProductPrice() {
        return driver.findElement(productPrice).getText();
    }

    public void addToCart() {
        driver.findElement(addToCartButton).click();
    }

    public void writeProductInfoToFile(String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Product Name: " + getProductName() + "\n");
            writer.write("Product Price: " + getProductPrice() + "\n");
            logger.info("Product info written to file: " + fileName);
        } catch (IOException e) {
            logger.error("Error writing product info to file: " + e.getMessage());
            throw e;
        }
    }

    public void selectRandomSize() {
        methods.waitForElement(selectSize);
        List<WebElement> sizes = driver.findElements(selectSize);
        if (!sizes.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(sizes.size());
            sizes.get(randomIndex).click();
            logger.info("Random size selected.");
        } else {
            logger.error("No size found.");
        }
    }

    public void goToCart() {
        methods.waitForElement(cartIcon).click();
        methods.waitForPageToLoad();
    }

}