package servicios;

import entidades.Artista;
import entidades.Numero;
import entidades.mongodb.Evaluacion;
import entidades.mongodb.Observacion;

public interface IDossierService {

    void crearDossier(Artista artista);
    void actualizarDatosBasicos(Artista artista);
    void actualizarTrayectoria(Artista artista, Numero numero);
    void añadirEvaluacion(long idArtista, Evaluacion evaluacion);
    void añadirObservacion(long idArtista, Observacion observacion);
}