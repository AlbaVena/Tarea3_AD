package servicios.implementacion;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entidades.Artista;
import entidades.Numero;
import jakarta.transaction.Transactional;
import repository.NumeroRepository;
import servicios.INumeroService;


@Service
public class NumeroService implements INumeroService{

	@Autowired
	private NumeroRepository numeroRepository;

	@Autowired
	private DossierService dossierService;
	
	
	 @Transactional
	public List<Numero> getNumeros() {		
		return  numeroRepository.findAll() ;
	}
	
	 @Transactional
	public void guardarNumero(Numero nuevo) {
		System.out.println("Guardamos Numeros:" + nuevo.getNombre() + " para los artistas:");
		numeroRepository.save(nuevo);
		for (Artista artista : nuevo.getArtistas()) {
			System.out.println("Artista a incluir: "+artista.getNombre());
        	dossierService.actualizarTrayectoria(artista, nuevo);
    }
	}
	
	 @Transactional
	 public Numero getNumeroById(Long id) {
		 return numeroRepository.findById(id).orElse(null);
	 }
	
	
	
	

}
