package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import entidades.Artista;
import entidades.Coordinador;
import entidades.Especialidad;
import entidades.Espectaculo;
import entidades.Informe;
import entidades.Numero;

public class EspectaculoXMLWriter {

    private final static String RUTAXML = "ficheros\\";

    public static void escribir(Espectaculo espectaculo) throws Exception {

        Informe informe = new Informe(espectaculo);
        String rutaFichero = generarNombreFichero(informe.getEspectaculo());

        if (!comprobarCarpeta(rutaFichero)) {
            throw new Exception("No existe la carpeta: " + new File(rutaFichero).getParent());
        }

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.newDocument();

        Element informeEl = doc.createElement("informe");
        doc.appendChild(informeEl);

        informeEl.appendChild(crearElemento(doc, "fechahora", Validador.formatearFechaHora(LocalDateTime.now())));

        Element espectaculoEl = doc.createElement("espectaculo");
        informeEl.appendChild(espectaculoEl);

        espectaculoEl.appendChild(crearElemento(doc, "id", String.valueOf(informe.getEspectaculo().getId())));
        espectaculoEl.appendChild(crearElemento(doc, "nombre", informe.getEspectaculo().getNombre()));
        espectaculoEl.appendChild(crearElemento(doc, "fechaini", Validador.formatearFecha(informe.getEspectaculo().getFechaini())));
        espectaculoEl.appendChild(crearElemento(doc, "fechafin", Validador.formatearFecha(informe.getEspectaculo().getFechafin())));

        Coordinador coord = informe.getEspectaculo().getEncargadoCoor();
        Element coordinacion = doc.createElement("coordinacion");
        coordinacion.appendChild(crearElemento(doc, "nombre", coord.getNombre()));
        coordinacion.appendChild(crearElemento(doc, "email", coord.getEmail()));
        espectaculoEl.appendChild(coordinacion);

        Element numeros = doc.createElement("numeros");
        espectaculoEl.appendChild(numeros);

        for (Numero numero : informe.getEspectaculo().getNumeros()) {
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

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        transformer.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(rutaFichero)));
    }

    private static Element crearElemento(Document doc, String tag, String valor) {
        Element el = doc.createElement(tag);
        el.setTextContent(valor);
        return el;
    }

    private static boolean comprobarCarpeta(String rutaFichero) {
        File carpeta = new File(rutaFichero).getParentFile();
        if (carpeta != null && !carpeta.exists()) {
            return carpeta.mkdirs();
        }
        return true;
    }

    private static String generarNombreFichero(Espectaculo espectaculo) {
        File carpeta = new File(RUTAXML);
        int count = 0;
        if (carpeta.exists()) {
            count = carpeta.listFiles(f -> f.getName().endsWith(".xml")).length;
        }
        return RUTAXML + String.format("informe_espectaculo%02d.xml", count + 1);
    }
}
