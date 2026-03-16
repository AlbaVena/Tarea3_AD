package principal;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication(scanBasePackages = {"controlador", "entidades", "factorias", "repository", "servicios", "implementacion", "utils"})
@EnableJpaRepositories(basePackages = "repository") 
@EntityScan(basePackages = "entidades")
public class principalAplicacion extends Application{

	private ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        // Esto arranca Spring Boot antes de que empiece la interfaz
        this.context = new SpringApplicationBuilder(principalAplicacion.class).run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // ¡ESTA ES LA CLAVE! 
        // Creamos el cargador y le decimos que use a Spring para crear los controladores
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/MenuInvitado.fxml"));
        loader.setControllerFactory(context::getBean); 
        
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Circo - Menú Invitado");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        // Cerramos el contexto de Spring al cerrar la ventana
        this.context.close();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
