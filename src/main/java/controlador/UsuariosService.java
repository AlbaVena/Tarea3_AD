package controlador;

import java.util.List;
import java.util.Optional;

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

@Service
public class UsuariosService {

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

	public Sesion getSesion() {
		return actual;
	}

	public void setSesion(Sesion actual) {
		this.actual = actual;
	}

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
			Persona p = cred.get().getPersona(); // recoger la persona vinculada
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
	public void mostrarFicha() {
		Persona usuario = actual.getUsuActual();
		/**
		 * vamos a recordar que actualmente hay un artista usando esyte metodo, ya
		 * tenemos su id en memoriA
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
	        // 1. Establecemos el vínculo mientras todo está en memoria (RAM)
	        nueva.getCredenciales().setPersona(nueva);
	    }

	    // 2. Guardamos la persona. 
	    // Al tener CascadeType.ALL, Hibernate guardará primero la Persona,
	    // obtendrá su ID, lo meterá en el objeto Credenciales y luego guardará las Credenciales.
	    personaRepository.save(nueva);
		
	}

	@Transactional
	public Boolean comprobarEmail(String email) {
		return personaRepository.existsByEmail(email);
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

	@Transactional // creo que sobra porque solo lee los datos
	public Boolean comprobarNombreUsuario(String nombreUsuario) {
		Boolean valido = true;
		if (credencialesRepository.existsByNombre(nombreUsuario)) {
			System.out.println("Ese nombre de usuario ya existe, elige otro.");
			return false;
		}
		return valido;
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

}
