package servicios.implementacion;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import entidades.LogOperacion;
import entidades.TipoOperacion;
import servicios.ILogService;

@Service
public class LogService implements ILogService {

	private static final String ruta = "ficheros/log.db4o";

	@Override
	public void registrarOperacion(String usuario, TipoOperacion tipoOperacion, String nombreEntidad, long idEntidad) {
		ObjectContainer db = Db4oEmbedded.openFile(ruta);

		try {
			LogOperacion log = new LogOperacion(usuario, tipoOperacion, nombreEntidad, idEntidad);
			db.store(log);
			db.commit();
		} finally {
			db.close();
		}

	}

	@Override
	public List<LogOperacion> consultarHistorial(String usuario, TipoOperacion tipo, LocalDateTime desde,
			LocalDateTime hasta) {

		ObjectContainer db = Db4oEmbedded.openFile(ruta);
		List<LogOperacion> resultado = new ArrayList<>();

		try {
			// QBE: objeto de ejemplo
			LogOperacion ejemplo = new LogOperacion();
			if (usuario != null && !usuario.isEmpty()) {
				ejemplo.setUsuario(usuario);
			}
			if (tipo != null) {
				ejemplo.setTipoOperacion(tipo.name());
			}

			// consulta por ejemplo (QBE)
			ObjectSet<LogOperacion> logs = db.queryByExample(ejemplo);

			// filtrar fechas en memoria (QBE no soporta rangos)
			while (logs.hasNext()) {
				LogOperacion log = logs.next();
				boolean incluir = true;

				if (desde != null && log.getFechaHora().compareTo(desde.toString()) < 0) {
					incluir = false;
				}
				if (hasta != null && log.getFechaHora().compareTo(hasta.toString()) > 0) {
					incluir = false;
				}

				if (incluir) {
					resultado.add(log);
				}
			}
		} finally {
			db.close();
		}

		return resultado;
	}

	// prueba temporal para ver lo que he guardado
	public List<LogOperacion> getTodos() {
		ObjectContainer db = Db4oEmbedded.openFile(ruta);
		List<LogOperacion> resultado = new ArrayList<>();
		try {
			ObjectSet<LogOperacion> logs = db.query(LogOperacion.class);
			while (logs.hasNext()) {
				resultado.add(logs.next());
			}
		} finally {
			db.close();
		}
		return resultado;
	}

}
