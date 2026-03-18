package pages;

import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    private final By emailInput = By.id("field-email");
    private final By passwordInput = By.id("field-password");
    private final By signInBtn = By.id("submit-login");
    private final By myAccountMarker = By.xpath("//*[contains(text(),'Sign out') or contains(text(),'My account')]");
    private final By loginError = By.cssSelector(".alert.alert-danger");

    public void login(String email, String password) {
        write(emailInput, email);
        write(passwordInput, password);
        click(signInBtn);
    }

    public boolean isLoginSuccessful() {
        return isVisible(myAccountMarker);
    }

    public boolean isLoginErrorDisplayed() {
        return isVisible(loginError);
    }
}
