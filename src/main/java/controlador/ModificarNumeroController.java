package controlador;


import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import org.springframework.stereotype.Controller;

import entidades.Artista;
import entidades.Numero;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import utils.Validador;

@Controller
public class ModificarNumeroController implements Initializable {
	
	@FXML TextField txtNombreNumMod;
	@FXML Spinner<Double> spTiempoNumMod;
	@FXML ListView<Artista> lvArtistasMod;
	@FXML ComboBox<Artista> cbArtistasNumMod;
	@FXML Button btnMasArtistasNumMod;
	@FXML Button btnMenosArtistasNumMod;
	@FXML Button btnGuardarNumMod;
	
	private Numero numeroEnEdicion;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 SpinnerValueFactory<Double> valueFactory = 
			        new SpinnerValueFactory.DoubleSpinnerValueFactory(5.0, 15.5, 5.0, 0.5);
			    spTiempoNumMod.setValueFactory(valueFactory);
			    
			    configurarListaArtistas();
			    configurarComboArtistas();
			    
		
	}
	
	public void setDatos(Numero numero, Set<Artista> artistasDisponibles) {
		
		this.numeroEnEdicion = numero;
		
		txtNombreNumMod.setText(numero.getNombre());
		spTiempoNumMod.getValueFactory().setValue(numero.getDuracion());
		lvArtistasMod.getItems().setAll(numero.getArtistas());
		cbArtistasNumMod.getItems().setAll(artistasDisponibles);
		
	}
	@FXML
	private void guardarModificacion() {
		boolean error = false;
		
		if (!Validador.esCadenaValida(txtNombreNumMod.getText(), Validador.nombreEspectaculoRegex)) {
	        txtNombreNumMod.setStyle("-fx-border-color: red;");
	        error = true;
	    } else {
	        txtNombreNumMod.setStyle(null);
	    }
	    
	    if (lvArtistasMod.getItems().isEmpty()) {
	        lvArtistasMod.setStyle("-fx-border-color: red;");
	        error = true;
	    } else {
	        lvArtistasMod.setStyle(null);
	    }
	    
	    if (error) return;
	    
	    numeroEnEdicion.setNombre(txtNombreNumMod.getText());
	    numeroEnEdicion.setDuracion(spTiempoNumMod.getValue());
	    numeroEnEdicion.setArtistas(new HashSet<Artista>(lvArtistasMod.getItems()));
	    
	    Stage stage = (Stage) btnGuardarNumMod.getScene().getWindow();
	    
	    stage.close();
	    
	}
	
	@FXML
	private void agregarArtista() {
		Artista seleccionado = cbArtistasNumMod.getValue();
		
		if (seleccionado != null && !lvArtistasMod.getItems().contains(seleccionado)) {
			lvArtistasMod.getItems().add(seleccionado);
			lvArtistasMod.setStyle(null);
		} else {
			cbArtistasNumMod.setStyle("-fx-border-color: red;");
		}
		
	}
	
	@FXML
	private void quitarArtista() {
		Artista seleccionado = lvArtistasMod.getSelectionModel().getSelectedItem();
	    if (seleccionado != null) {
	        lvArtistasMod.getItems().remove(seleccionado);
	    }
	}
	
	private void configurarListaArtistas() {
	    lvArtistasMod.setCellFactory(lv -> new ListCell<Artista>() {
	        @Override
	        protected void updateItem(Artista item, boolean empty) {
	            super.updateItem(item, empty);
	            setText(empty || item == null ? null : item.getNombre());
	        }
	    });
	}

	private void configurarComboArtistas() {
	    cbArtistasNumMod.setConverter(new StringConverter<Artista>() {
	        @Override
	        public String toString(Artista a) {
	            return (a == null) ? "" : a.getNombre();
	        }
	        @Override
	        public Artista fromString(String string) { return null; }
	    });
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
