package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ProdutoPage {

    private WebDriver driver;

    private By nomeProduto = By.className("h1");

    private By precoProduto = By.cssSelector(".current-price span:nth-child(1)"); //pegar o preço sem SAVE 20%
    //PRA PEGAR O PRIMEIRO SPAN, A GENTE COLOCA NTH-CHILD(1) PRA PUXAR O PRIMEIRO FILHO DO SPAN, SE QUISER PEGAR O SEGUNDO SERIA NTH-CHILD(2)

    private By tamanhoProduto = By.id("group_1");

    private By inputCorPreta = By.xpath("//ul[@id='group_2']//input[@value='11']");

    private By qtdProduto = By.id("quantity_wanted");

    private By botaoAddToCart = By.className("add-to-cart");

    //construtor
    public ProdutoPage(WebDriver driver){
        this.driver = driver;
//manter a interação entre ass páginas
    }

    public String obterNomeProduto(){
        //findElement porque retorna somente um produto
        return driver.findElement(nomeProduto).getText();

    }

    public String obterPrecoProduto(){
        return driver.findElement(precoProduto).getText();
    }


    public void selecionarOpcaoDropdown(String opcao){
            encontrarDropdownSize().selectByVisibleText(opcao);//nome da opção selecionada no HTML

    }

    public void selecionarCorPreta(){
        driver.findElement(inputCorPreta).click();
    }
    //devolver todas as opções selecionadas no select do tamanho
    public List<String> obterOpcoesSelecionadas(){
        List<WebElement> elementosSelecionados = encontrarDropdownSize().getAllSelectedOptions();

        //pegar o texto e colocar em uma lista
        List<String> listaOpcoesSelecionadas = new ArrayList<>();

        //pra cada elementoSelecionado eu vou armazenar a informação dentro de um webelement
        for(WebElement elemento : elementosSelecionados){
            listaOpcoesSelecionadas.add(elemento.getText());//preenche a lista com o conteúdo
        }
        return listaOpcoesSelecionadas;
    }
    public void alterarQuantidade(int quantidade){
        driver.findElement(qtdProduto).clear();//limpar o conteúdo antes de ir a informação certa
        driver.findElement(qtdProduto).sendKeys(Integer.toString(quantidade));
    }

    public Select encontrarDropdownSize(){
        return new Select(driver.findElement(tamanhoProduto));

    }

    public ModalProdutoPage clicarBotaoAddTocART(){
        driver.findElement(botaoAddToCart).click();
        return new ModalProdutoPage(driver);
    }
}
