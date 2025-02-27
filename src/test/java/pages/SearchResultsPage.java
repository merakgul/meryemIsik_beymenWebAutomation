package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Methods;

import java.util.List;
import java.util.Random;

public class SearchResultsPage {
    private WebDriver driver;
    private Methods methods;
    private static final Logger logger = LogManager.getLogger(SearchResultsPage.class);

    private By productList = By.xpath("//a[@class='o-productCard__link']");

    public SearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.methods = new Methods(driver);
    }

    public void selectRandomProduct() {
        methods.waitForElement(productList);
        List<WebElement> products = driver.findElements(productList);
        if (!products.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(products.size());
            products.get(randomIndex).click();
            logger.info("Random product selected.");
        } else {
            logger.error("No products found in the search results.");
        }
    }
}