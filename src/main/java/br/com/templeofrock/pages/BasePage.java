package br.com.templeofrock.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    protected WebElement find(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator){
        find(locator).click();
    }

    protected void type(By locator, String text){
        WebElement el = find(locator);
        el.clear();
        el.sendKeys(text);
    }

    protected String getText(By locator){
        return find(locator).getText();
    }

    protected boolean exists(By locator){
        try{
            driver.findElement(locator);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
