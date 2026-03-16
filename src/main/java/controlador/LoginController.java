package controlador;

import org.springframework.stereotype.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class LoginController {
	
	@FXML private TextField tfUsuario;
    @FXML private PasswordField tfPass;
    @FXML private Button btnLogin;
    @FXML private Button btnCancelar;

    @FXML
    public void initialize() {
      
    }

}
