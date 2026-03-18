package servicios.implementacion;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import entidades.Artista;
import entidades.Coordinador;
import entidades.Credenciales;
import entidades.Especialidad;
import entidades.Numero;
import entidades.Perfil;
import entidades.Persona;
import entidades.Sesion;
import jakarta.transaction.Transactional;
import repository.ArtistaRepository;
import repository.CoordinadorRepository;
import repository.CredencialesRepository;
import repository.EspecialidadRepository;
import repository.PersonaRepository;
import servicios.IUsuariosService;

@Service
public class UsuariosService implements IUsuariosService{

	@Value("${usuarioAdmin}")
	private String usuarioAdminMemoria;

	@Value("${passwordAdmin}")
	private String passwordAdminMemoria;

	@Autowired
	private PersonaRepository personaRepository;

	@Autowired
	private ArtistaRepository artistaRepository;

	@Autowired
	private CoordinadorRepository coordinadorRepository;

	@Autowired
	private CredencialesRepository credencialesRepository;

	@Autowired
	private EspecialidadRepository especialidadRepository;
	
	

	Sesion actual = new Sesion();
	Scanner leer = new Scanner(System.in);

	public Sesion getSesion() {
		return actual;
	}

	public void setSesion(Sesion actual) {
		this.actual = actual;
	}

	@Transactional
	public Persona login(String nombreUsuario, String password) {
		// Admin
		if (nombreUsuario.equals(usuarioAdminMemoria) && password.equals(passwordAdminMemoria)) {

			Persona admin = new Persona();
			admin.setNombre("Administrador");
			admin.setPerfil(Perfil.ADMIN);

			this.actual = new Sesion(admin);
			return admin;
		}

		Optional<Credenciales> cred = credencialesRepository.findByNombreAndPassword(nombreUsuario, password);

		if (cred.isPresent()) {
			Persona p = cred.get().getPersona(); // recoger la persona actual
			actual = new Sesion(p);
			return p;
		}
		return null;
	}

	@Transactional
	public Persona getPersonaById(Long id) {
		return personaRepository.findById(id).orElse(null);
	}

	public void logOut() {
		actual.setUsuActual(null);
	}

	@Transactional()
	public void mostrarFichaArtista() {
		Persona usuario = actual.getUsuActual();
		/**
		 * vamos a recordar que actualmente hay un artista usando esyte metodo, ya
		 * tenemos su id en memoria
		 */

		System.out.println("----FICHA PERSONAL----\n" + "Nombre: " + usuario.getNombre());
		if (usuario instanceof Artista) { // si es instancia, lo transforma
			Artista artista = (Artista) usuario;
			System.out.println("Más conocido como... " + artista.getApodo());
			System.out.println("Especializado en: ");
			if (artista.getEspecialidades() != null) {
				for (Especialidad esp : artista.getEspecialidades()) {
					System.out.print(esp.getNombre() + " ");
				}
			}
			if (!artista.getNumeros().isEmpty()) {
				for (Numero num : artista.getNumeros()) {
					System.out.println("- " + num.getNombre());
				}
			} else {
				System.out.println("El artista no participa en ningun numero aun.");
			}

		}

	}

	@Transactional
	public void crearPersona(Persona nueva) {

		
		if (nueva.getCredenciales() != null) {

	        nueva.getCredenciales().setPersona(nueva);
	    }

	    // guardamos la persona. 
	    // al tener CascadeType.ALL, Hibernate guardará primero la Persona,
	    // cogerásu ID, lo meterá en el objeto credenciales y luego guardará las credenciales.
	    personaRepository.save(nueva);
		
	}

	@Transactional
	public boolean comprobarEmail(String email) {
		return personaRepository.existsByEmail(email);
	}
	
	@Transactional
	public Boolean comprobarNombrePersona(String nombre) {
		return personaRepository.existsByNombre(nombre);
	}

	@Transactional
	public void modificarArtista(Artista artista) {
		artistaRepository.save(artista);
		System.out.println("Artista modificado con exito.");
	}

	@Transactional
	public void modificarCoordinador(Coordinador coordinador) {
		coordinadorRepository.save(coordinador);
	}

	@Transactional 
	public boolean comprobarNombreUsuario(String nombreUsuario) {

		return credencialesRepository.existsByNombre(nombreUsuario);
	}

	@Transactional
	public Coordinador getCoordinador(Long id) {
		return coordinadorRepository.findById(id).orElse(null);
	}

	@Transactional
	public List<Persona> getCredencialesSistema() {
		return personaRepository.findAll();
	}

	@Transactional
	public Artista getArtista(Long id) {
		return artistaRepository.findById(id).orElse(null);
	}

	@Transactional
	public List<Especialidad> getEspecialidades() {
		return especialidadRepository.findAll();
	}

	@Transactional
	public void modificarPersona() {

		
		
		List<Persona> lista = getCredencialesSistema();

		for (Persona per : lista) {
			System.out.println("ID: " + per.getId() + " - " + per.getNombre());
		}
		System.out.println("Introduce el ID de la persona a modificar:");
		Long idMod = leer.nextLong();
		leer.nextLine();

		Persona p = getPersonaById(idMod);

		if (p.getPerfil() == Perfil.ARTISTA) {
			Artista art = (Artista) p;

			System.out.println("Introduce un nuevo apodo o pulsa ENTER:");
			String nuevoApodo = leer.nextLine();
			if (!nuevoApodo.isBlank()) {
			    art.setApodo(nuevoApodo);
			}

			System.out.println("Especialidades de "+art.getNombre()+":");
			
			for (Especialidad e : art.getEspecialidades()) {
				System.out.println("- "+e.getNombre());
			}
			
			System.out.println("Quieres actualizar sus especialidades? (s/n)");
			String resp = leer.nextLine();
			if (resp.trim().toLowerCase().equals("s")) {
				Set<Especialidad> nuevasEspec = new HashSet<>();

				System.out.println("Especialidades disponibles:");
				List<Especialidad> especialidades = getEspecialidades();
				for (Especialidad e : especialidades) {
					System.out.println(e.getId() + " - " + e.getNombre());
				}

				System.out.println("Indica el conjunto de las especialidades separadas por comas (ej: 1,3,4)");
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
					System.out.println("Especialidades asignadas correctamente.");
				} else {
					System.out.println(
							"Error: Debes elegir entre 1 y 5 especialidades. El registro continuará sin ellas.");
				}
			}
			modificarArtista(art);

		} else if (p.getPerfil() == Perfil.COORDINACION) {
			Coordinador coor = (Coordinador) p;

			if (coor.isSenior()) {
				System.out.println("Este coordinador ya es senior, eso no se puede cambiar.");
			} else {
				System.out.println("Quieres hacer a " + coor.getNombre() + " Coordinador Senior? (s/n)");
				String resp = leer.nextLine().trim().toLowerCase();
				if (resp.equals("s")) {
					coor.setSenior(true);
					System.out.println("Introduce la fecha de ascenso: (yyyy-mm-dd)");
					try {
						coor.setFechasenior(LocalDate.parse(leer.nextLine()));
						System.out.println("Ascenso registrado");
					} catch (Exception e) {
						System.out.println("Formato de fecha erróneo. El ascenso no se ha guardado.");
						coor.setSenior(false);
					}
				}
			}

			modificarCoordinador(coor);
		}

	}

	@Transactional
	public Set<Artista> getArtistas() {
	    // Obtenemos la lista de todos los artistas y la transformamos en un HashSet
	    List<Artista> listaArtistas = artistaRepository.findAll();
	    return new HashSet<>(listaArtistas);
	}

}
