package principal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import utils.Menu;

@SpringBootApplication

@ComponentScan(basePackages = {"repository", "controlador", "utils", "factorias"})
@EntityScan(basePackages = "entidades")
@EnableJpaRepositories(basePackages = "repository")
public class PrincipalNuevo implements CommandLineRunner{

	
	@Autowired
    private Menu menu;

	public static void main(String[] args) {
		
		SpringApplication.run(PrincipalNuevo.class, args);
		
		
		
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Iniciando..");
		menu.menuInvitado();
		
		
	}

}
