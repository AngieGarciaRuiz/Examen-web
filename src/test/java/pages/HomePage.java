package pages;

import org.openqa.selenium.By;
import utils.ConfigReader;

public class HomePage extends BasePage {
    private final By homeContent = By.id("content");
    private final By signInBtn = By.cssSelector("a[title='Log in to your customer account'], a[href*='login']");

    public void openStore() {
        driver.get(ConfigReader.get("base.url"));
    }

    public boolean isStoreLoaded() {
        return isVisible(homeContent);
    }

    public void goToLogin() {
        click(signInBtn);
    }

    public void goToCategory(String category, String subcategory) {
        By categoryLink = By.xpath("//a[contains(normalize-space(), '" + category + "')]");
        By subcategoryLink = By.xpath("//a[contains(normalize-space(), '" + subcategory + "')]");
        click(categoryLink);
        click(subcategoryLink);
    }
}
