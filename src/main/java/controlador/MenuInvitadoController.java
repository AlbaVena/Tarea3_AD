package controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import entidades.Espectaculo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import servicios.IEspectaculosService;
import utils.Validador;

@Controller
public class MenuInvitadoController implements Initializable {

	@FXML
	private TableView<Espectaculo> tablaEspectaculos;
	@FXML
	private TableColumn<Espectaculo, String> columnNombreE;
	@FXML
	private TableColumn<Espectaculo, LocalDate> columnFechaIniE;
	@FXML
	private TableColumn<Espectaculo, LocalDate> columnFechaFinE;
	@FXML
	private Button btnVerEspectaculos;
	
	@FXML
	private VBox panelEspectaculos;
	
	@FXML
	private TextArea txtAreaDetalleEspectaculo;

	@Autowired
	private IEspectaculosService espectaculoService;

	@Autowired
	private ConfigurableApplicationContext context;

	@FXML
	private Button btnLogIn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		configurarTablaEspectaculos();
		
		tablaEspectaculos.setOnMouseClicked(event -> {
		    if (event.getClickCount() == 2) {
		        Espectaculo seleccionado = tablaEspectaculos.getSelectionModel().getSelectedItem();
		        if (seleccionado != null) {
		            mostrarDetalleBasico(seleccionado);
		        }
		    }
		});
	}
	
	private void configurarTablaEspectaculos() {
	    columnNombreE.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	    columnFechaIniE.setCellValueFactory(new PropertyValueFactory<>("fechaini"));
	    columnFechaIniE.setCellFactory(col -> new TableCell<Espectaculo, LocalDate>() {
	        @Override
	        protected void updateItem(LocalDate item, boolean empty) {
	            super.updateItem(item, empty);
	            setText(empty || item == null ? "" : Validador.formatearFecha(item));
	        }
	    });
	    
	    columnFechaFinE.setCellValueFactory(new PropertyValueFactory<>("fechafin"));
	    columnFechaFinE.setCellFactory(col -> new TableCell<Espectaculo, LocalDate>() {
	        @Override
	        protected void updateItem(LocalDate item, boolean empty) {
	            super.updateItem(item, empty);
	            setText(empty || item == null ? "" : Validador.formatearFecha(item));
	        }
	    });
	    tablaEspectaculos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_LAST_COLUMN);
	    tablaEspectaculos.setPlaceholder(new Label("No hay espectáculos disponibles."));
	}

	@FXML
	private void handleVerEspectaculos() {
		// Cargamos los datos de la BD
		List<Espectaculo> lista = espectaculoService.getEspectaculos();
		tablaEspectaculos.getItems().setAll(lista);

		panelEspectaculos.setVisible(true);
	}

	// Método para cambiar la escena completa
	@FXML
	private void handleAbrirLogin(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Login.fxml"));

			// Esto es CLAVE: permite que Spring inyecte dependencias en el LoginController
			loader.setControllerFactory(context::getBean);

			Parent root = loader.load();

			// Obtenemos el Stage actual a través del botón que lanzó el evento
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private void mostrarDetalleBasico(Espectaculo e) {
	    String detalle = "Id: " + e.getId() + "\n" +
	                     "Nombre: " + e.getNombre() + "\n" +
	                     "Inicio: " + e.getFechaini() + "\n" +
	                     "Fin: " + e.getFechafin();
	    txtAreaDetalleEspectaculo.setText(detalle);
	}
}
