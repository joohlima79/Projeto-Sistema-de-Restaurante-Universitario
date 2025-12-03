package sistema.modelos.pessoa.usuario;
import java.time.LocalDate;

/**
 * @author  Joanderson Borges de Lima
 * @version 1.0
 */
public class Servidor extends Usuario{

    private String cargo;
    private double salario;
    private String setor;
    private int anosServico;

    public Servidor(String cpf, String nome, LocalDate dataNascimento,
                    String login, String senha, String cargo, double salario) {
        super(cpf, nome, dataNascimento, login, senha);
        this.cargo = cargo;
        this.salario = salario;
        this.anosServico = 0;
    }

    public Servidor(String cpf, String nome, String login, String senha, String cargo) {
        this(cpf, nome, null, login, senha, cargo, 0.0);
    }

    /**
     * @param novoCargo   novo cargo
     * @param novoSalario novo salário
     * @return true se promovido com sucesso
     */
    public boolean promover(String novoCargo, double novoSalario) {
        if (novoSalario > this.salario) {
            this.cargo = novoCargo;
            this.salario = novoSalario;
            return true;
        }
        return false;
    }

    /**
     * @return valor do bônus
     */
    public double calcularBonus() {
        return salario * (0.10 + (anosServico * 0.01));
    }

    /**
     * @param multiplicador multiplicador do bônus
     * @return valor do bônus multiplicado
     */
    public double calcularBonus(double multiplicador) {
        return calcularBonus() * multiplicador;
    }

    @Override
    public void exibirMenu() {
        System.out.println("\n ________________________________________");
        System.out.println("  |            MENU SERVIDOR               |");
        System.out.println("  |________________________________________|");
        System.out.println("  | 1. Gerenciar estoque                   |");
        System.out.println("  | 2. Processar vendas                    |");
        System.out.println("  | 3. Consultar relatórios                |");
        System.out.println("  | 4. Alterar senha                       |");
        System.out.println("  | 5. Sair                                |");
        System.out.println("  |________________________________________|");
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public int getAnosServico() {
        return anosServico;
    }

    public void setAnosServico(int anosServico) {
        this.anosServico = anosServico;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Cargo: %s, Salário: R$ %.2f, Anos: %d",
                cargo, salario, anosServico);
    }
}