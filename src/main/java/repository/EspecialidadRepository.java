package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entidades.Especialidad;

@Repository
public interface EspecialidadRepository extends JpaRepository<Especialidad, Long>{

}
