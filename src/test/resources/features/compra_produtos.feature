# language: pt
Funcionalidade: Comprar Produtos
  Como um usuario logado
  Eu quero escolher um produto
  e visualizar esse produto no carrinho
  Para concluir o pedido

  @validacaoinicial
  Cenário: Deve mostrar uma lista de oito produtos na pagina incial
    Dado que estou na página inicial
    Quando nao estou logado
    Entao visualizo 8 produtos disponiveis
    E carrinho esta zerado

    @fluxopadrao
Esquema do Cenário: Deve mostrar produto escolhido confirmado
    Dado que estou na página inicial
    Quando estou logado
    E seleciono um produto na posicao <posicao>
    E nome do produto na tela principal e na tela do produto e <nomeProduto>
    E preco do produto na tela principal e na tela do produto e <precoProduto>
    E adiciono o produto no carrinho com tamanho <tamanhoProduto> cor <corProduto> e quantidade <qtdProduto>
    Entao o produto aparece na confirmacao com nome <nomeProduto> preco <precoProduto> tamanho <tamanhoProduto> cor <corProduto> e quantidade <qtdProduto>
Exemplos:
      |posicao|nomeProduto|precoProduto|tamanhoProduto|corProduto|qtdProduto|
      |  0    |"Hummingbird printed t-shirt"|"$19.12"|"M"|"Black"|2         |
      |1      |"Hummingbird Printed Sweater"|"$28.72" |"XL"|"N/A" |3         |