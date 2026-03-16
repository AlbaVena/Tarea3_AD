package controlador;


import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import entidades.Espectaculo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import servicios.IEspectaculosService;

@Controller
public class MenuInvitadoController implements Initializable{

	@FXML private TableView<Espectaculo> tablaEspectaculos;
    @FXML private TableColumn<Espectaculo, String> columnNombreE;
    @FXML private TableColumn<Espectaculo, LocalDate> columnFechaIniE;
    @FXML private TableColumn<Espectaculo, LocalDate> columnFechaFinE;
    @FXML private Button btnVerEspectaculos;
	
    @Autowired
    private IEspectaculosService espectaculoService;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		columnNombreE.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnFechaIniE.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        columnFechaFinE.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
		
        
        btnVerEspectaculos.setOnAction(event -> handleVerEspectaculos());
	}
	
	private void handleVerEspectaculos() {
        // Cargamos los datos de la BD
        List<Espectaculo> lista = espectaculoService.getEspectaculos();
        tablaEspectaculos.getItems().setAll(lista);
        
        // La hacemos visible (tu truco de magia)
        tablaEspectaculos.setVisible(true);
    }

}
