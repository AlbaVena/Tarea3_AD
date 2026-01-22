package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entidades.Numero;

@Repository
public interface NumeroRepository extends JpaRepository<Numero, Long>{

}
