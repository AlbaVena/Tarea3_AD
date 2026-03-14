package controlador;

import java.util.List;

import entidades.Espectaculo;

public interface IEspectaculosService {
	
	List<Espectaculo> getEspectaculos();
	
	Espectaculo getEspectaculo(long id);
	
	void guardarEspectaculo(Espectaculo aGuardar);

}
