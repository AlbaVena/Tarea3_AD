package controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import entidades.Artista;
import entidades.Especialidad;
import entidades.Espectaculo;
import entidades.Numero;
import entidades.Persona;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import servicios.IEspectaculosService;
import servicios.IUsuariosService;

@Controller
public class MenuArtistaController implements Initializable{

	
		@FXML private StackPane stkpaneInvitado; 
		@FXML private GridPane  fichaArtista;
		
		//eswto siempre, para Ver Espectaculos
		
	    @FXML private TableView<Espectaculo> tablaEspectaculos;
	    @FXML private TableColumn<Espectaculo, String> columnNombreE;
	    @FXML private TableColumn<Espectaculo, LocalDate> columnFechaIniE;
	    @FXML private TableColumn<Espectaculo, LocalDate> columnFechaFinE;
	    
	    @FXML private VBox panelEspectaculos;
	    @FXML private TextArea txtAreaDetalleEspectaculo;
	   
	    
	    @FXML private Label lblnombreFicha;
	    @FXML private Label lblapodoFicha;
	    @FXML private Label lblespecialidadesFicha;
	    @FXML private Label lblnumerosFicha;
	    @FXML private Label lblEmailFicha;
	    @FXML private Label lblNacionalidadFicha;
	    
	    @FXML private TextArea txtAreaNumeros;
	    
	    @FXML private Button btnVerEspectaculos;
	    @FXML private Button btnVerFicha;
	    @FXML private Button btnLogOut;

	    @Autowired private IEspectaculosService espectaculoService;
	    @Autowired private IUsuariosService usuariosService;
	    
	    @Autowired private ConfigurableApplicationContext context;
		
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			//configuracion de la tabla como en Invitadp
			columnNombreE.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		    columnFechaIniE.setCellValueFactory(new PropertyValueFactory<>("fechaini"));
		    columnFechaFinE.setCellValueFactory(new PropertyValueFactory<>("fechafin"));

		    tablaEspectaculos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_LAST_COLUMN);
		    tablaEspectaculos.setPlaceholder(new Label("No hay espectáculos disponibles en este momento."));

		    //Asignación de acciones a los botones
		    btnVerEspectaculos.setOnAction(event -> handleVerEspectaculos());
		    btnVerFicha.setOnAction(event -> handleVerFicha());
		    btnLogOut.setOnAction(event -> handleLogOut(event));
		    dobleClickEsp();
			
	}

		private void handleVerEspectaculos() {
			fichaArtista.setVisible(false);
			List<Espectaculo> lista = espectaculoService.getEspectaculos();
			tablaEspectaculos.getItems().setAll(lista);

			panelEspectaculos.setVisible(true);
		}
		
		@FXML
	    private void handleVerFicha() {
	        tablaEspectaculos.setVisible(false);

	        Persona p = usuariosService.getSesion().getUsuActual();

	        if (p instanceof Artista) {
	            Artista artista = (Artista) p;

	            // datos personales
	            lblnombreFicha.setText(artista.getNombre());
	            lblEmailFicha.setText(artista.getEmail());
	            lblNacionalidadFicha.setText(artista.getNacionalidad());

	            // apodo
	            if (artista.getApodo() != null && !artista.getApodo().isBlank()) {
	                lblapodoFicha.setText(artista.getApodo());
	            } else {
	                lblapodoFicha.setText("Sin apodo");
	            }

	            // especialidades con bucle
	            String especs = "";
	            for (Especialidad e : artista.getEspecialidades()) {
	                if (!especs.isEmpty()) especs += ", ";
	                especs += e.getNombre();
	            }
	            lblespecialidadesFicha.setText(especs.isEmpty() ? "Sin especialidades" : especs);

	            // trayectoria: id y nombre del numero, id y nombre del espectaculo
	            String nums = "";
	            for (Numero n : artista.getNumeros()) {
	                if (!nums.isEmpty()) nums += "\n";
	                nums += "- Número con id: " + n.getId() + " '" + n.getNombre() + "'" +
	                        " — del espectáculo con id: " + n.getEspectaculo().getId() +
	                        " '" + n.getEspectaculo().getNombre() + "'";
	            }
	            txtAreaNumeros.setText(nums.isEmpty() ? "No participa en ningún número aún." : nums);
	        }

	        fichaArtista.setVisible(true);
	    }
		
		@FXML
		private void handleLogOut(ActionEvent event) {
			try {
		        //limpiamos la sesion
		        usuariosService.getSesion().setUsuActual(null);

		        // cargamos de nuevo el FXML de invitado
		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/MenuInvitado.fxml"));
		        loader.setControllerFactory(context::getBean); // Para que Spring gestione el controlador
		        
		        Parent root = loader.load();
		        
		        // cambio ala escena de invitado
		        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		        stage.setScene(new Scene(root));
		        stage.show();
		        
		    } catch (IOException e) {
		        System.err.println("Error al cerrar sesión: " + e.getMessage());
		    }
		}
		
		private void dobleClickEsp() {
			tablaEspectaculos.setOnMouseClicked(event -> {
			    if (event.getClickCount() == 2) {
			        Espectaculo seleccionado = tablaEspectaculos.getSelectionModel().getSelectedItem();
			        if (seleccionado != null) {
			            mostrarDetalleCompleto(seleccionado);
			        }
			    }
			});
		}
		
		@FXML
		private void mostrarDetalleCompleto(Espectaculo e) {
		    String detalle = "*** ESPECTÁCULO ***\n" +
		                     "Id: " + e.getId() + "\n" +
		                     "Nombre: " + e.getNombre() + "\n" +
		                     "Inicio: " + e.getFechaini() + "\n" +
		                     "Fin: " + e.getFechafin() + "\n\n";

		    if (e.getEncargadoCoor() != null) {
		        detalle += "*** COORDINADOR ***\n" +
		                   "Nombre: " + e.getEncargadoCoor().getNombre() + "\n" +
		                   "Email: " + e.getEncargadoCoor().getEmail() + "\n" +
		                   "Senior: " + (e.getEncargadoCoor().isSenior() ? "Sí" : "No") + "\n\n";
		    }

		    detalle += "*** NÚMEROS Y ARTISTAS ***\n";
		    for (Numero n : e.getNumeros()) {
		        detalle += "- Id:" + n.getId() + " '" + n.getNombre() +
		                   "' | Duración: " + n.getDuracion() + " min\n";
		        detalle += "  Artistas:\n";
		        for (Artista a : n.getArtistas()) {
		            String especialidades = "";
		            for (Especialidad esp : a.getEspecialidades()) {
		                if (!especialidades.isEmpty()) especialidades += ", ";
		                especialidades += esp.getNombre();
		            }
		            detalle += "    · " + a.getNombre() +
		                       " (" + a.getNacionalidad() + ")" +
		                       " | Especialidades: " + especialidades;
		            if (a.getApodo() != null && !a.getApodo().isBlank()) {
		                detalle += " | Apodo: " + a.getApodo();
		            }
		            detalle += "\n";
		        }
		    }
		    txtAreaDetalleEspectaculo.setText(detalle);
		}
	

}
