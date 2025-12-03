package sistema.view;

import sistema.modelos.pessoa.usuario.Aluno;
import sistema.modelos.compra.Compra;
import sistema.modelos.produto.*;
import sistema.modelos.pagamento.*;
import sistema.servicos.*;
import java.util.Scanner;


public class MenuAluno {

    private Aluno aluno;
    private EstoqueServicos estoqueServicos;
    private CompraServicos compraServicos;
    private FluxoClienteServicos fluxoServicos;
    private Scanner scanner;

    public MenuAluno(Aluno aluno, EstoqueServicos estoqueServicos,
                     CompraServicos compraServicos, FluxoClienteServicos fluxoServicos,
                     Scanner scanner) {
        this.aluno = aluno;
        this.estoqueServicos = estoqueServicos;
        this.compraServicos = compraServicos;
        this.fluxoServicos = fluxoServicos;
        this.scanner = scanner;

        fluxoServicos.registrarEntrada(aluno.getCpf(), aluno.getNome());
    }

    public void exibir() {
        boolean continuar = true;

        while (continuar) {
            aluno.exibirMenu();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1 -> realizarCompra();
                case 2 -> consultarHistorico();
                case 3 -> alterarSenha();
                case 4 -> {
                    System.out.println("\nLogout realizado com sucesso!");
                    continuar = false;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void realizarCompra() {
        System.out.println("\n ___________________________________________ ");
        System.out.println("  |             NOVA COMPRA                   |");
        System.out.println("  |___________________________________________|");
        Compra compra = new Compra(aluno);
        boolean adicionandoItens = true;

        while (adicionandoItens) {
            estoqueServicos.listarDisponiveis();

            System.out.println("\nDigite o código do produto ou:");
            System.out.println("  'F' para finalizar compra");
            System.out.println("  'C' para cancelar");
            System.out.print("Opção: ");
            String codigo = scanner.nextLine().toUpperCase();

            if (codigo.equals("F")) {
                adicionandoItens = false;
                continue;
            }

            if (codigo.equals("C")) {
                System.out.println("Compra cancelada.");
                return;
            }

            Produto produto = estoqueServicos.buscar(codigo);

            if (produto == null) {
                System.out.println("Produto não encontrado!");
                continue;
            }

            System.out.println("\nProduto selecionado: " + produto.getNome());
            System.out.println("Preço: R$ " + String.format("%.2f", produto.calcularPrecoFinal()));
            System.out.println("Estoque disponível: " + produto.getQuantidadeEstoque());

            System.out.print("Quantidade desejada: ");
            int quantidade = lerOpcao();

            if (quantidade <= 0) {
                System.out.println("Quantidade inválida!");
                continue;
            }

            if (compra.adicionarItem(produto, quantidade)) {
                System.out.println("Item adicionado ao carrinho!");
            }
        }

        if (compra.getItens().isEmpty()) {
            System.out.println("Compra cancelada - carrinho vazio.");
            return;
        }

        // Aplicar desconto de aluno
        double descontoAluno = aluno.getDesconto() + (aluno.getSemestre() - 1) * 0.5;
        descontoAluno = Math.min(descontoAluno, 20.0);
        compra.aplicarDesconto(descontoAluno);

        compra.exibirResumo();

        System.out.println("\n ________________________________________ ");
        System.out.println("  |       FORMA DE PAGAMENTO               |");
        System.out.println("  |________________________________________|");
        System.out.println("  |  1. PIX                                |");
        System.out.println("  |  2. Dinheiro                           |");
        System.out.println("  |  3. Cartão de Crédito                  |");
        System.out.println("  |  4. Cartão de Débito                   |");
        System.out.println("  |  0. Cancelar Compra                    |");
        System.out.println("  |________________________________________|");

        int formaPagamento = lerOpcao();

        if (formaPagamento == 0) {
            System.out.println("Compra cancelada.");
            return;
        }

        FormaPagamento pagamento = criarPagamento(formaPagamento, compra.calcularTotal());

        if (pagamento != null && compra.finalizar(pagamento)) {
            pagamento.exibirComprovante();
            compraServicos.registrar(compra);
        }
    }

    private FormaPagamento criarPagamento(int tipo, double valor) {
        switch (tipo) {
            case 1 -> {
                System.out.print("\nChave PIX (email, telefone ou CPF): ");
                String chave = scanner.nextLine();
                return new Pix(valor, chave);
            }
            case 2 -> {
                System.out.printf("\nValor a pagar: R$ %.2f\n", valor);
                System.out.print("Valor entregue: R$ ");
                double valorPago = Double.parseDouble(scanner.nextLine());
                return new Dinheiro(valor, valorPago);
            }
            case 3 -> {
                System.out.print("\nNúmero do cartão: ");
                String numero = scanner.nextLine().replaceAll("[^0-9]", "");
                System.out.print("CVV: ");
                String cvv = scanner.nextLine();
                System.out.print("Número de parcelas: ");
                int parcelas = lerOpcao();
                return new Cartao(valor, numero, cvv, parcelas, Cartao.TipoCartao.CREDITO);
            }
            case 4 -> {
                System.out.print("\nNúmero do cartão: ");
                String numero = scanner.nextLine().replaceAll("[^0-9]", "");
                System.out.print("CVV: ");
                String cvv = scanner.nextLine();
                return new Cartao(valor, numero, cvv);
            }
            default -> {
                System.out.println("Forma de pagamento inválida!");
                return null;
            }
        }
    }

    private void consultarHistorico() {
        compraServicos.exibirHistorico(aluno);
    }

    private void alterarSenha() {
        System.out.println("\n ALTERAR SENHA ");
        System.out.print("Senha atual: ");
        String senhaAtual = scanner.nextLine();
        System.out.print("Nova senha: ");
        String novaSenha = scanner.nextLine();

        if (novaSenha.length() < 6) {
            System.out.println("Senha deve ter no mínimo 6 caracteres!");
            return;
        }

        if (aluno.alterarSenha(senhaAtual, novaSenha)) {
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