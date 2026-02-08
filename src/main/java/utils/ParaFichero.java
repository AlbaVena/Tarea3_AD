package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import entidades.Espectaculo;
import entidades.ProgramProperties;

public class ParaFichero {

	/**
	 * Para leer .txt Devuelve un arraylist con el contenido si no existe lo crea.
	 * 
	 * @param ruta
	 * @return
	 */
	private static ArrayList<String> leerFichero(String ruta) {

		ArrayList<String> lineas = new ArrayList<>();
		File archivo = new File(ruta);
		try {

			if (!archivo.exists()) {
				FileWriter writer = new FileWriter(archivo);
				writer.write("");
				writer.close();
			} else {

				BufferedReader reader = new BufferedReader(new FileReader(ruta));
				String linea;
				while ((linea = reader.readLine()) != null) {
					lineas.add(linea);
				}
				reader.close();

			}
		} catch (IOException e) {
			System.out.println("No se ha podido cargar el fichero: " + ruta);
		}
		return lineas;

	}

	/**
	 * leer un fichero txt devuelve un array de Persona
	 * 
	 * @return
	 */
//	private static ArrayList<Persona> cargarCredenciales() {
//		ArrayList<Persona> personas = new ArrayList<>();
//		// leer el fichero de credenciales
//		ArrayList<String> lineas = leerFichero(ProgramProperties.credenciales);
//
//		for (String linea : lineas) {
//			if (linea.contains("coordinacion")) {
//				personas.add(new Coordinador(linea));
//			} else if (linea.contains("artista")) {
//				personas.add(new Artista(linea));
//			}
//		}
//		return personas;
//	}

//	public static void persistirCredenciales() {
//		try {
//			FileWriter writer = new FileWriter(ProgramProperties.credenciales);
//			String contenido = "";
//			Persona[] credencialesSistema = null;
//			for (Persona p : credencialesSistema ) {
//				contenido += p.toFicheroCredenciales()+"\n";
//			}
//			writer.write(contenido);
//			writer.close();
//		} catch (IOException e) {
//			System.out.println("error al escribir el archivo");
//		}
//	}

	/**
	 * lee un .dat de Objetos Serializados
	 * 
	 * @return
	 */
	private static ArrayList<Espectaculo> cargarEspectaculosDat() {
		ArrayList<Espectaculo> espectaculos = new ArrayList<Espectaculo>();
		File archivo = new File(ProgramProperties.espectaculos);
		if (!archivo.exists()) {
			try (ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(ProgramProperties.espectaculos, true))) {
				oos.writeObject(espectaculos);
				oos.close();
			} catch (FileNotFoundException e) {
				System.out.println("archivo no encontrado.");
			} catch (IOException e) {
				System.out.println("Error de escritura del archivo");
			}
		} else {
			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ProgramProperties.espectaculos))) {

				espectaculos = (ArrayList<Espectaculo>) ois.readObject();
				ois.close();
			} catch (FileNotFoundException e) {
				System.out.println("archivo espectaculos no encontrado");
			} catch (IOException e) {
				System.out.println("error de lectura del archivo al cargar");
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				System.out.println("error de conversion de tipos.");
			}

		}

		return espectaculos;
	}

	/*
	 * escribee un .dat de objetos serializados
	 */
	public static void guardarEspectaculoDat(Espectaculo aGuardar) {

		ArrayList<Espectaculo> espectaculos = new ArrayList<Espectaculo>();
		espectaculos = cargarEspectaculosDat();

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ProgramProperties.espectaculos))) {
			espectaculos.add(aGuardar);
			oos.writeObject(espectaculos);
			System.out.println("archivo modificado.");

		} catch (FileNotFoundException e) {
			System.out.println("no se pudo encontrar el archivo de espectaculos");
		} catch (IOException e) {
			System.out.println("error al escribir el archivo de espectaculos");
			e.printStackTrace();
		}
	}

}