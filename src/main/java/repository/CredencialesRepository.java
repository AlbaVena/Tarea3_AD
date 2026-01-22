package repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entidades.Credenciales;

@Repository
public interface CredencialesRepository extends JpaRepository<Credenciales, Long> {

	public Optional<Credenciales> findByNombreAndPassword(String nombre, String password);
	
	public boolean existsByNombre(String nombre);
	
}
