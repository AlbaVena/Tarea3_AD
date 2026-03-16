package servicios.implementacion;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repository.PaisesRepository;

@Service
public class PaisesService {
	
	@Autowired
	private PaisesRepository paisesRepository;

	public Map<String, String> getPaises() {
		Map<String, String> paises = paisesRepository.cargarPaises();
		return paises;
	}

}
