package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import pages.CartPage;
import pages.CategoryPage;
import pages.HomePage;
import pages.LoginPage;
import pages.ProductPage;
import utils.ConfigReader;

public class StoreSteps {
    private final HomePage homePage = new HomePage();
    private final LoginPage loginPage = new LoginPage();
    private final CategoryPage categoryPage = new CategoryPage();
    private final ProductPage productPage = new ProductPage();
    private final CartPage cartPage = new CartPage();

    private boolean stopFlow = false;
    private double expectedTotal = 0.0;

    @Given("estoy en la página de la tienda")
    public void estoyEnLaPaginaDeLaTienda() {
        homePage.openStore();
        Assert.assertTrue("La página principal no cargó", homePage.isStoreLoaded());
    }

    @And("me logueo con mi usuario {string} y clave {string}")
    public void meLogueoConMiUsuarioYClave(String usuario, String clave) {
        String resolvedUser = ConfigReader.resolve(usuario);
        String resolvedPassword = ConfigReader.resolve(clave);

        homePage.goToLogin();
        loginPage.login(resolvedUser, resolvedPassword);

        if ("usuario_invalido".equals(usuario) || "clave_mala".equals(clave)) {
            Assert.assertTrue("Se esperaba error de autenticación", loginPage.isLoginErrorDisplayed());
            stopFlow = true;
        } else {
            Assert.assertTrue("Se esperaba login exitoso", loginPage.isLoginSuccessful());
        }
    }

    @When("navego a la categoria {string} y subcategoria {string}")
    public void navegoALaCategoriaYSubcategoria(String categoria, String subcategoria) {
        if (stopFlow) return;

        if ("Autos".equalsIgnoreCase(categoria)) {
            try {
                homePage.openStore();
                homePage.goToCategory(categoria, subcategoria);
                Assert.fail("La categoría inexistente no debería permitir continuar");
            } catch (Exception e) {
                stopFlow = true;
                Assert.assertTrue(true);
            }
            return;
        }

        homePage.openStore();
        homePage.goToCategory(categoria, subcategoria);
        Assert.assertTrue("No se cargó la subcategoría esperada", categoryPage.isCategoryLoaded(subcategoria));
        Assert.assertTrue("No hay productos listados en la categoría", categoryPage.hasProducts());
    }

    @And("agrego 2 unidades del primer producto al carrito")
    public void agrego2UnidadesDelPrimerProductoAlCarrito() {
        if (stopFlow) return;

        categoryPage.openFirstProduct();
        double unitPrice = productPage.captureUnitPrice();
        productPage.setQuantity(2);
        expectedTotal = unitPrice * 2;
        productPage.addToCart();
    }

    @Then("valido en el popup la confirmación del producto agregado")
    public void validoEnElPopupLaConfirmacionDelProductoAgregado() {
        if (stopFlow) return;
        Assert.assertTrue("No se mostró la confirmación de producto agregado", productPage.isConfirmationDisplayed());
    }

    @And("valido en el popup que el monto total sea calculado correctamente")
    public void validoEnElPopupQueElMontoTotalSeaCalculadoCorrectamente() {
        if (stopFlow) return;
        Assert.assertEquals("El monto del popup no coincide", expectedTotal, productPage.getPopupTotal(), 0.01);
    }

    @When("finalizo la compra")
    public void finalizoLaCompra() {
        if (stopFlow) return;
        productPage.proceedToCheckout();
    }

    @Then("valido el titulo de la pagina del carrito")
    public void validoElTituloDeLaPaginaDelCarrito() {
        if (stopFlow) return;
        Assert.assertTrue("El título del carrito es incorrecto", cartPage.isCartTitleCorrect());
    }

    @And("vuelvo a validar el calculo de precios en el carrito")
    public void vuelvoAValidarElCalculoDePreciosEnElCarrito() {
        if (stopFlow) return;
        Assert.assertEquals("El total del carrito no coincide", expectedTotal, cartPage.getCartTotal(), 0.01);
        Assert.assertEquals("La cantidad en carrito no es correcta", 2, cartPage.getQuantity());
    }
}
