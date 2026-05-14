package utils;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import entidades.Artista;
import entidades.Coordinador;
import entidades.Especialidad;
import entidades.Espectaculo;
import entidades.Numero;

public class EspectaculoXMLWriter {

    /**
     * Construye y devuelve el Document DOM del informe de un espectaculo.
     * solo construye la estructura XML en memoria.
     * 
     * igual que leiamos de un xml, pero al reves. va construyendo nodos o hijos con el contenido que se le indica
     */
    public static Document construirDocumento(Espectaculo espectaculo) throws ParserConfigurationException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.newDocument();

        Element informe = doc.createElement("informe");
        doc.appendChild(informe);

        informe.appendChild(crearElemento(doc, "fechahora", Validador.formatearFechaHora(LocalDateTime.now())));

        Element espectaculoEl = doc.createElement("espectaculo");
        informe.appendChild(espectaculoEl);

        espectaculoEl.appendChild(crearElemento(doc, "id", String.valueOf(espectaculo.getId())));
        espectaculoEl.appendChild(crearElemento(doc, "nombre", espectaculo.getNombre()));
        espectaculoEl.appendChild(crearElemento(doc, "fechaini", Validador.formatearFecha(espectaculo.getFechaini())));
        espectaculoEl.appendChild(crearElemento(doc, "fechafin", Validador.formatearFecha(espectaculo.getFechafin())));

        Coordinador coord = espectaculo.getEncargadoCoor();
        Element coordinacion = doc.createElement("coordinacion");
        coordinacion.appendChild(crearElemento(doc, "nombre", coord.getNombre()));
        coordinacion.appendChild(crearElemento(doc, "email", coord.getEmail()));
        espectaculoEl.appendChild(coordinacion);

        Element numeros = doc.createElement("numeros");
        espectaculoEl.appendChild(numeros);

        for (Numero numero : espectaculo.getNumeros()) {
            Element numeroEl = doc.createElement("numero");
            numeroEl.appendChild(crearElemento(doc, "nombre", numero.getNombre()));
            numeroEl.appendChild(crearElemento(doc, "duracion", String.valueOf(numero.getDuracion())));

            Element artistas = doc.createElement("artistas");
            for (Artista artista : numero.getArtistas()) {
                Element artistaEl = doc.createElement("artista");
                artistaEl.appendChild(crearElemento(doc, "nombre", artista.getNombre()));
                artistaEl.appendChild(crearElemento(doc, "nacionalidad", artista.getNacionalidad()));
                artistaEl.appendChild(crearElemento(doc, "email", artista.getEmail()));

                String especialidades = artista.getEspecialidades().stream()
                        .map(Especialidad::getNombre)
                        .collect(Collectors.joining(","));
                artistaEl.appendChild(crearElemento(doc, "especialidades", especialidades));

                String apodo = artista.getApodo();
                if (apodo != null && !apodo.isBlank()) {
                    artistaEl.appendChild(crearElemento(doc, "apodo", apodo));
                }
                artistas.appendChild(artistaEl);
            }
            numeroEl.appendChild(artistas);
            numeros.appendChild(numeroEl);
        }

        return doc;
    }

    /**
     * crea una ETIQUETA xml, con un nombre y un valor
     * @param doc documento en memoria (que se va construyendo)
     * @param tag nombre
     * @param valor contrenido
     * @return la etiqueta formada
     */
    private static Element crearElemento(Document doc, String tag, String valor) {
        Element el = doc.createElement(tag);
        el.setTextContent(valor);
        return el;
    }
}