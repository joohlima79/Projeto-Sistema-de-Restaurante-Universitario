package sistema.modelos.pessoa;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author  Joanderson Borges de Lima
 * @version 1.0
 */
public class Cliente extends Pessoa {

    private LocalDateTime dataHoraAcesso;
    private int numeroAcessos;

    /**
     * @param cpf CPF do visitante
     * @param nome nome completo do visitante
     */
    public Cliente(String cpf, String nome) {
        super(cpf, nome);
        this.dataHoraAcesso = LocalDateTime.now();
        this.numeroAcessos = 1;
    }

    @Override
    public boolean validar() {
        // Validação mínima: apenas CPF e nome
        return getCpf() != null && getCpf().length() == 11 &&
                getNome() != null && !getNome().isEmpty();
    }

    public void registrarAcesso() {
        this.dataHoraAcesso = LocalDateTime.now();
        this.numeroAcessos++;
    }

    /**
     * @return string formatada com dados de acesso
     */
    public String obterDadosFluxo() {
        return String.format("Cliente: %s | CPF: %s | Acessos: %d | Último acesso: %s",
                getNome(), getCpf(), numeroAcessos, dataHoraAcesso);
    }

    public LocalDateTime getDataHoraAcesso() { return dataHoraAcesso; }
    public int getNumeroAcessos() { return numeroAcessos; }

    @Override
    public String toString() {
        return String.format("Visitante: %s - CPF: %s (Acessos: %d)",
                getNome(), getCpf(), numeroAcessos);
    }
}
