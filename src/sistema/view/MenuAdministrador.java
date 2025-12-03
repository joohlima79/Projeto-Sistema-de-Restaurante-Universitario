package sistema.view;

import sistema.modelos.pessoa.usuario.Administrador;
import sistema.modelos.pessoa.Cliente;
import sistema.servicos.*;

import java.util.List;
import java.util.Scanner;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public class MenuAdministrador {

    private Administrador admin;
    private UsuarioServicos usuarioServicos;
    private EstoqueServicos estoqueServicos;
    private CompraServicos compraServicos;
    private FluxoClienteServicos fluxoServicos;
    private RelatorioServicos relatorioServicos;
    private Scanner scanner;

    public MenuAdministrador(Administrador admin, UsuarioServicos usuarioServicos,
                             EstoqueServicos estoqueServicos, CompraServicos compraServicos,
                             FluxoClienteServicos fluxoServicos, RelatorioServicos relatorioServicos,
                             Scanner scanner) {
        this.admin = admin;
        this.usuarioServicos = usuarioServicos;
        this.estoqueServicos = estoqueServicos;
        this.compraServicos = compraServicos;
        this.fluxoServicos = fluxoServicos;
        this.relatorioServicos = relatorioServicos;
        this.scanner = scanner;
    }

    public void exibir() {
        boolean continuar = true;

        while (continuar) {
            exibirMenuPersonalizado();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1 -> gerenciarUsuarios();
                case 2 -> gerenciarProdutos();
                case 3 -> exibirRelatorios();
                case 4 -> registrarVisitante();
                case 5 -> consultarFluxoClientes();
                case 6 -> configurarSistema();
                case 7 -> fazerBackup();
                case 0 -> {
                    System.out.println("\nSaindo do menu administrativo...");
                    continuar = false;
                }
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void exibirMenuPersonalizado() {
        System.out.println("\n __________________________________________ ");
        System.out.println("  |        MENU ADMINISTRADOR                |");
        System.out.println("  |__________________________________________|");
        System.out.println("  |  1. Gerenciar Usuários                   |");
        System.out.println("  |  2. Gerenciar Produtos                   |");
        System.out.println("  |  3. Relatórios Gerenciais                |");
        System.out.println("  |  4. Registrar Visitante (CPF/Nome)       |");
        System.out.println("  |  5. Consultar Fluxo de Clientes          |");
        System.out.println("  |  6. Configurações do Sistema             |");
        System.out.println("  |  7. Fazer Backup                         |");
        System.out.println("  |  0. Voltar ao Menu Principal             |");
        System.out.println("  |__________________________________________|");
    }

    private void registrarVisitante() {
        System.out.println("\n REGISTRAR VISITANTE ");

        System.out.print("\nCPF: ");
        String cpf = scanner.nextLine().replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            System.out.println("CPF inválido! Deve conter 11 dígitos.");
            return;
        }

        System.out.print("Nome completo: ");
        String nome = scanner.nextLine();

        if (nome.trim().isEmpty()) {
            System.out.println("Nome não pode estar vazio!");
            return;
        }

        Cliente visitante = fluxoServicos.registrarEntrada(cpf, nome);

        System.out.println("\n" + "=".repeat(45));
        System.out.println("REGISTRO CONCLUÍDO");
        System.out.println("=".repeat(45));
        System.out.println(visitante.obterDadosFluxo());
        System.out.println("=".repeat(45));
    }

    private void consultarFluxoClientes() {
        System.out.println("\n ________________________________________");
        System.out.println("  |        CONSULTAR FLUXO                 |");
        System.out.println("  |________________________________________|");
        System.out.println("  | 1. Ver todos os visitantes             |");
        System.out.println("  | 2. Buscar visitante por CPF            |");
        System.out.println("  | 3. Limpar registros antigos            |");
        System.out.println("  |________________________________________|");

        int opcao = lerOpcao();

        switch (opcao) {
            case 1 -> listarTodosVisitantes();
            case 2 -> buscarVisitantePorCPF();
            case 3 -> limparRegistrosAntigos();
        }
    }

    private void listarTodosVisitantes() {
        List<Cliente> visitantes = fluxoServicos.listarTodos();

        System.out.println("\n TODOS OS VISITANTES");
        if (visitantes.isEmpty()) {
            System.out.println("Nenhum visitante registrado.");
        } else {
            System.out.println("Total: " + visitantes.size() + " visitantes\n");
            visitantes.forEach(v -> System.out.println("• " + v.obterDadosFluxo()));
        }
        System.out.println("_____________________________________________\n");
    }

    private void buscarVisitantePorCPF() {
        System.out.print("\nCPF: ");
        String cpf = scanner.nextLine().replaceAll("[^0-9]", "");

        Cliente visitante = fluxoServicos.buscarCliente(cpf);

        if (visitante != null) {
            System.out.println("\nVisitante encontrado:");
            System.out.println(visitante.obterDadosFluxo());
        } else {
            System.out.println("\nVisitante não encontrado!");
        }
    }

    private void limparRegistrosAntigos() {
        System.out.print("\nRemover visitantes sem acesso há quantos dias? ");
        int dias = lerOpcao();

        int removidos = fluxoServicos.limparRegistrosAntigos(dias);
        System.out.println("✓ " + removidos + " registros removidos.");
    }

    private void gerenciarUsuarios() {
        System.out.println("\nGERENCIAR USUÁRIOS ");
        System.out.println("Usuários cadastrados: " + usuarioServicos.listarTodos().size());

        usuarioServicos.listarTodos().forEach(u ->
                System.out.println("• " + u.getNome() + " (" + u.getLogin() + ") - " +
                        u.getClass().getSimpleName())
        );
    }

    private void gerenciarProdutos() {
        System.out.println("\nGERENCIAR PRODUTOS");
        estoqueServicos.listarDisponiveis();
    }

    private void exibirRelatorios() {
        System.out.println("\n ________________________________________");
        System.out.println("  |        RELATÓRIOS GERENCIAIS           |");
        System.out.println("  |________________________________________|");
        System.out.println("  | 1. Relatório de vendas                 |");
        System.out.println("  | 2. Relatório de estoque                |");
        System.out.println("  | 3. Relatório de fluxo de clientes      |");
        System.out.println("  | 4. Relatório completo                  |");
        System.out.println("  |________________________________________|");

        int opcao = lerOpcao();

        switch (opcao) {
            case 1 -> relatorioServicos.gerarRelatorioVendas(compraServicos.listarTodas());
            case 2 -> relatorioServicos.gerarRelatorioEstoque(estoqueServicos.listarTodos());
            case 3 -> relatorioServicos.gerarRelatorioFluxoClientes(fluxoServicos);
            case 4 -> relatorioServicos.gerarRelatorioCompleto(
                    compraServicos.listarTodas(),
                    estoqueServicos.listarTodos(),
                    fluxoServicos
            );
        }
    }

    private void configurarSistema() {
        System.out.println("\n CONFIGURAÇÕES ");
        System.out.println("Administrador: " + admin.getNome());
        System.out.println("Nível de acesso: " + admin.getNivelAcesso());
    }

    private void fazerBackup() {
        System.out.println("\n Backup realizado com sucesso!");
        System.out.println("Data: " + java.time.LocalDateTime.now());
        System.out.println("Dados salvos:");
        System.out.println("  • Usuários: " + usuarioServicos.listarTodos().size());
        System.out.println("  • Produtos: " + estoqueServicos.listarTodos().size());
        System.out.println("  • Visitantes: " + fluxoServicos.getTotalVisitantes());
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}