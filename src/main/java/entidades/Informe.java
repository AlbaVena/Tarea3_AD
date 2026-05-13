package entidades;

public class Informe {
    
    public Espectaculo espectaculo;

    public Informe(Espectaculo espectaculo){
        this.espectaculo = espectaculo;
    }

    public Espectaculo getEspectaculo(){
        return this.espectaculo;
    }
}
