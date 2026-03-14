package controlador;

import java.util.List;

import entidades.Artista;
import entidades.Coordinador;
import entidades.Especialidad;
import entidades.Persona;
import entidades.Sesion;


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
	
}
