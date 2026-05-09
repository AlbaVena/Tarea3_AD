package servicios;

import java.time.LocalDateTime;
import java.util.List;

import entidades.objectdb.Incidencia;
import entidades.objectdb.ResolucionIncidencia;
import entidades.objectdb.TipoIncidencia;

public interface IIncidenciasService {
	
	public void registrarIncidencia(Incidencia incidencia);
	
	public void resolverIncidencia(Long idIncidencia, String accionesRealizadas, Long idPersonaResuelve);
	
	public List <Incidencia> consultarIncidencias (TipoIncidencia tipo, Boolean resuelta, Long idEspectaculo, Long idNumero, 
			LocalDateTime desde, LocalDateTime  hasta);
	public ResolucionIncidencia getResolucionIncidencia(Long idIncidencia);

}
