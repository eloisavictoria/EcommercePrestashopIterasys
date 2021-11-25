package steps;

import com.google.common.io.Files;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.opera.OperaDriver;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import pages.HomePage;
import pages.LoginPage;
import pages.ModalProdutoPage;
import pages.ProdutoPage;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class ComprarProdutosSteps {
    private static WebDriver driver;
    private HomePage homePage = new HomePage(driver);

    @Before
    public static void inicializar(){
        System.setProperty("webdriver.opera.driver", "C:\\webdriver\\operadriver\\81\\operadriver.exe");
        driver = new OperaDriver();

    }

    @Dado("que estou na página inicial")
    public void que_estou_na_página_inicial() {
        homePage.carregarPaginaInicial();
        assertThat(homePage.obterTituloPaginaInicial(), is("Loja de Teste"));
    }

    @Quando("nao estou logado")
    public void nao_estou_logado() {
       assertThat(homePage.estaLogado(), is(false));

    }

    @Entao("visualizo {int} produtos disponiveis")
    public void visualizo_produtos_disponiveis(Integer int1) {
        //verifica o tamanho da lista ed produtos
       assertThat(homePage.contarProdutos(), is(int1));
    }

    @Entao("carrinho esta zerado")
    public void carrinho_esta_zerado() {
       assertThat(homePage.obterQuantidasdeProdutosNoCarrinho(), is(0));
    }

    LoginPage loginPage;

    @Quando("estou logado")
    public void estou_logado() {
        //Clicar no botão sign in na horme page
        loginPage = homePage.clicarBotaoSignIn();

        //Preencher usuário e senha
        loginPage.preencherEmail("eloisa@teste.com");
        loginPage.preencherPassword("eloisa");
        //Clicar no botão sign in para logar
        loginPage.clicarBoataoSignIn();
        //Validar se o usuário está logado de fato
        assertThat(homePage.estaLogado("Eloisa Victoria"), is(true));

        homePage.carregarPaginaInicial();

    }
    ProdutoPage produtoPage;
    String nomeProdutos_HomePage;
    String precoProduto_HomePage;

    String nomeProduto_ProdutoPage;
    String precoProduto_ProdutoPage;

    @Quando("seleciono um produto na posicao {int}")
    public void seleciono_um_produto_na_posicao(Integer indice) {

        nomeProdutos_HomePage = homePage.obterNomeProduto(indice);
        precoProduto_HomePage = homePage.obterPrecoProduto(indice);

        /*System.out.println(nomeProdutos_HomePage);
        System.out.println(precoProduto_HomePage);*/

        produtoPage = homePage.clicarProduto(indice);

        nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
        precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();


    }

    @Quando("nome do produto na tela principal e na tela do produto e {string}")
    public void nome_do_produto_na_tela_principal_é(String nomeProduto) {
        //valida o nome do produto na pagina inicial
        assertThat(nomeProdutos_HomePage.toUpperCase(), is(nomeProduto.toUpperCase()));

        //valida nome do produto na página do produto
        assertThat(nomeProduto_ProdutoPage.toUpperCase(), is(nomeProduto.toUpperCase()));



    }

    @Quando("preco do produto na tela principal e na tela do produto e {string}")
    public void preco_do_produto_na_tela_principal_e(String precoProduto) {
        assertThat(precoProduto_HomePage, is(precoProduto.toUpperCase()));
        assertThat(precoProduto_ProdutoPage, is(precoProduto.toUpperCase()));
    }

    ModalProdutoPage modalProdutoPage;

    @Quando("adiciono o produto no carrinho com tamanho {string} cor {string} e quantidade {int}")
    public void adiciono_o_produto_no_carrinho_com_tamanho_cor_e_quantidade(String tamanhoproduto, String corproduto, Integer qtdproduto) {
        //Selecionar tamanho
        List<String> listaOpcoes = produtoPage.obterOpcoesSelecionadas();
        System.out.println(listaOpcoes.get(0));
        System.out.println("Tamanho da lista:" + listaOpcoes.size());

        produtoPage.selecionarOpcaoDropdown(tamanhoproduto);

        listaOpcoes = produtoPage.obterOpcoesSelecionadas();
        System.out.println(listaOpcoes.get(0));
        System.out.println("Tamanho da lista:" + listaOpcoes.size());

        //Selecionar cor
        if(!corproduto.equals("N/A")){ //se for diferente de NA então ele vai selecionar uma cor
            produtoPage.selecionarCorPreta();
        }
        //Selecionar quantidade
        produtoPage.alterarQuantidade(qtdproduto);

        //Adicionar no carrinho
        modalProdutoPage = produtoPage.clicarBotaoAddTocART();

        //Validações

        //esse asserthat não deu certo porque na mensagem do site tem um ícone na frente da frase
        //assertThat(modalProdutoPage.obterMnesagemProdutoAdicionado(), is("Product successfully added to your shopping cart"));

        assertTrue(modalProdutoPage.obterMnesagemProdutoAdicionado().endsWith("Product successfully added to your shopping cart"));
    }

    @Entao("o produto aparece na confirmacao com nome {string} preco {string} tamanho {string} cor {string} e quantidade {int}")
    public void o_produto_aparece_na_confirmacao_com_nome_preco_tamanho_cor_e_quantidade(String nomeProdutos, String precoProduto, String tamanhoProduto, String corProduto, Integer qtdProduto) {
        assertThat(modalProdutoPage.obterDescricaoProduto().toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));

        //valores encontrados são aqueles que encontrei usando os valores esperados
        Double precoProdutoDoubleEncontrado = Double.parseDouble(modalProdutoPage.obterPrecoProduto().replace("$",""));//converter a string em número

        //os valores esperados são aqueles que passei como parâmetro na hora que chamo o método
        Double precoProdutoDoubleEsperado = Double.parseDouble(precoProduto.replace("$",""));//converter a string em número

        assertThat(modalProdutoPage.obterTamanhoProduto(), is(tamanhoProduto));
        //não precisa validar cor do produto se não tem cor selecionada
        if(!corProduto.equals("N/A")){
            assertThat(modalProdutoPage.obterCorProduto(), is(corProduto));
        }

        assertThat(modalProdutoPage.obterQuantidadeProduto(), is(Integer.toString(qtdProduto)));


        String subtotalString =  modalProdutoPage.obterSubtotal();
        subtotalString = subtotalString.replace("$",""); //tirando o $ do valor do produto

        Double subtotalEsperado = Double.parseDouble(subtotalString);//converter a string em número

        Double subtotalCalculadoEsperado = qtdProduto * precoProdutoDoubleEsperado;//calculo do preço

        assertThat(subtotalEsperado, is(subtotalCalculadoEsperado));
        //subtotal: informação que pegamos da tela
        //subtotalCalculado: informação que calculamos no código pra saber se bate com o valor que é apresentado na tela




    }
    @After(order = 1) //define a ordem de execução do after, numeros maiores executam primeiro
    public void capturaTela(Scenario scenario){
        TakesScreenshot camera = (TakesScreenshot) driver;//TakesScreenshot vem do selenium
        File capturaDeTela = camera.getScreenshotAs(OutputType.FILE);
        System.out.println(scenario.getId());

        //pegando o ID, como ele termina com .feature no final, então eu pego essa última informação e somo no valor da string para poder pegar somente o ID do final ou seja, vai pegar somente o que vem depois do :
        String scenarioId = scenario.getId().substring(scenario.getId().lastIndexOf(".feature:")+ 9 );

        String nomeArquivo = "resources/screenshots/"+ scenario.getName() + "_" + scenarioId+ "_"+ scenario.getStatus() + ".png";
        System.out.println(nomeArquivo +" getname: "+scenario.getName());
        try {
            Files.move(capturaDeTela, new File(nomeArquivo));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @After(order = 0)
    public static void finalizar(){

        driver.quit();
    }

}
