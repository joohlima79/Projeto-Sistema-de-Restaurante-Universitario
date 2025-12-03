package sistema;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 * @since 2025-11-01
 */

import sistema.modelos.pessoa.usuario.*;
import sistema.modelos.produto.*;
import sistema.servicos.*;
import sistema.view.MenuPrincipal;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        UsuarioServicos usuarioServicos = new UsuarioServicos();
        EstoqueServicos estoqueServicos = new EstoqueServicos();
        CompraServicos compraServicos = new CompraServicos();
        FluxoClienteServicos fluxoServicos = new FluxoClienteServicos();
        RelatorioServicos relatorioServicos = new RelatorioServicos();

        inicializarSistema(usuarioServicos, estoqueServicos, fluxoServicos);

        MenuPrincipal menu = new MenuPrincipal(
                usuarioServicos, estoqueServicos, compraServicos,
                fluxoServicos, relatorioServicos
        );
        menu.iniciar();
    }

    private static void inicializarSistema (UsuarioServicos usuarioServicos,
                                           EstoqueServicos estoqueServicos,
                                           FluxoClienteServicos fluxoClienteServicos) {
        System.out.println("Inicializando sistema...\n");

        System.out.println("Criando usuários do sistema...");

        Aluno aluno1 = new Aluno(
                "12345678901",
                "João Pedro Silva Santos",
                LocalDate.of(2000, 5, 15),
                "joao.silva",
                "senha123",
                "2024001",
                "Engenharia de Software"
        );
        aluno1.setSemestre(3);

        Aluno aluno2 = new Aluno(
                "99988877766",
                "Pedro Santos Oliveira",
                LocalDate.of(2001, 8, 20),
                "pedro.santos",
                "senha456",
                "2024002",
                "Ciência da Computação"
        );

        Servidor servidor1 = new Servidor(
                "98765432100",
                "Carlos Eduardo Souza Lima",
                LocalDate.of(1985, 3, 10),
                "carlos.souza",
                "admin123",
                "Gerente de Vendas",
                5000.00
        );
        servidor1.setAnosServico(5);

        Administrador admin = new Administrador(
                "11122233344",
                "Ana Carolina Ferreira Alves",
                LocalDate.of(1990, 8, 20),
                "ana.admin",
                "admin456",
                "MASTER"
        );

        usuarioServicos.adicionar(aluno1);
        usuarioServicos.adicionar(aluno2);
        usuarioServicos.adicionar(servidor1);
        usuarioServicos.adicionar(admin);

        System.out.println(usuarioServicos.listarTodos().size() + " usuários criados\n");

        System.out.println("Adicionando produtos ao estoque...");

        Comestivel sanduiche = new Comestivel(
                "P001",
                "Sanduíche Natural",
                12.50,
                50,
                LocalDate.now().plusDays(2),
                "Lanche"
        );

        Bebida suco = new Bebida(
                "P002",
                "Suco de Laranja Natural",
                6.00,
                100,
                500.0,
                "Laranja"
        );
        suco.setGelada(true);

        Comestivel bolo = new Comestivel(
                "P003",
                "Bolo de Chocolate",
                15.00,
                20,
                LocalDate.now().plusDays(10),
                "Doce"
        );

        Bebida refrigerante = new Bebida(
                "P004",
                "Refrigerante Cola",
                5.50,
                150,
                350.0,
                "Cola"
        );

        Comestivel pizza = new Comestivel(
                "P005",
                "Pizza Marguerita",
                35.00,
                15,
                LocalDate.now().plusDays(1),
                "Refeição"
        );

        estoqueServicos.adicionar(sanduiche);
        estoqueServicos.adicionar(suco);
        estoqueServicos.adicionar(bolo);
        estoqueServicos.adicionar(refrigerante);
        estoqueServicos.adicionar(pizza);

        System.out.println(estoqueServicos.listarTodos().size() + " produtos em estoque\n");

        System.out.println("Registrando visitantes...");

        fluxoClienteServicos.registrarEntrada("11111111111", "Maria Eduarda Oliveira Costa");
        fluxoClienteServicos.registrarEntrada("22222222222", "José Silva Santos");
        fluxoClienteServicos.registrarEntrada("33333333333", "Ana Paula Rodrigues");

        fluxoClienteServicos.registrarEntrada("11111111111", "Maria Eduarda Oliveira Costa");
        fluxoClienteServicos.registrarEntrada("11111111111", "Maria Eduarda Oliveira Costa");

        System.out.println(fluxoClienteServicos.getTotalVisitantes() + " visitantes registrados");
        System.out.println(fluxoClienteServicos.getTotalAcessos() + " acessos registrados\n");

        exibirCredenciais();
    }


    private static void exibirCredenciais() {
        System.out.println("\n _____________________________________________");
        System.out.println("  |          CREDENCIAIS DE TESTE               |");
        System.out.println("  |_____________________________________________|");
        System.out.println("\n ALUNO:");
        System.out.println("   Login: joao.silva");
        System.out.println("   Senha: senha123");
        System.out.println("   Desconto: 11%"); //10% base + 1% por semestre

        System.out.println("\n SERVIDOR:");
        System.out.println("   Login: carlos.souza");
        System.out.println("   Senha: admin123");
        System.out.println("   Bônus: R$ 750,00"); //10% + 5% anos serviço

        System.out.println("\n ADMINISTRADOR:");
        System.out.println("   Login: ana.admin");
        System.out.println("   Senha: admin456");
        System.out.println("   Acesso: MASTER");

        System.out.println("\n CLIENTE:");
        System.out.println("   Registro apenas do CPF e Nome");
        System.out.println("_____________________________________________");
    }
}