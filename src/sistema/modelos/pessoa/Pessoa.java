package sistema.modelos.pessoa;

import java.time.LocalDate;
import java.time.Period;

/**
 * @author  Joanderson Borges de Lima
 * @version 1.0
 */
public abstract class Pessoa {

    private String cpf;
    private String nome;
    private LocalDate dataNascimento;

    /**
     * @param cpf CPF da pessoa
     * @param nome nome completo
     * @param dataNascimento data de nascimento
     */
    public Pessoa(String cpf, String nome, LocalDate dataNascimento) {
        this.cpf = cpf;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    /**
     * @param cpf CPF da pessoa
     * @param nome nome completo
     */
    public Pessoa(String cpf, String nome) {
        this(cpf, nome, null);
    }

    /**
     * @return true se dados válidos
     */
    public abstract boolean validar();

    /**
     * @return idade em anos
     */
    public int calcularIdade() {
        if (dataNascimento == null) return 0;
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    /**
     * @return CPF
     */
    public String getCpf() { return cpf; }

    /**
     * @param cpf novo CPF
     */
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return String.format("Nome: %s, CPF: %s, Idade: %d anos",
                nome, cpf, calcularIdade());
    }
}