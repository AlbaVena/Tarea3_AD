package utils;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import controlador.EspectaculosService;
import controlador.NumeroService;
import controlador.UsuariosService;
import entidades.Espectaculo;
import entidades.Persona;


@Component
public class Menu {
	
	private final Scanner leer = new Scanner(System.in);
	
	@Autowired
	UsuariosService usuariosService;

	@Autowired
	NumeroService numerosService;

	@Autowired
	EspectaculosService espectaculosService;
	
	
	public void menuInvitado() {
		int opcion = -1;
		
		System.out.println("--MENU INVITADO--");
		
		do {
			System.out.println("Elige una opcion: \n\t1. Ver espectaculos"
					+"\n\t2. Log IN\\n\\t3. Salir");
			opcion = leer.nextInt();
			leer.nextLine();
			
			switch (opcion) {
			case 1:
				verEspectaculos();
				break;
			case 2:
				String usuario, password;
;

				do {
					System.out.println("Introduce tu nombre de usuario");
					usuario = leer.nextLine();
				} while (usuario == null);

				do {
					System.out.println("Introduce tu contraseña");
					password = leer.nextLine();
				} while (password == null);
				Persona usuarioLogueado = usuariosService.login(usuario, password);
				break;
			case 3:
				break;
				default:
					break;
			}
		}
		while (opcion != 3);
		
		
	}
	
	public void menuAdmin() {
		
	}
	
	public void menuArtista() {
		
	}
	
	public void menuCoordinador() {
		
	}
	
	private void verEspectaculos() {
        List <Espectaculo> lista = espectaculosService.getEspectaculos();
        if (lista.isEmpty()) {
            System.out.println("No hay espectáculos programados.");
        } else {
            lista.forEach(e -> System.out.println("- " + e.getNombre() + " (" + e.getFechaini() + ")"));
        }
    }
	

}
