package entidades;

import java.time.LocalDateTime;

public class LogOperacion {
	
	private long id;
	private String fechaHora; //da problemas con LocalDateTime
	private String usuario;
	private String tipoOperacion;
	private String resumen;
	
	public LogOperacion(String usuario, TipoOperacion tipoOperacion, String nombreEntidad, long idEntidad) {
		this.fechaHora = LocalDateTime.now().toString();
		this.usuario = usuario;
		this.tipoOperacion = tipoOperacion.name();
		
		//resumen automatico
		if (tipoOperacion == TipoOperacion.NUEVO) {
	        this.resumen = "Se ha insertado un nuevo " + nombreEntidad + " con id " + idEntidad;
	    } else if (tipoOperacion == TipoOperacion.ACTUALIZACION) {
	        this.resumen = "Se ha actualizado la información del id " + idEntidad + " de " + nombreEntidad;
	    } else if (tipoOperacion == TipoOperacion.BORRADO) {
	        this.resumen = "Se ha borrado el " + nombreEntidad + " con id " + idEntidad;
	    }
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}



	public String getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}



	public String getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}
	
 

}
