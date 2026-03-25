package controlador;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import entidades.Persona;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import servicios.IUsuariosService;

@Controller
public class LoginController {
	
	@FXML private TextField tfUsuario;
    @FXML private PasswordField tfPass;
    @FXML private Button btnLogin;
    @FXML private Button btnCancelar;
    
    @Autowired
    private IUsuariosService usuariosService;

    @Autowired
    private ConfigurableApplicationContext context;
    
    

    @FXML
    public void initialize() {
        btnCancelar.setOnAction(this::handleCancelar);    
        btnLogin.setOnAction(this::handleLogin);
        
        //con boton Pass seleccionado, pulsar ENTER sirve para entrar
        tfPass.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                handleLogin(new ActionEvent());
            }
        });
    }
    
    
    private void handleLogin(ActionEvent event) {
        String usuario = tfUsuario.getText();
        String password = tfPass.getText();
        
        //TODO este metodo es temporal hasta que estén todas las pantallas
        Persona usuarioLogueado = usuariosService.login(usuario, password);
        
        if (usuarioLogueado != null) {
        // Por ahora, solo informamos por consola para saber que el "cerebro" funciona
        System.out.println("Login correcto para el perfil: " + usuarioLogueado.getPerfil());
        
        //TODO cuando todo funcione, aqui va el switch de cambiar a una pantalla u otra
        
        switch (usuarioLogueado.getPerfil()) { 
        case ARTISTA:
            cargarPantalla(event, "/vista/MenuArtista.fxml");
            break;
        case ADMIN:
             System.out.println("Pantalla de Admin en construcción...");
            break;
        case COORDINACION:
        	cargarPantalla(event, "/vista/MenuCoordinador.fxml");
            break;
        default:
            System.out.println("Perfil no reconocido.");
    }
        
        
        
        } else {
            // Si falla, damos feedback visual en los campos [cite: 29, 30]
            tfUsuario.setStyle("-fx-border-color: red;");
            tfPass.setStyle("-fx-border-color: red;");
            System.out.println("Usuario o contraseña incorrecto");
        }
        
        
        
    }

    private void cargarPantalla(ActionEvent event, String rutaFxml) {
    	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFxml));
            loader.setControllerFactory(context::getBean); 
            Parent root = loader.load();
            
            
            Stage stage = (Stage) tfPass.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
//            
//            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(new Scene(root));
//            stage.show();
            
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al cargar la vista: " + rutaFxml);
        }
		
	}


	private void handleCancelar(ActionEvent event) {
        try {
            // Volver a MenuInvitado.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/MenuInvitado.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
