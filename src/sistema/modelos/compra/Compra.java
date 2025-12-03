package sistema.modelos.compra;

import sistema.modelos.pessoa.usuario.Usuario;
import sistema.modelos.produto.Produto;
import sistema.modelos.pagamento.FormaPagamento;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */

public class Compra {

    private String id;
    private Usuario usuario;
    private List<ItemCompra> itens;
    private FormaPagamento formaPagamento;
    private LocalDateTime dataHora;
    private StatusCompra status;
    private double desconto;

    public Compra(Usuario usuario) {
        this.id = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.usuario = usuario;
        this.itens = new ArrayList<>();
        this.dataHora = LocalDateTime.now();
        this.status = StatusCompra.PENDENTE;
        this.desconto = 0.0;
    }

    /**
     * @param produto produto a adicionar
     * @param quantidade quantidade desejada
     * @return true se adicionado com sucesso
     */
    public boolean adicionarItem(Produto produto, int quantidade) {
        if (status != StatusCompra.PENDENTE) {
            System.out.println("Não é possível adicionar itens a uma compra já finalizada!");
            return false;
        }

        if (!produto.verificarDisponibilidade(quantidade)) {
            System.out.println("Estoque insuficiente para " + produto.getNome());
            return false;
        }

        itens.add(new ItemCompra(produto, quantidade));
        return true;
    }

    /**
     * @param codigoProduto código do produto
     * @return true se removido
     */
    public boolean removerItem(String codigoProduto) {
        return itens.removeIf(item ->
                item.getProduto().getCodigo().equals(codigoProduto));
    }

    /**
     * @return subtotal da compra
     */
    public double calcularSubtotal() {
        return itens.stream()
                .mapToDouble(ItemCompra::getSubtotal)
                .sum();
    }

    /**
     * @return total final
     */
    public double calcularTotal() {
        double subtotal = calcularSubtotal();
        return subtotal * (1 - desconto / 100);
    }

    /**
     * @param percentual percentual de desconto
     */
    public void aplicarDesconto(double percentual) {
        if (percentual >= 0 && percentual <= 100) {
            this.desconto = percentual;
        }
    }

    /**
     * @param pagamento forma de pagamento
     * @return true se finalizado com sucesso
     */
    public boolean finalizar(FormaPagamento pagamento) {
        if (itens.isEmpty()) {
            System.out.println("Carrinho vazio! Adicione itens antes de finalizar.");
            return false;
        }

        this.formaPagamento = pagamento;

        if (pagamento.processar()) {
            for (ItemCompra item : itens) {
                item.getProduto().atualizarEstoque(-item.getQuantidade());
            }

            status = StatusCompra.CONCLUIDA;
            System.out.println("\nCompra finalizada com sucesso!");
            System.out.println("ID da Compra: " + id);

            return true;
        }

        status = StatusCompra.CANCELADA;
        System.out.println("\nErro ao processar pagamento!");
        return false;
    }

    public void cancelar() {
        this.status = StatusCompra.CANCELADA;
    }

    public void exibirResumo() {
        System.out.println("\n RESUMO DA COMPRA ");
        System.out.println("ID: " + id);
        System.out.println("Usuário: " + usuario.getNome());
        System.out.println("Data: " + dataHora);
        System.out.println("\nITENS:");

        itens.forEach(item -> {
            System.out.printf("  %s x%d - R$ %.2f\n",
                    item.getProduto().getNome(),
                    item.getQuantidade(),
                    item.getSubtotal());
        });

        System.out.println("\nSubtotal: R$ " + String.format("%.2f", calcularSubtotal()));
        if (desconto > 0) {
            System.out.println("Desconto: " + desconto + "%");
        }
        System.out.println("TOTAL: R$ " + String.format("%.2f", calcularTotal()));
        System.out.println("Status: " + status);
        System.out.println("______________________________________\n");
    }

    public String getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public List<ItemCompra> getItens() { return new ArrayList<>(itens); }
    public StatusCompra getStatus() { return status; }
    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public LocalDateTime getDataHora() { return dataHora; }
}