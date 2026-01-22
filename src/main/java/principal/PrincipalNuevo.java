package principal;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import controlador.EspectaculosService;
import controlador.NumeroService;
import controlador.UsuariosService;
import utils.Menu;

@SpringBootApplication
@ComponentScan(basePackages = { "controlador", "entidades", "repository", "factorias" })
public class PrincipalNuevo {

	private final Scanner leer = new Scanner(System.in);
	
	@Autowired
	UsuariosService usuariosService;
	
	@Autowired
	NumeroService numerosService;
	
	@Autowired
	EspectaculosService espectaculosService;
	
	@Autowired
    private Menu menu;

	public static void main(String[] args) {

	}

}
