package principal;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Esta clase le dice a Spring que cree un EntityManagerFactory separado solo para ObjectDB.
 */
@Configuration
public class ObjectDBConfig {

	@Value("${objectdb.url}")
	private String url;
	
	@Value("${objectdb.user}")
    private String user;

    @Value("${objectdb.password}")
    private String password;
    
    // crea el EntityManagerFactory especifico para ObjectDB
    // separado del de Hibernate/MySQL
    @Bean(name = "objectdbEntityManagerFactory")
    public EntityManagerFactory objectdbEntityManagerFactory() {
    	
        Map<String, String> propiedades = new HashMap<>();
        propiedades.put("javax.persistence.jdbc.url", url);
        propiedades.put("javax.persistence.jdbc.user", user);
        propiedades.put("javax.persistence.jdbc.password", password);
        
        
        //entidades que gestiona solo objectdb para que no las toque Hibernate:
        propiedades.put("objectdb.temp.no-enhancement-caching", "true");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb", propiedades);
        
        return emf;
    }
}
