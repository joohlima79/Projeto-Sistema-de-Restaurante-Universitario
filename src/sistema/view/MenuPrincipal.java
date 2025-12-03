package sistema.view;

import sistema.modelos.pessoa.usuario.*;
import sistema.servicos.*;
import sistema.servicos.*;

import java.util.Scanner;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public class MenuPrincipal {

    private Scanner scanner;
    private UsuarioServicos usuarioServicos;
    private EstoqueServicos estoqueServicos;
    private CompraServicos compraServicos;
    private FluxoClienteServicos fluxoServicos;
    private RelatorioServicos relatorioServicos;

    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
        this.usuarioServicos = new UsuarioServicos();
        this.estoqueServicos = new EstoqueServicos();
        this.compraServicos = new CompraServicos();
        this.fluxoServicos = new FluxoClienteServicos();
        this.relatorioServicos = new RelatorioServicos();
    }

    public MenuPrincipal(UsuarioServicos usuarioServicos, EstoqueServicos estoqueServicos,
                         CompraServicos compraServicos, FluxoClienteServicos fluxoServicos,
                         RelatorioServicos relatorioServicos) {
        this.scanner = new Scanner(System.in);
        this.usuarioServicos = usuarioServicos;
        this.estoqueServicos = estoqueServicos;
        this.compraServicos = compraServicos;
        this.fluxoServicos = fluxoServicos;
        this.relatorioServicos = relatorioServicos;
    }

    public void iniciar() {
        System.out.println(" --------------------------------------------------------- ");
        System.out.println("|    SISTEMA DE GESTÃO DE RESTAURANTE UNIVERSITÁRIO       |");
        System.out.println("|_________________________________________________________|\n");

        boolean continuar = true;

        while (continuar) {
            exibirMenuPrincipal();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1 -> fazerLogin();
                case 2 -> cadastrarUsuario();
                case 0 -> {
                    System.out.println("\nEncerrando sistema...");
                    System.out.println("Até logo!");
                    continuar = false;
                }
                default -> System.out.println("Ppção inválida!");
            }
        }

        scanner.close();
    }

    private void exibirMenuPrincipal() {
        System.out.println("\n ________________________________________");
        System.out.println("  |          MENU PRINCIPAL                |");
        System.out.println("  |----------------------------------------|");
        System.out.println("  |  1. Login                              |");
        System.out.println("  |  2. Cadastrar usuário                  |");
        System.out.println("  |  0. Sair do sistema                    |");
        System.out.println("  |________________________________________|");
    }

    private int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void fazerLogin() {
        System.out.println("\nLOGIN");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario usuario = usuarioServicos.autenticar(login, senha);

        if (usuario != null) {
            System.out.println("\nLogin realizado com sucesso!");
            System.out.println("Bem vindo, " + usuario.getNome() + "!");

            redirecionarMenu(usuario);
        } else {
            System.out.println("\nLogin ou senha incorretos!");
        }
    }

    private void redirecionarMenu(Usuario usuario) {
        if (usuario instanceof Aluno) {
            new MenuAluno((Aluno) usuario, estoqueServicos, compraServicos,
                    fluxoServicos, scanner).exibir();
        } else if (usuario instanceof Servidor) {
            new MenuServidor((Servidor) usuario, estoqueServicos, compraServicos,
                    relatorioServicos, scanner).exibir();
        } else if (usuario instanceof Administrador) {
            new MenuAdministrador((Administrador) usuario, usuarioServicos,
                    estoqueServicos, compraServicos, fluxoServicos,
                    relatorioServicos, scanner).exibir();
        }
    }

    private void cadastrarUsuario() {
        System.out.println("\nCADASTRO DE USUÁRIO");
        System.out.println("Esta funcionalidade está disponível apenas para administradores no sistema.");
    }
}

