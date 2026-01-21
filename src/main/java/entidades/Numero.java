package entidades;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "numeros")
public class Numero implements Serializable{
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	/*
	 * no lo defino con JPA porque no se va a usar
	 */
	private int orden;
	
	@Column(name = "nombre", nullable = false, length = 25)
	private String nombre;
	
	@Column(name = "duracion", nullable = false, length = 2)
	private int duracion;
	
	@ManyToOne
	@JoinColumn(name = "id_espectaculo", referencedColumnName = "id_espectaculo", nullable = false)
	private Espectaculo espectaculo;
	
	@ManyToMany(mappedBy = "numeros")
	private Set <Artista> artistas;
	
	public Numero() {
		super();
	}



	public Numero(long id, int orden, String nombre, int duracion, Espectaculo espectaculo, Set<Artista> artistas) {
		this.id = id;
		this.orden = orden;
		this.nombre = nombre;
		this.duracion = duracion;
		this.espectaculo = espectaculo;
		this.artistas = artistas;
	}
	
	

	public Numero(long id, String nombre, int duracion, Set<Artista> artistas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.duracion = duracion;
		this.artistas = artistas;
	}



	public Numero (String nombre, int duracion) {
		this.nombre = nombre;
		this.duracion = duracion;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public Espectaculo getEspectaculo() {
		return espectaculo;
	}

	public void setEspectaculo(Espectaculo espectaculo) {
		this.espectaculo = espectaculo;
	}

	public Set<Artista> getArtistas() {
		return artistas;
	}

	public void setArtistas(Set<Artista> artistas) {
		this.artistas = artistas;
	}

	@Override
	public String toString() {
		return "Numero id:" + id + ", nombre=" + nombre
				+ ", duracion=" + duracion + ", espectaculo=" + espectaculo
				+ ", artistas=" + artistas + "]";
	}
	
	

}
