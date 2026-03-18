package pages;

import org.openqa.selenium.By;

public class CartPage extends BasePage {
    private final By cartTitle = By.cssSelector("#main h1, h1");
    private final By cartTotal = By.cssSelector(".cart-summary-line.cart-total span.value");
    private final By quantityField = By.cssSelector("input.js-cart-line-product-quantity");

    public boolean isCartTitleCorrect() {
        return getText(cartTitle).toLowerCase().contains("shopping cart");
    }

    public double getCartTotal() {
        return parsePrice(getText(cartTotal));
    }

    public int getQuantity() {
        String value = find(quantityField).getAttribute("value");
        return Integer.parseInt(value);
    }

    private double parsePrice(String raw) {
        String clean = raw.replace("PEN", "")
                .replace("$", "")
                .replace(",", "")
                .trim();
        return Double.parseDouble(clean);
    }
}
