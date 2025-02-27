package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Methods {

    private final WebDriver driver;

    public Methods(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement waitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20L));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void hoverOverElement(WebElement webElement) {
        Actions action = new Actions(driver);
        action.moveToElement(webElement).perform();
    }

    public void waitForPageToLoad() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20L));
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete';"));
    }

    public void clickByJS(By locator) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", locator);
    }

    public void clickByJS(WebElement locator) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", locator);
    }

    public void sleep(Long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
