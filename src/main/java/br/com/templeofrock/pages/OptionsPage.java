package br.com.templeofrock.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OptionsPage extends BasePage {

    private By registerBandBtn = By.xpath("//button[contains(text(),'Cadastrar Banda')]");
    private By editBandBtn = By.id("editBandBtn");
    private By addDiscsName = By.id("discBandName");
    private By addDiscsBtn = By.id("editDiscographyBtn");

    public OptionsPage(WebDriver driver){
        super(driver);
    }

    public void clickRegisterBand(){
        click(registerBandBtn);
    }

    public void openRegisterBandModal() {
        click(By.xpath("//button[contains(text(),'Cadastrar Banda')]"));

        // aguarda o modal ficar visível
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("bandName")));
    }

    public void clickEditBand(){
        click(editBandBtn);
    }

    public void goToAddDiscs(String band){
        type(addDiscsName, band);
        click(addDiscsBtn);
    }
}
