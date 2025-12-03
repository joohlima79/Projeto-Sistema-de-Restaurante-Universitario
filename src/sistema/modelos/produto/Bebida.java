package sistema.modelos.produto;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public class Bebida extends Produto {

    private double volume; //Em ml
    private boolean gelada;
    private String sabor;

    public Bebida(String codigo, String nome, double preco,
                  int quantidadeEstoque, double volume, String sabor) {
        super(codigo, nome, preco, quantidadeEstoque);
        this.volume = volume;
        this.sabor = sabor;
        this.gelada = false;
        setCategoria("Bebida");
    }

    public Bebida(String codigo, String nome, double preco, double volume) {
        this(codigo, nome, preco, 0, volume, "Natural");
    }

    @Override
    public double calcularPrecoFinal() {
        double precoBase = getPreco();

        if (gelada) {
            precoBase *= 1.15;
        }

        if (volume > 500) {
            precoBase *= 1.05;
        }

        return precoBase;
    }

    /**
     * @param quantidade número de unidades
     * @return preço total
     */
    public double calcularPrecoFinal(int quantidade) {
        return calcularPrecoFinal() * quantidade;
    }

    @Override
    public String obterDescricaoCompleta() {
        return String.format("%s - %s, %.0fml, %s",
                getNome(), sabor, volume,
                gelada ? "Gelada" : "Natural");
    }

    public double getVolume() { return volume; }
    public void setVolume(double volume) { this.volume = volume; }

    public boolean isGelada() { return gelada; }
    public void setGelada(boolean gelada) { this.gelada = gelada; }

    public String getSabor() { return sabor; }
    public void setSabor(String sabor) { this.sabor = sabor; }
}