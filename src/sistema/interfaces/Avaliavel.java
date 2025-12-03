package sistema.interfaces;

/**
 * @author Joanderson Borges de lima
 * @version 1.0
 */
public interface Avaliavel {

    /**
     * @param nota       nota da avaliação (0 a 5)
     * @param comentario comentário opcional
     */
    void avaliar(double nota, String comentario);

    /**
     * @return média das avaliações
     */
    double obterAvaliacaoMedia();

}
