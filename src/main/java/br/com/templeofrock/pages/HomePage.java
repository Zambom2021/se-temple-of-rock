package br.com.templeofrock.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final String URL = "http://localhost:9090/web";

    private By loginLink = By.cssSelector("#loginSection a");
    private By letterInput = By.id("letterSearch");
    private By letterBtn = By.id("searchByLetterBtn");
    private By bandsList = By.id("bandsList");

    private By searchNameInput = By.cssSelector("#searchBandSection #searchBand");
    private By searchNameBtn = By.id("searchByNameBtn");
    private By bandDetails = By.id("bandDetails");

    public HomePage(WebDriver driver){
        super(driver);
    }

    public void visit(){
        driver.get(URL);
    }

    public void clickLoginLink(){
        click(loginLink);
    }

    public void searchBandByLetter(String letter){
        type(letterInput, letter);
        click(letterBtn);
    }

    public boolean isBandsListDisplayed(){
        return exists(bandsList);
    }

    public int getBandsListCount(){
        return driver.findElements(By.cssSelector("#bandsList > *")).size();
    }

    public void searchBandByName(String name){
        type(searchNameInput, name);
        click(searchNameBtn);
    }

    public boolean isBandDetailsDisplayed(){
        return exists(bandDetails);
    }

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }
}
