package controlador;

import java.util.ArrayList;

import entidades.Numero;
import entidadesDAO.NumeroDAO;
import entidadesDAO.PersonaDAO;

public class NumeroService {
	
	private NumeroDAO NDAO = null;
	private PersonaDAO PDAO= null;

	private ArrayList<Numero> numeros = null;
	
	public NumeroService() {

		NDAO = new NumeroDAO();
        PDAO = new PersonaDAO();
	}
	
	public ArrayList<Numero> getNumeros() {
		ArrayList<Numero> result = null;
		result = NDAO.getNumeros();

		return result;
	}
	
	public void guardarNumero(Numero nuevo) {
		NDAO.insertarNumero(nuevo);
	}
	
	
	
	
	

}
