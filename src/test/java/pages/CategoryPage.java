package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CategoryPage extends BasePage {
    private final By categoryTitle = By.cssSelector("#js-product-list-header h1, h1");
    private final By productCards = By.cssSelector(".product-miniature");
    private final By firstProductLink = By.cssSelector(".product-miniature a.thumbnail.product-thumbnail");

    public boolean isCategoryLoaded(String expectedText) {
        return getText(categoryTitle).toLowerCase().contains(expectedText.toLowerCase());
    }

    public boolean hasProducts() {
        List<WebElement> products = finds(productCards);
        return !products.isEmpty();
    }

    public void openFirstProduct() {
        click(firstProductLink);
    }
}
