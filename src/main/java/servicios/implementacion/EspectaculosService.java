package servicios.implementacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entidades.Espectaculo;
import entidades.Sesion;
import entidades.TipoOperacion;
import jakarta.transaction.Transactional;
import repository.EspectaculoRepository;
import servicios.IEspectaculosService;
import servicios.ILogService;

@Service
public class EspectaculosService implements IEspectaculosService{

	@Autowired
	private EspectaculoRepository espectaculoRepository;
	
	@Autowired
	private Sesion sesion;

	@Autowired
	private ILogService logService;

	@Transactional
	public List<Espectaculo> getEspectaculos() {
		return espectaculoRepository.findAll();
	}

	@Transactional
	public void setEspectaculos(List<Espectaculo> espectaculos) {
		espectaculoRepository.saveAll(espectaculos);
	}

	@Transactional
	public void guardarEspectaculo(Espectaculo aGuardar) {
		boolean esNuevo = aGuardar.getId() == 0;
		espectaculoRepository.save(aGuardar);
		
		String usuario = sesion.getUsuActual() != null ?
		        sesion.getUsuActual().getCredenciales().getNombre() : "sistema";
		    TipoOperacion tipo = esNuevo ? TipoOperacion.NUEVO : TipoOperacion.ACTUALIZACION;
		    logService.registrarOperacion(usuario, tipo, "Espectaculo", aGuardar.getId());
	}
	
	@Transactional
	public Espectaculo getEspectaculo(long id) {
		return espectaculoRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public void eliminarEspectaculo(long id) {
		espectaculoRepository.deleteById(id);
		
		String usuario = sesion.getUsuActual() != null ?
		        sesion.getUsuActual().getCredenciales().getNombre() : "sistema";
		    logService.registrarOperacion(usuario, TipoOperacion.BORRADO, "Espectaculo", id);
	}
	
	
	

}
