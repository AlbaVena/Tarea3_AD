/**
* Clase Socio.java
* @author Alba Vena Garcia
* @version 1.0
*/

package entidades;

import java.time.LocalDate;

public class Socio extends Persona{
	
	private LocalDate fechainscripcion;

	public Socio(String email, String nombre, String nacionalidad,Credenciales credenciales, LocalDate fechainscripcion) {
		super(id, email, email, email, credenciales, Perfil.SOCIO);
		this.fechainscripcion = fechainscripcion;
	}
	
	
	

}
