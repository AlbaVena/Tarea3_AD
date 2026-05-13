package servicios.implementacion;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import entidades.Artista;
import entidades.Espectaculo;
import entidades.Numero;
import repository.InformesRepository;
import servicios.IInformeService;

@Service
public class InformeService implements IInformeService {

    @Autowired
    private InformesRepository informesRepository;

    @Override
    public void generarYGuardarInforme(Espectaculo espectaculo) {
        try {
            // 1. Generar el XML con DOM
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.newDocument();

            Element raiz = doc.createElement("informe");
            doc.appendChild(raiz);

            Element eNombre = doc.createElement("nombre");
            eNombre.setTextContent(espectaculo.getNombre());
            raiz.appendChild(eNombre);

            Element eFechaIni = doc.createElement("fechaInicio");
            eFechaIni.setTextContent(espectaculo.getFechaini().toString());
            raiz.appendChild(eFechaIni);

            Element eFechaFin = doc.createElement("fechaFin");
            eFechaFin.setTextContent(espectaculo.getFechafin().toString());
            raiz.appendChild(eFechaFin);

            if (espectaculo.getEncargadoCoor() != null) {
                Element eCoor = doc.createElement("coordinador");
                eCoor.setTextContent(espectaculo.getEncargadoCoor().getNombre());
                raiz.appendChild(eCoor);
            }

            Element eNumeros = doc.createElement("numeros");
            raiz.appendChild(eNumeros);

            if (espectaculo.getNumeros() != null) {
                for (Numero num : espectaculo.getNumeros()) {
                    Element eNum = doc.createElement("numero");

                    Element eNomNum = doc.createElement("nombre");
                    eNomNum.setTextContent(num.getNombre());
                    eNum.appendChild(eNomNum);

                    Element eDur = doc.createElement("duracion");
                    eDur.setTextContent(num.getDuracion() + " min");
                    eNum.appendChild(eDur);

                    Element eArtistas = doc.createElement("artistas");
                    if (num.getArtistas() != null) {
                        for (Artista a : num.getArtistas()) {
                            Element eArt = doc.createElement("artista");
                            eArt.setTextContent(a.getNombre());
                            eArtistas.appendChild(eArt);
                        }
                    }
                    eNum.appendChild(eArtistas);
                    eNumeros.appendChild(eNum);
                }
            }

            // 2. Document → String
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter sw = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            String xmlString = sw.toString();

            // 3. Guardar en ficheros/
            String nombreFichero = "informe_espectaculo" + espectaculo.getId() + ".xml";
            File dir = new File("ficheros");
            if (!dir.exists()) dir.mkdirs();
            try (FileWriter fw = new FileWriter("ficheros/" + nombreFichero)) {
                fw.write(xmlString);
            }
            System.out.println("XML guardado en disco: ficheros/" + nombreFichero);

            // 4. Guardar en eXistDB
            informesRepository.guardarDocumento(nombreFichero, xmlString);

        } catch (Exception e) {
            System.err.println("Error al generar informe: " + e.getMessage());
            e.printStackTrace();
        }
    }
}