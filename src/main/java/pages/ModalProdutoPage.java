package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class ModalProdutoPage {

    private WebDriver driver;

    private By mensagemProdutoAdicionado = By.id("myModalLabel");

    private By descricaoProduto = By.className("product-name");

    private By precoProduto = By.cssSelector("div.modal-body p.product-price");

    private By listaValoresInformados = By.cssSelector("div.divide-right .col-md-6:nth-child(2) span strong");

    private By subtotal = By.cssSelector(".cart-content p:nth-child(2) span.value");

    private By botaoProcedToCheckout = By.cssSelector("div.cart-content-btn a.btn-primary");


    public ModalProdutoPage(WebDriver driver){
        this.driver = driver;
    }

    public String obterMnesagemProdutoAdicionado(){
        //inserir uma espera para que seja possível encontrar o cssselector do label que abre na tela
        FluentWait wait = new FluentWait(driver).withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        //withTimeout(Duration.ofSeconds(5): duração de espera de 5 segundos
        //pollingEvery(Duration.ofSeconds(1): verificação de um em um segundo
        //ignoring(NoSuchElementException.class): ignorar a exceção quando ele não achar o label
        //esse comando faz só igonra o erro, agora tempos que fazer a ação para disparar o wait

        wait.until(ExpectedConditions.visibilityOfElementLocated(mensagemProdutoAdicionado));
        //wait.until: esperar até que
        //ExpectedConditions: lista de todas as condições de espera
        //ExpectedConditions.visibilityOfElementLocated(mensagemProdutoAdicionado): esperar até que esteja visivel a mensagem de produto adicionado


        return driver.findElement(mensagemProdutoAdicionado).getText();

    }

    public String obterDescricaoProduto(){
     return driver.findElement(descricaoProduto).getText();

    }

    public String obterPrecoProduto(){
        return driver.findElement(precoProduto).getText();

    }
    public String obterTamanhoProduto(){
        //findElements: vai retornar vários elementos de um só vez. Retorna uma lista de webelements
        //get(0) vai trazer o primeiro elemento, no caso o nosso primeiro elemento é o tamanho(size)
        return driver.findElements(listaValoresInformados).get(0).getText();

    }

    public String obterCorProduto(){
        //findElements: vai retornar vários elementos de um só vez. Retorna uma lista de webelements
        //get(0) vai trazer o primeiro elemento, no caso o nosso segundo elemento é a cor

        //se o tamanho da lista for de 3 itens (significa que tem como selecionar a cor) ele executa o return pegando a segunda posição, que é onde fica a cor
        if(driver.findElements(listaValoresInformados).size() == 3){
            return driver.findElements(listaValoresInformados).get(1).getText();
        }else{//se não tiver como escolher a cor, então só vai ter duas posições, então ele vai retornar N/A pq não tem cor
            return "N/A";
        }



    }

    public String obterQuantidadeProduto(){
        //findElements: vai retornar vários elementos de um só vez. Retorna uma lista de webelements
        //get(0) vai trazer o primeiro elemento, no caso o nosso terceiro elemento é a quantidade
        //se não tiver cor, o campo da quantidade passa a ser o get(1) (que é a segunda posição, onde ficaria a cor).
        if(driver.findElements(listaValoresInformados).size() == 3) {
            return driver.findElements(listaValoresInformados).get(2).getText();
        }else{//se não tiver cor, então vai ser somente o tamanho (posição 0) e quantidade (posição 1)
            return driver.findElements(listaValoresInformados).get(1).getText();
        }
    }

    public String obterSubtotal(){
        return driver.findElement(subtotal).getText();
    }

    public CarrinhoPage clicarBotaoProcedToCheckout(){
        driver.findElement(botaoProcedToCheckout).click();

        return new CarrinhoPage(driver);

    }

}
