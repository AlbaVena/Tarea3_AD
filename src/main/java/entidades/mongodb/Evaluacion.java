package entidades.mongodb;

import java.time.LocalDate;

public class Evaluacion {

    private LocalDate fecha;
    private Evaluador realizadaPor;
    private String comentario;
    private String nivel;

    public Evaluacion() {}

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Evaluador getRealizadaPor() { return realizadaPor; }
    public void setRealizadaPor(Evaluador realizadaPor) { this.realizadaPor = realizadaPor; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }
}