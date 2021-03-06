package homepage;

import base.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import pages.*;
import util.Funcoes;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HomePageTests extends BaseTest {

    ProdutoPage produtoPage;
    LoginPage loginPage;
    String nomeProduto_ProdutoPage;
    ModalProdutoPage modalProdutoPage;
    CarrinhoPage carrinhoPage;

    @Test
    public void testcontarprodutos_oitoProdutosDiferentes(){
        carregarPaginaInicial();
        assertThat(homePage.contarProdutos(), is (8));

    }
    @Test
    public void testValidarCarrinhoZerado_ZeroItensNoCarrinho(){
        int produtosNoCarrinho = homePage.obterQuantidasdeProdutosNoCarrinho();
        //  System.out.println(produtosNoCarrinho);
        assertThat(produtosNoCarrinho, is(0));
    }

    @Test
    public void testValidarDetalhesDoProdutos_DescricaoEValorIguais(){
        int indice = 0;
        String nomeProdutos_HomePage = homePage.obterNomeProduto(indice);
        String precoProduto_HomePage = homePage.obterPrecoProduto(indice);

        System.out.println(nomeProdutos_HomePage);
        System.out.println(precoProduto_HomePage);

        produtoPage = homePage.clicarProduto(indice);

        nomeProduto_ProdutoPage = produtoPage.obterNomeProduto();
        String precoProduto_ProdutoPage = produtoPage.obterPrecoProduto();

        System.out.println(nomeProduto_ProdutoPage);
        System.out.println(precoProduto_ProdutoPage);

        //comparar os textos
        assertThat(nomeProdutos_HomePage.toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));
        assertThat(precoProduto_HomePage, is(precoProduto_ProdutoPage));
    }

    @Test
    public void testLoginComSucesso_UsuarioLogado(){
        //Clicar no bot??o sign in na horme page
        loginPage = homePage.clicarBotaoSignIn();

        //Preencher usu??rio e senha
        loginPage.preencherEmail("eloisa@teste.com");
        loginPage.preencherPassword("eloisa");
        //Clicar no bot??o sign in para logar
        loginPage.clicarBoataoSignIn();
        //Validar se o usu??rio est?? logado de fato
        assertThat(homePage.estaLogado("Eloisa Victoria"), is(true));

        carregarPaginaInicial();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/massaTeste_Login.csv", numLinesToSkip = 1, delimiter = ';')
    //pegando o arquivo CSV e pulando a primeira linha que ?? o cabe??alho
    //e falo que o delimitado ?? o ;
    public void testLogin_UsuarioLogadoComDadosValidos(String nomeTeste, String email, String password, String nomeUsuario, String resultado){
        //sequencia das striings deve ser a mesma que consta no arquivo csv
        //Clicar no bot??o sign in na horme page
        loginPage = homePage.clicarBotaoSignIn();

        //Preencher usu??rio e senha
        loginPage.preencherEmail(email);
        loginPage.preencherPassword(password);
        //Clicar no bot??o sign in para logar
        loginPage.clicarBoataoSignIn();

        boolean esperado_loginOk;
        if(resultado.equals("positivo")){
            esperado_loginOk=true;
        }else{
            esperado_loginOk=false;
        }
        //Validar se o usu??rio est?? logado de fato
        assertThat(homePage.estaLogado(nomeUsuario), is(esperado_loginOk));

        capturaTela(nomeTeste, resultado);

        //se o login der ceto, eu preciso sair dele para poder fazer o outro teste com outros dados de login

        if(esperado_loginOk){
            homePage.clicarBotaoSignOut();
        }
        carregarPaginaInicial();
    }

    @Test
    public void testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso(){
        //Vari??veis
        String tamanhoProduto = "M";
        String corPreta = "Black";
        int quantidadeProduto = 2;
        //Pr??-condi????o
        //Verificar se est?? logado
        if(!homePage.estaLogado("Eloisa Victoria")){//se n??o estiver logado, ent??o tenho que entrar
            testLoginComSucesso_UsuarioLogado();
        }
        //Teste
        //Selecionando produto
        testValidarDetalhesDoProdutos_DescricaoEValorIguais();

        //Selecionar tamanho
        List<String> listaOpcoes = produtoPage.obterOpcoesSelecionadas();
        System.out.println(listaOpcoes.get(0));
        System.out.println("Tamanho da lista:" + listaOpcoes.size());

        produtoPage.selecionarOpcaoDropdown(tamanhoProduto);

        listaOpcoes = produtoPage.obterOpcoesSelecionadas();
        System.out.println(listaOpcoes.get(0));
        System.out.println("Tamanho da lista:" + listaOpcoes.size());

        //Selecionar cor
        produtoPage.selecionarCorPreta();
        //Selecionar quantidade
        produtoPage.alterarQuantidade(quantidadeProduto);

        //Adicionar no carrinho
        modalProdutoPage = produtoPage.clicarBotaoAddTocART();

        //Valida????es

        //esse asserthat n??o deu certo porque na mensagem do site tem um ??cone na frente da frase
        //assertThat(modalProdutoPage.obterMnesagemProdutoAdicionado(), is("Product successfully added to your shopping cart"));

        assertTrue(modalProdutoPage.obterMnesagemProdutoAdicionado().endsWith("Product successfully added to your shopping cart"));
/*
        System.out.println(modalProdutoPage.obterTamanhoProduto());
        System.out.println(modalProdutoPage.obterCorProduto());
        System.out.println(modalProdutoPage.obterQuantidadeProduto());*/

        System.out.println(modalProdutoPage.obterDescricaoProduto());

        assertThat(modalProdutoPage.obterDescricaoProduto().toUpperCase(), is(nomeProduto_ProdutoPage.toUpperCase()));

        String precoProdutoString =  modalProdutoPage.obterPrecoProduto();
        precoProdutoString = precoProdutoString.replace("$",""); //tirando o $ do valor do produto

        Double precoProduto = Double.parseDouble(precoProdutoString);//converter a string em n??mero



        System.out.println(modalProdutoPage.obterCorProduto());


        System.out.println(modalProdutoPage.obterSubtotal());
        String subtotalString =  modalProdutoPage.obterSubtotal();
        subtotalString = subtotalString.replace("$",""); //tirando o $ do valor do produto

        Double subtotal = Double.parseDouble(subtotalString);//converter a string em n??mero

        Double subtotalCalculado = quantidadeProduto * precoProduto;//calculo do pre??o

        assertThat(subtotal, is(subtotalCalculado));
        //subtotal: informa????o que pegamos da tela
        //subtotalCalculado: informa????o que calculamos no c??digo pra saber se bate com o valor que ?? apresentado na tela


        assertThat(modalProdutoPage.obterTamanhoProduto(), is(tamanhoProduto));
        assertThat(modalProdutoPage.obterCorProduto(), is(corPreta));
        assertThat(modalProdutoPage.obterQuantidadeProduto(), is(Integer.toString(quantidadeProduto)));

    }

    //Valores esperados

    String esperado_nomeProduto = "Hummingbird printed t-shirt";
    Double esperado_precoProduto =19.12;
    String eperado_tamanhoProduto="M";
    String esperado_corProduto="Black";
    int esperado_qtdProduto=2;
    Double esperado_subtotalProduto= esperado_precoProduto * esperado_qtdProduto;

    int esperado_numeroItensProduto=2;
    Double esperado_subtotalTotalProduto=esperado_subtotalProduto;
    Double esperado_shippingProduto=7.00;//valores fixos
    Double esperado_taxexclProduto=esperado_subtotalTotalProduto + esperado_shippingProduto;
    Double esperado_taxinclProduto=esperado_taxexclProduto;
    Double esperado_taxesProduto=0.00; //valores fixos

    String esperado_nomecliente = "Eloisa Victoria";

    @Test
    public void IrParCarrinho_InformacoesPersistidas(){
        //Pr?? condi????es
        //Produto incluido na tela ModalProdutoPage
        testIncluirProdutoNoCarrinho_ProdutoIncluidoComSucesso();

        carrinhoPage = modalProdutoPage.clicarBotaoProcedToCheckout(); //quando clica no bot??o ele retorna uma outra pagina

        //Teste

        //Validar todos os elementos da tela
        System.out.println("*******IMPRIMINDO VALORES NA TELA**********");
        System.out.println(carrinhoPage.obter_nomeProduto());
        System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()));
        System.out.println(carrinhoPage.obter_tamanhoProduto());
        System.out.println(carrinhoPage.obter_corProduto());
        System.out.println(carrinhoPage.obter_qtdProduto());
        System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalProduto()));
        System.out.println("*******IMPRIMINDO TOTAIS NA TELA**********");
        System.out.println(Funcoes.removeTextoItensDevolveInt(carrinhoPage.obter_numeroItensProduto()));
        System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingProduto()));
        System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxexclProduto()));
        System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxinclProduto()));
        System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxesProduto()));
        System.out.println(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotalProduto()));

        //ASSER????ES HAMCREST
        assertThat(carrinhoPage.obter_nomeProduto(),is(esperado_nomeProduto));
        assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()),is(esperado_precoProduto));
        assertThat(carrinhoPage.obter_tamanhoProduto(),is(eperado_tamanhoProduto));
        assertThat(carrinhoPage.obter_corProduto(),is(esperado_corProduto));
        assertThat(Integer.parseInt(carrinhoPage.obter_qtdProduto()),is(esperado_qtdProduto));
        assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalProduto()),is(esperado_subtotalProduto));
        assertThat(Funcoes.removeTextoItensDevolveInt(carrinhoPage.obter_numeroItensProduto()),is(esperado_numeroItensProduto));
        assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotalProduto()),is(esperado_subtotalTotalProduto));
        assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingProduto()),is(esperado_shippingProduto));
        assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxexclProduto()),is(esperado_taxexclProduto));
        assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxinclProduto()),is(esperado_taxinclProduto));
        assertThat(Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxesProduto()),is(esperado_taxesProduto));

        //Assert Junit
/*
     assertEquals(esperado_nomeProduto, carrinhoPage.obter_nomeProduto());
     assertEquals(esperado_precoProduto, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_precoProduto()));
        assertEquals(eperado_tamanhoProduto, carrinhoPage.obter_tamanhoProduto());
        assertEquals(esperado_corProduto, carrinhoPage.obter_corProduto());
        assertEquals(esperado_qtdProduto, carrinhoPage.obter_qtdProduto());
        assertEquals(esperado_subtotalProduto, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalProduto()));
        assertEquals(esperado_numeroItensProduto, Funcoes.removeTextoItensDevolveInt(carrinhoPage.obter_numeroItensProduto()));
        assertEquals(esperado_subtotalTotalProduto, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_subtotalTotalProduto()));
        assertEquals(esperado_shippingProduto, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_shippingProduto()));
        assertEquals(esperado_taxexclProduto, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxexclProduto()));
        assertEquals(esperado_taxinclProduto, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxesProduto()));
        assertEquals(esperado_taxesProduto, Funcoes.removeCifraoDevolveDouble(carrinhoPage.obter_taxesProduto()));
*/
    }
    CheckoutPage checkoutPage;

    @Test
    public void IrParaCheckout_FreteMeioPagamentoEnderecoListaOk(){
        //Pr??-condi????es

        //Produto dispon??vel no carrinho de compras
        IrParCarrinho_InformacoesPersistidas();
        //Teste
        //Clicar no bot??o
        checkoutPage = carrinhoPage.clicarBotaoProceedToCheckout();

        //Preencher informa????es
        //Validar infoma????es na tela
        assertThat(Funcoes.removeCifraoDevolveDouble(checkoutPage.obter_totalTaxIncTotal()), is(esperado_taxinclProduto));
        //assertThat(checkoutPage.obter_nomeCliente(), is(esperado_nomecliente));

        //Assert do Junit
        assertTrue(checkoutPage.obter_nomeCliente().startsWith(esperado_nomecliente));
        checkoutPage.clicarBotaoContinueAddress();
        //System.out.println("Continuar:"+ checkoutPage.clicarBotaoContinueAddress());

        String encontrado_shippingValor = checkoutPage.obter_shippingValor();
        System.out.println("Shipping:"+ encontrado_shippingValor);
        encontrado_shippingValor= Funcoes.removeTexto(encontrado_shippingValor," tax excl.");//removendo o texto da variavel
        Double encontrado_shippingValor_Double = Funcoes.removeCifraoDevolveDouble(encontrado_shippingValor);//tirando o cifr??o para trazer somente o valor

        assertThat(encontrado_shippingValor_Double, is(esperado_shippingProduto));

        checkoutPage.clicarBotaoContinueShipping();

        //assertThat(checkoutPage.obter_shippingValor(), is(esperado_shippingProduto));

        //Selecionar op????o Pay by Check
        checkoutPage.selecionarRadioPayByCheck();
        //Validar valor do cheque
        String encontrado_paybycheck = checkoutPage.obter_amountPayByCheck();
        encontrado_paybycheck = Funcoes.removeTexto(encontrado_paybycheck, " (tax incl.)");

        Double encontrado_paybycheck_Double = Funcoes.removeCifraoDevolveDouble(encontrado_paybycheck);

        assertThat(encontrado_paybycheck_Double, is(esperado_taxinclProduto));

        //Clicar na op????o I agree
        checkoutPage.selecionarCheckBoxIAgree();
        assertTrue(checkoutPage.estaSelecionadoCheckBoxIAgree());


    }

    @Test
    public void finalizarPedido_pedidoFinalizadoComSucesso(){
        //Pr??-condi????es
        //Checkout completamente conclu??do
        IrParaCheckout_FreteMeioPagamentoEnderecoListaOk();

        //Teste
        //Clicar no bot??o para confirmar o pedido
        PedidoPage pedidoPage = checkoutPage.clicarBotaoConfirmaPedido();


        //Validar valores da tela

        assertTrue(pedidoPage.obter_textoPedidoConfirmado().endsWith("YOUR ORDER IS CONFIRMED"));
        //assertThat(pedidoPage.obter_textoPedidoConfirmado().toUpperCase(), is("Your order is confirmed"));

        assertThat(pedidoPage.obter_email(), is("eloisa@teste.com"));

        assertThat(pedidoPage.obter_TotalProdutos(), is(esperado_subtotalProduto));

        assertThat(pedidoPage.obter_totalTaxIncl(), is(esperado_taxinclProduto));

        assertThat(pedidoPage.obter_metodoPagamento(), is("Payments by check"));

    }

}
