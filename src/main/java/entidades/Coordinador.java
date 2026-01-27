package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;



@Entity
@Table(name = "coordinadores")
	@PrimaryKeyJoinColumn(name = "id_persona") //hereda el id_persona (buscame en el origen de id_persona)
public class Coordinador extends Persona implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "id_coordinador", insertable = false, updatable = false)
	private Long idCoord;
	
	@Column(name = "senior", nullable = false)
	private boolean senior = false;
	
	@Column(name = "fechasenior")
	private LocalDate fechasenior = null;
	
	@OneToMany(mappedBy = "encargadoCoor", cascade = CascadeType.ALL)
	private Set <Espectaculo> espectaculos;
	
	
	
	public Coordinador() {
		super();
	}

//	public Coordinador (String linea) {
//		super();
//		String[] propiedades = linea.split("\\|");
//		this.id = Long.parseLong(propiedades[0]);
//		this.credenciales = new Credenciales(propiedades[1], propiedades[2]);
//		this.email = propiedades[3];
//		this.nombre = propiedades[4];
//		this.nacionalidad = propiedades[5];
//			this.perfil=Perfil.COORDINACION;
//	}

	public Coordinador(Long id, String email, String nombre, String nacionalidad, Credenciales credenciales, long idCoordinador) {
		super(id, email, nombre, nacionalidad, credenciales, Perfil.COORDINACION);
		this.idCoord = idCoordinador;
	}

	public Coordinador(Long id, String email, String nombre, String nacionalidad, Credenciales credenciales,
			long idCoord, boolean senior, LocalDate fechasenior, Set <Espectaculo> espectaculos) {
		super(id, email, nombre, nacionalidad, credenciales, Perfil.COORDINACION);
		this.idCoord = idCoord;
		this.senior = senior;
		this.fechasenior = fechasenior;
		this.espectaculos = espectaculos;
	}

	public Long getIdCoord() {
		return idCoord;
	}

	public void setIdCoord(Long idCoord) {
		this.idCoord = idCoord;
	}

	public boolean isSenior() {
		return senior;
	}

	public void setSenior(boolean senior) {
		this.senior = senior;
	}

	public LocalDate getFechasenior() {
		return fechasenior;
	}

	public void setFechasenior(LocalDate fechasenior) {
		this.fechasenior = fechasenior;
	}

	public Set <Espectaculo> getEspectaculos() {
		return espectaculos;
	}

	public void setEspectaculos(Set<Espectaculo> espectaculos) {
		this.espectaculos = espectaculos;
	}

	@Override
	public String toString() {
		return "Coordinador [idCoord=" + idCoord + ", senior=" + senior
				+ ", fechasenior=" + fechasenior + ", espectaculos="
				+ espectaculos + "]";
	}
	
	

	
	
}
