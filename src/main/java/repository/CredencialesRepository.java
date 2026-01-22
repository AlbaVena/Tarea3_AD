package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entidades.Credenciales;

@Repository
public interface CredencialesRepository extends JpaRepository<Credenciales, Long> {

}
