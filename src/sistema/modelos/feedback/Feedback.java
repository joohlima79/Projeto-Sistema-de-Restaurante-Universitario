package sistema.modelos.feedback;

import java.time.LocalDate;

/**
 * @author Joanderson Borges de Lima
 * @version 1.0
 */
public class Feedback {

    private double nota;
    private String comentario;
    private LocalDate data;

    public Feedback(double nota, String comentario, LocalDate data) {
        this.nota = nota;
        this.comentario = comentario;
        this.data = data;
    }

    public double getNota() { return nota; }
    public String getComentario() { return comentario; }
    public LocalDate getData() { return data; }

    @Override
    public String toString() {
        return String.format("[%.1f] %s - %s", nota, comentario, data);
    }
}