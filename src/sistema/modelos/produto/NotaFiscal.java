package sistema.modelos.produto;

import sistema.modelos.pessoa.Cliente;
import java.time.LocalDate;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public class NotaFiscal extends Produto {

    private String numeroNota;
    private LocalDate dataEmissao;
    private Cliente cliente;
    private double valorTotal;

    public NotaFiscal(String codigo, String numeroNota, Cliente cliente, double valorTotal) {
        super(codigo, "Nota Fiscal #" + numeroNota, valorTotal, 1);
        this.numeroNota = numeroNota;
        this.dataEmissao = LocalDate.now();
        this.cliente = cliente;
        this.valorTotal = valorTotal;
        setCategoria("Documento");
    }

    @Override
    public double calcularPrecoFinal() {
        return valorTotal;
    }

    @Override
    public String obterDescricaoCompleta() {
        return String.format("NF: %s | Data: %s | Cliente: %s | Valor: R$ %.2f",
                numeroNota, dataEmissao,
                cliente.getNome(), valorTotal);
    }

    public String getNumeroNota() { return numeroNota; }
    public LocalDate getDataEmissao() { return dataEmissao; }
    public Cliente getCliente() { return cliente; }
    public double getValorTotal() { return valorTotal; }
}
