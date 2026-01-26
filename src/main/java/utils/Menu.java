package utils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import controlador.EspectaculosService;
import controlador.NumeroService;
import controlador.UsuariosService;
import entidades.Artista;
import entidades.Coordinador;
import entidades.Credenciales;
import entidades.Espectaculo;
import entidades.Numero;
import entidades.Perfil;
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
			System.out.println("Elige una opcion: \n\t1. Ver espectaculos" + "\n\t2. Log OUT\\n\\t3. Salir");
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
				if (usuarioLogueado != null) {
					switch (usuarioLogueado.getPerfil()) {
					case ARTISTA:
						menuArtista();
						break;
					case COORDINACION:
						menuCoordinador();
						break;
					case ADMIN:
						menuAdmin();
						break;
					default:
						System.out.println("Perfil no reconocido.");
						break;
					}
				} else {
					System.out.println("Usuario o contraseña incorrecto");
				}
				break;
			case 3:
				System.out.println("**-- Se ha cerrado la aplicación. Adiós! --**");
				break;
			default:
				System.out.println("No has intrpducido una opción valida, por favor inténtalo de nuevo.");
				break;
			}
		} while (opcion != 3);

	}

	public void menuAdmin() {

		System.out.println("--MENU ADMINISTRADOR--");

		int opcion = -1;

		while (opcion != 4) {
			System.out.println("Elige una opcion:");
			System.out.println("\t1. Ver espectaculos");
			System.out.println("\t2. Gestionar espectáculos");
			System.out.println("\t3. Gestionar personas y credenciales");
			System.out.println("\t4. Log OUT");

			opcion = leer.nextInt();
			leer.nextLine();

			switch (opcion) {
			case 1:
				verEspectaculos();
				break;
			case 2:
				gestionarEspectaculo();
				break;
			case 3:
				gestionarPersonas();
				break;
			case 4:
				usuariosService.logOut();
				break;
			default:
				System.out.println("Has introducido una opcion invalida. Por favor, intentalo de nuevo.");
			}
		}

	}

	public void menuArtista() {
		int opcion = -1;

		System.out.println("--MENU ARTISTA--");

		do {
			System.out.println("Elige una opcion: \n\t1. Ver tu ficha" + "\n\t2. Ver espectaculos\n\t3. Log OUT");
			opcion = leer.nextInt();
			leer.nextLine();
			switch (opcion) {
			case 1:
				usuariosService.mostrarFicha();
				break;
			case 2:
				verEspectaculos();
				break;
			case 3:
				usuariosService.logOut();
				break;
			default:
				System.out.println("No has introducido una opcion valida. Por favor inténtalo de nuevo");

			}

		} while (opcion != 3);

	}

	public void menuCoordinador() {
		int opcion = -1;

		System.out.println("--MENU COORDINACIÓN--");

		do {
			System.out.println("Elige una opción: \n\t1. Ver espectáculos"
					+ "\n\t2. Crear o Modificar un espectáculo\n\t4. Log OUT");
			opcion = leer.nextInt();
			leer.nextLine();

			switch (opcion) {
			case 1:
				espectaculosService.getEspectaculos();
				break;
			case 2:
				guardarEspectaculo();
				break;
			case 3:
				usuariosService.logOut();
				break;
			default:
				System.out.println("No has intrpducido una opción valida, por favor inténtalo de nuevo.");

			}

		} while (opcion != 4);

	}

	public void verEspectaculos() {
		List<Espectaculo> lista = espectaculosService.getEspectaculos();
		if (lista.isEmpty()) {
			System.out.println("No hay espectáculos programados.");
		} else {
			for (Espectaculo e : lista) {
				System.out.println("- " + e.getNombre() + " (" + e.getFechaini() + ")");
			}
		}
	}

	public void guardarEspectaculo() {
		System.out.println("Qué deseas hacer?");
		System.out.println("\t1. Crear un espectaculo nuevo\n\t2. Modificar un expectaculo existente");

		Espectaculo espectaculo = null;
		int opcion = leer.nextInt();
		leer.nextLine();

		if (opcion == 2) {
			List<Espectaculo> lista = espectaculosService.getEspectaculos();
			if (lista.isEmpty()) {
				System.out.println("No existen espectaculos que modificar");
				return;
			}
			for (Espectaculo e : lista) {
				System.out.println(e.getId() + " - " + e.getNombre());
			}

			System.out.println("Introduce el id del espectaculo a modificar:");

			long idModificar = leer.nextLong();
			leer.nextLine();

			try {
				espectaculo = espectaculosService.getEspectaculo(idModificar);
			} catch (Exception e) {
				System.out.println("ID no válido");
			}

			if (espectaculo == null) {
				System.out.println("No se encontró el espectáculo.");
				return;
			}

		}

		else if (opcion == 1) {
			espectaculo = new Espectaculo();
		} else {
			System.out.println("opcion no valida");
			return;
		}

		// Datos a pedir
		// nombre
		System.out.println("\t-- Introduce los siguientes datos  --");
		System.out.println("Nombre del espectaculo (MAX 25 caracteres): ");
		String nombre = leer.nextLine();

		if (!nombre.isEmpty()) {
			if (nombre.length() <= 25) {
				espectaculo.setNombre(nombre);
			} else {
				System.out.println("Nombre demasiado largo. No se guardarán los cambios");
			}
		}

		// fecha
		boolean fechasValidas = false;
		do {
			try {
				System.out.println("Fecha inicio actual" + espectaculo.getFechaini() + "\nNueva fecha (yyyy-mm-dd):");
				String f1 = leer.nextLine();
				if (!f1.isEmpty())
					espectaculo.setFechaini(LocalDate.parse(f1));

				System.out.println("Fecha fin actual" + espectaculo.getFechafin() + "\nNueva fecha (yyyy-mm-dd)");
				String f2 = leer.nextLine();
				if (!f2.isEmpty())
					espectaculo.setFechafin(LocalDate.parse(f2));

				if (espectaculo.getFechaini() != null && espectaculo.getFechafin() != null) {
					if (espectaculo.getFechafin().isBefore(espectaculo.getFechaini())) {
						System.out.println("La fecha final no puede ser anterior.");
					} else if (espectaculo.getFechafin().isAfter(espectaculo.getFechaini().plusYears(1))) {
						System.out.println("No puede durar más de un año.");
					} else {
						fechasValidas = true;
					}
				} else {
					fechasValidas = true; // Si son nulos (modificacion parcial), seguimos
				}
			} catch (Exception e) {
				System.out.println("Error en el formato de fecha.");
			}
		} while (!fechasValidas);

		// encargado coordinador
		if (espectaculo.getEncargadoCoor() == null) {
			System.out.println("Asignacion de coordinador.");

			Persona logueado = usuariosService.getSesion().getUsuActual();

			if (logueado.getPerfil() == Perfil.COORDINACION) {

				espectaculo.setEncargadoCoor((Coordinador) logueado);

			} else {
				List<Persona> personas = usuariosService.getCredencialesSistema();
				System.out.println("Elige coordinador por su id:");
				for (Persona p : personas) {
					if (p.getPerfil() == Perfil.COORDINACION) {
						System.out.println(p.getId() + " - " + p.getNombre());
					}
				}

				long idC = leer.nextLong();
				leer.nextLine();

				espectaculo.setEncargadoCoor(usuariosService.getCoordinador(idC));

			}
		}

		// numero
		System.out.println("¿Quieres añadir un número a este espectáculo ahora? (s/n)");
		String respuesta = leer.nextLine().trim().toLowerCase();

		if (respuesta.equals("s")) {
			Numero nuevoNum = new Numero();
			System.out.println("Nombre del numero:");
			String nombreNum = leer.nextLine();
			System.out.println("Duracion (total minutos):");
			int durNum = leer.nextInt();
			leer.nextLine();
			nuevoNum.setNombre(nombreNum);
			nuevoNum.setDuracion(durNum);
			nuevoNum.setEspectaculo(espectaculo);

			if (espectaculo.getNumeros() == null) {
				espectaculo.setNumeros(new HashSet<Numero>());
			}
			espectaculo.getNumeros().add(nuevoNum);
		}

		espectaculosService.guardarEspectaculo(espectaculo);
		System.out.println("Espectaculo guardado con éxito.");

	}

	public void gestionarEspectaculo() {
		int opcion2 = -1;
		do {
			System.out.println("--Gestion de espectaculos--");
			System.out.println("Qué deseas hacer?");
			System.out.println("\t1. Crear o modificar un espectaculo");
			System.out.println("\t2. Crear o modificar un numero");
			System.out.println("\t3. Asignar artistas a un numero");
			System.out.println("\t4. Atrás");

			opcion2 = leer.nextInt();
			leer.nextLine();

			switch (opcion2) {
			case 1:
				guardarEspectaculo();
				break;
			case 2:
				guardarNumero();
				break;
			case 3:
				asignarArtistas();
				break;
			case 4:
				break;
			default:
				System.out.println("Opcion invalida. Intentalo de nuevo");
			}
		} while (opcion2 != 4);

	}

	private void asignarArtistas() {

		System.out.println("--Asignar artistas--");
		List<Numero> numeros = numerosService.getNumeros();

		for (Numero n : numeros) {
			System.out.println(n.getId() + " - " + n.getNombre());
		}
		System.out.println("Introduce el id del numero");
		Long idNum = leer.nextLong();
		leer.nextLine();
		Numero numero = numerosService.getNumeroById(idNum);

		System.out.println("Artistas disponibles:");
		List<Persona> personas = usuariosService.getCredencialesSistema();

		for (Persona p : personas) {
			if (p instanceof Artista) {
				System.out.println(p.getId() + " - " + p.getNombre());
			}
		}
		System.out.println("Introduce el ID del artista a añadir:");
		Long idArt = leer.nextLong();
		leer.nextLine();

		Artista artista = (Artista) usuariosService.getArtista(idArt);

		if (artista != null) {

			if (numero.getArtistas() == null) {
				numero.setArtistas(new HashSet<Artista>());
			}
		}
		numero.getArtistas().add(artista);
		numerosService.guardarNumero(numero);
		System.out.println("Informacion guardada.");

	}

	public void guardarNumero() {
		int opcion3 = -1;

		System.out.println("Qué deseas hacer?");
		System.out.println("\t1. Crear un numero nuevo");
		System.out.println("\t2. Modificar un numero existente");
		opcion3 = leer.nextInt();
		leer.nextLine();

		Numero numero = null;

		if (opcion3 == 2) {
			List<Numero> lista = numerosService.getNumeros();
			for (Numero n : lista) {
				System.out.println(n.getId() + " - " + n.getNombre());
			}
			System.out.println("Introduce id del número a modificar:");
			long idNum = leer.nextLong();
			leer.nextLine();

			try {
				numero = numerosService.getNumeroById(idNum);
			} catch (Exception e) {
				System.out.println("ID invalido");
			}
		} else {
			numero = new Numero();
		}

		System.out.println("Nombre actual [" + numero.getNombre() + "]");
		System.out.println("Introduce un nombre nuevo o pulsa ENTER");
		String nombre = leer.nextLine();

		if (!nombre.isEmpty()) {
			numero.setNombre(nombre);
		}

		System.out.println("Duracion actual [" + numero.getDuracion() + " minutos]");
		System.out.println("Introduce una nueva duracion o pulsa ENTER");
		int duracion = leer.nextInt();
		leer.nextLine();

		if (duracion > 0) {
			numero.setDuracion(duracion);
		}

		if (numero.getEspectaculo() == null) {
			for (Espectaculo e : espectaculosService.getEspectaculos()) {
				System.out.println(e.getId() + " - " + e.getNombre());
			}
			System.out.println("Introduce el ID del espectaculo al que pertenece");
			long idEsp = leer.nextLong();
			leer.nextLine();
			numero.setEspectaculo(espectaculosService.getEspectaculo(idEsp));
		}

		numerosService.guardarNumero(numero);
		System.out.println("Numero guardado con éxito.");

	}
	
	public void gestionarPersonas() {
		int opcion4 = -1;
		
		do {
			System.out.println("--Gestion de personas--");
			System.out.println("Qué deseas hacer?");
			System.out.println("\t1. Registrar una persona nueva");
			System.out.println("\t2. Gestionar datos de Artista o Coordinador");
			System.out.println("\t3. Atrás");
			
			switch (opcion4) {
			case 1:
				registrarPersonaNueva();
				break;
			case 2:
				modificarPersona();
				break;
			case 3:
				break;
				default: 
					System.out.println("Opcion invalida, prueba de nuevo");
			}
			
		} while (opcion4 != 3);
	}
	
	public void registrarPersonaNueva() {
		Persona p = null;
		System.out.println("--Nuevo usuario--");
		
		System.out.println("El nuevo usuario es.. 1-Artista o 2-Coordinador?");
		int perfil = leer.nextInt();
		leer.nextLine();
		if (perfil == 1) {
			p = new Artista();
			p.setPerfil(Perfil.ARTISTA);
			
		} else if (perfil == 2) {
			p = new Coordinador();
			p.setPerfil(Perfil.COORDINACION);
		}		

		System.out.println("Introduce su nombre:");
		p.setNombre(leer.nextLine());
		System.out.println("Introduce su email:");
		p.setEmail(leer.nextLine());
		System.out.println("Introduce su nacionalidad");
		p.setNacionalidad(leer.nextLine());	
		
		System.out.println("Nombre de usuario (login):");
		String usuario = leer.nextLine();
		System.out.println("Contraseña:");
		String pass = leer.nextLine();
		
		p.setCredenciales(new Credenciales(usuario, pass));
		
		usuariosService.crearPersona(p);
	}

	public void modificarPersona() {
		
	}
}
