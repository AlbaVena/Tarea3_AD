package controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import entidades.Artista;
import entidades.Coordinador;
import entidades.Especialidad;
import entidades.Espectaculo;
import entidades.Numero;
import entidades.Persona;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import servicios.IEspectaculosService;
import servicios.IUsuariosService;

@Controller
public class MenuAdminController implements Initializable{
	
@FXML private StackPane stkpaneCoor; 
	
	//ver espectaculos
	@FXML private TableView<Espectaculo> tablaEspectaculos;
    @FXML private TableColumn<Espectaculo, String> columnNombreE;
    @FXML private TableColumn<Espectaculo, LocalDate> columnFechaIniE;
    @FXML private TableColumn<Espectaculo, LocalDate> columnFechaFinE;
   
    //botones
    @FXML private Button btnVerEspectaculos;
    @FXML private Button btnLogOut;
    @FXML private Button btnCargarE;
    @FXML private Button btnFinalizarTodo;
    @FXML private Button btnRegistrarNumero;
    @FXML private Button btnAgregar;
    @FXML private Button btnEliminarA;
    @FXML private Button btnEliminarE;
    @FXML private Button btnEliminar;
    
    //paneles de vistas
    @FXML private GridPane panelFormularioDatos;
    @FXML private VBox panelGestionNumeros;
    @FXML private ScrollPane panelResumen;
    @FXML private VBox panelBuscadorE;
    @FXML private GridPane gridDatosPersona;
    @FXML private GridPane gridArtista;
    @FXML private GridPane gridCoordinador;
    @FXML private ScrollPane panelResumenP;
    
   //form datos
    @FXML private TextField tfNombre;
    @FXML private DatePicker dpFechaIni;
    @FXML private DatePicker dpFechafin;
    @FXML private Button btnAccion;
    
    //numeros
    @FXML private TextField tfnombreN;
    @FXML private Spinner<Integer> spnduracionN;
    @FXML private ComboBox<Artista> cbartistasN;
    @FXML private ListView<Numero> lvNumCreados;
    @FXML private ListView<Artista> lvArtistasSeleccionados;
    
    //buscador para modificar
    @FXML private ComboBox<Espectaculo> cbSelectorE;   
    
    //buscador personas
    @FXML private VBox panelBuscadorP;
    @FXML private ComboBox<Persona> cbSelectorP;
    @FXML private Button btnCargarP;
    @FXML private Button btnEliminarP;
    
    //resumen
    @FXML private Label lblResumenNombre, lblResumenFechas, lblResumenCoordinadr;
    @FXML private ListView<String> lvResumenNumeros;
    
    //tabla artistas
    @FXML private TableView<Artista> tablaArtistas;
    @FXML private TableColumn<Artista, String> columnNombreA;
    @FXML private TableColumn<Artista, String> columnApodoA;
    @FXML private TableColumn<Artista, String> columnEspecA;
    
    //panel datos comunes persona    
    @FXML private RadioButton rbArtista;
    @FXML private RadioButton rbCoor;
    @FXML private ToggleGroup tipoPersona;
    @FXML private TextField txtNombre;
    @FXML private TextField txtEmail;
    @FXML private ComboBox<String> cbNacionalidad;
    @FXML private TextField txtNombreU;
    @FXML private TextField txtPass;
    @FXML private Button btnCancelarP;
    @FXML private Button btnSiguienteP;
    
 // panel datos artista
    @FXML private TextField txtApodo;
    @FXML private ComboBox<Especialidad> cbArtistaEsp;
    @FXML private ListView<Especialidad> lvArtistaEsp;
    @FXML private Button btnAgregarEsp;
    @FXML private Button btnEliminarEsp;
    @FXML private Button btnAtrasA;
    @FXML private Button btnFinalizarA;
    
    //panel datos coordinador
    @FXML private CheckBox chbCoor;
    @FXML private DatePicker dateCoor;
    @FXML private Button btnAtrasC;
    @FXML private Button btnFinalizarC;
    
    
    //resumen persona
    @FXML private Label lblResumenNombreP;
    @FXML private Label lblResumenEmailP;
    @FXML private Label lblResumenNacionP;
    @FXML private Label lblResumenUsuarioP;
    @FXML private Label lblResumenPuestoP;
    @FXML private VBox vBResumenArtista;
    @FXML private VBox vbDatosCoor;
    @FXML private Label lblResumenApodoA;
    @FXML private Label lblResumenEspA;
    @FXML private Label lblResumenCoor;

    @Autowired private IEspectaculosService espectaculoService;
    @Autowired private IUsuariosService usuariosService;
    @Autowired private ConfigurableApplicationContext context;


    //temporales
    private Espectaculo espectaculoEnEdicion;
    private ObservableList<Artista> artistasDelNumeroActual = FXCollections.observableArrayList();
    
    /**
     * MÉTODOS:
     */
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	//configuracion de la tabla como en Invitadp
		columnNombreE.setCellValueFactory(new PropertyValueFactory<>("nombre"));
	    columnFechaIniE.setCellValueFactory(new PropertyValueFactory<>("fechaini"));
	    columnFechaFinE.setCellValueFactory(new PropertyValueFactory<>("fechafin"));

	    tablaEspectaculos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_LAST_COLUMN);
	    tablaEspectaculos.setPlaceholder(new Label("No hay espectáculos disponibles en este momento."));

	    //cargar el spinner de duracion
	    SpinnerValueFactory<Integer> valueFactory = 
	            new SpinnerValueFactory.IntegerSpinnerValueFactory(15, 150, 15);//min, max, base
	    spnduracionN.setValueFactory(valueFactory);
	    
	    //conversor del buscador de espectaculos
	    cbSelectorE.setConverter(new StringConverter<Espectaculo>() {
	        @Override
	        public String toString(Espectaculo esp) {
	            return (esp == null) ? "" : esp.getNombre();
	        }
	        //conversor en direccion contraria
	        @Override
	        public Espectaculo fromString(String string) { return null; }
	    });
	    
	    //conversor igual para artistas
	    conversorArtistasCombo();
	    conversorArtistasSeleccionados();
	    
	    conversorNumeros();
	 
	    
	    //cargar artistas
	    cbartistasN.setItems(FXCollections.observableArrayList(usuariosService.getArtistas()));
	    lvArtistasSeleccionados.setItems(artistasDelNumeroActual);
	    
	    //BINDING = condicion
        //condicion: si algun campo no esta bien relleno
        
        //panel 1: si faltan datos
        btnAccion.disableProperty().bind(
                tfNombre.textProperty().isEmpty()
                .or(dpFechaIni.valueProperty().isNull())
                .or(dpFechafin.valueProperty().isNull())
            );
        
        //buscador, si no se selecciona nada
        btnCargarE.disableProperty().bind(
                cbSelectorE.valueProperty().isNull()
            );
        
        btnEliminar.disableProperty().bind(cbSelectorE.valueProperty().isNull());
        
        //boton finalizar, con menos de 3 numeros
        btnFinalizarTodo.disableProperty().bind(
                Bindings.size(lvNumCreados.getItems()).lessThan(3)
            );
        
        
        //estado inicial
        ocultarTodo();
    }
    
    /**
     * para ocultar todos los paneles a la vez
     */
    @FXML
    private void ocultarTodo() {
        tablaEspectaculos.setVisible(false);
        panelFormularioDatos.setVisible(false);
        panelGestionNumeros.setVisible(false);
        panelResumen.setVisible(false);
        panelBuscadorE.setVisible(false);
        
   
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
        //combo con los espectáculos actuales
        cbSelectorE.getItems().setAll(espectaculoService.getEspectaculos());
        btnCargarE.setVisible(true);
        btnEliminar.setVisible(false);
        panelBuscadorE.setVisible(true);
    }
    @FXML
    private void handleBotonEliminarLateral() {
        ocultarTodo();
        cbSelectorE.getItems().setAll(espectaculoService.getEspectaculos());
        btnCargarE.setVisible(false);
        btnEliminar.setVisible(true);
        panelBuscadorE.setVisible(true);
    }
    
    @FXML
    private void handleEliminarE() {
        Espectaculo seleccionado = cbSelectorE.getValue();
        if (seleccionado != null) {
            espectaculoService.eliminarEspectaculo(seleccionado.getId());
            cbSelectorE.getItems().remove(seleccionado);
            cbSelectorE.setValue(null);
        }
    }
    
    
   
    
    @FXML
    private void handleCrearE() {
        ocultarTodo();
        espectaculoEnEdicion = new Espectaculo();
        lvNumCreados.getItems().clear();
        btnAccion.setText("Guardar");
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
            
            //cargar los numeros que ya tiene en la listview
            ObservableList<Numero> numerosCargados = FXCollections.observableArrayList(espectaculoEnEdicion.getNumeros());
            
            lvNumCreados.getItems().clear();
            lvNumCreados.getItems().addAll(espectaculoEnEdicion.getNumeros());
            
            btnAccion.setText("Siguiente");
            ocultarTodo();
            panelFormularioDatos.setVisible(true);
        }
    }
    
    
    
    @FXML
    private void handleEliminarArtista() {
        Artista seleccionado = lvArtistasSeleccionados.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            lvArtistasSeleccionados.getItems().remove(seleccionado);
        }
    }
    
    @FXML
    private void handleEliminarNumero() {
        Numero seleccionado = lvNumCreados.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            lvNumCreados.getItems().remove(seleccionado);
        }
    }
    
    @FXML
    public void handleModificarE() {
        ocultarTodo();
        // [NUEVO] Cargar lista de la BD para el ComboBox
        cbSelectorE.setItems(FXCollections.observableArrayList(espectaculoService.getEspectaculos()));
        panelBuscadorE.setVisible(true);
        
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

   

    @FXML
	public void conversorArtistasCombo() {
		cbartistasN.setConverter(new StringConverter<Artista>() {
	        @Override
	        public String toString(Artista a) {
	            return (a == null) ? "" : a.getNombre();
	        }
	        @Override
	        public Artista fromString(String string) { return null; }
	    });
	}
    
    @FXML
    private void conversorArtistasSeleccionados() {
    	lvArtistasSeleccionados.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(Artista item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNombre());
            }
        });
    }
    
    @FXML
    private void conversorNumeros() {
    	lvNumCreados.setCellFactory(lv -> new ListCell<>() {
            @Override protected void updateItem(Numero item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNombre() + " (" + item.getDuracion() + " min) - " + 
                           (item.getArtistas() != null ? item.getArtistas().size() : 0) + " artistas");
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
        if (seleccionado != null && !artistasDelNumeroActual.contains(seleccionado)) {//TODO peude estar mal
            artistasDelNumeroActual.add(seleccionado);
            cbartistasN.setStyle("-fx-background-color: #D6EAF8;"); // Quitamos error si lo hubiera
        } else {
            cbartistasN.setStyle("-fx-border-color: red; -fx-background-color: #D6EAF8;");
        }
    }
    
    @FXML
    private void handleAgregarNumero() {
    	limpiarEstilosErrores();
        boolean error = false;
        
        //Nombre del Número
        String nombreN = tfnombreN.getText();
        if (nombreN == null || nombreN.trim().isEmpty()) {
            tfnombreN.setStyle("-fx-border-color: red; -fx-background-color: #D6EAF8;");
            error = true;
        }
        
        //validar Artista seleccionado
        Artista artistaSeleccionado = cbartistasN.getValue();
        if (artistaSeleccionado == null) {
            cbartistasN.setStyle("-fx-border-color: red; -fx-background-color: #D6EAF8;");
            error = true;
        }

        if (error) return; //si hay error, no se sigue
        
        Numero nuevoNumero = new Numero();
        nuevoNumero.setNombre(nombreN);
        nuevoNumero.setDuracion(spnduracionN.getValue()); // el Spinner ya está validado
        Set<Artista> artistasSet = new HashSet<>(artistasDelNumeroActual);
        nuevoNumero.setArtistas(artistasSet); 
        nuevoNumero.setEspectaculo(espectaculoEnEdicion);
        
        lvNumCreados.getItems().add(nuevoNumero);
        
        //al final, limppiar campos
        limpiarCampos();
        
        
    }

    @FXML
	public void limpiarCampos() {
		tfnombreN.clear();
        cbartistasN.setValue(null);
        spnduracionN.getValueFactory().setValue(15);
        dpFechaIni.setValue(null);
        dpFechafin.setValue(null);
        lvNumCreados.getItems().clear();
        lvArtistasSeleccionados.getItems().clear();
	}
    
    //navegacion atras en creacion/modificacion
    @FXML
    private void handleAtras() {
        ocultarTodo();
        panelFormularioDatos.setVisible(true);
    }
    
    @FXML
    private void handleCancelar() {
        ocultarTodo();
        
    }
    
    //navegacion de continuar en creacion/modificacion
    @FXML
    private void handleContinuar() {
        // actualiza el objeto con los datos de los campos antes de cambiar
        espectaculoEnEdicion.setNombre(tfNombre.getText());
        espectaculoEnEdicion.setFechaini(dpFechaIni.getValue());
        espectaculoEnEdicion.setFechafin(dpFechafin.getValue());

        ocultarTodo();
        panelGestionNumeros.setVisible(true);
    }
    
    
    @FXML
    private void handleFinalizar() {
    	//asigna automaticamente el coordinador actual
    	Persona usuarioActual = usuariosService.getSesion().getUsuActual();
        if (usuarioActual instanceof Coordinador coor) {
            espectaculoEnEdicion.setEncargadoCoor(coor);
        }
        
        espectaculoEnEdicion.setNumeros(new HashSet<>(lvNumCreados.getItems()));
        espectaculoService.guardarEspectaculo(espectaculoEnEdicion);
        
        //mostrar resumen
        lblResumenNombre.setText(espectaculoEnEdicion.getNombre());
        lblResumenFechas.setText("Válido de " + espectaculoEnEdicion.getFechaini() + 
        		" a " + espectaculoEnEdicion.getFechafin());
        lblResumenCoordinadr.setText(usuarioActual.getNombre());
        
        //listview:
        ObservableList<String> itemsResumen = FXCollections.observableArrayList();
        for (Numero n : espectaculoEnEdicion.getNumeros()) {
        	n.getArtistas().size();
            itemsResumen.add(n.getNombre() + " | Duración: " + n.getDuracion() + " min");
            
        }
        lvResumenNumeros.setItems(itemsResumen);
        btnFinalizarTodo.disableProperty().unbind();
        btnFinalizarTodo.disableProperty().bind(
        Bindings.size(lvNumCreados.getItems()).lessThan(3));
        
        //cuando trodo cargado, mostrar
        ocultarTodo();
        panelResumen.setVisible(true);
        
    }
    
   
    
    /**
     * boton '+' añade un artista a una lista temporal, para luego 
     * pasar a la listview y al numero
     */
    @FXML
    public void handleVincularArtista() {
    	//TODO comprobacion temporal
    	System.out.println("Intentando vincular artista...");
    	Artista seleccionado = cbartistasN.getValue();
    	
    	if (seleccionado != null) {
            // evitar duplicados en la lista de este número concreto
            if (!lvArtistasSeleccionados.getItems().contains(seleccionado)) {
                lvArtistasSeleccionados.getItems().add(seleccionado);
                
                
                //TODO prueba temporal
                System.out.println("Artista añadido a la lista: " + seleccionado.getNombre());
                
                // forzar que refresque al añadir
                lvArtistasSeleccionados.refresh();
            }
            else {
                System.out.println("El artista ya está en la lista.");
            }
        }
    	
    }
    
    @FXML
    private void handleRegistrarNumero() {
    	 String nombreNum = tfnombreN.getText();
         int duracion = spnduracionN.getValue();
         List<Artista> seleccionados = new ArrayList<>(lvArtistasSeleccionados.getItems());

         if (nombreNum != null && !nombreNum.isEmpty() && !seleccionados.isEmpty()) {
             Numero nuevoNumero = new Numero();
             nuevoNumero.setNombre(nombreNum);
             nuevoNumero.setDuracion(duracion);
             nuevoNumero.setArtistas(new HashSet<>(seleccionados));
             nuevoNumero.setEspectaculo(espectaculoEnEdicion);
             
             lvNumCreados.getItems().add(nuevoNumero);

             //hay que limpiar para añadir  nuevo numero
             tfnombreN.clear();
             lvArtistasSeleccionados.getItems().clear();
             spnduracionN.getValueFactory().setValue(15);
         }
    	
    }

}
