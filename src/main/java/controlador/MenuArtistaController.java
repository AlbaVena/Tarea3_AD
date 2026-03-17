package controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import servicios.IEspectaculosService;
import servicios.IUsuariosService;

@Controller
public class MenuArtistaController implements Initializable{

	
		@FXML private StackPane stkpaneInvitado; 
		@FXML private GridPane  fichaArtista;
		
	    @FXML private TableView<Espectaculo> tablaEspectaculos;
	    @FXML private TableColumn<Espectaculo, String> columnNombreE;
	    @FXML private TableColumn<Espectaculo, LocalDate> columnFechaIniE;
	    @FXML private TableColumn<Espectaculo, LocalDate> columnFechaFinE;
	   
	    
	    @FXML private Label lblnombreFicha;
	    @FXML private Label lblapodoFicha;
	    @FXML private Label lblespecialidadesFicha;
	    @FXML private Label lblnumerosFicha;
	    
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

		    // 3. Asignación de acciones a los botones
		    btnVerEspectaculos.setOnAction(event -> handleVerEspectaculos());
		    btnVerFicha.setOnAction(event -> handleVerFicha());
		    btnLogOut.setOnAction(event -> handleLogOut(event));
			
	}

		private void handleVerEspectaculos() {
			fichaArtista.setVisible(false);
			List<Espectaculo> lista = espectaculoService.getEspectaculos();
			tablaEspectaculos.getItems().setAll(lista);

			tablaEspectaculos.setVisible(true);
		}
		
		@FXML
	    private void handleVerFicha() {
	        tablaEspectaculos.setVisible(false);
	        
	        // Obtenemos el artista logueado (necesitarás un método en tu servicio que lo devuelva)
	        Persona p = usuariosService.getSesion().getUsuActual();
	        
	        if (p instanceof Artista artista) {
	            lblnombreFicha.setText(artista.getNombre());
	            lblapodoFicha.setText(artista.getApodo());
	            
	            // Convertimos las listas a texto bonito para los labels
	            String especs = artista.getEspecialidades().stream()
	                                   .map(Especialidad::getNombre)
	                                   .collect(Collectors.joining(", "));
	            lblespecialidadesFicha.setText(especs);
	            
	            String nums = artista.getNumeros().stream()
	                                 .map(Numero::getNombre)
	                                 .collect(Collectors.joining(", "));
	            lblnumerosFicha.setText(nums);
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
	

}
