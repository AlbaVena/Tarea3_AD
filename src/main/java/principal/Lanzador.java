package principal;

public class Lanzador {

	public static void main(String[] args) {
        // Al llamar al main de la otra clase desde aquí, 
        // la JVM no activa las comprobaciones estrictas de JavaFX
        principalAplicacion.main(args);
    }
	
}
