package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "espectaculos")
public class Espectaculo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_espectaculo")
	private long id;
	
	@Column(name = "nombre", nullable = false, length = 25)
	private String nombre;
	
	@Column(name = "fecha_inicio")
	private LocalDate fechaini;
	
	@Column(name = "fecha_fin")
	private LocalDate fechafin;
	
	@OneToMany(mappedBy = "espectaculo", cascade = CascadeType.ALL)
	private Set <Numero> numeros;
	
	@ManyToOne
	@JoinColumn(name = "id_coordinador", referencedColumnName = "id_coordinador", nullable = false)
	private Coordinador encargadoCoor;
	
	
	
	public Espectaculo() {
		super();
	}

	public Espectaculo(long id, String nombre, LocalDate fechaini, LocalDate fechafin, Coordinador coordinador) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaini = fechaini;
		this.fechafin = fechafin;
		this.numeros = new HashSet<>();
		this.encargadoCoor = coordinador;
	}

	public Espectaculo(long id, String nombre, LocalDate fechaini, LocalDate fechafin,Set<Numero> numeros, Coordinador encargadoCoor ) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaini = fechaini;
		this.fechafin = fechafin;
		this.numeros = numeros;
		this.setEncargadoCoor(encargadoCoor);
		
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public LocalDate getFechaini() {
		return fechaini;
	}
	public void setFechaini(LocalDate fechaini) {
		this.fechaini = fechaini;
	}
	public LocalDate getFechafin() {
		return fechafin;
	}
	public void setFechafin(LocalDate fechafin) {
		this.fechafin = fechafin;
	}
	public Set<Numero> getNumeros() {
		return numeros;
	}
	public void setNumeros(Set<Numero> numeros) {
		this.numeros = numeros;
	}
	@Override
	public String toString() {
		return "Espectaculo id:" + id + ",nombre: "+ nombre + ", desde " +fechaini + ", hasta " + fechafin;
	}
	public Coordinador getEncargadoCoor() {
		return encargadoCoor;
	}
	public void setEncargadoCoor(Coordinador encargadoCoor) {
		this.encargadoCoor = encargadoCoor;
	}
	
	
	
	

}
