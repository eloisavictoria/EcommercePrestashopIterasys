package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CarrinhoPage {
    private WebDriver driver;

    private  By nomeProduto = By.cssSelector("div.product-line-info a");

    private  By precoProduto = By.cssSelector("span.price");

    private  By tamanhoProduto = By.xpath("//div[contains(@class, 'product-line-grid-body')]//div[3]/span[contains(@class,'value')]");

    private  By corProduto = By.xpath("//div[contains(@class, 'product-line-grid-body')]//div[4]/span[contains(@class,'value')]");

    private  By input_qtdProduto = By.cssSelector("input.js-cart-line-product-quantity");

    private  By subtotalProduto = By.cssSelector("span.product-price strong");

    private  By numeroItensTotal = By.cssSelector("span.js-subtotal");

    private  By subtotalTotal = By.cssSelector("#cart-subtotal-products span.value");

    private  By shippingTotal = By.cssSelector("#cart-subtotal-shipping span.value");

    private By totalTaxExclTotal = By.cssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(1)  span.value");

    private By totalTaxInclTotal = By.cssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(2)  span.value");

    private By taxes = By.cssSelector("div.cart-summary-totals div.cart-summary-line:nth-child(3)  span.value");

    private By botaoproceedCheckout = By.cssSelector("a.btn-primary");



    public CarrinhoPage(WebDriver driver){
        this.driver = driver;
    }

    public String obter_nomeProduto(){
        return driver.findElement(nomeProduto).getText();
    }

    public String obter_precoProduto(){
        return driver.findElement(precoProduto).getText();
    }

    public String obter_tamanhoProduto(){
        return driver.findElement(tamanhoProduto).getText();
    }

    public String obter_corProduto(){
        return driver.findElement(corProduto).getText();
    }

    public String obter_qtdProduto(){
        return driver.findElement(input_qtdProduto).getAttribute("value");
    }

    public String obter_subtotalProduto(){
        return driver.findElement(subtotalProduto).getText();
    }

    public String obter_numeroItensProduto(){
        return driver.findElement(numeroItensTotal).getText();
    }

    public String obter_subtotalTotalProduto(){
        return driver.findElement(subtotalTotal).getText();
    }
    public String obter_shippingProduto(){
        return driver.findElement(shippingTotal).getText();
    }

    public String obter_taxexclProduto(){
        return driver.findElement(totalTaxExclTotal).getText();
    }

    public String obter_taxinclProduto(){
        return driver.findElement(totalTaxInclTotal).getText();
    }

    public String obter_taxesProduto(){
        return driver.findElement(taxes).getText();
    }

    public CheckoutPage clicarBotaoProceedToCheckout() {
        driver.findElement(botaoproceedCheckout).click();
        return new CheckoutPage(driver);
    }




}
