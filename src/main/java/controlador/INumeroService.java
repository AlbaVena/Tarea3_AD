package controlador;

import java.util.List;

import entidades.Numero;

public interface INumeroService {
	
	List<Numero> getNumeros();
	
	Numero getNumeroById(Long id);

	void guardarNumero(Numero nuevo);
}
