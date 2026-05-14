package entidades.mongodb;

import java.util.ArrayList;
import java.util.List;

public class Trayectoria {

    private long idEspectaculo;
    private String nombreEspectaculo;
    private List<NumeroResumen> numeros = new ArrayList<>();

    public Trayectoria() {}

    public long getIdEspectaculo() { return idEspectaculo; }
    public void setIdEspectaculo(long idEspectaculo) { this.idEspectaculo = idEspectaculo; }

    public String getNombreEspectaculo() { return nombreEspectaculo; }
    public void setNombreEspectaculo(String nombreEspectaculo) { this.nombreEspectaculo = nombreEspectaculo; }

    public List<NumeroResumen> getNumeros() { return numeros; }
    public void setNumeros(List<NumeroResumen> numeros) { this.numeros = numeros; }
}