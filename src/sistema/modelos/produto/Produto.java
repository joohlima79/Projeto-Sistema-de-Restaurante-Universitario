package sistema.modelos.produto;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public abstract class Produto {

    private String codigo;
    private String nome;
    private double preco;
    private int quantidadeEstoque;
    private String categoria;

    public Produto(String codigo, String nome, double preco, int quantidadeEstoque) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Produto(String codigo, String nome, double preco) {
        this(codigo, nome, preco, 0);
    }

    /**
     * @return preço final calculado
     */
    public abstract double calcularPrecoFinal();

    /**
     * @return descrição do produto
     */
    public abstract String obterDescricaoCompleta();

    /**
     * @param quantidade quantidade desejada
     * @return true se disponível
     */
    public boolean verificarDisponibilidade(int quantidade) {
        return quantidadeEstoque >= quantidade;
    }

    /**
     * @param quantidade quantidade a adicionar (positivo) ou remover (negativo)
     * @return true se atualizado com sucesso
     */
    public boolean atualizarEstoque(int quantidade) {
        if (quantidadeEstoque + quantidade >= 0) {
            quantidadeEstoque += quantidade;
            return true;
        }
        return false;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getQuantidadeEstoque() { return quantidadeEstoque; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return String.format("[%s] %s - R$ %.2f (Est: %d)",
                codigo, nome, calcularPrecoFinal(), quantidadeEstoque);
    }
}