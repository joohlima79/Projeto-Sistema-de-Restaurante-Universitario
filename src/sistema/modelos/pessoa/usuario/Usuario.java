package sistema.modelos.pessoa.usuario;

import sistema.interfaces.Autenticavel;
import sistema.modelos.pessoa.Pessoa;
import java.time.LocalDate;

/**
 * @author  Joanderson Borges de Lima
 * @version 1.0
 */
public abstract class Usuario extends Pessoa implements Autenticavel {

    private String login;
    private String senha;
    private boolean ativo;

    public Usuario(String cpf, String nome, LocalDate dataNascimento,
                   String login, String senha) {
        super(cpf, nome, dataNascimento);
        this.login = login;
        this.senha = senha;
        this.ativo = true;
    }

    @Override
    public boolean autenticar(String loginTentativa, String senhaTentativa) {
        if (!ativo) {
            return false;
        }
        return login.equals(loginTentativa) && senha.equals(senhaTentativa);
    }

    @Override
    public boolean alterarSenha(String senhaAntiga, String novaSenha) {
        if (senha.equals(senhaAntiga)) {
            this.senha = novaSenha;
            return true;
        }
        return false;
    }

    @Override
    public boolean validar() {
        return getCpf() != null && getCpf().length() == 11 &&
                login != null && !login.isEmpty() &&
                senha != null && senha.length() >= 6;
    }

    public abstract void exibirMenu();

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    @Override
    public String toString() {
        return super.toString() + String.format(", Login: %s, Status: %s",
                login, ativo ? "Ativo" : "Inativo");
    }
}