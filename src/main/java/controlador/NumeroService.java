package controlador;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entidades.Numero;
import jakarta.transaction.Transactional;
import repository.NumeroRepository;


@Service
public class NumeroService {

	@Autowired
	private NumeroRepository numeroRepository;
	
	
	 @Transactional
	public List<Numero> getNumeros() {		
		return  numeroRepository.findAll() ;
	}
	
	 @Transactional
	public void guardarNumero(Numero nuevo) {
		numeroRepository.save(nuevo);
	}
	
	
	
	
	

}
