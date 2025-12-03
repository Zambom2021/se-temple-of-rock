package br.com.templeofrock.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.List;

public class BandDetailsPage extends BasePage {

    private By bandName = By.id("bandName");
    private By genre = By.id("bandGenre");
    private By country = By.id("bandCountry");
    private By year = By.id("formationYear");
    private By members = By.id("bandMembers");
    private By discItems = By.cssSelector("#discographyList .discography-item");
    

    public BandDetailsPage(WebDriver driver) {
        super(driver);
    }

    public String getName() { return getText(bandName).trim(); }
    public String getGenre() { return getText(genre).trim(); }
    public String getCountry() { return getText(country).trim(); }
    public String getYear() { return getText(year).trim(); }
    public String getMembers() { return getText(members).trim(); }

    public List<String> getDiscography() {
        return driver.findElements(discItems)
                .stream()
                .map(we -> we.getText().trim())
                .toList();
    }
}
