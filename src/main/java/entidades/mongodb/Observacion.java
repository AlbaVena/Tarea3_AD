package entidades.mongodb;

import java.time.LocalDate;

public class Observacion {

    private LocalDate fecha;
    private String texto;
    private String autor;

    public Observacion() {}

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
}