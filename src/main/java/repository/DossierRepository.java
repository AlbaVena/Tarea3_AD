package repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import entidades.mongodb.Dossier;

@Repository
public interface DossierRepository extends MongoRepository<Dossier, String> {

    Optional<Dossier> findByIdArtista(long idArtista);
}