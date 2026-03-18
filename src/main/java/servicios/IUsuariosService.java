package servicios;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import entidades.Artista;
import entidades.Coordinador;
import entidades.Especialidad;
import entidades.Persona;
import entidades.Sesion;
import javafx.util.Callback;

@Service
public interface IUsuariosService {

	Persona login(String nombreUsuario, String password);
	
	void logOut();
	
	void mostrarFichaArtista();
	
	List<Persona> getCredencialesSistema();
	
	Persona getPersonaById(Long id);
	
	Sesion getSesion();
	
	Coordinador getCoordinador(Long id);
	
	Artista getArtista(Long id);
	
	void modificarPersona();
	
	List<Especialidad> getEspecialidades();
	
	boolean comprobarEmail(String email);
	
	boolean comprobarNombreUsuario(String nombreUsuario);
	
	void crearPersona(Persona nueva);

	Set<Artista> getArtistas();
	
}
