package entidades.mongodb;

public class NumeroResumen {

    private long idNumero;
    private String nombreNumero;

    public NumeroResumen() {}

    public long getIdNumero() { return idNumero; }
    public void setIdNumero(long idNumero) { this.idNumero = idNumero; }

    public String getNombreNumero() { return nombreNumero; }
    public void setNombreNumero(String nombreNumero) { this.nombreNumero = nombreNumero; }
}