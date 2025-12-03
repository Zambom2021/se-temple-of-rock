package br.com.templeofrock.pages;

import br.com.templeofrock.models.Disc;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class EditBandPage extends BasePage {

    private By searchInput = By.id("searchBand");
    private By searchBtn = By.xpath("//button[contains(text(),'Editar Banda')]");

    private By editGenre = By.id("editGenre");
    private By editMembers = By.id("editMembers");
    private By editCountry = By.id("editCountry");
    private By editDiscography = By.id("editDiscography");

    private By saveBtn = By.id("saveBtn");
    private By confirmYes = By.id("confirmYes");
    private By confirmNo = By.id("confirmNo");

    private By success = By.cssSelector(".success");

    public EditBandPage(WebDriver driver){
        super(driver);
    }

    public void searchBand(String name){
        type(searchInput, name);
        click(searchBtn);
    }

    public void updateBandInfo(String genre, String members, String country){
        type(editGenre, genre);
        type(editMembers, members);
        type(editCountry, country);
    }

    public void updateDiscography(List<Disc> discs){
        StringBuilder sb = new StringBuilder();
        for (Disc d : discs){
            sb.append(d.getTitle()).append(" - ").append(d.getReleaseYear()).append(", ");
        }
        String text = sb.toString().trim().replaceAll(",$", "");
        type(editDiscography, text);
    }

    public void saveChanges(boolean confirm){
        click(saveBtn);
        if(confirm)
            click(confirmYes);
        else
            click(confirmNo);
    }

    public void addDisc(Disc disc){
        type(By.id("discTitle"), disc.getTitle());
        type(By.id("discYear"), String.valueOf(disc.getReleaseYear()));
        click(By.xpath("//button[contains(text(),'Salvar Disco')]"));
    }

    public boolean isSuccessMessageDisplayed(){
        return exists(success);
    }

    public String getSuccessMessage(){
        return getText(success);
    }
}
