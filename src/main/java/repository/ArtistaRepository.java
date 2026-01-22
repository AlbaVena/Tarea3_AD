package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import entidades.Artista;

@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long>{

	public Optional<Artista> getByName (String nombre);
}
