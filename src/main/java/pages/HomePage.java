package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class HomePage {

    private WebDriver driver;

    List<WebElement> listaProdutos = new ArrayList<>();

    private By produtos = By.className("product-description");

    private By testoProdutosNoCarrinho = By.className("cart-products-count");

    //usar . significa que é uma classe
    private By descricoesDosProdutos = By.cssSelector(".product-description a");

    private By precoDosProdutos = By.className("price");

    //usar # significa que é um ID
    private By botaoSignIn = By.cssSelector("#_desktop_user_info .hidden-sm-down");

    private By usuarioLogado = By.cssSelector("#_desktop_user_info span.hidden-sm-down");

    private By botaoSignOut = By.cssSelector("a.logout");

    public HomePage(WebDriver driver) {//construtor
        this.driver = driver;

    }

    public int contarProdutos() {
        carregarListaProdutos();
        return listaProdutos.size();
    }

    private void carregarListaProdutos() {
        listaProdutos = driver.findElements(produtos);
    }


    public int obterQuantidasdeProdutosNoCarrinho() {
        String quantidadeProdutosNoCarrinho = driver.findElement(testoProdutosNoCarrinho).getText();
        //a quantidade no site vem em formato de String e vem entre parenteses, então preciso tirar os parenteses e deixar somente o numero
        quantidadeProdutosNoCarrinho = quantidadeProdutosNoCarrinho.replace("(", "");
        quantidadeProdutosNoCarrinho = quantidadeProdutosNoCarrinho.replace(")", "");

        int qtdProdutosNoCarrinho = Integer.parseInt(quantidadeProdutosNoCarrinho);

        return qtdProdutosNoCarrinho;
    }

    public String obterNomeProduto(int indice){
        //vai pegar a lista de todos os elementor que tem o seletor: driver.findElements(descricoesDosProdutos)
        //depois vou pegar o texto dentro do incide que vou passar: get(indice).getText();
        //no caso, como quero o primeiro produto, então vou passar indice 0
        return driver.findElements(descricoesDosProdutos).get(indice).getText();

    }

    public String obterPrecoProduto(int indice){
        return driver.findElements(precoDosProdutos).get(indice).getText();
    }

    public ProdutoPage clicarProduto(int indice){
        //consigo clicar no nome do produto para entrar em outra página
        driver.findElements(descricoesDosProdutos).get(indice).click();
        return new ProdutoPage(driver); //retorna um produtopage para manter a interação.

    }

    public LoginPage clicarBotaoSignIn(){
        //retorna uma nova página, que no caso é a de login
        driver.findElement(botaoSignIn).click();
        return new LoginPage(driver);
    }

    public boolean estaLogado(String texto){
        //contentEquals: retorna um boolean
        //se os textos dentro das variáveis forem iguais (var: texto e var: usuariologado), ele retorna verdadeiro
      return  texto.contentEquals(driver.findElement(usuarioLogado).getText());
    }

    public void clicarBotaoSignOut(){
        driver.findElement(botaoSignOut).click();

    }

    public void carregarPaginaInicial() {
        driver.get("https://marcelodebittencourt.com/demoprestashop/");
    }

    public String obterTituloPaginaInicial() {
        return driver.getTitle();
    }

    public boolean estaLogado() {
        //se aparecer SIGIN IN siginifica que NÃO está logado, então na verdade se retornar verdadeiro (está aparecendo SIGN In) então vou negar para retornar false (significa que çao estou logado)
        return  !"Sign in".contentEquals(driver.findElement(usuarioLogado).getText());
    }
}