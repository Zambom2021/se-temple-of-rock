package br.com.templeofrock.pages;

import br.com.templeofrock.models.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private By username = By.id("username");
    private By password = By.id("password");
    private By loginBtn = By.id("loginBtn");

    private By registerUsername = By.id("registerUsername");
    private By registerPassword = By.id("registerPassword");
    private By registerEmail = By.id("registerEmail");
    private By registerBtn = By.id("registerBtn");

    private By success = By.cssSelector(".success");
    private By error = By.cssSelector(".error");

    public LoginPage(WebDriver driver){
        super(driver);
    }

    public void loginWithSuccess(String user, String pass){
        type(username, user);
        type(password, pass);
        click(loginBtn);
    }

    public void login(String user, String pass){
        type(username, user);
        type(password, pass);
        click(loginBtn);
    }

    public void registerUser(User user){
        type(registerUsername, user.getUsername());
        type(registerPassword, user.getPassword());
        type(registerEmail, user.getEmail());
        click(registerBtn);
    }

    public boolean isSuccessMessageDisplayed(){
        return exists(success);
    }

    public boolean isErrorMessageDisplayed(){
        return exists(error);
    }

    public String getSuccessMessage(){
        return exists(success) ? getText(success) : "";
    }

    public String getErrorMessage(){
        return exists(error) ? getText(error) : "";
    }
}
