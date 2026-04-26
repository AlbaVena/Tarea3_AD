package servicios;

import java.time.LocalDateTime;
import java.util.List;

import entidades.LogOperacion;
import entidades.TipoOperacion;

public interface ILogService {
	
	void registrarOperacion(String usuario, TipoOperacion tipoOperacion, String nombreEntidad, long idEntidad);
	
	List <LogOperacion> consultarHistorial(String usuario, List<String> tipos, LocalDateTime desde, LocalDateTime hasta);
	
	List<LogOperacion> getTodos();

}
