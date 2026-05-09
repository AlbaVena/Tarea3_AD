package servicios.implementacion;

import java.lang.module.ResolutionException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import entidades.objectdb.Incidencia;
import entidades.objectdb.ResolucionIncidencia;
import entidades.objectdb.TipoIncidencia;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import servicios.IIncidenciasService;

/**
 * ObjectDB no maneja @Transactional de Spring, porque está configurado para Hibernate.
 * En este caso tenemos que gestionar el EntityManager manualmente abrienndo, commiteando
 * y cerrando la operacion ( con .begin, .commit, .rollback y .close).
 */
@Service
public class IncidenciasService implements IIncidenciasService {
	
	@Value("${objectdb.url}")
    private String url;

    @Value("${objectdb.user}")
    private String user;

    @Value("${objectdb.password}")
    private String password;


    private EntityManagerFactory emf;
	
    // se ejecuta despues de inyectar los @Value
    @jakarta.annotation.PostConstruct
    private void init() {
        Map<String, Object> propiedades = new HashMap<>();
        //propiedades.put("jakarta.persistence.jdbc.url", 
        //    url + "?user=" + user + "&password=" + password);
        
        propiedades.put("jakarta.persistence.jdbc.user", user);
        propiedades.put("jakarta.persistence.jdbc.password", password);
        
        this.emf = Persistence.createEntityManagerFactory(
        			url, propiedades);
        
        //this.emf = new com.objectdb.jpa.Provider()
        //    .createEntityManagerFactory(url, propiedades);
    }

    private EntityManager getEntityManager() {
    	//Map<String, Object> props = new HashMap<>();
    	//props.put("jakarta.persistence.jdbc.user", user);
    	//props.put("jakarta.persistence.jdbc.password", password);
        return emf.createEntityManager();
    }
    
	@Override
	public void registrarIncidencia(Incidencia incidencia) {
		EntityManager em = getEntityManager();
		
		try {
			em.getTransaction().begin();
			em.persist(incidencia);
			em.getTransaction().commit();
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
		
	}

	@Override
	public void resolverIncidencia(Long idIncidencia, String accionesRealizadas, Long idPersonaResuelve) {
		EntityManager em = getEntityManager();
		
		try {
			em.getTransaction().begin();
			
			//igual que findById- (clase,id)
			Incidencia incidencia = em.find(Incidencia.class, idIncidencia);
			
			if (incidencia != null) {
				
				ResolucionIncidencia resolucion = new ResolucionIncidencia();
				resolucion.setAccionesRealizadas(accionesRealizadas);
				resolucion.setIdPersonaResuelve(idPersonaResuelve);
				resolucion.setIncidencia(incidencia);
				
				incidencia.setResuelta(true);
				em.merge(incidencia);
				
				em.persist(resolucion);
			}
			em.getTransaction().commit();
			
			
		} catch (Exception e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
		
	}

	@Override
	public List<Incidencia> consultarIncidencias(TipoIncidencia tipo, Boolean resuelta, Long idEspectaculo,
			Long idNumero, LocalDateTime desde, LocalDateTime hasta) {
		
		EntityManager em = getEntityManager();
		
		try {
			
			String jpql = "SELECT i FROM Incidencia i WHERE 1=1";
			
			if (tipo != null) {
				jpql += " AND i.tipo = :tipo";
			}
			
			if ( idEspectaculo != null) {
				jpql += " AND i.idEspectaculo = :idEspectaculo";
			}
			if (idNumero != null) {
				jpql += " AND i.idNumero = :idNumero";
			}
			if(desde != null) {
				jpql += " AND i.fechaHora >= :desde";
			}
			if (hasta != null) {
				jpql += " AND i.fechaHora <= :hasta";
			}
			
			TypedQuery<Incidencia> query = em.createQuery(jpql, Incidencia.class);
			
			//parametros de la consulta:
			
			if ( tipo != null) {
				query.setParameter("tipo", tipo);
			}
			if (idEspectaculo != null) {
				query.setParameter("idEspectaculo", idEspectaculo);
			}
			if ( idNumero != null) {
				query.setParameter("idNumero", idNumero);
			}
			if (desde != null) {
				query.setParameter("desde", desde);
			}
			if ( hasta != null) {
				query.setParameter("hasta", hasta);
			}
			
			List<Incidencia> resultado = query.getResultList();

			// filtrar por resuelta en memoria porque ObjectDB no lo reconoce en JPQL
			if (resuelta != null) {
			    final Boolean filtro = resuelta;
			    resultado = resultado.stream()
			        .filter(i -> filtro.equals(i.isResuelta()))
			        .collect(java.util.stream.Collectors.toList());
			}

			return resultado;
			
			
		} finally {
			em.close();
		}
		
	}

	@Override
	public ResolucionIncidencia getResolucionIncidencia(Long idIncidencia) {
		
		EntityManager em = getEntityManager();
	    try {
	        TypedQuery<ResolucionIncidencia> query = em.createQuery(
	            "SELECT r FROM ResolucionIncidencia r WHERE r.incidencia.id = :idIncidencia",
	            ResolucionIncidencia.class);
	        query.setParameter("idIncidencia", idIncidencia);
	        List<ResolucionIncidencia> resultados = query.getResultList();
	        return resultados.isEmpty() ? null : resultados.get(0);
	    } finally {
	        em.close();
	    }
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
