package repository;

import java.io.File;
import java.io.FileWriter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

/**
 * Persistencia de informes: disco local y eXistDB
 */
@Repository
public class InformesRepository {

    @Value("${existdb.url}")
    private String url;

    @Value("${existdb.collection}")
    private String collectionPath;

    @Value("${existdb.user}")
    private String user;

    @Value("${existdb.password}")
    private String password;

    /**
     * Guarda el XML en la carpeta ficheros/ del disco local
     */
    public void guardarEnDisco(String nombreFichero, String contenidoXML) throws Exception {
        File dir = new File("ficheros");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try (FileWriter fw = new FileWriter("ficheros/" + nombreFichero)) {
            fw.write(contenidoXML);
        }
        System.out.println("XML guardado en disco: ficheros/" + nombreFichero);
    }

    /**
     * Guarda el XML en la coleccion /db/informes de eXistDB
     */
    public void guardarDocumento(String nombreFichero, String contenidoXML) {
        Collection col = null;
        XMLResource res = null;
        try {
            inicializarDriver();
            col = DatabaseManager.getCollection(url + collectionPath, user, password); //conecta con la coleccion donde se guarda
            if (col == null) {
                col = crearColeccion();
            }
            res = (XMLResource) col.createResource(nombreFichero, "XMLResource"); //crea el XML
            res.setContent(contenidoXML); //le añade el contenido
            col.storeResource(res); //lo guarda en ExistDB
            System.out.println("Documento guardado en eXistDB: " + nombreFichero);
        } catch (Exception e) {
            System.err.println("Error al guardar en eXistDB: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (res != null) {
                    ((org.exist.xmldb.EXistResource) res).freeResources();
            
                }} catch (Exception ignored) {
            }
            try {
                if (col != null) {
                    col.close();
            
                }} catch (Exception ignored) {
            }
        }
    }

    private void inicializarDriver() throws Exception {
        Class<?> cl = Class.forName("org.exist.xmldb.DatabaseImpl"); //parecido a import
        //carga una clase (que no esta) en tiempo de ejecucion por su nombre.
        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
    }

    private Collection crearColeccion() throws Exception {
        // separamos "/db/informes" en padre="/db" e hijo="informes"
        String padre = collectionPath.substring(0, collectionPath.lastIndexOf("/"));
        String nombreCol = collectionPath.substring(collectionPath.lastIndexOf("/") + 1);

        Collection raiz = DatabaseManager.getCollection(url + padre, user, password);
        CollectionManagementService cms = (CollectionManagementService) raiz
                .getService("CollectionManagementService", "1.0");
        Collection nueva = cms.createCollection(nombreCol);
        System.out.println("Coleccion creada en eXistDB: " + collectionPath);
        raiz.close();
        return nueva;
    }
}
