package sistema.modelos.pagamento;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public class Pix extends FormaPagamento {

    private String chavePix;
    private LocalDateTime dataHoraProcessamento;

    public Pix(double valor, String chavePix) {
        super(valor);
        this.chavePix = chavePix;
    }

    @Override
    public boolean processar() {
        if (validarPagamento()) {
            processado = true;
            dataHoraProcessamento = LocalDateTime.now();
            return true;
        }
        return false;
    }

    @Override
    public boolean validarPagamento() {
        return chavePix != null && !chavePix.isEmpty() && valor > 0;
    }

    @Override
    public void exibirComprovante() {
        System.out.println("\n COMPROVANTE PIX ");
        System.out.println("Chave PIX: " + chavePix);
        System.out.println("Valor: R$ " + String.format("%.2f", valor));
        System.out.println("Data/Hora: " +
                dataHoraProcessamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println("Status: APROVADO");
        System.out.println("_____________________________________\n");
    }

    public String getChavePix() { return chavePix; }
}