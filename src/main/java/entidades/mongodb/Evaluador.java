package entidades.mongodb;

public class Evaluador {

    private long idPersona;
    private String rol;

    public Evaluador() {}

    public Evaluador(long idPersona, String rol) {
        this.idPersona = idPersona;
        this.rol = rol;
    }

    public long getIdPersona() { return idPersona; }
    public void setIdPersona(long idPersona) { this.idPersona = idPersona; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}