package entidades;

import java.util.List;
import java.util.Set;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "artistas")
	@PrimaryKeyJoinColumn(name = "id_persona") //hereda el id_persona (buscame en el origen de id_persona)
public class Artista extends Persona {
	
	
	private static final long serialVersionUID = 1L;

	@Column(name = "id_artista", insertable = false, updatable = false)
	private long idArt;
	
	@Column(name = "apodo", length = 25)
	private String apodo = null;
	
	@ManyToMany
	@JoinTable(name = "artita_especialidad",
		joinColumns = @JoinColumn(name = "id_artista", referencedColumnName = "id_artista"), //une la tabla con id_ARTISTA, a la tabla con id_especialidad
		inverseJoinColumns = @JoinColumn(name = "id_especialidad"))
	private Set<Especialidad> especialidades;
	
	@ManyToMany
	@JoinTable(name = "artistas_numeros",
	joinColumns = @JoinColumn(name = "id_artista", referencedColumnName = "id_artista"),
	inverseJoinColumns = @JoinColumn(name = "id_numero"))
	private List<Numero> numeros;
	
	

	
	public Artista() {

	}

	public Artista(long id, String email, String nombre, String nacionalidad,
			Credenciales credenciales) {
		super(id, email, nombre, nacionalidad, credenciales, Perfil.ARTISTA);
	}
	public Artista (String linea) {
		super();
		String[] propiedades = linea.split("\\|");
		this.id = Long.parseLong(propiedades[0]);
		this.credenciales = new Credenciales(propiedades[1], propiedades[2]);
		this.email = propiedades[3];
		this.nombre = propiedades[4];
		this.nacionalidad = propiedades[5];
		this.perfil=Perfil.ARTISTA;
}

	public Artista(long id, String email, String nombre, String nacionalidad,
			Credenciales credenciales, long idArt, String apodo,
			Set<Especialidad> especialidades, List<Numero> numeros) {
		super(id, email, nombre, nacionalidad, credenciales, Perfil.ARTISTA);
		this.idArt = idArt;
		this.apodo = apodo;
		this.especialidades = especialidades;
		this.numeros = numeros;
	}

	public long getIdArt() {
		return idArt;
	}

	public void setIdArt(long idArt) {
		this.idArt = idArt;
	}

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public Set<Especialidad> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(Set<Especialidad> especialidades) {
		this.especialidades = especialidades;
	}

	public List<Numero> getNumeros() {
		return numeros;
	}

	public void setNumeros(List<Numero> numeros) {
		this.numeros = numeros;
	}


}
