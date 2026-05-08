package controlador;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import entidades.Espectaculo;
import entidades.Numero;
import entidades.objectdb.Incidencia;
import entidades.objectdb.TipoIncidencia;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import servicios.IEspectaculosService;
import servicios.IIncidenciasService;
import servicios.IUsuariosService;

@Controller
public class MenuIncidenciasController implements Initializable {
	
	
	
    // --- paneles ---
    @FXML private VBox panelPrincipalIncidencias;
    @FXML private VBox panelRegistrarIncidencia;
    @FXML private VBox panelConsultarIncidencias;
    @FXML private VBox panelResolverIncidencia;

    // --- panel principal ---
    @FXML private Label lblBienvenidaIncidencias;
    @FXML private Label lblNotaRolIncidencias;

    // --- botones laterales ---
    @FXML private Button btnRegistrar;
    @FXML private Button btnConsultar;
    @FXML private Button btnResolver;
    @FXML private Button btnAtras;
    @FXML private Button btnCerrarSesion;

    // --- panel registrar ---
    @FXML private RadioButton rbTecnica;
    @FXML private RadioButton rbArtistica;
    @FXML private RadioButton rbOrganizativa;
    @FXML private ToggleGroup tipoIncidencia;
    @FXML private ComboBox<Espectaculo> cbEspectaculoIncidencia;
    @FXML private ComboBox<Numero> cbNumeroIncidencia;
    @FXML private TextArea txtDescripcionIncidencia;
    @FXML private Button btnLimpiarIncidencia;
    @FXML private Button btnRegistrarIncidencia;

    // --- panel consultar ---
    @FXML private CheckBox chbFiltroTecnica;
    @FXML private CheckBox chbFiltroArtistica;
    @FXML private CheckBox chbFiltroOrganizativa;
    @FXML private ComboBox<String> cbFiltroEstado;
    @FXML private ComboBox<Espectaculo> cbFiltroEspectaculo;
    @FXML private ComboBox<Numero> cbFiltroNumero;
    @FXML private DatePicker dpFiltroDesde;
    @FXML private DatePicker dpFiltroHasta;
    @FXML private Button btnFiltroLimpiar;
    @FXML private Button btnFiltroBuscar;
    @FXML private TableView<Incidencia> tablaIncidencias;
    @FXML private TableColumn<Incidencia, Long> colIdIncidencia;
    @FXML private TableColumn<Incidencia, LocalDateTime> colFechaIncidencia;
    @FXML private TableColumn<Incidencia, TipoIncidencia> colTipoIncidencia;
    @FXML private TableColumn<Incidencia, String> colEstadoIncidencia;
    @FXML private TableColumn<Incidencia, String> colDescripcionIncidencia;

    // --- panel resolver ---
    @FXML private TableView<Incidencia> tablaIncidenciasNoResueltas;
    @FXML private TableColumn<Incidencia, Long> colIdResolver;
    @FXML private TableColumn<Incidencia, LocalDateTime> colFechaResolver;
    @FXML private TableColumn<Incidencia, TipoIncidencia> colTipoResolver;
    @FXML private TableColumn<Incidencia, String> colDescripcionResolver;
    @FXML private TextArea txtAccionesResolver;
    @FXML private Button btnConfirmarResolucion;
    @FXML private Button btnCancelarResolucion;

    // --- servicios ---
    @Autowired private IIncidenciasService incidenciasService;
    @Autowired private IEspectaculosService espectaculosService;
    @Autowired private IUsuariosService usuariosService;
    @Autowired private ConfigurableApplicationContext context;

    // variable para recordar de donde venimos al resolver
    private boolean llegadoDesdeConsultar = false;

    // incidencia seleccionada para resolver
    private Incidencia incidenciaAResolver = null;

    // pantalla de origen para el boton atras
    private String pantallaOrigen;

	
	
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
	private void configurarTablas() {
		colIdIncidencia.setCellValueFactory(new PropertyValueFactory<>("id"));
		colFechaIncidencia.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));
        colTipoIncidencia.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colDescripcionIncidencia.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colEstadoIncidencia.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().isResuelta() ? "Resuelta" : "No resuelta"));

        tablaIncidencias.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_LAST_COLUMN);

        // tabla resolver
        colIdResolver.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFechaResolver.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));
        colTipoResolver.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colDescripcionResolver.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        tablaIncidenciasNoResueltas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_LAST_COLUMN);
        
        //doble click para ir a resolver
        tablaIncidencias.setOnMouseClicked(event ->{
        	if (event.getClickCount() == 2) {
        		Incidencia seleccionada = tablaIncidencias.getSelectionModel().getSelectedItem();
        		if (seleccionada != null && !seleccionada.isResuelta()) {
        			llegadoDesdeConsultar = true;
        			incidenciaAResolver = seleccionada;
        			mostrarPanelResolver();
        		}
        	}
        });
        
        //doble click desde resolver para cargar incidencia
        tablaIncidenciasNoResueltas.setOnMouseClicked(event ->{
        	if(event.getClickCount() == 2) {
        		incidenciaAResolver = tablaIncidenciasNoResueltas.getSelectionModel().getSelectedItem();
        	}
        });
        

	}
	
	private void configurarCombos() {
		//filtro estado
		cbFiltroEstado.setItems(FXCollections.observableArrayList("Todas", "Resueltas", "No resueltas"));
		
		//conversor de espectaculos
		javafx.util.StringConverter<Espectaculo> convEsp = new javafx.util.StringConverter<>() {
			@Override public String toString(Espectaculo e) {
				return e == null? "" : e.getNombre();
			}

			@Override
			public Espectaculo fromString(String string) {
				return null;
			}
		};
		
		//conversor de numeros
		javafx.util.StringConverter<Numero> convNum = new javafx.util.StringConverter<>() {
            @Override public String toString(Numero n) {
                return n == null ? "" : n.getNombre();
            }
            @Override public Numero fromString(String s) { return null; }
        };
        cbNumeroIncidencia.setConverter(convNum);
        cbFiltroNumero.setConverter(convNum);
        
        //listener espectaculo registrar. carga sus numeros
        cbEspectaculoIncidencia.setOnAction(e ->{
        	Espectaculo esp = cbEspectaculoIncidencia.getValue();
        	if (esp != null) {
        		cbNumeroIncidencia.setItems(FXCollections.observableArrayList(esp.getNumeros()));
        	} else {
        		cbNumeroIncidencia.getItems().clear();
        	}
        });
        
        //listener espectaculo filtro. cargar sus numeros
        cbFiltroEspectaculo.setOnAction(e -> {
            Espectaculo esp = cbFiltroEspectaculo.getValue();
            if (esp != null) {
                cbFiltroNumero.setItems(
                    FXCollections.observableArrayList(esp.getNumeros()));
            } else {
                cbFiltroNumero.getItems().clear();
            }
        });
		
	}
	
	private void ocultarTodo() {
		panelPrincipalIncidencias.setVisible(false);
		panelRegistrarIncidencia.setVisible(false);
		panelConsultarIncidencias.setVisible(false);
		panelResolverIncidencia.setVisible(false);
	}
	
	@FXML
	private void handleRegistrar() {
		//primero recargo los espectaculos por si hay nuevos
		cbEspectaculoIncidencia.setItems(FXCollections.observableArrayList(espectaculosService.getEspectaculos()));
		
		ocultarTodo();
		panelRegistrarIncidencia.setVisible(true);
	}
	
	@FXML
	private void handleConsultar() {
		
		// recargar espectaculos
        cbFiltroEspectaculo.setItems(
            FXCollections.observableArrayList(espectaculosService.getEspectaculos()));
        // cargar todas las incidencias sin filtro
        tablaIncidencias.setItems(
            FXCollections.observableArrayList(
                incidenciasService.consultarIncidencias(null, null, null, null, null, null)));
        ocultarTodo();
        panelConsultarIncidencias.setVisible(true);
		
	}
	
	@FXML
	private void handleResolver() {
		llegadoDesdeConsultar = false;
		
		mostrarPanelResolver();
	}
	
	
	private void mostrarPanelResolver() {
		//incidencias no resueltas
		List<Incidencia> noResueltas = incidenciasService.consultarIncidencias(null, false, null, null, null, null);
		
		tablaIncidenciasNoResueltas.setItems(FXCollections.observableArrayList(noResueltas));
		ocultarTodo();
		panelResolverIncidencia.setVisible(true);
	}
	
	@FXML
	private void registrarIncidencia() {
		if(tipoIncidencia.getSelectedToggle() == null) {
			rbTecnica.setStyle("-fx-border-color: red;");
			rbArtistica.setStyle("-fx-border-color: red;");
			rbOrganizativa.setStyle("-fx-border-color: red;");
			return;
		}
		
		if(txtDescripcionIncidencia.getText().isBlank()) {
			txtDescripcionIncidencia.setStyle("-fx-border-color: red;");
			return;
		}
		
		//ahora se construye la incidencia
		Incidencia nueva = new Incidencia();
		
		nueva.setIdPersonaReporta(usuariosService.getSesion().getUsuActual().getId());
		
		RadioButton seleccionado = (RadioButton) tipoIncidencia.getSelectedToggle();//TODO duda
		
		if(seleccionado == rbTecnica) {
			nueva.setTipo(TipoIncidencia.TECNICA);
		} else if (seleccionado == rbArtistica) {
			nueva.setTipo(TipoIncidencia.ARTISTICA);
		} else if (seleccionado == rbOrganizativa) {
			nueva.setTipo(TipoIncidencia.ORGANIZATIVA);
		}
		
		nueva.setDescripcion(txtDescripcionIncidencia.getText());
		
		//opcionales
		if (cbEspectaculoIncidencia.getValue() != null) {
			nueva.setIdEspectaculo(cbEspectaculoIncidencia.getValue().getId());
		}
		if (cbNumeroIncidencia.getValue() != null) {
			nueva.setIdNumero(cbNumeroIncidencia.getValue().getId());
		}
		
		incidenciasService.registrarIncidencia(nueva);
		
		limpiarIncidencia();
		ocultarTodo();
		panelPrincipalIncidencias.setVisible(true);
		
	}
	
	@FXML
	private void limpiarIncidencia() {
		tipoIncidencia.selectToggle(null);
        rbTecnica.setStyle("");
        rbArtistica.setStyle("");
        rbOrganizativa.setStyle("");
        txtDescripcionIncidencia.clear();
        txtDescripcionIncidencia.setStyle("");
        cbEspectaculoIncidencia.setValue(null);
        cbNumeroIncidencia.getItems().clear();
	}
	
	
	
	
	
	
	
	
	

}
