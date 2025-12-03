package sistema.view;

import sistema.modelos.pessoa.usuario.Servidor;
import sistema.modelos.produto.*;
import sistema.servicos.*;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public class MenuServidor {

    private Servidor servidor;
    private EstoqueServicos estoqueServicos;
    private CompraServicos compraServicos;
    private RelatorioServicos relatorioServicos;
    private Scanner scanner;

    public MenuServidor(Servidor servidor, EstoqueServicos estoqueServicos,
                        CompraServicos compraServicos, RelatorioServicos relatorioServicos,
                        Scanner scanner) {
        this.servidor = servidor;
        this.estoqueServicos = estoqueServicos;
        this.compraServicos = compraServicos;
        this.relatorioServicos = relatorioServicos;
        this.scanner = scanner;
    }

    public void exibir() {
        boolean continuar = true;

        while (continuar) {
            servidor.exibirMenu();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1 -> gerenciarEstoque();
                case 2 -> processarVendas();
                case 3 -> consultarRelatorios();
                case 4 -> alterarSenha();
                case 5 -> {
                    System.out.println("\nVoltando ao menu principal...");
                    continuar = false;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void gerenciarEstoque() {
        System.out.println("\n ________________________________________");
        System.out.println("  |            GERENCIAR ESTOQUE           |");
        System.out.println("  |________________________________________|");
        System.out.println("  | 1. Adicionar produto                   |");
        System.out.println("  | 2. Remover produto                     |");
        System.out.println("  | 3. Atualizar produto                   |");
        System.out.println("  | 4. Listar produtos                     |");
        System.out.println("  |________________________________________|");

        int opcao = lerOpcao();

        switch (opcao) {
            case 1 -> adicionarProduto();
            case 2 -> removerProduto();
            case 3 -> atualizarProduto();
            case 4 -> estoqueServicos.listarDisponiveis();
        }
    }

    private void adicionarProduto() {
        System.out.println("\n1. Comestível");
        System.out.println("2. Bebida");
        System.out.print("Tipo: ");
        int tipo = lerOpcao();

        System.out.print("Código: ");
        String codigo = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Preço: ");
        double preco = Double.parseDouble(scanner.nextLine());
        System.out.print("Quantidade: ");
        int quantidade = lerOpcao();

        Produto produto = null;

        if (tipo == 1) {
            System.out.print("Dias até vencimento: ");
            int dias = lerOpcao();
            produto = new Comestivel(codigo, nome, preco, quantidade,
                    LocalDate.now().plusDays(dias), "Lanche");
        } else if (tipo == 2) {
            System.out.print("Volume (ml): ");
            double volume = Double.parseDouble(scanner.nextLine());
            produto = new Bebida(codigo, nome, preco, quantidade, volume, "Natural");
        }

        if (produto != null) {
            estoqueServicos.adicionar(produto);
        }
    }

    private void removerProduto() {
        System.out.print("\nCódigo do produto: ");
        String codigo = scanner.nextLine();
        estoqueServicos.remover(codigo);
    }

    private void atualizarProduto() {
        System.out.print("\nCódigo do produto: ");
        String codigo = scanner.nextLine();

        Produto produto = estoqueServicos.buscar(codigo);
        if (produto == null) {
            System.out.println("Produto não encontrado!");
            return;
        }

        System.out.print("Novo preço: ");
        double novoPreco = Double.parseDouble(scanner.nextLine());
        produto.setPreco(novoPreco);

        System.out.print("Ajustar estoque: ");
        int ajuste = lerOpcao();
        produto.atualizarEstoque(ajuste);

        estoqueServicos.atualizar(produto);
    }

    private void processarVendas() {
        System.out.println("\n[Processamento de vendas no caixa]");
    }

    private void consultarRelatorios() {
        System.out.println("\nRELATÓRIOS");
        relatorioServicos.gerarRelatorioEstoque(estoqueServicos.listarTodos());
        System.out.println("\nBônus do servidor: R$ " +
                String.format("%.2f", servidor.calcularBonus()));
    }

    private void alterarSenha() {
        System.out.print("\nSenha atual: ");
        String senhaAtual = scanner.nextLine();
        System.out.print("Nova senha: ");
        String novaSenha = scanner.nextLine();

        if (servidor.alterarSenha(senhaAtual, novaSenha)) {
            System.out.println("Senha alterada com sucesso!");
        } else {
            System.out.println("Senha atual incorreta!");
        }
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}