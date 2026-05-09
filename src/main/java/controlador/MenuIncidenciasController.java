package controlador;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import entidades.Espectaculo;
import entidades.Numero;
import entidades.Perfil;
import entidades.Persona;
import entidades.objectdb.Incidencia;
import entidades.objectdb.ResolucionIncidencia;
import entidades.objectdb.TipoIncidencia;
import javafx.collections.FXCollections;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import servicios.IEspectaculosService;
import servicios.IIncidenciasService;
import servicios.IUsuariosService;
import utils.Validador;

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
	
		configurarTablas();
		configurarCombos();
		ocultarTodo();
		panelPrincipalIncidencias.setVisible(true);
		
	}
	
	private void configurarTablas() {
		colIdIncidencia.setCellValueFactory(new PropertyValueFactory<>("id"));
		colFechaIncidencia.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));
		colFechaIncidencia.setCellFactory(col -> new TableCell<Incidencia, LocalDateTime>(){
				@Override
				protected void updateItem(LocalDateTime item, boolean empty) {
			super.updateItem(item, empty);
			setText(empty || item == null ? "" : Validador.formatearFechaHora(item));
		}
		});
        colTipoIncidencia.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colDescripcionIncidencia.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colEstadoIncidencia.setCellValueFactory(cellData ->
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().isResuelta() ? "Resuelta" : "No resuelta"));

        tablaIncidencias.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_LAST_COLUMN);

        // tabla resolver
        colIdResolver.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFechaResolver.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));

        colFechaResolver.setCellFactory(col -> new TableCell<Incidencia, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : Validador.formatearFechaHora(item));
            }
        });
        
        colTipoResolver.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colDescripcionResolver.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        tablaIncidenciasNoResueltas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_LAST_COLUMN);
        
        //doble click para ir a resolver o mostrar contenido(excepto si es artista
        tablaIncidencias.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Incidencia seleccionada = tablaIncidencias.getSelectionModel().getSelectedItem();
                if (seleccionada != null) {
                    if (seleccionada.isResuelta()) {
                        mostrarDetalleIncidencia(seleccionada);
                    } else {
                        // solo coordinador y admin pueden resolver
                        Perfil perfil = usuariosService.getSesion().getUsuActual().getPerfil();
                        if (perfil == Perfil.COORDINACION || perfil == Perfil.ADMIN) {
                            llegadoDesdeConsultar = true;
                            incidenciaAResolver = seleccionada;
                            mostrarPanelResolver();
                        }
                        // si es artista, no hace nada
                    }
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
		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Éxito");
		alert.setHeaderText(null);
		alert.setContentText("Incidencia registrada correctamente.");
		alert.showAndWait();
		
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
	
	@FXML
	private void buscarIncidencia() {
		TipoIncidencia tipo = null;
		
		int marcados = (chbFiltroTecnica.isSelected() ? 1 : 0) + (chbFiltroArtistica.isSelected() ? 1 :0)+ (chbFiltroOrganizativa.isSelected() ? 1 : 0);
		
		if (marcados == 1) {
			if (chbFiltroTecnica.isSelected()) {
				tipo = TipoIncidencia.TECNICA;
			} else if (chbFiltroArtistica.isSelected()) {
				tipo = TipoIncidencia.ARTISTICA;
			} else {
				tipo = TipoIncidencia.ORGANIZATIVA;
			}
		}
		
		Boolean resuelta = null;
		
		if (cbFiltroEstado.getValue() != null) {
			if (cbFiltroEstado.getValue().equals("Resueltas")) {
				resuelta = true;
			} else if (cbFiltroEstado.getValue().equals("No resueltas")) {
				resuelta = false;
			}
		}
		
		Long idEsp = cbFiltroEspectaculo.getValue() != null ? cbFiltroEspectaculo.getValue().getId() : null;
		
		Long idNum = cbFiltroNumero.getValue() != null ? cbFiltroNumero.getValue().getId() : null;
		
		LocalDateTime desde = dpFiltroDesde.getValue() != null ? dpFiltroDesde.getValue().atStartOfDay() : null;
		
		LocalDateTime hasta  = dpFiltroHasta.getValue() != null ? dpFiltroHasta.getValue().atTime(23,59,59) : null;
		
		
		List <Incidencia> encontradas = incidenciasService.consultarIncidencias(tipo, resuelta, idEsp, idNum, desde, hasta);
		
		tablaIncidencias.setItems(FXCollections.observableArrayList(encontradas));
		
		
	}
	
	@FXML
	private void limpiarFiltros() {
		chbFiltroTecnica.setSelected(false);
        chbFiltroArtistica.setSelected(false);
        chbFiltroOrganizativa.setSelected(false);
        cbFiltroEstado.setValue(null);
        cbFiltroEspectaculo.setValue(null);
        cbFiltroNumero.getItems().clear();
        dpFiltroDesde.setValue(null);
        dpFiltroHasta.setValue(null);
        tablaIncidencias.getItems().clear();
	}
	
	@FXML
	private void confirmarResolucion() {
		
		if (incidenciaAResolver == null) {
			return;
		}
		
		if (txtAccionesResolver.getText().isBlank()){
			txtAccionesResolver.setStyle("-fx-border-color: red;");
			return;
		}
		
		
		
		incidenciasService.resolverIncidencia(incidenciaAResolver.getId(), txtAccionesResolver.getText(), usuariosService.getSesion().getUsuActual().getId());
		
		txtAccionesResolver.clear();
		txtAccionesResolver.setStyle("");
		incidenciaAResolver = null;
		
		//volver segun de donde vengamos
		if (llegadoDesdeConsultar) {
			handleConsultar();
		} else {
			ocultarTodo();
			panelPrincipalIncidencias.setVisible(true);
		}
		
		
	}
	
	@FXML
	private void cancelarResolucion() {
		
		txtAccionesResolver.clear();
		txtAccionesResolver.setStyle("");
		incidenciaAResolver = null;
		
		//volver segun de donde vengamos
				if (llegadoDesdeConsultar) {
					handleConsultar();
				} else {
					ocultarTodo();
					panelPrincipalIncidencias.setVisible(true);
				}
	}
	
	
	public void setPantallaOrigen(String pantalla) {
		this.pantallaOrigen = pantalla;
	}
	
	
	@FXML
	private void atras(ActionEvent event) {
		try {
			String fxml = "/vista/Menu" + pantallaOrigen + ".fxml";
			FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
			loader.setControllerFactory(context::getBean);
			Parent root = loader.load();
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {
			System.out.println("Error a resolver"+ e.getMessage());
		}
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
	
	public void configurarBienvenida() {
		System.out.println("configurarBienvenida llamado");
		Persona p = usuariosService.getSesion().getUsuActual();
		System.out.println("perfil: " + (p != null ? p.getPerfil() : "null"));
		
		if (p != null) {
			lblBienvenidaIncidencias.setText(p.getNombre()+", bienvenido/a al menú de incidencias.");
			
			if (p.getPerfil().name().equals(Perfil.ARTISTA.name())) {
				btnResolver.setVisible(false);
				btnResolver.setManaged(false);
				lblNotaRolIncidencias.setText("Como Artista, puedes registrar y consultar incidencias, pero no resolverlas.");
			}
		}
	}
	
	private void mostrarDetalleIncidencia(Incidencia i) {
	    String detalle = "Id: " + i.getId() + "\n" +
	                     "Fecha: " + Validador.formatearFechaHora(i.getFechaHora()) + "\n" +
	                     "Tipo: " + i.getTipo() + "\n" +
	                     "Descripción: " + i.getDescripcion() + "\n" +
	                     "Estado: Resuelta";
	    
	    ResolucionIncidencia resolucion = incidenciasService.getResolucionIncidencia(i.getId());
	    
	    if (resolucion != null) {
	        detalle += "\n=== RESOLUCIÓN ===\n" +
	        		"Fecha resolución: " + Validador.formatearFechaHora(resolucion.getFechaHoraResolucion()) + "\n" +
	                   "Acciones realizadas: " + resolucion.getAccionesRealizadas();
	    }

	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle("Detalle de incidencia");
	    alert.setHeaderText("Incidencia Nº" + i.getId());
	    
	    // TextArea para que no se corte el texto
	    TextArea textArea = new TextArea(detalle);
	    textArea.setEditable(false);
	    textArea.setWrapText(true);
	    textArea.setPrefSize(400, 200);

	    alert.getDialogPane().setContent(textArea);
	    alert.showAndWait();
	    
	}
	
	
	
	
	
	

}
