package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entidades.Artista;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long>{

}
