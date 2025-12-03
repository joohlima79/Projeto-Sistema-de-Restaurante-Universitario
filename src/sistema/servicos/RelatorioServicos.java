package sistema.servicos;

import sistema.modelos.compra.Compra;
import sistema.modelos.produto.Produto;
import sistema.modelos.pessoa.Cliente;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public class RelatorioServicos {

    /**
     * @param compras lista de compras
     */
    public void gerarRelatorioVendas(List<Compra> compras) {
        System.out.println("\n ___________________________________________ ");
        System.out.println("  |        RELATÓRIO DE VENDAS                |");
        System.out.println("  |___________________________________________|");

        double totalVendas = compras.stream()
                .mapToDouble(Compra::calcularTotal)
                .sum();

        System.out.println("   Estatísticas:");
        System.out.println("   Total de Vendas: " + compras.size());
        System.out.println("   Faturamento Total: R$ " + String.format("%.2f", totalVendas));
        System.out.println("   Ticket Médio: R$ " +
                String.format("%.2f", totalVendas / Math.max(compras.size(), 1)));
        System.out.println("_____________________________________________\n");
    }

    /**
     * @param produtos lista de produtos
     */
    public void gerarRelatorioEstoque(List<Produto> produtos) {
        System.out.println("\n ___________________________________________ ");
        System.out.println("  |        RELATÓRIO DE ESTOQUE               |");
        System.out.println("  |___________________________________________|");

        long produtosDisponiveis = produtos.stream()
                .filter(p -> p.getQuantidadeEstoque() > 0)
                .count();

        long produtosEsgotados = produtos.stream()
                .filter(p -> p.getQuantidadeEstoque() == 0)
                .count();

        int estoqueTotal = produtos.stream()
                .mapToInt(Produto::getQuantidadeEstoque)
                .sum();

        System.out.println("   Estatísticas:");
        System.out.println("   Total de Produtos: " + produtos.size());
        System.out.println("   Disponíveis: " + produtosDisponiveis);
        System.out.println("   Esgotados: " + produtosEsgotados);
        System.out.println("   Estoque Total: " + estoqueTotal + " unidades");
        System.out.println("_____________________________________________\n");
    }

    /**
     * @param fluxoService service de fluxo de clientes
     */
    public void gerarRelatorioFluxoClientes(FluxoClienteServicos fluxoService) {
        System.out.println("\n ___________________________________________ ");
        System.out.println("  |     RELATÓRIO DE FLUXO DE VISITANTES      |");
        System.out.println("  |___________________________________________|");

        System.out.println("   Estatísticas Gerais:");
        System.out.println("   Total de Visitantes Únicos: " + fluxoService.getTotalVisitantes());
        System.out.println("   Total de Acessos Registrados: " + fluxoService.getTotalAcessos());
        System.out.println("   Visitantes Hoje: " + fluxoService.getVisitantesHoje().size());

        System.out.println("\n Lista dos 5 visitantes mais frequentes:");
        List<Cliente> frequentes = fluxoService.getVisitantesFrequentes(5);
        int posicao = 1;
        for (Cliente cliente : frequentes) {
            System.out.printf("   %d. %s - %d acessos\n",
                    posicao++, cliente.getNome(), cliente.getNumeroAcessos());
        }

        System.out.println("\n Visitantes de Hoje:");
        List<Cliente> hoje = fluxoService.getVisitantesHoje();
        if (hoje.isEmpty()) {
            System.out.println("   Nenhum visitante registrado hoje.");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            for (Cliente cliente : hoje) {
                System.out.printf("   • %s (CPF: %s) - Último acesso: %s\n",
                        cliente.getNome(),
                        cliente.getCpf(),
                        cliente.getDataHoraAcesso().format(formatter));
            }
        }

        System.out.println("_____________________________________________\n");
    }

    /**
     * @param compras      lista de compras
     * @param produtos     lista de produtos
     * @param fluxoService service de fluxo
     */
    public void gerarRelatorioCompleto(List<Compra> compras, List<Produto> produtos,
                                       FluxoClienteServicos fluxoService) {
        System.out.println("\n");
        System.out.println("   ___________________________________________ ");
        System.out.println("  |    RELATÓRIO GERENCIAL COMPLETO           |");
        System.out.println("  |___________________________________________|");

        gerarRelatorioVendas(compras);
        gerarRelatorioEstoque(produtos);
        gerarRelatorioFluxoClientes(fluxoService);
    }
}