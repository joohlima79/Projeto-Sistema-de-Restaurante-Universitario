package sistema.modelos.pessoa.usuario;

import java.time.LocalDate;

/**
 * @author  Joanderson Borges de Lima
 * @version 1.0
 */
public class Aluno extends Usuario{

    private String matricula;
    private String curso;
    private int semestre;
    private double desconto;

    public Aluno(String cpf, String nome, LocalDate dataNascimento,
                 String login, String senha, String matricula, String curso) {
        super(cpf, nome, dataNascimento, login, senha);
        this.matricula = matricula;
        this.curso = curso;
        this.semestre = 1;
        this.desconto = 10.0;
    }

    public Aluno(String cpf, String nome, String login, String senha, String matricula) {
        this(cpf, nome, null, login, senha, matricula, "Não definido");
    }

    public void avancarSemestre() {
        this.semestre++;
    }

    /**
     * @param valorOriginal valor original da compra
     * @return valor com desconto aplicado
     */
    public double calcularDesconto(double valorOriginal) {
        double descontoAdicional = (semestre - 1) * 0.5;
        double descontoTotal = Math.min(desconto + descontoAdicional, 20.0);
        return valorOriginal * (1 - descontoTotal / 100);
    }

    @Override
    public void exibirMenu() {
        System.out.println("\n ________________________________________");
        System.out.println("  |            MENU ALUNO                 |");
        System.out.println("  |________________________________________|");
        System.out.println("  | 1. Comprar ticket                      |");
        System.out.println("  | 2. Consultar histórico                 |");
        System.out.println("  | 3. Alterar Senha                       |");
        System.out.println("  | 4. Sair                                |");
        System.out.println("  |________________________________________|");
    }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getCurso() { return curso; }
    public void setCurso(String curso) { this.curso = curso; }

    public int getSemestre() { return semestre; }
    public void setSemestre(int semestre) { this.semestre = semestre; }

    public double getDesconto() { return desconto; }

    @Override
    public String toString() {
        return super.toString() + String.format(", Matrícula: %s, Curso: %s, Semestre: %d, Desconto: %.1f%%",
                matricula, curso, semestre, desconto);
    }
}