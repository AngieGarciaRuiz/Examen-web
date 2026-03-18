package pages;

import org.openqa.selenium.By;

public class ProductPage extends BasePage {
    private final By quantityInput = By.id("quantity_wanted");
    private final By addToCartBtn = By.cssSelector("button.add-to-cart");
    private final By successModal = By.id("blockcart-modal");
    private final By successTitle = By.cssSelector("#blockcart-modal h4, .modal-title.h6.text-sm-center");
    private final By productPrice = By.cssSelector("span.current-price-value");
    private final By modalTotal = By.xpath("//*[contains(text(),'Total')]/following::span[contains(@class,'value')][1]");
    private final By checkoutBtn = By.cssSelector("#blockcart-modal a.btn.btn-primary, .cart-content-btn a.btn-primary");

    public double captureUnitPrice() {
        return parsePrice(getText(productPrice));
    }

    public void setQuantity(int qty) {
        write(quantityInput, String.valueOf(qty));
    }

    public void addToCart() {
        click(addToCartBtn);
    }

    public boolean isConfirmationDisplayed() {
        return isVisible(successModal) && getText(successTitle).toLowerCase().contains("successfully added");
    }

    public double getPopupTotal() {
        return parsePrice(getText(modalTotal));
    }

    public void proceedToCheckout() {
        click(checkoutBtn);
    }

    private double parsePrice(String raw) {
        String clean = raw.replace("PEN", "")
                .replace("$", "")
                .replace(",", "")
                .trim();
        return Double.parseDouble(clean);
    }
}
