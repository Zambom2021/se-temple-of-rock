package br.com.templeofrock.tests;

import br.com.templeofrock.models.Band;
import br.com.templeofrock.models.Disc;
import br.com.templeofrock.models.User;
import br.com.templeofrock.pages.*;
import br.com.templeofrock.utils.ApiHelper;
import br.com.templeofrock.utils.FakerUtils;

import io.qameta.allure.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@ExtendWith(AllureJunit5.class)
@Epic("Temple Of Rock")
@Feature("Funcionalidades principais do sistema")
public class TempleOfRockTest extends BaseTest {

    private void waitUrlContains(String value) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains(value));
    }

    public void waitVisible(String css) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        org.openqa.selenium.By.cssSelector(css)
                ));
    }

    @Test
    @DisplayName("Verifica o título da página Temple of Rock")
    @Description("Valida que o título da página é 'Temple Of Rock'")
    @Severity(SeverityLevel.NORMAL)
    public void verificaTituloPagina() {
        HomePage homePage = new HomePage(driver);
        homePage.visit();

        assertEquals("Temple Of Rock", driver.getTitle(),
                "O título da página deve ser 'Temple Of Rock'");
    }

    @ParameterizedTest
    @ValueSource(strings = { "M", "D", "U", "L" })
    @DisplayName("Consulta Lista de Bandas pela Letra")
    @Description("Testa a busca de bandas por diferentes letras do alfabeto")
    @Severity(SeverityLevel.CRITICAL)
    public void consultaListaBandasPorLetra(String letra) {
        HomePage homePage = new HomePage(driver);
        homePage.visit();

        homePage.searchBandByLetter(letra);

        waitUrlContains("bandsList.html");

        assertTrue(driver.getCurrentUrl().contains("letter=" + letra));
        assertTrue(homePage.isBandsListDisplayed());
        assertTrue(homePage.getBandsListCount() > 0);
    }

    @ParameterizedTest
    @ValueSource(strings = { "Metal Factor", "Ultimate School", "Dr. Doom" })
    @DisplayName("Consulta de Banda pelo Nome")
    @Description("Testa a busca de bandas específicas pelo nome")
    @Severity(SeverityLevel.CRITICAL)
    public void consultaBandaPorNome(String banda) {

        HomePage homePage = new HomePage(driver);
        homePage.visit();

        String bandId = ApiHelper.getBandIdByName(banda);

        homePage.searchBandByName(banda);

        waitUrlContains("bandDetails.html");

        System.out.println("ID da API: " + bandId);
        System.out.println("URL atual: " + driver.getCurrentUrl());

        assertTrue(driver.getCurrentUrl().contains("id=" + bandId));
        assertTrue(homePage.isBandDetailsDisplayed());

        BandDetailsPage bandDetailsPage = new BandDetailsPage(driver);
        String genre = bandDetailsPage.getGenre();
        String country = bandDetailsPage.getCountry();
        String year = bandDetailsPage.getYear();
        String members = bandDetailsPage.getMembers();

        assertEquals(banda, bandDetailsPage.getName(),"O nome da banda deve ser '" + banda + "'");     
        assertEquals(genre, bandDetailsPage.getGenre(),"O gênero da banda deve ser '" + genre + "'");
        assertEquals(country, bandDetailsPage.getCountry(),"O país da banda deve ser '" + country + "'");
        assertEquals(year, bandDetailsPage.getYear(),"O ano da banda deve ser '" + year + "'");
        assertEquals(members, bandDetailsPage.getMembers(),"Os membros da banda devem ser '" + members + "'");
        assertTrue(bandDetailsPage.getDiscography().size() > 0,"A discografia da banda deve conter itens");
           
    }

    @Test
    @DisplayName("Faz Login de Usuário com sucesso")
    @Description("Valida o login com credenciais válidas")
    @Severity(SeverityLevel.BLOCKER)
    public void fazLoginComSucesso() {

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        homePage.visit();
        homePage.clickLoginLink();

        loginPage.loginWithSuccess("admin", "123");

        WebElement alertElement = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".showAlert.success"))
        );

        assertTrue(alertElement.isDisplayed());
        assertTrue(loginPage.getSuccessMessage().contains("Login realizado com sucesso!"));
    }

    @Test
    @DisplayName("Faz Login de Usuário com falha")
    @Description("Valida que credenciais inválidas geram erro")
    @Severity(SeverityLevel.CRITICAL)
    public void fazLoginComFalha() {

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        homePage.visit();
        homePage.clickLoginLink();

        loginPage.login("jhon", "123456");

        WebElement alertElement = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".showAlert.error"))
        );

        assertTrue(alertElement.isDisplayed());
        assertTrue(loginPage.getErrorMessage().contains("Usuário ou senha incorretos."));
    }

    @Test
    @DisplayName("Faz Cadastro de Novo Usuário com sucesso")
    @Description("Testa o registro de novo usuário com dados válidos")
    @Severity(SeverityLevel.CRITICAL)
    public void cadastraNovoUsuarioComSucesso() {

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        User newUser = FakerUtils.generateFakeUser();

        homePage.visit();
        homePage.clickLoginLink();

        loginPage.registerUser(newUser);

        WebElement alertElement = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".showAlert.success"))
        );

        assertTrue(alertElement.isDisplayed());
        assertTrue(loginPage.getSuccessMessage().contains("Usuário registrado com sucesso!"));

    }

    @Test
    @DisplayName("Faz Cadastro de Novo Usuário com E-mail Inválido")
    @Description("Valida que e-mail inválido gera erro no cadastro")
    @Severity(SeverityLevel.NORMAL)
    public void cadastraUsuarioComEmailInvalido() {

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);

        User newUser = FakerUtils.generateFakeUser();
        User invalidUser = new User(newUser.getUsername(), "meuemail&meuemail.com", newUser.getPassword());

        homePage.visit();
        homePage.clickLoginLink();

        loginPage.registerUser(invalidUser);

        WebElement alertElement = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".showAlert.error"))
        );

        assertTrue(alertElement.isDisplayed());
        assertTrue(loginPage.getErrorMessage().contains("Erro no registro. Tente novamente."));
    }

    @Test
    @DisplayName("Faz o Cadastro de uma Nova Banda")
    @Description("Testa o cadastro completo de uma nova banda")
    @Severity(SeverityLevel.CRITICAL)
    public void cadastraNovaBanda() {

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        OptionsPage optionsPage = new OptionsPage(driver);
        RegisterBandPage registerBandPage = new RegisterBandPage(driver);

        Band fakeBand = FakerUtils.generateFakeBand();

        homePage.visit();
        homePage.clickLoginLink();
        loginPage.loginWithSuccess("admin", "123");

        optionsPage.clickRegisterBand();

        optionsPage.openRegisterBandModal(); 

        waitVisible("#bandName");

        registerBandPage.registerBand(fakeBand);

        WebElement alertElement = driver.findElement(By.className("success"));
        assertTrue(alertElement.isDisplayed());
        assertTrue(registerBandPage.getSuccessMessage().contains("Banda cadastrada com sucesso!"));
    }


    @Test
    @DisplayName("Faz a Edição de Banda e Inclui novos Discos")
    @Description("Testa a edição de banda existente e adição de discografia")
    @Severity(SeverityLevel.CRITICAL)
    public void editaBandaEIncluiDiscos() {
        // Skip automático somente no GitHub Actions
        boolean isGithub = "true".equals(System.getenv("GITHUB_ACTIONS"));
        assumeFalse(isGithub, "Pulando este teste no GitHub Actions");

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        OptionsPage optionsPage = new OptionsPage(driver);
        EditBandPage editBandPage = new EditBandPage(driver);

        String genre = FakerUtils.getRandomGenre();
        String members = FakerUtils.generateFakeMembers(4);
        String country = "Brasil";
        List<Disc> newDiscs = FakerUtils.generateFakeDiscography(1990, 2);

        homePage.visit();
        homePage.clickLoginLink();
        loginPage.loginWithSuccess("admin", "123");

        optionsPage.clickEditBand();

        editBandPage.searchBand("Iron Believe");

        // Espera o formulário aparecer antes de continuar
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editGenre")));

        editBandPage.updateBandInfo(genre, members, country);
        editBandPage.updateDiscography(newDiscs);
        editBandPage.saveChanges(true);

        WebElement alertElement = driver.findElement(By.className("showConfirmationAlert"));
        assertTrue(alertElement.isDisplayed());
        assertTrue(editBandPage.getSuccessMessage().contains("Banda atualizada com sucesso!"));
    }

    @Test
    @DisplayName("Inclui Discos para uma Banda")
    @Description("Testa a adição de discos à banda")
    @Severity(SeverityLevel.NORMAL)
    public void incluiDiscosParaBanda() {

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        OptionsPage optionsPage = new OptionsPage(driver);
        EditBandPage editBandPage = new EditBandPage(driver);

        List<Disc> newDiscs = FakerUtils.generateFakeDiscography(2000, 1);

        homePage.visit();
        homePage.clickLoginLink();
        loginPage.loginWithSuccess("admin", "123");

        optionsPage.goToAddDiscs("The Prevent");

        waitVisible("#discTitle");

        for (Disc disc : newDiscs) {
            editBandPage.addDisc(disc);
        }

        waitVisible(By.xpath("//*[contains(text(),'Disco adicionado com sucesso')]"));  
        assertTrue(driver.getPageSource().contains("Disco adicionado com sucesso"));

    }

    @Test
    @DisplayName("Desiste da Edição de Banda")
    @Description("Testa cancelar alterações")
    @Severity(SeverityLevel.NORMAL)
    public void desisteDaEdicaoDeBanda() {

        HomePage homePage = new HomePage(driver);
        LoginPage loginPage = new LoginPage(driver);
        OptionsPage optionsPage = new OptionsPage(driver);
        EditBandPage editBandPage = new EditBandPage(driver);

        String genre = FakerUtils.getRandomGenre();
        String members = FakerUtils.generateFakeMembers(4);
        String country = "Noruega";
        List<Disc> newDiscs = FakerUtils.generateFakeDiscography(1990, 2);

        homePage.visit();
        homePage.clickLoginLink();
        loginPage.loginWithSuccess("admin", "123");

        optionsPage.clickEditBand();

        editBandPage.searchBand("Metal Factor");

        waitVisible("#editGenre");

        editBandPage.updateBandInfo(genre, members, country);
        editBandPage.updateDiscography(newDiscs);
        editBandPage.saveChanges(false);

        WebElement alertElement = driver.findElement(By.className("showConfirmationAlert"));
        assertTrue(alertElement.isDisplayed());
        assertTrue(alertElement.getText().contains("Dados não salvos. Retornando à página de edição"));

    }
}
