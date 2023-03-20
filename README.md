# sistemaDeCompras
Os ajustes que fiz no projeto em maioria foram em regras de negocio, para facilitar o entendimento tomei a liberdade de seprar em ordem de alteração(commits)

1 - Uma mensagem de Boas Vindas com o usuario logado é exibida para até quem está fazendo testes não ficar tão perdido. <br>
2 - Fechei o scanner que estava em aberto. <br>
3 - case default adicionado no switch, caso a escolha seja uma alternativa diferente das possibilidades, evitando um travamento. <br>

4 - Criação de uma função que lista o carrinho do usuario utlizando um for assim exibindo o index em vez do Id e o nome do produto para facilitar a exclusão futuramente. <br>
5 - O cliente pode ver seu carrinho antes de finalizar a compra, utilizando um forEach no carrinho e exibindo na tela. <br>
6 - O cliente pode remover um item do carrinho antes de finalizar a compra, assim que o item é removido do carrinho o estoque do produto é atualizado de acordo com a quantidade correspondente, utilizando o index comentado anteriormente a exclusão ficou bem simples. <br>
7 - Sempre que adicionado um produto no carrinho a quantidade de produto no estoque é atualizada. <br>
9 - Caso a empresa não tenha mais estoque do produto o cliente é avisado e impossibilitado de adicionar o produto no carrinho. <br>

9- A empresa agora tem seu saldo alterado de acordo com sua taxa, antes estava dando um saldo total. <br>
10- O estoque do produto é atualizado de acordo com a quantidade de itens no carrinho do usuario. <br>
11- Empresa pode verificar os produtos com estoque baixo para repor antes que acabe assim não gerando uma perda de ganhos <br>

12 - Admin pode ver lista de empresas e exibir detalhes
