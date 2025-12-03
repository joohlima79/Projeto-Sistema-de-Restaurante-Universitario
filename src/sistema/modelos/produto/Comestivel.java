package sistema.modelos.produto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public class Comestivel extends Produto {

    private LocalDate dataValidade;
    private String tipo;
    private boolean refrigerado;

    public Comestivel(String codigo, String nome, double preco,
                      int quantidadeEstoque, LocalDate dataValidade, String tipo) {
        super(codigo, nome, preco, quantidadeEstoque);
        this.dataValidade = dataValidade;
        this.tipo = tipo;
        this.refrigerado = false;
        setCategoria("Comestível");
    }

    public Comestivel(String codigo, String nome, double preco, LocalDate dataValidade) {
        this(codigo, nome, preco, 0, dataValidade, "Geral");
    }

    @Override
    public double calcularPrecoFinal() {
        long diasParaVencer = ChronoUnit.DAYS.between(LocalDate.now(), dataValidade);

        if (diasParaVencer < 0) {
            return 0.0;
        } else if (diasParaVencer <= 2) {
            return getPreco() * 0.50;
        } else if (diasParaVencer <= 5) {
            return getPreco() * 0.70;
        } else if (diasParaVencer <= 10) {
            return getPreco() * 0.90;
        }

        return getPreco();
    }

    /**
     * @param descontoExtra desconto adicional em porcentagem
     * @return preço final com desconto extra
     */
    public double calcularPrecoFinal(double descontoExtra) {
        return calcularPrecoFinal() * (1 - descontoExtra / 100);
    }

    @Override
    public String obterDescricaoCompleta() {
        return String.format("%s - Tipo: %s, Validade: %s, %s",
                getNome(), tipo, dataValidade,
                verificarValidade() ? "Válido" : "VENCIDO");
    }

    /**
     * @return true se válido
     */
    public boolean verificarValidade() {
        return LocalDate.now().isBefore(dataValidade) ||
                LocalDate.now().isEqual(dataValidade);
    }

    /**
     * @return número de dias
     */
    public long diasParaVencer() {
        return ChronoUnit.DAYS.between(LocalDate.now(), dataValidade);
    }

    public LocalDate getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public boolean isRefrigerado() { return refrigerado; }
    public void setRefrigerado(boolean refrigerado) {
        this.refrigerado = refrigerado;
    }
}
