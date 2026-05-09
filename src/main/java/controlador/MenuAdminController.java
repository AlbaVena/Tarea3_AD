package controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import entidades.Artista;
import entidades.Coordinador;
import entidades.Credenciales;
import entidades.Especialidad;
import entidades.Espectaculo;
import entidades.LogOperacion;
import entidades.Numero;
import entidades.Perfil;
import entidades.Persona;
import entidades.TipoOperacion;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;
import servicios.IEspectaculosService;
import servicios.ILogService;
import servicios.IUsuariosService;
import servicios.implementacion.PaisesService;

import utils.Validador;

@Slf4j
@Controller
public class MenuAdminController implements Initializable {

	@FXML
	private StackPane stkpaneCoor;

	// ver espectaculos
	@FXML
	private TableView<Espectaculo> tablaEspectaculos;
	@FXML
	private TableColumn<Espectaculo, String> columnNombreE;
	@FXML
	private TableColumn<Espectaculo, LocalDate> columnFechaIniE;
	@FXML
	private TableColumn<Espectaculo, LocalDate> columnFechaFinE;

	// botones
	@FXML
	private Button btnVerEspectaculos;
	@FXML
	private Button btnLogOut;
	@FXML
	private Button btnCargarE;
	@FXML
	private Button btnFinalizarTodo;
	@FXML
	private Button btnRegistrarNumero;
	@FXML
	private Button btnAgregar;
	@FXML
	private Button btnEliminarA;
	@FXML
	private Button btnEliminarE;
	@FXML
	private Button btnEliminar;
	@FXML
	private Button btnRegistrarP;
	@FXML
	private Button btnmodificarP;
	@FXML
	private Button btnEliminarPLateral;
	
	@FXML
	private Button btnIncidencias;

	// paneles de vistas
	@FXML
	private GridPane panelFormularioDatos;
	@FXML
	private VBox panelGestionNumeros;
	@FXML
	private ScrollPane panelResumen;
	@FXML
	private VBox panelBuscadorE;
	@FXML
	private GridPane gridDatosPersona;
	@FXML
	private GridPane gridArtista;
	@FXML
	private GridPane gridCoordinador;
	@FXML
	private ScrollPane panelResumenP;
	@FXML
	private VBox panelHistorial;
	
	// form datos
	@FXML
	private TextField tfNombre;
	@FXML
	private DatePicker dpFechaIni;
	@FXML
	private DatePicker dpFechafin;
	@FXML
	private Button btnAccion;

	// numeros
	@FXML
	private TextField tfnombreN;
	@FXML
	private Spinner<Double> spnduracionN;
	@FXML
	private ComboBox<Artista> cbartistasN;
	@FXML
	private ListView<Numero> lvNumCreados;
	@FXML
	private ListView<Artista> lvArtistasSeleccionados;
	
	@FXML
	private ComboBox<Coordinador> cbCoordinadores;

	// buscador para modificar
	@FXML
	private ComboBox<Espectaculo> cbSelectorE;
	@FXML
	private Label lblTituloBuscadorE;

	// buscador personas
	@FXML
	private VBox panelBuscadorP;
	@FXML
	private ComboBox<Persona> cbSelectorP;
	@FXML
	private Button btnCargarP;
	@FXML
	private Button btnEliminarP;
	@FXML
	private Label lblPanelBuscadorP;

	// resumen
	@FXML
	private Label lblResumenNombre, lblResumenFechas, lblResumenCoordinadr;
	@FXML
	private ListView<String> lvResumenNumeros;
	@FXML
	private VBox vbDatosEspefcificos;

	// tabla artistas
	@FXML
	private TableView<Artista> tablaArtistas;
	@FXML
	private TableColumn<Artista, String> columnNombreA;
	@FXML
	private TableColumn<Artista, String> columnApodoA;
	@FXML
	private TableColumn<Artista, String> columnEspecA;

	// panel datos comunes persona
	@FXML
	private RadioButton rbArtista;
	@FXML
	private RadioButton rbCoor;
	@FXML
	private ToggleGroup tipoPersona;
	@FXML
	private TextField txtNombre;
	@FXML
	private TextField txtEmail;
	@FXML
	private ComboBox<String> cbNacionalidad;
	@FXML
	private TextField txtNombreU;
	@FXML
	private TextField txtPass;
	@FXML
	private Button btnCancelarP;
	@FXML
	private Button btnSiguienteP;

	// panel datos artista
	@FXML
	private TextField txtApodo;
	@FXML
	private ComboBox<Especialidad> cbArtistaEsp;
	@FXML
	private ListView<Especialidad> lvArtistaEsp;
	@FXML
	private Button btnAgregarEsp;
	@FXML
	private Button btnEliminarEsp;
	@FXML
	private Button btnAtrasA;
	@FXML
	private Button btnFinalizarA;

	// panel datos coordinador
	@FXML
	private CheckBox chbCoor;
	@FXML
	private DatePicker dateCoor;
	@FXML
	private Button btnAtrasC;
	@FXML
	private Button btnFinalizarC;
	@FXML
	private Label lblTituloSenior;

	// resumen persona
	@FXML
	private Label lblResumenNombreP;
	@FXML
	private Label lblResumenEmailP;
	@FXML
	private Label lblResumenNacionP;
	@FXML
	private Label lblResumenUsuarioP;
	@FXML
	private Label lblResumenPuestoP;
	@FXML
	private VBox vBResumenArtista;
	@FXML
	private VBox vbDatosCoor;
	@FXML
	private Label lblResumenApodoA;
	@FXML
	private Label lblResumenEspA;
	@FXML
	private Label lblResumenCoor;
	
	//panel Historial operaciones
	@FXML
	private Button btnHistorialOperaciones;	
	@FXML 
	private TextField tfFiltroUsuario;	
	@FXML
	private CheckBox chbFiltroNuevo;
	@FXML 
	private CheckBox chbFiltroActualizacion;
	@FXML 
	private CheckBox chbFiltroBorrado;
	@FXML 
	private DatePicker dpFiltroDesde;	
	@FXML 
	private DatePicker dpFiltroHasta;
	@FXML 
	private Button btnBuscarLog;
	@FXML 
	private TableView<LogOperacion> tablaHistorial;
	@FXML 
	private TableColumn<LogOperacion, String> colFechaLog;
	@FXML 
	private TableColumn<LogOperacion, String> colUsuarioLog;
	@FXML 
	private TableColumn<LogOperacion, String> colTipoLog;
	@FXML 
	private TableColumn<LogOperacion, String> colResumenLog;

	@Autowired
	private IEspectaculosService espectaculoService;
	@Autowired
	private IUsuariosService usuariosService;
	@Autowired
	private ConfigurableApplicationContext context;
	@Autowired
	private PaisesService paisesService;
    @Autowired 
    private ILogService logService;

	// temporales
	private Espectaculo espectaculoEnEdicion;
	private Persona personaEnEdicion; // null = crear, no null = modificar
	private ObservableList<Artista> artistasDelNumeroActual = FXCollections
			.observableArrayList();
	
	

	/**
	 * METODOS AUXILIARES DE VALIDACION
	 */

	private boolean validarCampo(TextField campo, String regex) {
		if (!Validador.esCadenaValida(campo.getText(), regex)) {
			campo.setStyle(
					"-fx-border-color: red; -fx-background-color: #D6EAF8;");
			return true;
		}
		campo.setStyle("-fx-background-color: #D6EAF8;");
		return false;
	}
	
	private boolean validarCombo(ComboBox<?> combo) {
		if (combo.getValue() == null) {
			combo.setStyle(
					"-fx-border-color: red; -fx-background-color: #D6EAF8;");
			return true;
		}
		combo.setStyle("-fx-background-color: #D6EAF8;");
		return false;
	}

	private boolean validarFecha(DatePicker fecha) {
		if (fecha.getValue() == null) {
			fecha.setStyle(
					"-fx-border-color: red; -fx-background-color: #D6EAF8;");
			return true;
		}
		fecha.setStyle("-fx-background-color: #D6EAF8;");
		return false;
	}

	private boolean validarLista(ListView<?> lista, int min, int max) {
		if (lista.getItems().size() < min || lista.getItems().size() > max) {
			lista.setStyle("-fx-border-color: red;");
			return true;
		}
		lista.setStyle(null);
		return false;
	}

	/**
	 * MÉTODOS:
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    log.info("Sesion iniciada como Admin");
	    configurarTablaEspectaculos();
	    configurarTablaArtistas();
	    configurarTablaHistorial();
	    configurarSpinner();
	    configurarConversores();
	    configurarCombos();
	    configurarBindings();
	    configurarFecha();
	    ocultarTodo();
	}
	
	
	private void configurarTablaEspectaculos() {
	    columnNombreE.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	    columnFechaIniE.setCellValueFactory(new PropertyValueFactory<>("fechaini"));
	    columnFechaFinE.setCellValueFactory(new PropertyValueFactory<>("fechafin"));
	    tablaEspectaculos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_LAST_COLUMN);
	    tablaEspectaculos.setPlaceholder(new Label("No hay espectáculos disponibles."));
	}

	private void configurarTablaArtistas() {
	    columnNombreA.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	    columnApodoA.setCellValueFactory(new PropertyValueFactory<>("apodo"));
	    columnEspecA.setCellValueFactory(cellData -> {
	        Artista artista = cellData.getValue();
	        String especialidades = "";
	        if (artista.getEspecialidades() != null) {
	            for (Especialidad e : artista.getEspecialidades()) {
	                if (!especialidades.isEmpty()) especialidades += ", ";
	                especialidades += e.getNombre();
	            }
	        }
	        return new javafx.beans.property.SimpleStringProperty(especialidades);
	    });
	    tablaArtistas.setPlaceholder(new Label("No hay artistas registrados."));
	    tablaArtistas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_LAST_COLUMN);
	}

	private void configurarTablaHistorial() {
	    colFechaLog.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));
	    colUsuarioLog.setCellValueFactory(new PropertyValueFactory<>("usuario"));
	    colTipoLog.setCellValueFactory(new PropertyValueFactory<>("tipoOperacion"));
	    colResumenLog.setCellValueFactory(new PropertyValueFactory<>("resumen"));
	    tablaHistorial.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_LAST_COLUMN);
	}

	private void configurarSpinner() {
	    SpinnerValueFactory<Double> valueFactory = 
	        new SpinnerValueFactory.DoubleSpinnerValueFactory(5.0, 15.5, 5.0, 0.5);
	    spnduracionN.setValueFactory(valueFactory);
	}

	private void configurarConversores() {
	    cbSelectorE.setConverter(new StringConverter<Espectaculo>() {
	        @Override public String toString(Espectaculo esp) {
	            return (esp == null) ? "" : esp.getNombre();
	        }
	        @Override public Espectaculo fromString(String string) { return null; }
	    });
	    cbCoordinadores.setConverter(new StringConverter<Coordinador>() {
	        @Override public String toString(Coordinador c) {
	            return (c == null) ? "" : c.getNombre();
	        }
	        @Override public Coordinador fromString(String string) {
	            for (Coordinador c : cbCoordinadores.getItems()) {
	                if (c.getNombre().equals(string)) return c;
	            }
	            return null;
	        }
	    });
	    cbSelectorP.setConverter(new StringConverter<Persona>() {
	        @Override public String toString(Persona p) {
	            return (p == null) ? "" : p.getNombre() + " (" + p.getPerfil() + ")";
	        }
	        @Override public Persona fromString(String string) { return null; }
	    });
	    cbArtistaEsp.setConverter(new StringConverter<Especialidad>() {
	        @Override public String toString(Especialidad e) {
	            return (e == null) ? "" : e.getNombre();
	        }
	        @Override public Especialidad fromString(String string) { return null; }
	    });
	    conversorArtistasCombo();
	    conversorArtistasSeleccionados();
	    conversorEspecialidadArtista();
	    conversorNumeros();
	}

	private void configurarCombos() {
	    // paises ordenados
	    List<String> paises = new ArrayList<>(paisesService.getPaises().values());
	    Collections.sort(paises);
	    cbNacionalidad.setItems(FXCollections.observableArrayList(paises));
	    // especialidades
	    cbArtistaEsp.setItems(FXCollections.observableArrayList(usuariosService.getEspecialidades()));
	    // artistas y lista temporal
	    cbartistasN.setItems(FXCollections.observableArrayList(usuariosService.getArtistas()));
	    lvArtistasSeleccionados.setItems(artistasDelNumeroActual);
	    // coordinadores
	    cbCoordinadores.setItems(FXCollections.observableArrayList(usuariosService.getCoordinadores()));
	}

	private void configurarBindings() {
	    btnAccion.disableProperty().bind(
	        tfNombre.textProperty().isEmpty()
	        .or(dpFechaIni.valueProperty().isNull())
	        .or(dpFechafin.valueProperty().isNull())
	        .or(cbCoordinadores.valueProperty().isNull())
	    );
	    btnCargarE.disableProperty().bind(cbSelectorE.valueProperty().isNull());
	    btnEliminar.disableProperty().bind(cbSelectorE.valueProperty().isNull());
	    btnFinalizarTodo.disableProperty().bind(Bindings.size(lvNumCreados.getItems()).lessThan(3));
	    btnCargarP.disableProperty().bind(cbSelectorP.valueProperty().isNull());
	    btnEliminarP.disableProperty().bind(cbSelectorP.valueProperty().isNull());
	    btnFinalizarA.disableProperty().bind(Bindings.size(lvArtistaEsp.getItems()).lessThan(1));
	    btnAgregarEsp.disableProperty().bind(Bindings.size(lvArtistaEsp.getItems()).greaterThanOrEqualTo(5));
	}

	private void configurarFecha() {
	    dateCoor.disableProperty().bind(chbCoor.selectedProperty().not());
	}

	/**
	 * para ocultar todos los paneles a la vez
	 */
	@FXML
	private void ocultarTodo() {
		log.info("ocultando todas las pantallas");
		// de espectaculos
		tablaEspectaculos.setVisible(false);
		panelFormularioDatos.setVisible(false);
		panelGestionNumeros.setVisible(false);
		panelResumen.setVisible(false);
		panelBuscadorE.setVisible(false);

		// de personas
		tablaArtistas.setVisible(false);
		panelBuscadorP.setVisible(false);
		gridDatosPersona.setVisible(false);
		gridArtista.setVisible(false);
		gridCoordinador.setVisible(false);
		panelResumenP.setVisible(false);
		
		//de historial
		panelHistorial.setVisible(false);

	}

	@FXML
	private void verArtistas() {

		ocultarTodo();
		log.info("mostrando lista de artistas del sistema");
		tablaArtistas.getItems().setAll(usuariosService.getArtistas());
		tablaArtistas.setVisible(true);
	}

	@FXML
	private void registrarPersona() {

		personaEnEdicion = null;

		ocultarTodo();
		log.info("mostrar panel de creacion de usuario");
		txtNombre.clear();
		txtEmail.clear();
		txtNombreU.clear();
		txtPass.clear();
		txtApodo.clear();
		cbNacionalidad.setValue(null);
		rbArtista.setSelected(false);
		rbCoor.setSelected(false);
		lvArtistaEsp.getItems().clear();
		dateCoor.setValue(null);
		chbCoor.setSelected(false);

		gridDatosPersona.setVisible(true);
	}

	@FXML
	private void handleEliminarPersona() {
		log.info("en DESARROLLO - eliminar usuario del sistema");
		// mostrar recuadro
		Alert alerta = new Alert(AlertType.INFORMATION);
		alerta.setTitle("En desarrollo");
		alerta.setHeaderText(null);// queda mejor sin cabecera
		alerta.setContentText("Esta función está actualmente en desarrollo.");
		alerta.showAndWait();
	}

	@FXML
	private void botonModificarP() {
		ocultarTodo();
		log.info("buscar usuario para modificar");
		cbSelectorP.getItems().setAll(usuariosService.getCredencialesSistema());
		btnCargarP.setVisible(true);
		btnEliminarP.setVisible(false);
		panelBuscadorP.setVisible(true);
	}

	@FXML
	private void botonEliminarP() {
		ocultarTodo();
		lblPanelBuscadorP.setText("Eliminar usuario");
		cbSelectorP.getItems().setAll(usuariosService.getCredencialesSistema());
		btnCargarP.setVisible(false);
		btnEliminarP.setVisible(true);
		panelBuscadorP.setVisible(true);

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("En desarrollo");
		alert.setHeaderText(null); // sin cabecera, queda más limpio
		alert.setContentText("Esta función está actualmente en desarrollo.");
		alert.showAndWait();
	}

	/**
	 * muestra datos para artista o coordinador dependiendo de lo que este
	 * marcado
	 */
	@FXML
	private void botonSiguientePersona() {
		boolean error = false;
		// ocultarTodo();
		System.out.println("validando nombre: " + txtNombre.getText());
		System.out.println("Resultado: " + Validador.esCadenaValida(
				txtNombre.getText(), Validador.nombreGeneralRegex));

		// limpiar estilos
		txtNombre.setStyle("-fx-background-color: #D6EAF8;");
		txtEmail.setStyle("-fx-background-color: #D6EAF8;");
		txtNombreU.setStyle("-fx-background-color: #D6EAF8;");
		txtPass.setStyle("-fx-background-color: #D6EAF8;");
		cbNacionalidad.setStyle("-fx-background-color: #D6EAF8;");
		rbArtista.setStyle(null);
		rbCoor.setStyle(null);

		// validaciones

		if (validarCampo(txtNombre, Validador.nombreGeneralRegex)) {
			error = true;
		}
		if (validarCampo(txtEmail, Validador.emailRegex)) {
			error = true;
		}
		if (validarCampo(txtNombreU, Validador.nombreUsuarioRegex)) {
			error = true;
		}
		if (validarCampo(txtPass, Validador.passwordRegex)) {
			error = true;
		}
		if (validarCombo(cbNacionalidad)) {
			error = true;
		}
		// comprobar que no use "admin" en ningun campo
		if (txtNombre.getText().equalsIgnoreCase("admin")) {
		    txtNombre.setStyle("-fx-border-color: red; -fx-background-color: #D6EAF8;");
		    error = true;
		} //admin
		if (txtNombreU.getText().equalsIgnoreCase("admin")) {
		    txtNombreU.setStyle("-fx-border-color: red; -fx-background-color: #D6EAF8;");
		    error = true;
		}

		if (!error && personaEnEdicion == null) {
			if (usuariosService.comprobarEmail(txtEmail.getText())) {
				txtEmail.setStyle(
						"-fx-border-color: red; -fx-background-color: #D6EAF8;");
				log.info("email ya registrado");
				error = true;
			}
			if (usuariosService.comprobarNombreUsuario(txtNombreU.getText())) {
				txtNombreU.setStyle(
						"-fx-border-color: red; -fx-background-color: #D6EAF8;");
				log.info("nombre de usuario ya registrado");
				error = true;
			}
		}

		if (!rbArtista.isSelected() && !rbCoor.isSelected()) {
			rbArtista.setStyle("-fx-border-color: red;");
			rbCoor.setStyle("-fx-border-color: red;");
			error = true;
		}

		if (error) {
			return;
		}

		ocultarTodo();

		if (rbArtista.isSelected()) {
			gridArtista.setVisible(true);
		} else if (rbCoor.isSelected()) {
			gridCoordinador.setVisible(true);
		}
	}

	@FXML
	private void botonAtrasDesdeArtista() {
		ocultarTodo();
		gridDatosPersona.setVisible(true);
	}

	@FXML
	private void botonAtrasDesdeCoordinador() {
		ocultarTodo();
		gridDatosPersona.setVisible(true);
	}

	@FXML
	private void finalizarArtista() {
		Artista nuevo = new Artista();

		if (personaEnEdicion == null) {
			// comunes
			nuevo.setNombre(txtNombre.getText());
			nuevo.setEmail(txtEmail.getText());
			nuevo.setNacionalidad(cbNacionalidad.getValue());
			nuevo.setPerfil(Perfil.ARTISTA);
			
			// especificos artista
			nuevo.setApodo(txtApodo.getText());
			nuevo.setEspecialidades(
					new HashSet<Especialidad>(lvArtistaEsp.getItems()));

			// credenciales
			Credenciales cred = new Credenciales(txtNombreU.getText().toLowerCase(),
					txtPass.getText(), Perfil.ARTISTA);
			nuevo.setCredenciales(cred);
			cred.setPersona(nuevo);
		} else {
			nuevo = (Artista) personaEnEdicion;
			nuevo.getCredenciales().setNombre(txtNombreU.getText());
			nuevo.getCredenciales().setPassword(txtPass.getText());
			//comunes
			nuevo.setNombre(txtNombre.getText());
		    nuevo.setEmail(txtEmail.getText());
		    nuevo.setNacionalidad(cbNacionalidad.getValue());
		    nuevo.getCredenciales().setNombre(txtNombreU.getText().toLowerCase());
		    nuevo.getCredenciales().setPassword(txtPass.getText());
		    nuevo.setApodo(txtApodo.getText());
		    nuevo.setEspecialidades(new HashSet<Especialidad>(lvArtistaEsp.getItems()));
			// especificos artista
			nuevo.setApodo(txtApodo.getText());
			nuevo.setEspecialidades(
					new HashSet<Especialidad>(lvArtistaEsp.getItems()));
		}

		

		// se guarda:
		usuariosService.crearPersona(nuevo);

		// mostrar en resumen

		lblResumenNombreP.setText(nuevo.getNombre());
		lblResumenEmailP.setText(nuevo.getEmail());
		lblResumenNacionP.setText(nuevo.getNacionalidad());
		lblResumenUsuarioP.setText(txtNombreU.getText());
		lblResumenPuestoP.setText("Artista");
		lblResumenApodoA.setText(nuevo.getApodo());

		String especialidades = "";
		for (Especialidad e : nuevo.getEspecialidades()) {
			if (!especialidades.isEmpty()) {
				especialidades += ", ";
			}
			especialidades += e.getNombre();
		}

		lblResumenEspA.setText(especialidades);

		// mostrar artista, ocultar coordinador
		vBResumenArtista.setVisible(true);
		vBResumenArtista.setManaged(true);
		vbDatosCoor.setVisible(false);
		vbDatosCoor.setManaged(false);

		ocultarTodo();
		panelResumenP.setVisible(true);
		
	}

	@FXML
private void finalizarCoordinador() {
    Coordinador nuevo;

    if (personaEnEdicion == null) {
        // modo crear
        nuevo = new Coordinador();
        nuevo.setPerfil(Perfil.COORDINACION);
        Credenciales cred = new Credenciales(txtNombreU.getText().toLowerCase(),
                txtPass.getText(), Perfil.COORDINACION);
        nuevo.setCredenciales(cred);
        cred.setPersona(nuevo);
    } else {
        // modo modificar
        nuevo = (Coordinador) personaEnEdicion;
        nuevo.getCredenciales().setNombre(txtNombreU.getText().toLowerCase());
        nuevo.getCredenciales().setPassword(txtPass.getText());
    }

    // datos comunes en ambos casos
    nuevo.setNombre(txtNombre.getText());
    nuevo.setEmail(txtEmail.getText());
    nuevo.setNacionalidad(cbNacionalidad.getValue());
    nuevo.setSenior(chbCoor.isSelected());
    if (chbCoor.isSelected()) {
        nuevo.setFechasenior(dateCoor.getValue());
    }

    usuariosService.crearPersona(nuevo);

    // mostrar resumen
    lblResumenNombreP.setText(nuevo.getNombre());
    lblResumenEmailP.setText(nuevo.getEmail());
    lblResumenNacionP.setText(nuevo.getNacionalidad());
    lblResumenUsuarioP.setText(txtNombreU.getText());
    lblResumenPuestoP.setText("Coordinador");

    if (nuevo.isSenior()) {
        lblTituloSenior.setVisible(true);
        lblResumenCoor.setText(nuevo.getFechasenior().toString());
    } else {
        lblTituloSenior.setVisible(false);
        lblResumenCoor.setText("No es Senior.");
    }

    vBResumenArtista.setVisible(false);
    vBResumenArtista.setManaged(false);
    vbDatosCoor.setVisible(true);
    vbDatosCoor.setManaged(true);

    ocultarTodo();
    panelResumenP.setVisible(true);
}

	@FXML
	private void handleVerEspectaculos() {
		ocultarTodo();
		List<Espectaculo> lista = espectaculoService.getEspectaculos();
		tablaEspectaculos.getItems().setAll(lista);

		tablaEspectaculos.setVisible(true);
	}

	@FXML
	private void handleBotonModificarLateral() {
		ocultarTodo();
		// combo con los espectáculos actuales
		cbSelectorE.getItems().setAll(espectaculoService.getEspectaculos());
		btnCargarE.setVisible(true);
		btnEliminar.setVisible(false);
		panelBuscadorE.setVisible(true);
	}

	@FXML
	private void handleBotonEliminarLateral() {
		ocultarTodo();
		lblTituloBuscadorE.setText("Eliminar Espectaculo");
		cbSelectorE.getItems().setAll(espectaculoService.getEspectaculos());
		btnCargarE.setVisible(false);
		btnEliminar.setVisible(true);
		panelBuscadorE.setVisible(true);

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("En desarrollo");
		alert.setHeaderText(null); // sin cabecera, queda más limpio
		alert.setContentText("Esta función está actualmente en desarrollo.");
		alert.showAndWait();

	}

	@FXML
	private void handleEliminarE() {
		Espectaculo seleccionado = cbSelectorE.getValue();
		if (seleccionado != null) {
			espectaculoService.eliminarEspectaculo(seleccionado.getId());
			cbSelectorE.getItems().remove(seleccionado);
			cbSelectorE.setValue(null);
		}
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("En desarrollo");
		alert.setHeaderText(null); // sin cabecera, queda más limpio
		alert.setContentText("Esta función está actualmente en desarrollo.");
		alert.showAndWait();
	}

	@FXML
	private void handleCrearE() {
		ocultarTodo();
	    espectaculoEnEdicion = new Espectaculo();
	    lvNumCreados.getItems().clear();
	    tfNombre.clear();
	    dpFechaIni.setValue(null);
	    dpFechafin.setValue(null);
	    cbCoordinadores.setValue(null);
	    btnAccion.setText("Guardar");
	 // recargar coordinadores por si se han añadido nuevos
	    cbCoordinadores.setItems(FXCollections.observableArrayList(usuariosService.getCoordinadores()));
	    panelFormularioDatos.setVisible(true);
	}

	@FXML
	private void handleCargarParaModificar() {
		ocultarTodo();
		espectaculoEnEdicion = cbSelectorE.getValue();

		if (espectaculoEnEdicion != null) {
			// Rellenamos el primer formulario
			tfNombre.setText(espectaculoEnEdicion.getNombre());
			dpFechaIni.setValue(espectaculoEnEdicion.getFechaini());
			dpFechafin.setValue(espectaculoEnEdicion.getFechafin());

			// SE COMENTA PORQUE NO SE USA

			// cargar los numeros que ya tiene en la listview
			// ObservableList<Numero> numerosCargados =
			// FXCollections.observableArrayList(espectaculoEnEdicion.getNumeros());

			lvNumCreados.getItems().clear();
			lvNumCreados.getItems().addAll(espectaculoEnEdicion.getNumeros());

			btnAccion.setText("Siguiente");
			ocultarTodo();
			// recargar coordinadores por si se han añadido nuevos
			cbCoordinadores.setItems(FXCollections.observableArrayList(usuariosService.getCoordinadores()));
			panelFormularioDatos.setVisible(true);
		}
	}

	@FXML
	private void agregarEspecialidad() {
		Especialidad seleccionada = cbArtistaEsp.getValue();
		boolean error = false;

		if (seleccionada == null) {
			cbArtistaEsp.setStyle(
					"-fx-border-color: red; -fx-background-color: #D6EAF8;");
			error = true;
		} else if (lvArtistaEsp.getItems().contains(seleccionada)) {
			cbArtistaEsp.setStyle(
					"-fx-border-color: red; -fx-background-color: #D6EAF8;");
			error = true;
		}

		if (error) {
			return;
		} else {
			lvArtistaEsp.getItems().add(seleccionada);
			cbArtistaEsp.setStyle("-fx-background-color: #D6EAF8;");
		}
	}

	@FXML
	private void eliminarEspecialidad() {
		Especialidad seleccionada = lvArtistaEsp.getSelectionModel()
				.getSelectedItem();
		if (seleccionada != null) {
			lvArtistaEsp.getItems().remove(seleccionada);
		}
	}

	@FXML
	private void cargarPersonaParaModificar() {
		
		personaEnEdicion = cbSelectorP.getValue();

		if (personaEnEdicion == null) {
			return;
		}
		txtNombre.setText(personaEnEdicion.getNombre());
		txtEmail.setText(personaEnEdicion.getEmail());
		cbNacionalidad.setValue(personaEnEdicion.getNacionalidad());
		txtNombreU.setText(personaEnEdicion.getCredenciales().getNombre());
		txtPass.setText(personaEnEdicion.getCredenciales().getPassword());

		if (personaEnEdicion instanceof Artista) {
			Artista artista = (Artista) personaEnEdicion;
			rbArtista.setSelected(true);
			txtApodo.setText(artista.getApodo());
			lvArtistaEsp.getItems().setAll(artista.getEspecialidades());
			
		} else if (personaEnEdicion instanceof Coordinador) {
			Coordinador coordinador = (Coordinador) personaEnEdicion;
			rbCoor.setSelected(true);
			chbCoor.setSelected(coordinador.isSenior());
			if (coordinador.isSenior()) {
				dateCoor.setValue(coordinador.getFechasenior());
			}
		}
		ocultarTodo();
		gridDatosPersona.setVisible(true);
	}

	@FXML
	private void handleEliminarArtista() {
		Artista seleccionado = lvArtistasSeleccionados.getSelectionModel()
				.getSelectedItem();
		if (seleccionado != null) {
			lvArtistasSeleccionados.getItems().remove(seleccionado);
		}
	}

	@FXML
	private void handleEliminarNumero() {
		Numero seleccionado = lvNumCreados.getSelectionModel()
				.getSelectedItem();
		if (seleccionado != null) {
			lvNumCreados.getItems().remove(seleccionado);
		}
	}

	@FXML
	public void handleModificarE() {
		ocultarTodo();
		// [NUEVO] Cargar lista de la BD para el ComboBox
		cbSelectorE.setItems(FXCollections
				.observableArrayList(espectaculoService.getEspectaculos()));
		panelBuscadorE.setVisible(true);

	}

	@FXML
	private void handleLogOut(ActionEvent event) {
		try {
			// limpiamos la sesion
			usuariosService.getSesion().setUsuActual(null);

			// cargamos de nuevo el FXML de invitado
			FXMLLoader loader = new FXMLLoader(
					getClass().getResource("/vista/MenuInvitado.fxml"));
			loader.setControllerFactory(context::getBean); // Para que Spring
															// gestione el
															// controlador

			Parent root = loader.load();

			// cambio ala escena de invitado
			Stage stage = (Stage) ((Node) event.getSource()).getScene()
					.getWindow();
			stage.setScene(new Scene(root));
			stage.show();

		} catch (IOException e) {
			System.err.println("Error al cerrar sesión: " + e.getMessage());
		}
	}

	@FXML
	public void conversorArtistasCombo() {
		cbartistasN.setConverter(new StringConverter<Artista>() {
			@Override
			public String toString(Artista a) {
				return (a == null) ? "" : a.getNombre();
			}

			@Override
			public Artista fromString(String string) {
				return null;
			}
		});
	}

	@FXML
	private void conversorArtistasSeleccionados() {
		lvArtistasSeleccionados.setCellFactory(lv -> new ListCell<>() {
			@Override
			protected void updateItem(Artista item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.getNombre());
			}
		});
	}

	@FXML
	private void conversorEspecialidadArtista() {
		lvArtistaEsp.setCellFactory(lv -> new ListCell<Especialidad>() {
			@Override
			protected void updateItem(Especialidad item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty || item == null ? null : item.getNombre());
			}
		});
	}

	@FXML
	private void conversorNumeros() {
		lvNumCreados.setCellFactory(lv -> new ListCell<>() {
			@Override
			protected void updateItem(Numero item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(item.getNombre() + " (" + item.getDuracion()
							+ " min) - "
							+ (item.getArtistas() != null
									? item.getArtistas().size()
									: 0)
							+ " artistas");
				}
			}
		});
	}

	@FXML
	private void limpiarEstilosErrores() {
		tfnombreN.setStyle("-fx-background-color: #D6EAF8;");
		cbartistasN.setStyle("-fx-background-color: #D6EAF8;");
	}

	@FXML
	private void handleAnadirArtistaAListaTemporal() {

		Artista seleccionado = cbartistasN.getValue();
		if (seleccionado != null
				&& !artistasDelNumeroActual.contains(seleccionado)) {// TODO
																		// peude
																		// estar
																		// mal
			artistasDelNumeroActual.add(seleccionado);
			cbartistasN.setStyle("-fx-background-color: #D6EAF8;"); // Quitamos
																	// error si
																	// lo
																	// hubiera
		} else {
			cbartistasN.setStyle(
					"-fx-border-color: red; -fx-background-color: #D6EAF8;");
		}
	}

	@FXML
	private void handleAgregarNumero() {
		limpiarEstilosErrores();
		boolean error = false;

		// Nombre del Número
		String nombreN = tfnombreN.getText();
		if (nombreN == null || nombreN.trim().isEmpty()) {
			tfnombreN.setStyle(
					"-fx-border-color: red; -fx-background-color: #D6EAF8;");
			error = true;
		}

		// validar Artista seleccionado
		Artista artistaSeleccionado = cbartistasN.getValue();
		if (artistaSeleccionado == null) {
			cbartistasN.setStyle(
					"-fx-border-color: red; -fx-background-color: #D6EAF8;");
			error = true;
		}

		if (error)
			return; // si hay error, no se sigue

		Numero nuevoNumero = new Numero();
		nuevoNumero.setNombre(nombreN);
		nuevoNumero.setDuracion(spnduracionN.getValue()); // el Spinner ya está
															// validado
		Set<Artista> artistasSet = new HashSet<>(artistasDelNumeroActual);
		nuevoNumero.setArtistas(artistasSet);
		nuevoNumero.setEspectaculo(espectaculoEnEdicion);

		lvNumCreados.getItems().add(nuevoNumero);

		// al final, limppiar campos
		limpiarCampos();

	}

	@FXML
	public void limpiarCampos() {
		tfnombreN.clear();
		cbartistasN.setValue(null);
		spnduracionN.getValueFactory().setValue(5.0);
		dpFechaIni.setValue(null);
		dpFechafin.setValue(null);
		lvNumCreados.getItems().clear();
		lvArtistasSeleccionados.getItems().clear();
	}

	// navegacion atras en creacion/modificacion
	@FXML
	private void handleAtras() {
		ocultarTodo();
		panelFormularioDatos.setVisible(true);
	}

	@FXML
	private void handleCancelar() {
		ocultarTodo();

	}

	// navegacion de continuar en creacion/modificacion
	@FXML
	private void handleContinuar() {
		
		  System.out.println("handleContinuar llamado");
		boolean error = false;

		tfNombre.setStyle("-fx-background-color: #D6EAF8;");
		dpFechaIni.setStyle("-fx-background-color: #D6EAF8;");
		dpFechafin.setStyle("-fx-background-color: #D6EAF8;");

		if (validarCampo(tfNombre, Validador.nombreEspectaculoRegex)) {
			error = true;
			System.out.println("error en nombre");
		}
		if (validarCombo(cbCoordinadores)) {
			System.out.println("error en coordinador");
			error = true;
			
		}

		if (dpFechaIni.getValue() == null || dpFechafin.getValue() == null) {
			if (dpFechaIni.getValue() == null) {
				dpFechaIni.setStyle(
						"-fx-border-color: red; -fx-background-color: #D6EAF8;");
				System.out.println("error en fechas nulas");
			}
			if (dpFechafin.getValue() == null) {
				dpFechafin.setStyle(
						"-fx-border-color: red; -fx-background-color: #D6EAF8;");
				System.out.println("error en rango fechas");
			}
			error = true;
		} else {
			
			if (!Validador.esFechaValida(dpFechaIni.getValue(), dpFechafin.getValue())) {
			    dpFechafin.setStyle("-fx-border-color: red; -fx-background-color: #D6EAF8;");
			    System.out.println("error en fechas");
			    error = true;
			}
		}

		if (error) {
			return;
		}
		System.out.println("avanzando a panel numeros");

		// actualiza el objeto con los datos de los campos antes de cambiar
		espectaculoEnEdicion.setNombre(tfNombre.getText());
		espectaculoEnEdicion.setFechaini(dpFechaIni.getValue());
		espectaculoEnEdicion.setFechafin(dpFechafin.getValue());

		ocultarTodo();
		// recargar artistas por si se han añadido nuevos
		cbartistasN.setItems(FXCollections.observableArrayList(usuariosService.getArtistas()));
		
		panelGestionNumeros.setVisible(true);
	}

	@FXML
	private void handleFinalizar() {
		// asigna automaticamente el coordinador actual
		Coordinador coordinadorElegido = cbCoordinadores.getValue();
		if (coordinadorElegido != null) {
			espectaculoEnEdicion.setEncargadoCoor(coordinadorElegido);
		}

		espectaculoEnEdicion.setNumeros(new HashSet<>(lvNumCreados.getItems()));
		espectaculoService.guardarEspectaculo(espectaculoEnEdicion);

		// mostrar resumen
		lblResumenNombre.setText(espectaculoEnEdicion.getNombre());
		lblResumenFechas
				.setText("Disponible de " + espectaculoEnEdicion.getFechaini()
						+ " a " + espectaculoEnEdicion.getFechafin());
		lblResumenCoordinadr.setText(cbCoordinadores.getValue().getNombre());

		// listview:
		ObservableList<String> itemsResumen = FXCollections
				.observableArrayList();
		for (Numero n : espectaculoEnEdicion.getNumeros()) {
			String artistas = "";
			for (Artista a : n.getArtistas()) {
		        if (!artistas.isEmpty()) {
		            artistas += ", ";
		        }
		        artistas += a.getNombre();
		    }
			itemsResumen.add(
					n.getNombre() + " | Duración: " + n.getDuracion() + " min | Artistas: "+artistas);

		}
		lvResumenNumeros.setItems(itemsResumen);
		btnFinalizarTodo.disableProperty().unbind();
		btnFinalizarTodo.disableProperty()
				.bind(Bindings.size(lvNumCreados.getItems()).lessThan(3));

		// cuando trodo cargado, mostrar
		ocultarTodo();
		panelResumen.setVisible(true);

	}

	/**
	 * boton '+' añade un artista a una lista temporal, para luego pasar a la
	 * listview y al numero
	 */
	@FXML
	public void handleVincularArtista() {
		// TODO comprobacion temporal
		System.out.println("Intentando vincular artista...");
		Artista seleccionado = cbartistasN.getValue();

		if (seleccionado != null) {
			// evitar duplicados en la lista de este número concreto
			if (!lvArtistasSeleccionados.getItems().contains(seleccionado)) {
				lvArtistasSeleccionados.getItems().add(seleccionado);

				// TODO prueba temporal
				System.out.println("Artista añadido a la lista: "
						+ seleccionado.getNombre());

				// forzar que refresque al añadir
				lvArtistasSeleccionados.refresh();
			} else {
				System.out.println("El artista ya está en la lista.");
			}
		}

	}

	@FXML
	private void handleRegistrarNumero() {
		boolean error = false;
		
		//limpiar previos
		tfnombreN.setStyle("-fx-background-color: #D6EAF8;");
		lvArtistasSeleccionados.setStyle(null);
		
		//validaciones
		if(validarCampo(tfnombreN, Validador.nombreEspectaculoRegex)) {
			error = true;
		}
		if (validarLista(lvArtistasSeleccionados, 1, Integer.MAX_VALUE)) {
			error = true;
		}
		
		if (error) {
			return;
		}
		
		String nombreNum = tfnombreN.getText();
		double duracion = spnduracionN.getValue();
		List<Artista> seleccionados = new ArrayList<>(
				lvArtistasSeleccionados.getItems());

		if (nombreNum != null && !nombreNum.isEmpty()
				&& !seleccionados.isEmpty()) {
			Numero nuevoNumero = new Numero();
			nuevoNumero.setNombre(nombreNum);
			nuevoNumero.setDuracion(duracion);
			nuevoNumero.setArtistas(new HashSet<>(seleccionados));
			nuevoNumero.setEspectaculo(espectaculoEnEdicion);

			lvNumCreados.getItems().add(nuevoNumero);

			// hay que limpiar para añadir nuevo numero
			tfnombreN.clear();
			lvArtistasSeleccionados.getItems().clear();
			spnduracionN.getValueFactory().setValue(5.0);
		}

	}
	
	@FXML
	private void verHistorial() {
		ocultarTodo();
		
		tablaHistorial.getItems().clear();
		panelHistorial.setVisible(true);
	}
	
	@FXML
	private void buscarHistorial() {
		
		String usuario = tfFiltroUsuario.getText().trim();
		
		//checkboxes
		List<String> tiposSeleccionados = new ArrayList<>();
	    if (chbFiltroNuevo.isSelected()) {
	        tiposSeleccionados.add("NUEVO");
	    }
	    if (chbFiltroActualizacion.isSelected()) {
	        tiposSeleccionados.add("ACTUALIZACION");
	    }
	    if (chbFiltroBorrado.isSelected()) {
	        tiposSeleccionados.add("BORRADO");
	    }
		
		LocalDateTime desde = null;
		LocalDateTime hasta = null;
		
		if (dpFiltroDesde.getValue() != null) {
			desde = dpFiltroDesde.getValue().atStartOfDay();
		}
		if (dpFiltroHasta.getValue() != null) {
			hasta = dpFiltroHasta.getValue().atTime(23,59,59);
		}
		
		List<LogOperacion> resultado = logService.consultarHistorial(
		        usuario.isEmpty() ? null : usuario, 
		        tiposSeleccionados.isEmpty() ? null : tiposSeleccionados, 
		        desde, hasta);
		
		tablaHistorial.getItems().setAll(resultado);
		
	}
	
	@FXML
	private void handleIncidencias(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/MenuIncidencias.fxml"));
	        loader.setControllerFactory(context::getBean);
	        Parent root = loader.load();
	        
	        // pasamos la pantalla de origen para el boton atras
	        MenuIncidenciasController controller = loader.getController();
	        controller.setPantallaOrigen("Artista"); // o "Coordinador" o "Admin"
	        controller.configurarBienvenida();
	        
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(new Scene(root));
	        stage.show();
	    } catch (IOException e) {
	        System.err.println("Error al abrir incidencias: " + e.getMessage());
	    }
	}

}
