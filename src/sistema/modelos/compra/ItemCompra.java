package sistema.modelos.compra;

import sistema.modelos.produto.Produto;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public class ItemCompra {

    private Produto produto;
    private int quantidade;
    private double precoUnitario;

    public ItemCompra(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.calcularPrecoFinal();
    }

    /**
     * @return subtotal
     */
    public double getSubtotal() {
        return precoUnitario * quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }
}