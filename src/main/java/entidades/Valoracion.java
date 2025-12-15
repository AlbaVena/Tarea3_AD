/**
* Clase Valoracion.java
* @author Alba Vena Garcia
* @version 1.0
*/

package entidades;

import java.time.LocalDate;

public class Valoracion {

	private long id;
	private float puntuacion;
	private String comentario;
	private LocalDate fechahora;

	public Valoracion(long id, float puntuacion, String comentario,
			LocalDate fechahora) {
		super();
		this.id = id;
		this.puntuacion = puntuacion;
		this.comentario = comentario;
		this.fechahora = fechahora;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getPuntuacion() {
		return puntuacion;
	}

	public void setPuntuacion(float puntuacion) {
		this.puntuacion = puntuacion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public LocalDate getFechahora() {
		return fechahora;
	}

	public void setFechahora(LocalDate fechahora) {
		this.fechahora = fechahora;
	}

}
