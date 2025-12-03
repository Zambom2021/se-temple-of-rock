package br.com.templeofrock.pages;

import br.com.templeofrock.models.Band;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RegisterBandPage extends BasePage {

    private By name = By.id("bandName");
    private By genre = By.id("genre");
    private By members = By.id("members");
    private By formation = By.id("formationYear");
    private By origin = By.id("origin");
    private By submit = By.id("CadastroBtn");
    private By success = By.cssSelector(".success");

    public RegisterBandPage(WebDriver driver){
        super(driver);
    }

    public void registerBand(Band band){
        type(name, band.name);
        type(genre, band.genre);
        type(members, band.members);
        type(formation, String.valueOf(band.formationYear));
        type(origin, band.country);
        click(submit);

        // CORREÇÃO: espera o alerta aparecer
        wait.until(ExpectedConditions.visibilityOfElementLocated(success));
    }

    public boolean isSuccessMessageDisplayed(){
        return exists(success);
    }

    public String getSuccessMessage(){
        return getText(success);
    }
}
