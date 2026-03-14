package utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import controlador.IEspectaculosService;
import controlador.INumeroService;
import controlador.IUsuariosService;
import controlador.implementacion.EspectaculosService;
import controlador.implementacion.NumeroService;
import controlador.implementacion.PaisesService;
import entidades.Artista;
import entidades.Coordinador;
import entidades.Credenciales;
import entidades.Especialidad;
import entidades.Espectaculo;
import entidades.Numero;
import entidades.Perfil;
import entidades.Persona;
import jakarta.transaction.Transactional;

@Component
public class Menu {

	private final Scanner leer = new Scanner(System.in);

	@Autowired
	IUsuariosService usuariosService;

	@Autowired
	INumeroService numerosService;

	@Autowired
	IEspectaculosService espectaculosService;
	
	@Autowired
	PaisesService paisesService;
	
	


	public void menuInvitado() {
		int opcion = -1;

		System.out.println("--MENU INVITADO--");

		do {
			System.out.println("Elige una opcion: \n\t1. Ver espectaculos" + "\n\t2. Log IN\n\t3. Salir");
			opcion = leer.nextInt();
			leer.nextLine();

			switch (opcion) {
			case 1:
				verEspectaculos();
				break;
			case 2:
				String usuario, password;

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
				System.out.println("No has introducido una opción valida, por favor inténtalo de nuevo.");
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
				usuariosService.mostrarFichaArtista();
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
					+ "\n\t2. Crear o Modificar un espectáculo\n\t3. Log OUT");
			opcion = leer.nextInt();
			leer.nextLine();

			switch (opcion) {
			case 1:
				verEspectaculos();
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

		} while (opcion != 3);

	}

	@Transactional
	public void verEspectaculos() {
		List<Espectaculo> lista = espectaculosService.getEspectaculos();
		if (lista.isEmpty()) {
			System.out.println("No hay espectáculos programados.");
		} else {
			for (Espectaculo e : lista) {
				System.out.println(
						"- " + e.getNombre() + " ( desde " + e.getFechaini() + " hasta " + e.getFechafin() + ")");
			}
		}
	}

	@Transactional
	public void guardarEspectaculo() {
		boolean esnuevo = false;
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
			esnuevo = true;
		} else {
			System.out.println("opcion no valida");
			return;
		}

		// Datos a pedir
		// nombre
		System.out.println("\t-- Introduce los siguientes datos  --");
		String nombre = "";
		boolean espValido = false;
		do {
			System.out.println("Nombre del espectaculo (MAX 25 caracteres): ");

			nombre = leer.nextLine();

			if (!nombre.isEmpty()) {
				if (nombre.length() <= 25) {
					espectaculo.setNombre(nombre);
					espValido = true;
				} else {
					System.out.println("Nombre demasiado largo. Intentalo de nuevo.");

				}
			}
		} while (!espValido);

		// fecha
		boolean fechasValidas = false;
		do {
			try {
				if (!esnuevo) {
					System.out.println(
							"Fecha inicio actual: " + espectaculo.getFechaini() + "\nNueva fecha (yyyy-mm-dd):");
				} else {
					System.out.println("Introduce la fecha de inicio (yyyy-mm-dd):");
				}
				String fecha1 = leer.nextLine();
				if (!fecha1.isEmpty())
					espectaculo.setFechaini(LocalDate.parse(fecha1));

				if (!esnuevo) {
					System.out.println("Fecha fin actual: " + espectaculo.getFechafin() + "\nNueva fecha (yyyy-mm-dd)");
				} else {
					System.out.println("Introduce la fecha de fin (yyyy-mm-dd):");
				}

				String fecha2 = leer.nextLine();
				if (!fecha2.isEmpty())
					espectaculo.setFechafin(LocalDate.parse(fecha2));

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

		Numero aInsertar = new Numero();
		// añadir al menos 3 numeros
		boolean completo = false;
		System.out.println("- Registro de numeros -    (minimo 3) ");
		List<Numero> numeros = new ArrayList<Numero>();

		do {
			System.out.println("Añadiendo numero " + (numeros.size() + 1)); 
			String nombreNum = "";
			boolean nombreValido = false;

			do {
				// nombre
				System.out.println("Introduce un nombre para el numero: (MAX 25 caracteres)");
				nombreNum = leer.nextLine().trim();
				if (nombreNum.length() > 25 || nombreNum.length() < 3) { 
					System.out.println("Recuerda: el nombre debe tener entre 3 y 25 caracteres. Inténtalo de nuevo");
				} else {
					aInsertar.setNombre(nombreNum);
					nombreValido = true;
				}

			} while (!nombreValido);

			// duracion
			int minutos = -1;
			
			do {
				System.out.println("Introduce la duracion total en minutos: (ej: 126)");
				System.out.println("(Duracion maxima 150 minutos, minima 15 minutos.)");
				minutos = leer.nextInt();
				leer.nextLine();
				if (minutos > 15 && minutos < 150) {
					aInsertar.setDuracion(minutos);
					aInsertar.setEspectaculo(espectaculo);
				} else {
					minutos = -1;
				}
			}
			while (minutos < 15);

			// artistas

			Set<Artista> artistasDelNumero = new HashSet<>();

			do {
				List<Persona> listaPersonas = usuariosService.getCredencialesSistema();
				System.out.println("Lista de Artistas disponibles:");
				for (Persona p : listaPersonas) {
					if (p.getPerfil() == Perfil.ARTISTA) {
						System.out.println(p.getId() + " - " + p.getNombre());
					}
				}

				System.out.print("Introduce el ID del artista para este número: ");
				long idArt = leer.nextLong();
				leer.nextLine();

				Artista a = (Artista) usuariosService.getPersonaById(idArt);
				if (a != null && !artistasDelNumero.contains(a)) { // TODO corregir que no se escoja EL MISMO
					artistasDelNumero.add(a);
					System.out.println("Artista añadido.");
				}

				if (!artistasDelNumero.isEmpty()) {
					System.out.print("¿Añadir otro artista a este mismo número? (s/n): ");
					if (leer.nextLine().equalsIgnoreCase("n"))
						break;
				} else {
					System.out.println("Error: Todo número debe tener al menos un artista.");
				}
			} while (true);

			aInsertar.setArtistas(artistasDelNumero);

			numeros.add(aInsertar);
			completo = true;

		} while (!completo);

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
//		System.out.println("¿Quieres añadir un número a este espectáculo ahora? (s/n)");
//		String respuesta = leer.nextLine().trim().toLowerCase();
//
//		if (respuesta.equals("s")) {
//			Numero nuevoNum = new Numero();
//			System.out.println("Nombre del numero:");
//			String nombreNum = leer.nextLine();
//			System.out.println("Duracion (total minutos):");
//			int durNum = leer.nextInt();
//			leer.nextLine();
//			nuevoNum.setNombre(nombreNum);
//			nuevoNum.setDuracion(durNum);
//			nuevoNum.setEspectaculo(espectaculo);
//
//			if (espectaculo.getNumeros() == null) {
//				espectaculo.setNumeros(new HashSet<Numero>());
//			}
//			espectaculo.getNumeros().add(nuevoNum);
//		}

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
				gestionarNumero();
				break;
			case 3:
				asignarArtistas();
				break;
			case 4:
				break;
			default:
				System.out.println("Opcion inválida. Intentalo de nuevo");
			}
		} while (opcion2 != 4);

	}

	@Transactional
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
		System.out.println("Información guardada.");

	}

	@Transactional
	public void gestionarNumero() {
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
			System.out.println("Nombre actualizado.");
		}

		System.out.println("Duración actual [" + numero.getDuracion() + " minutos]");
		System.out.println("Introduce una nueva duración o pulsa ENTER");

		String intentoDuracion = leer.nextLine().trim();

		if (!intentoDuracion.isEmpty()) {
			int duracion = -1;
			try {
				duracion = Integer.parseInt(intentoDuracion);
			} catch (NumberFormatException e) {
				System.err.println("Formato incorrecto para Duración");
				duracion = -1;
				System.out.println("No se ha modificado la duración.");
			}
			if (duracion > 0) {
				numero.setDuracion(duracion);
				System.out.println("Duración actualizada.");
			}

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

	@Transactional
	public void gestionarPersonas() {
		int opcion4 = -1;

		do {
			System.out.println("--Gestión de personas--");
			System.out.println("Qué deseas hacer?");
			System.out.println("\t1. Registrar una persona nueva");
			System.out.println("\t2. Gestionar datos de Artista o Coordinador");
			System.out.println("\t3. Atrás");

			try {
				opcion4 = leer.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Opcion invalida");
				opcion4 = -1;
			} finally {				
				leer.nextLine();
			}

			switch (opcion4) {
			case 1:
				registrarPersonaNueva();
				break;
			case 2:
				usuariosService.modificarPersona();
				break;
			case 3:
				break;
			default:
				System.out.println("Opcion invalida, prueba de nuevo");
			}

		} while (opcion4 != 3);
	}

	@Transactional
	public void registrarPersonaNueva() {

		Persona p = null;
		System.out.println("--Nuevo usuario--");
		int perfil = -1;
		do {
			System.out.println("El nuevo usuario es.. 1-Artista o 2-Coordinador?");
			try {
				perfil = leer.nextInt();

			} catch (NumberFormatException e) {
				System.out.println("error: 'NumberFormat'. Opciones validas (1) o (2)");
				perfil = -1;
			} catch (InputMismatchException e) {
				System.out.println("error: 'InputMismatch'. Opciones validas (1) o (2)");
				perfil = -1;
			} finally {
				leer.nextLine();
			}
		} while (perfil != 1 && perfil != 2); // TODO meter try catch por si la respuesta no es 1 o 2

		if (perfil == 1) {
			p = new Artista();
			p.setPerfil(Perfil.ARTISTA);
			// apodo
			System.out.println("Introduce su apodo");
			System.out.println("(No acepta diéresis o números)");
			Artista art = (Artista) p;
			String apodo = leer.nextLine();

			art.setPerfil(Perfil.ARTISTA);
			if (apodo != null && Validador.esCadenaValida(apodo, Validador.nombreGeneralRegex)) {
				art.setApodo(apodo);
				System.out.println("Apodo guardado.\n");
			} else {
				if (!Validador.esCadenaValida(apodo, Validador.nombreGeneralRegex)) {
					System.out.println("El apodo introducido no es válido.");
				}
				System.out.println("Se continúa sin guardar un apodo, esto se puede modificar más adelante.");
			}

			// especialidades
			Set<Especialidad> nuevasEspec = new HashSet<>();

			nuevasEspec.clear();
			System.out.println("Especialidades disponibles:");
			List<Especialidad> especialidades = usuariosService.getEspecialidades();
			for (Especialidad e : especialidades) {
				System.out.println(e.getId() + " - " + e.getNombre());
			}
			System.out.println("Indica el conjunto de las especialidades separadas por comas (ej: 1,3,4)");
			System.out.println("(IMPORTANTE: un artista debe tener al menos UNA especialidad, y máximo CINCO)");
			String[] seleccion = leer.nextLine().split(",");

			for (String s : seleccion) {
				try {
					int indice = Integer.parseInt(s.trim()) - 1; // -1 porque la lista empieza en 0

					if (indice >= 0 && indice < especialidades.size()) {
						Especialidad especialidadAgregada = especialidades.get(indice);
						nuevasEspec.add(especialidadAgregada);
					} else {
						System.out.println("La opción " + (indice + 1) + " no existe.");
					}
				} catch (NumberFormatException e) {
					System.out.println("'" + s + "' no es un número válido.");
				}
			}

			if (nuevasEspec.size() >= 1 && nuevasEspec.size() <= 5) {
				art.setEspecialidades(nuevasEspec);
				System.out.println("Especialidades cuardadas.\n");
			} else {
				System.out.println(
						"Error: Debes elegir entre 1 y 5 especialidades. El registro continuará sin ellas, se puede modificar más adelante.");
			}

		} else if (perfil == 2) {
			p = new Coordinador();
			p.setPerfil(Perfil.COORDINACION);
			boolean respuestaValida = false;
			String esSenior = "";

			// senior
			do {
				System.out.println("Es Senior? (s/n)");
				esSenior = leer.nextLine().trim().toLowerCase();

				if (esSenior.equals("s") || esSenior.equals("n")) {
					respuestaValida = true;
				} else {
					System.out.println("Por favor introduce 's' para SI, o 'n' para NO.");
				}
			} while (!respuestaValida);
			Coordinador coor = (Coordinador) p;
			coor.setPerfil(Perfil.COORDINACION);
			if (esSenior.equals("s")) {
				coor.setSenior(true);

				// fechasenior
				boolean fechaValida = false;
				while (!fechaValida) {
					System.out.println("Introduce la fecha Senior (formato yyyy-mm-dd):");
					String fechaStr = leer.nextLine().trim();
					try {
						LocalDate fecha = LocalDate.parse(fechaStr);
						if (fecha.isAfter(LocalDate.now())) {
							System.out.println("La fecha no puede ser futura.");
						} else {
							coor.setFechasenior(fecha);
							fechaValida = true;
							System.out.println("Información Senior guardada.\n");
						}
					} catch (Exception e) {
						System.out.println("Formato de fecha incorrecto.");
					}
				}

			} else {
				coor.setSenior(false);
				coor.setFechasenior(null);
				System.out.println("El usuario NO es senior. Información guardada.");
			}
		}

		// persona

		// nombre

		String nombre = null;

		do {
			System.out.println("Introduce su nombre:");
			System.out.println("(No acepta diéresis o números)");
			nombre = leer.nextLine().toLowerCase();
			if (Validador.esCadenaValida(nombre,Validador.nombreGeneralRegex)) {
				p.setNombre(nombre);
				System.out.println("Nombre guardado.\n");
			} else {
				nombre = null;
				System.out.println("Ese nombre no es válido, inténtalo de nuevo");
			}
		} while (nombre == null);

		// email
		String email = null;
		do {
			System.out.println("Introduce su email:");
			email = leer.nextLine().trim();

			if (Validador.esCadenaValida(email, Validador.emailRegex)) {
				if (usuariosService.comprobarEmail(email)) {
					email = null;
					System.out.println("Ese email ya está registrado, por favor inténtalo de nuevo.");
				} else {
					p.setEmail(email);
					System.out.println("Email guardado.\n");
				}
			} else {
				System.out.println(
						"Formato de email invalido, prueba de nuevo como 'nombre@email.com' o 'nombre@email.es'.");
				email = null;
			}
		} while (email == null);

		
		
		
		// nacionalidad 
		String opcionPais = null;
		
		do {
			System.out.println("Introduce su nacionalidad");
			mostrarPaises();
			
			opcionPais = leer.nextLine().toUpperCase();
			if (paisesService.getPaises().containsKey(opcionPais)) {
				p.setNacionalidad(paisesService.getPaises().get(opcionPais));
				System.out.println("Nacionalidad guardada.\n");
			} else {
				opcionPais = null;
				System.out.println("Opción inválida. Prueba de nuevo");
			}
		} while (opcionPais == null);

		
		
		// nombreUsuario
		String usuario = null;
		do {
			System.out.println("Introduce su nombre de usuario (login):");
			System.out.println("(Solo admite letras, numeros, '_', '#', '!','\\', '-' y '.')");
			usuario = leer.nextLine();
			if (!usuariosService.comprobarNombreUsuario(usuario)) {
				if (!Validador.esCadenaValida(usuario, Validador.nombreUsuarioRegex)) {
					System.out.println("Nombre invalido, recuerda:");
					usuario = null;
				} else {
					System.out.println("Nombre de usuario guardado.\n");
				}

			} else {
				System.out.println("Ese nombre de usuario ya existe, por favor inténtalo de nuevo.");
				usuario = null;

			}
		} while (usuario == null);

		// contraseña
		String pass = null;

		do {
			System.out.println("Introduce su contraseña:"); 	//^[a-zA-Z0-9_!]{4,15}$";
			System.out.println("(Solo admite letras, numeros, '_' y '!'. Debe tener más de 4 caracteres)");
			pass = leer.nextLine();
			if (!Validador.esCadenaValida(pass, Validador.passwordRegex)) {
				pass = null;
			} else {
				System.out.println("Contraseña guardada.\n");
			}

		} while (pass == null);

		Credenciales cred = new Credenciales(usuario.toLowerCase(), pass, p.getPerfil());
		p.setCredenciales(cred);
		cred.setPersona(p);

		usuariosService.crearPersona(p);
	}
	
	private void mostrarPaises() {
		Map<String, String> mapaPaises = paisesService.getPaises();
		mapaPaises.forEach((id, nombre2) -> System.out.println(id + " - " + nombre2));
		
	}

	

}
