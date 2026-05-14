package servicios.implementacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entidades.Artista;
import entidades.Especialidad;
import entidades.Espectaculo;
import entidades.Numero;
import entidades.mongodb.Dossier;
import entidades.mongodb.Evaluacion;
import entidades.mongodb.NumeroResumen;
import entidades.mongodb.Observacion;
import entidades.mongodb.Trayectoria;
import repository.DossierRepository;
import servicios.IDossierService;

@Service
public class DossierService implements IDossierService {

    @Autowired
    private DossierRepository dossierRepository;

    @Override
    public void crearDossier(Artista artista) {
        Dossier dossier = new Dossier(artista);
        System.out.println("Intentando guardar dossier para artista: " + artista.getId());
        dossierRepository.save(dossier);
        System.out.println("Dossier guardado con id: " + dossier.getId());
    }

    @Override
    public void actualizarDatosBasicos(Artista artista) {
        Dossier dossier = dossierRepository.findByIdArtista(artista.getId()).orElse(new Dossier(artista));
        dossier.setNombre(artista.getNombre());
        dossier.setNacionalidad(artista.getNacionalidad());
        dossier.setEmail(artista.getEmail());
        dossier.setApodo(artista.getApodo());

        List<String> especialidades = new ArrayList<>();
        for (Especialidad e : artista.getEspecialidades()) {
            especialidades.add(e.getNombre());
        }
        dossier.setEspecialidades(especialidades);

        dossierRepository.save(dossier);
    }

    @Override
    public void actualizarTrayectoria(Artista artista, Numero numero) {
        System.out.println("=== actualizarTrayectoria para artista: " + artista.getNombre() + ", numero: " + numero.getNombre() + " (id=" + numero.getId() + ")");

        Dossier dossier = dossierRepository.findByIdArtista(artista.getId()).orElse(new Dossier(artista));
        System.out.println("Dossier encontrado, trayectoria actual: " + dossier.getTrayectoria().size() + " espectaculos");

        Espectaculo espectaculo = numero.getEspectaculo();
        System.out.println("Espectaculo del numero: " + (espectaculo != null ? espectaculo.getNombre() + " (id=" + espectaculo.getId() + ")" : "NULL"));

        Trayectoria t = buscarOCrearTrayectoria(dossier.getTrayectoria(), espectaculo);
        System.out.println("Numeros en esa trayectoria antes de añadir: " + t.getNumeros().size());

        NumeroResumen nr = new NumeroResumen();
        nr.setIdNumero(numero.getId());
        nr.setNombreNumero(numero.getNombre());

        boolean yaExiste = false;
        for (NumeroResumen existing : t.getNumeros()) {
            if (existing.getIdNumero() == nr.getIdNumero()) {
                yaExiste = true;
                break;
            }
        }
        System.out.println("Ya existe en trayectoria: " + yaExiste);

        if (!yaExiste) {
            t.getNumeros().add(nr);
        }

        System.out.println("Numeros en trayectoria tras añadir: " + t.getNumeros().size());
        dossierRepository.save(dossier);
        System.out.println("Dossier guardado.");
    }

    private Trayectoria buscarOCrearTrayectoria(List<Trayectoria> trayectoria, Espectaculo espectaculo) {
        for (Trayectoria t : trayectoria) {
            if (t.getIdEspectaculo() == espectaculo.getId()) {
                return t;
            }
        }
        Trayectoria nueva = new Trayectoria();
        nueva.setIdEspectaculo(espectaculo.getId());
        nueva.setNombreEspectaculo(espectaculo.getNombre());
        trayectoria.add(nueva);
        return nueva;
    }

    @Override
    public void añadirEvaluacion(long idArtista, Evaluacion evaluacion) {
        Dossier dossier = dossierRepository.findByIdArtista(idArtista).orElse(null);
        if (dossier == null) {
            System.err.println("Dossier no encontrado para idArtista: " + idArtista);
            return;
        }
        dossier.getEvaluaciones().add(evaluacion);
        dossierRepository.save(dossier);
    }

    @Override
    public void añadirObservacion(long idArtista, Observacion observacion) {
        Dossier dossier = dossierRepository.findByIdArtista(idArtista).orElse(null);
        if (dossier == null) {
            System.err.println("Dossier no encontrado para idArtista: " + idArtista);
            return;
        }
        dossier.getObservaciones().add(observacion);
        dossierRepository.save(dossier);
    }
}
