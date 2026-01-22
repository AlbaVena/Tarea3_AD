/**
 * Clase EspectaculoDAO.java
 *
 * @author Alba Vena Garcia
 * @version 1.0
 */
package entidadesDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import com.mysql.jdbc.Statement;

import entidades.Coordinador;
import entidades.Espectaculo;
import entidades.Persona;
import factorias.DAOFactoryJDBC;

public class EspectaculoDAO {

    private DAOFactoryJDBC DAOF; // CONEXION
    private PersonaDAO PDAO;

    /**
     * PREPARED STATEMENT para trabajar con espectaculos:
     */
    private final String INSERTARESPECTACULOPS = "INSERT INTO espectaculos (nombre, fecha_inicio, fecha_fin, id_coordinador) VALUES (?, ?, ?, ?)";

    private final String SELECTESPECTACULO = "SELECT * FROM espectaculos";

    /**
     * Constructor
     * al crear un EspectaculoDAO se obtiene una conexion
     * se crea una instancia de PersonaDAO
     */
    public EspectaculoDAO() {
        DAOF = DAOFactoryJDBC.getDAOFactory();
        PDAO = new PersonaDAO();
    }

    /**
     * Se conecta a la base, hace el metodo, se desconecta de la base
     * @param espectaculo
     * @return un espectaculo creado en la bd
     */
    public Espectaculo insertarEspectaculo(Espectaculo espectaculo) {
        Espectaculo resultado = null;
        Boolean autocommit = true;
        try {
            Connection con = DAOF.getConexion();
            autocommit = con.getAutoCommit();
            con.setAutoCommit(false); // Desactivar el autocommit

           resultado = insertarEspectaculoBase(con, espectaculo);

            con.commit();
        } catch (SQLException e) {
            try {
                DAOF.getConexion().rollback();
                DAOF.getConexion().setAutoCommit(autocommit);
                System.err.println("Rollback.");
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return resultado;
    }

    /**
     * Inserta los datos del espectaculo
     * @param con conexion
     * @param espectaculo
     * @return espectaculo con los datos añadidos
     * @throws SQLException
     */
    private Espectaculo insertarEspectaculoBase(Connection con, Espectaculo espectaculo) throws SQLException {
        Espectaculo result = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

       
        try {
            // ***** Obtener la conexion ( y de paso los ID)****
            ps = con.prepareStatement(INSERTARESPECTACULOPS, Statement.RETURN_GENERATED_KEYS);

            // añadir los campos en el mismo orden
            ps.setString(1, espectaculo.getNombre());

            Date sqlDateIni = Date.valueOf(espectaculo.getFechaini()); //fecha de java a SQL
            ps.setDate(2, sqlDateIni);

            Date sqlDateFin = Date.valueOf(espectaculo.getFechafin());
            ps.setDate(3, sqlDateFin);

            ps.setLong(4, espectaculo.getEncargadoCoor().getIdCoord());

            //ps.setString(1, persona.getEmail());
            //ps.setString(2, persona.getNombre());
            //ps.setString(3, persona.getNacionalidad());
            // actualizar cambios
            int filas = ps.executeUpdate();

            if (filas == 0) {
                throw new SQLException("No se inserto nada");
            }

            // guardamos las claves ID autogeneradas
            rs = ps.getGeneratedKeys();

            if (rs.next()) {
                espectaculo.setId(rs.getInt(1));
            } else {
                throw new SQLException("No se pudo obtener el ID");
            }
            result = espectaculo;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new SQLException("No se pudo añadir");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new SQLException("Error al cerrar la consulta");
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new SQLException("Error al cerrar la conexion");
                }
            }
        }
        return result;
    }

    /**
     * Devuelve un array con los espectaculos de la BD
     * obtiene los espectaculos y crea un Espectaculo de cada fila
     * @return
     */
    public ArrayList<Espectaculo> getEspectaculos() {
        ArrayList<Espectaculo> espectaculos = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = DAOF.getConexion().prepareStatement(SELECTESPECTACULO);
            rs = ps.executeQuery();

            while (rs.next()) {
                // de persona
                long idEspectaculo = rs.getLong("id_espectaculo");
                String nombre = rs.getString("nombre");

                java.sql.Date fecha_inicio = rs.getDate("fecha_inicio"); //fecha de SQL a java
                LocalDate fecha_inicioLocal = fecha_inicio == null ? null : fecha_inicio.toLocalDate(); //ternario comprueba si fecha null

                java.sql.Date fecha_fin = rs.getDate("fecha_fin");
                LocalDate fecha_finLocal = fecha_fin == null ? null : fecha_fin.toLocalDate();
                
                long idCoordinador = rs.getLong("id_coordinador");
                Coordinador coordinadorEspectaculo = null;

                ArrayList<Persona> personas = PDAO.getPersonas();
                for(Persona p : personas){
                    if (p instanceof Coordinador){
                        Coordinador coordinadorLocal = (Coordinador)p;
                        if (((Coordinador) p).getIdCoord() == idCoordinador){
                            coordinadorEspectaculo = coordinadorLocal;
                        }
                    }
                }

                if (coordinadorEspectaculo == null){
                    throw new SQLException("Coordinador de espectaculo no encontrado");
                }

                Espectaculo espectaculoLocal = new Espectaculo(idEspectaculo, nombre, fecha_inicioLocal, fecha_finLocal, coordinadorEspectaculo);
                espectaculos.add(espectaculoLocal);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar la consulta");
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.err.println("Error al cerrar la conexion");
                }
            }
        }

        return espectaculos;
    }
}
