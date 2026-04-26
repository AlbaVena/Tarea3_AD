package entidades.objectdb;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ResolucionIncidencia {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private LocalDateTime fechaHoraResolucion;
	
	@Column(nullable = false)
	private String accionesRealizadas;
	
	@Column(nullable = false)
	private Long idPersonaResuelve;
	
	@ManyToOne
	private Incidencia incidencia;
	
	
	public ResolucionIncidencia() {
		this.fechaHoraResolucion = LocalDateTime.now();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public LocalDateTime getFechaHoraResolucion() {
		return fechaHoraResolucion;
	}


	public void setFechaHoraResolucion(LocalDateTime fechaHoraResolucion) {
		this.fechaHoraResolucion = fechaHoraResolucion;
	}


	public String getAccionesRealizadas() {
		return accionesRealizadas;
	}


	public void setAccionesRealizadas(String accionesRealizadas) {
		this.accionesRealizadas = accionesRealizadas;
	}


	public Long getIdPersonaResuelve() {
		return idPersonaResuelve;
	}


	public void setIdPersonaResuelve(Long idPersonaResuelve) {
		this.idPersonaResuelve = idPersonaResuelve;
	}


	public Incidencia getIncidencia() {
		return incidencia;
	}


	public void setIncidencia(Incidencia incidencia) {
		this.incidencia = incidencia;
	}
	
	
	
	
}
