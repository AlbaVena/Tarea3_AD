package repository;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Repository
public class PaisesRepository {

	/**
	 * Recupera los datos de un fichero XML
	 * 
	 * @return una coleccion <K> siglas <V> NombrePais
	 */
	public Map<String, String> cargarPaises() {
		Map<String, String> paises = new HashMap<String, String>();

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document documento = builder.parse("ficheros/paises.xml");
			documento.getDocumentElement().normalize();

			NodeList listaPaises = documento.getElementsByTagName("pais");
			// en
			// la
			// lista
			// los
			// elementos
			// con
			// etiqueta
			// "pais"
			for (int i = 0; i < listaPaises.getLength(); i++) {
				Node nodo = listaPaises.item(i); // me devuelve el nodo en
				// posicion i

				if (nodo.getNodeType() == Node.ELEMENT_NODE) { // devuelve un
					// entero. solo
					// los elementos
					// tienen
					// etiquetas
					// hijo
					Element elemento = (Element) nodo; // lo covertimos a
					// element para usar los
					// metodos <PAIS>

					String id = getNodo("id", elemento);
					String nombre = getNodo("nombre", elemento);

					paises.put(id, nombre);

				}

			}
		} catch (Exception e) {
			System.out.println("Error al leer XML: " + e.getMessage());
			e.printStackTrace();
		}

		return paises;
	}

	/**
	 * metodo para trabajar con XML
	 * 
	 * @param etiqueta
	 * @param elem
	 * @return el nodo hijo de la etiqueta concreta
	 */
	private static String getNodo(String etiqueta, Element elem) { // "etiqueta"
																	// concreta
		NodeList nodo = elem.getElementsByTagName(etiqueta).item(0).getChildNodes(); // busca todas las qtiquetas hijas
		// con el nombre de la etiqueta
		// devuelve los nodos hijos
		Node valorNodo = nodo.item(0); // primer hijo ID
		return valorNodo.getNodeValue(); // el nodo de TEXTO (valor real) NOMBRE
	}
}
