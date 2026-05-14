package entidades.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import entidades.Artista;
import entidades.Especialidad;

@Document(collection = "dossiers")
public class Dossier {

    @Id
    private String id;
    private long idArtista;
    private String nombre;
    private String nacionalidad;
    private String email;
    private String apodo;
    private List<String> especialidades = new ArrayList<>();
    private List<Trayectoria> trayectoria = new ArrayList<>();
    private List<Evaluacion> evaluaciones = new ArrayList<>();
    private List<Observacion> observaciones = new ArrayList<>();

    public Dossier(Artista artista) {
        this.idArtista = artista.getId();
        this.nombre = artista.getNombre();
        this.nacionalidad = artista.getNacionalidad();
        this.email = artista.getEmail();
        this.apodo = artista.getApodo();
        for (Especialidad e : artista.getEspecialidades()) {
            this.especialidades.add(e.getNombre());
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(long idArtista) {
        this.idArtista = idArtista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public List<String> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(List<String> especialidades) {
        this.especialidades = especialidades;
    }

    public List<Trayectoria> getTrayectoria() {
        return trayectoria;
    }

    public void setTrayectoria(List<Trayectoria> trayectoria) {
        this.trayectoria = trayectoria;
    }

    public List<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(List<Evaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }

    public List<Observacion> getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(List<Observacion> observaciones) {
        this.observaciones = observaciones;
    }
}
