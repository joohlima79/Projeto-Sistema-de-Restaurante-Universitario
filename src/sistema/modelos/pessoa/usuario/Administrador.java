package sistema.modelos.pessoa.usuario;

import java.time.LocalDate;

/**
 * @author  Joanderson Borges de Lima
 * @version 1.0
 */
public class Administrador extends Usuario {

    private String nivelAcesso;

    public Administrador(String cpf, String nome, LocalDate dataNascimento,
                         String login, String senha, String nivelAcesso) {
        super(cpf, nome, dataNascimento, login, senha);
        this.nivelAcesso = nivelAcesso;
    }

    /**
     * @param permissao permissão a verificar
     * @return true se possui permissão
     */
    public boolean temPermissao(String permissao) {
        return "MASTER".equals(nivelAcesso) ||
                ("GERENTE".equals(nivelAcesso) && !permissao.equals("DELETAR SISTEMA"));
    }

    @Override
    public void exibirMenu() {
        System.out.println("\n ________________________________________");
        System.out.println("  |           MENU ADMINISTRADOR           |");
        System.out.println("  |________________________________________|");
        System.out.println("  | 1. Gerenciar usuários                  |");
        System.out.println("  | 2. Gerenciar produtos                  |");
        System.out.println("  | 3. Relatórios completos                |");
        System.out.println("  | 4. Configurações do sistema            |");
        System.out.println("  | 5. Backup de dados                     |");
        System.out.println("  | 6. Sair                                |");
        System.out.println("  |________________________________________|");
    }

    public String getNivelAcesso() { return nivelAcesso; }
    public void setNivelAcesso(String nivelAcesso) { this.nivelAcesso = nivelAcesso; }

    @Override
    public String toString() {
        return super.toString() + ", Nível: " + nivelAcesso;
    }
}