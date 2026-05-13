package repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.modules.XMLResource;

/**
 * Clase dedicada exclusivamente al manejo de eXistDB.
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

    private void inicializarDriver() throws Exception {
        Class<?> cl = Class.forName("org.exist.xmldb.DatabaseImpl");
        Database database = (Database) cl.getDeclaredConstructor().newInstance();
        database.setProperty("create-database", "true");
        DatabaseManager.registerDatabase(database);
    }

    /**
     * Guarda un documento XML en eXistDB en la coleccion /db/informes
     */
    public void guardarDocumento(String nombreFichero, String contenidoXML) {
        Collection col = null;
        XMLResource res = null;
        try {
            inicializarDriver();
            col = DatabaseManager.getCollection(url + collectionPath, user, password);
            if (col == null) {
                throw new RuntimeException("No se pudo conectar con la coleccion eXistDB: " + collectionPath);
            }
            res = (XMLResource) col.createResource(nombreFichero, "XMLResource");
            res.setContent(contenidoXML);
            col.storeResource(res);
            System.out.println("Documento guardado en eXistDB: " + nombreFichero);
        } catch (Exception e) {
            System.err.println("Error al guardar en eXistDB: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (res != null) ((org.exist.xmldb.EXistResource) res).freeResources(); } catch (Exception ignored) {}
            try { if (col != null) col.close(); } catch (Exception ignored) {}
        }
    }
}