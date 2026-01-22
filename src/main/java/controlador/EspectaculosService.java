package controlador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entidades.Espectaculo;
import jakarta.transaction.Transactional;
import repository.EspectaculoRepository;

@Service
public class EspectaculosService {

	@Autowired
	private EspectaculoRepository espectaculoRepository;

	@Transactional
	public List<Espectaculo> getEspectaculos() {
		return espectaculoRepository.findAll();
	}

	@Transactional
	public void setEspectaculos(ArrayList<Espectaculo> espectaculos) {
		espectaculoRepository.saveAll(espectaculos);
	}

	@Transactional
	public void guardarEspectaculo(Espectaculo aGuardar) {
		espectaculoRepository.save(aGuardar);
	}
	
	

}
