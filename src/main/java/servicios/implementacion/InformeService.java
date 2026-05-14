package servicios.implementacion;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import entidades.Espectaculo;
import repository.InformesRepository;
import servicios.IInformeService;
import utils.EspectaculoXMLWriter;

@Service
public class InformeService implements IInformeService {

    @Autowired
    private InformesRepository informesRepository;

    @Override
    public void generarYGuardarInforme(Espectaculo espectaculo) {
        try {
            //primero construye el document DOM
            Document doc = EspectaculoXMLWriter.construirDocumento(espectaculo);

            /**
             * doc es un OBJETO, para guardarlo como texto hay que serializarlo
             * con Transformer.
             * crea un conversor, con lo que le indiquemos (UTF8)
             */
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            //sw escribe en memoria
            StringWriter sw = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(sw));//de donde leer, a donde escribir
            String xmlString = sw.toString();//ya en texto plano lo puede escribir

            //persistir (repository)
            String nombreFichero = "informe_espectaculo" + espectaculo.getId() + ".xml";
            informesRepository.guardarEnDisco(nombreFichero, xmlString);
            informesRepository.guardarDocumento(nombreFichero, xmlString);

        } catch (Exception e) {
            System.err.println("Error al generar informe: " + e.getMessage());
            e.printStackTrace();
        }
    }
}