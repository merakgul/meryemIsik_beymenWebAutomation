package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;
import utils.ExcelReader;
import utils.Methods;

import java.io.IOException;

public class BeymenTest {
    private WebDriver driver;
    private HomePage homePage;
    private Methods methods;
    private SearchResultsPage searchResultsPage;
    private ProductPage productPage;
    private CartPage cartPage;
    private ExcelReader excelReader;
    private static final Logger logger = LogManager.getLogger(BeymenTest.class);

    @BeforeEach
    public void setUp() throws IOException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        methods = new Methods(driver);
        excelReader = new ExcelReader("testdata.xlsx");
        logger.info("Test setup completed.");
    }

    @Test
    public void testBeymenAutomation() throws IOException {
        driver.get("https://www.beymen.com");
        Assertions.assertEquals("Beymen.com – Türkiye’nin Tek Dijital Lüks Platformu", driver.getTitle(), "Home page is not opened.");

        homePage.closeModals();
        String searchTerm1 = excelReader.getCellData(0, 0);
        homePage.searchFor(searchTerm1);
        homePage.clearSearchBox();

        String searchTerm2 = excelReader.getCellData(1, 0);
        homePage.searchFor(searchTerm2);
        homePage.pressEnter();

        methods.sleep(2000L);
        searchResultsPage = new SearchResultsPage(driver);
        searchResultsPage.selectRandomProduct();

        productPage = new ProductPage(driver);
        productPage.writeProductInfoToFile("product_info.txt");
        String productPrice = productPage.getProductPrice();
        productPage.selectRandomSize();
        methods.sleep(2000L);
        productPage.addToCart();
        try {
            productPage.goToCart();
            cartPage = new CartPage(driver);
            Assertions.assertTrue(
                    cartPage.standardizePrice(cartPage.getCartItemPrice()).contains(cartPage.standardizePrice(productPrice)),
                    "Product price mismatch. Expected: " + productPrice + ", Actual: " + cartPage.getCartItemPrice()
            );

            boolean isQuantityIncreased = cartPage.increaseQuantity();
            if (isQuantityIncreased) {
                Assertions.assertEquals("2", cartPage.getQuantity(), "Quantity is not increased to 2.");
            } else {
                System.out.println("Quantity was not increased because the current value is already 1.");
                Assertions.assertEquals("1", cartPage.getQuantity(), "Quantity is still 1.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred during the test: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (cartPage != null) {
                try {
                    cartPage.removeItem();
                    Assertions.assertTrue(cartPage.isCartEmpty(), "Cart is not empty.");
                } catch (Exception e) {
                    System.err.println("An error occurred while cleaning up the cart: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        logger.info("Test teardown completed.");
    }
}