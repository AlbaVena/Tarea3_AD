package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Validador {

	public final static String nombreUsuarioRegex = "^[a-zA-Z]{3,25}$";
	public final static String nombreGeneralRegex = "^[a-zA-ZñÑ]{3,25}$";
	public final static String nombreEspectaculoRegex = "^[a-zA-ZñÑ\\s]{3,25}$";
	public final static String passwordRegex = "^[a-zA-Z0-9_!]{4,15}$";
	public final static String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";


	public static final DateTimeFormatter formFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public static boolean esFechaValida(String fechaIni, String fechaFin) {
		LocalDate inicio = LocalDate.parse(fechaIni, formFecha);
		LocalDate fin = LocalDate.parse(fechaFin, formFecha);
		
		if (fin.isBefore(inicio)) {
			return false; //si inicio es antes que fin
		}
		
		long meses = ChronoUnit.MONTHS.between(inicio, fin);
		return meses >=1 && meses <=12 ; //validar entre 1 y 12 meses		
	}
	
	public static boolean esDuracionValida(int duracion) {
        return duracion > 15; //Yo he decidido la duracion minima
    }
	
	/**
	 * Compara una cadena mediante un regex.
	 * @param cadena
	 * @param regex a eleccion
	 * @return true si la cadena coincide
	 */
	public static boolean esCadenaValida(String cadena, String regex) {
		return cadena != null && cadena.matches(regex);
	}

}
