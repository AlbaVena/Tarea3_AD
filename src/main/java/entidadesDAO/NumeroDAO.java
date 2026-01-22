
package entidadesDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import entidades.Artista;
import entidades.Coordinador;
import entidades.Numero;
import entidades.Persona;
import factorias.DAOFactoryJDBC;

public class NumeroDAO {

	private PersonaDAO PDAO;
	private DAOFactoryJDBC DAOF; // CONEXION

	private final String INSERTARNUMEROPS = "INSERT INTO numeros (nombre, duracion, id_espectaculo) VALUES (?, ?, ?)";
	private final String INSERTARARTISTA_NUMEROSPS = "INSERT INTO artistas_numeros (id_artista, id_numero) VAUES (?, ?)";
	
	private final String SELECTNUMEROPS = "SELECT * FROM numeros";

	public NumeroDAO() {
        DAOF = DAOFactoryJDBC.getDAOFactory();
        PDAO = new PersonaDAO();
    }
	
	
	public int insertarNumero(Numero numero) {
		int resultado = -1;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = DAOF.getConexion().prepareStatement(INSERTARNUMEROPS, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, numero.getNombre());
			ps.setInt(2, numero.getDuracion());
			ps.setLong(3, numero.getEspectaculo().getId());

			int filas = ps.executeUpdate();

			if (filas == 0) {
				throw new SQLException("No se inserto nada");
			}

			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				resultado = rs.getInt(1);
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

		return resultado;
	}

	public boolean asignarNumeroArtista(long idArtista, int idNumero) {
		boolean exito = false;
		PreparedStatement ps = null;

		try {
			ps = DAOF.getConexion().prepareStatement(INSERTARARTISTA_NUMEROSPS);

			ps.setLong(1, idArtista);
			ps.setInt(2, idNumero);

			int filas = ps.executeUpdate();

			if (filas > 0) {
				exito = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					System.err.println("Error al cerrar la conexion");
				}
			}
		}

		return exito;

	}
	
	public ArrayList<Numero> getNumeros() {
        ArrayList<Numero> numeros = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = DAOF.getConexion().prepareStatement(SELECTNUMEROPS);
            rs = ps.executeQuery();

            while (rs.next()) {
                // de persona
                String nombre = rs.getString("nombre");
                
                long idNumero = rs.getLong("id_numero");

                int duracion = rs.getInt("duracion");
                
                long idCoordinador = rs.getLong("id_coordinador");
                Coordinador coordinadorEspectaculo = null;

                ArrayList<Persona> personas = PDAO.getPersonas();
                Set <Artista> artistas = new HashSet<>();
                for(Persona p : personas){
                    if (p instanceof Artista){
                        Artista artistaLocal = (Artista)p;
                        artistas.add(artistaLocal);
                        
                    }
                }


                Numero numeroLocal = new Numero(idNumero, nombre, duracion, artistas);
                numeros.add(numeroLocal);

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

        return numeros;
    }

}
