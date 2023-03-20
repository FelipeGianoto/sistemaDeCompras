import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {

		// SIMULANDO BANCO DE DADOS

		List<Produto> carrinho = new ArrayList<Produto>();
		List<Venda> vendas = new ArrayList<Venda>();

		Empresa empresa = new Empresa(2, "SafeWay Padaria", "30021423000159", 0.15, 0.0);
		Empresa empresa2 = new Empresa(1, "Level Varejo", "53239160000154", 0.05, 0.0);
		Empresa empresa3 = new Empresa(3, "SafeWay Restaurante", "41361511000116", 0.20, 0.0);

		Produto produto8 = new Produto(8, "Sonho", 5, 8.50, empresa);
		Produto produto9 = new Produto(9, "Croissant", 7, 6.50, empresa);
		Produto produto10 = new Produto(10, "Ché Gelado", 4, 5.50, empresa);
		Produto produto = new Produto(1, "Pão Frances", 5, 3.50, empresa);
		Produto produto2 = new Produto(2, "Coturno", 10, 400.0, empresa2);
		Produto produto3 = new Produto(3, "Jaqueta Jeans", 15, 150.0, empresa2);
		Produto produto4 = new Produto(4, "Calça Sarja", 15, 150.0, empresa2);
		Produto produto5 = new Produto(5, "Prato feito - Frango", 10, 25.0, empresa3);
		Produto produto6 = new Produto(6, "Prato feito - Carne", 10, 25.0, empresa3);
		Produto produto7 = new Produto(7, "Suco Natural", 30, 10.0, empresa3);

		Cliente cliente = new Cliente("07221134049", "Allan da Silva", "cliente", 20);
		Cliente cliente2 = new Cliente("72840700050", "Samuel da Silva", "cliente2", 24);

		Usuario usuario1 = new Usuario("admin", "1234", null, null);
		Usuario usuario3 = new Usuario("cliente", "1234", cliente, null);
		Usuario usuario4 = new Usuario("cliente2", "1234", cliente2, null);
		Usuario usuario2 = new Usuario("empresa", "1234", null, empresa);
		Usuario usuario5 = new Usuario("empresa2", "1234", null, empresa2);
		Usuario usuario6 = new Usuario("empresa3", "1234", null, empresa3);

		List<Usuario> usuarios = Arrays.asList(usuario1, usuario2, usuario3, usuario4, usuario5, usuario6);
		List<Cliente> clientes = Arrays.asList(cliente, cliente2);
		List<Empresa> empresas = Arrays.asList(empresa, empresa2, empresa3);
		List<Produto> produtos = Arrays.asList(produto, produto2, produto3, produto4, produto5, produto6, produto7,
				produto8, produto9, produto10);
		executar(usuarios, clientes, empresas, produtos, carrinho, vendas);
	}

	public static void executar(List<Usuario> usuarios, List<Cliente> clientes, List<Empresa> empresas,
			List<Produto> produtos, List<Produto> carrinho, List<Venda> vendas) {
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Scanner sc = new Scanner(System.in);

		System.out.println("Entre com seu usuário e senha:");
		System.out.print("Usuário: ");
		String username = sc.next();
		System.out.print("Senha: ");
		String senha = sc.next();

		List<Usuario> usuariosSearch = usuarios.stream().filter(x -> x.getUsername().equals(username))
				.collect(Collectors.toList());
		if (usuariosSearch.size() > 0) {
			Usuario usuarioLogado = usuariosSearch.get(0);

			if (usuarioLogado.getSenha().equals(senha)) {

				if (usuarioLogado.IsAdmin()) {
					System.out.println("\nAdmin\n");
				} else {
					System.out.println(
							"\nSeja Bem Vindo! " + (usuarioLogado.IsEmpresa() ? usuarioLogado.getEmpresa().getNome()
									: usuarioLogado.getCliente().getNome()) + "\n");
				}

				System.out.println("Escolha uma opção para iniciar");
				if (usuarioLogado.IsEmpresa()) {
					System.out.println("1 - Listar vendas");
					System.out.println("2 - Ver produtos");
					System.out.println("0 - Deslogar");
					Integer escolha = sc.nextInt();

					switch (escolha) {
					case 1: {
						System.out.println();
						System.out.println("************************************************************");
						System.out.println("VENDAS EFETUADAS");
						vendas.stream().forEach(venda -> {
							if (venda.getEmpresa().getId().equals(usuarioLogado.getEmpresa().getId())) {
								System.out.println("************************************************************");
								System.out.println("Venda de código: " + venda.getCódigo() + " no CPF "
										+ venda.getCliente().getCpf() + ": ");
								System.out.println("Data: " + dataFormatada.format(venda.getDataVenda()));
								venda.getItens().stream().forEach(x -> {
									System.out.println(x.getId() + " - " + x.getNome() + "    R$" + x.getPreco());
								});
								System.out.println("Total Venda: R$" + venda.getValor());
								System.out.println("Total Taxa a ser paga: R$" + venda.getComissaoSistema());
								System.out.println("Total Líquido  para empresa: R$"
										+ (venda.getValor() - venda.getComissaoSistema()));
								System.out.println("************************************************************");
							}

						});
						double taxa = vendas.stream().mapToDouble(Venda::getComissaoSistema).sum();
						System.out.println("Saldo Empresa: R$ " + (usuarioLogado.getEmpresa().getSaldo() - taxa));
						System.out.println("************************************************************");

						executar(usuarios, clientes, empresas, produtos, carrinho, vendas);
					}
					case 2: {
						System.out.println();
						System.out.println("************************************************************");
						System.out.println("MEUS PRODUTOS");
						produtos.stream().forEach(produto -> {
							if (produto.getEmpresa().getId().equals(usuarioLogado.getEmpresa().getId())) {
								System.out.println("************************************************************");
								System.out.println("Código: " + produto.getId());
								System.out.println("Produto: " + produto.getNome());
								System.out.println("Quantidade em estoque: " + produto.getQuantidade());
								System.out.println("Valor: R$" + produto.getPreco());
								System.out.println("************************************************************");
							}

						});
						System.out.println("Saldo Empresa: " + usuarioLogado.getEmpresa().getSaldo());
						System.out.println("************************************************************");

						executar(usuarios, clientes, empresas, produtos, carrinho, vendas);
					}
					default: {
						executar(usuariosSearch, clientes, empresas, produtos, carrinho, vendas);
					}
					}

				} else if (usuarioLogado.IsCliente()) {
					
					System.out.println("1 - Relizar Compras");
					System.out.println("2 - Ver Compras");
					System.out.println("0 - Deslogar");
					Integer escolha = sc.nextInt();
					switch (escolha) {
					case 1: {
						System.out.println("Para realizar uma compra, escolha a empresa onde deseja comprar: ");
						empresas.stream().forEach(x -> {
							System.out.println(x.getId() + " - " + x.getNome());
						});
						Integer escolhaEmpresa = sc.nextInt();
						Integer escolhaProduto = -1;
						do {
							System.out.println("Escolha os seus produtos: ");
							produtos.stream().forEach(x -> {
								if (x.getEmpresa().getId().equals(escolhaEmpresa)) {
									System.out.println(x.getId() + " - " + x.getNome());
								}
							});
							int size = produtos.size();
							int listCarrinho = size + 1;
							int removerItem = listCarrinho + 1;
							System.out.println(listCarrinho + " - Listar carrinho");
							System.out.println(removerItem + " - Remover item");
							System.out.println("0 - Finalizar compra");
							escolhaProduto = sc.nextInt();
							if (escolhaProduto == listCarrinho) {
								listCarrinho(carrinho);
							} else if (escolhaProduto == removerItem) {
								listCarrinho(carrinho);
								System.out.println("Escolha o produto que deseja remover: ");
								int removerProduto = sc.nextInt();
								Produto produto = carrinho.remove(removerProduto);

								for (Produto produtoSearch : produtos) {
									if (produtoSearch.getId().equals(produto.getId())) {
										produtoSearch.setQuantidade(produtoSearch.getQuantidade() + 1);
									}
								}

							} else {
								for (Produto produtoSearch : produtos) {
									if (produtoSearch.getId().equals(escolhaProduto)) {
										if (produtoSearch.getQuantidade() > 0) {
											carrinho.add(produtoSearch);
											produtoSearch.setQuantidade(produtoSearch.getQuantidade() - 1);
										} else {
											System.out.println("************************************************************");
											System.out.println("\n Produto Fora de Estoque! \n");
											System.out.println("************************************************************");
										}
									}
								}

							}
						} while (escolhaProduto != 0);

						System.out.println("************************************************************");
						System.out.println("Resumo da compra: ");

						carrinho.stream().forEach(x -> {
							if (x.getEmpresa().getId().equals(escolhaEmpresa)) {
								System.out.println(x.getId() + " - " + x.getNome() + "    R$" + x.getPreco());
							}
						});
						Empresa empresaEscolhida = empresas.stream().filter(x -> x.getId().equals(escolhaEmpresa))
								.collect(Collectors.toList()).get(0);
						Cliente clienteLogado = clientes.stream()
								.filter(x -> x.getUsername().equals(usuarioLogado.getUsername()))
								.collect(Collectors.toList()).get(0);
						Venda venda = criarVenda(carrinho, empresaEscolhida, clienteLogado, vendas);
						System.out.println("Total: R$" + venda.getValor());
						System.out.println("************************************************************");
						carrinho.clear();
						executar(usuarios, clientes, empresas, produtos, carrinho, vendas);
					}
					case 2: {
						System.out.println();
						System.out.println("************************************************************");
						System.out.println("COMPRAS EFETUADAS");
						vendas.stream().forEach(venda -> {
							if (venda.getCliente().getUsername().equals(usuarioLogado.getUsername())) {
								System.out.println("************************************************************");
								System.out.println("Compra de código: " + venda.getCódigo() + " na empresa "
										+ venda.getEmpresa().getNome() + ": ");
								System.out.println("Data: " + dataFormatada.format(venda.getDataVenda()));
								venda.getItens().stream().forEach(x -> {
									System.out.println(x.getId() + " - " + x.getNome() + "    R$" + x.getPreco());
								});
								System.out.println("Total: R$" + venda.getValor());
								System.out.println("************************************************************");
							}

						});
						executar(usuarios, clientes, empresas, produtos, carrinho, vendas);
					}
					default: {
						executar(usuariosSearch, clientes, empresas, produtos, carrinho, vendas);
					}
					}
				
				} else {
					System.out.println("1 - Listar Empresas");
					System.out.println("0 - Deslogar");
					Integer escolha = sc.nextInt();
					switch (escolha) {
					case 1: {
						System.out.println("************************************************************");
						System.out.println("Empresas: \n");
						empresas.forEach((empresa) -> {
							System.out.println(empresa.getId() + " - " + empresa.getNome());
						});
						System.out.println("************************************************************");
						System.out.println("Escolha a empresa que deseja visualizar");
						Integer empresa = sc.nextInt();
						for (Empresa empresaSearch : empresas) {
							if (empresaSearch.getId().equals(empresa)) {
								System.out.println("************************************************************");
								System.out.println("Nome: " + empresaSearch.getNome());
								System.out.println("Cnpj: " + empresaSearch.getCnpj());
								System.out.println("Saldo: " + empresaSearch.getSaldo());
								System.out.println("Taxa: " + empresaSearch.getTaxa());
								System.out.println("************************************************************");
							}
						}
					}
					default: {
						executar(usuariosSearch, clientes, empresas, produtos, carrinho, vendas);
					}
					}
				}
				sc.close();
			} else
				System.out.println("Senha incorreta");
		} else {
			System.out.println("Usuário não encontrado");
		}
	}

	public static Venda criarVenda(List<Produto> carrinho, Empresa empresa, Cliente cliente, List<Venda> vendas) {
		Double total = carrinho.stream().mapToDouble(Produto::getPreco).sum();
		Double comissaoSistema = total * empresa.getTaxa();
		int idVenda = vendas.isEmpty() ? 1 : vendas.get(vendas.size() - 1).getCódigo() + 1;
		Venda venda = new Venda(idVenda, carrinho.stream().toList(), total, comissaoSistema, empresa, cliente,
				new Date());
		empresa.setSaldo(empresa.getSaldo() + total);
		vendas.add(venda);
		return venda;
	}

	public static void listCarrinho(List<Produto> carrinho) {

		System.out.println("Carrinho\n");
		for (int i = 0; i < carrinho.size(); i++) {
			Produto produto = carrinho.get(i);
			System.out.println(i + " - " + produto.getNome() + " R$" + produto.getPreco());
		}
		System.out.println("\nValor Total: " + carrinho.stream().mapToDouble(Produto::getPreco).sum() + "R$");
		System.out.println("************************************************************");
	}
}
