package servicios.implementacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entidades.Artista;
import entidades.Espectaculo;
import entidades.Numero;
import entidades.Sesion;
import entidades.TipoOperacion;
import jakarta.transaction.Transactional;
import repository.EspectaculoRepository;
import servicios.IDossierService;
import servicios.IEspectaculosService;
import servicios.ILogService;

@Service
public class EspectaculosService implements IEspectaculosService {

    @Autowired
    private EspectaculoRepository espectaculoRepository;

    @Autowired
    private Sesion sesion;

    @Autowired
    private IDossierService dossierService;

    @Autowired
    private ILogService logService;

    @Transactional
    public List<Espectaculo> getEspectaculos() {
        return espectaculoRepository.findAll();
    }

    @Transactional
    public void setEspectaculos(List<Espectaculo> espectaculos) {
        espectaculoRepository.saveAll(espectaculos);
    }

    @Transactional
    public void guardarEspectaculo(Espectaculo aGuardar) {
        boolean esNuevo = aGuardar.getId() == 0;
        espectaculoRepository.save(aGuardar);

        Espectaculo guardado = espectaculoRepository.findById(aGuardar.getId()).orElse(aGuardar);
        System.out.println("Guardamos espectaculo "+aGuardar.getNombre());
        for (Numero numero : guardado.getNumeros()) {
            System.out.println("\t- Guardamos el numero: "+numero.getNombre());
            for (Artista artista : numero.getArtistas()) {
                System.out.println("\t\t-- Actualizamos artista: "+artista.getNombre() + " con el numero: "+numero.getNombre());
                dossierService.actualizarTrayectoria(artista, numero);
            }
        }

        String usuario;
        if (sesion.getUsuActual() == null) {
            usuario = "sistema";
        } else if (sesion.getUsuActual().getCredenciales() == null) {
            usuario = "admin"; // usuario admin de memoria
        } else {
            usuario = sesion.getUsuActual().getCredenciales().getNombre();
        }

        TipoOperacion tipo = esNuevo ? TipoOperacion.NUEVO : TipoOperacion.ACTUALIZACION;
        logService.registrarOperacion(usuario, tipo, "Espectaculo", aGuardar.getId());
    }

    @Transactional
    public Espectaculo getEspectaculo(long id) {
        return espectaculoRepository.findById(id).orElse(null);
    }

    @Transactional
    public void eliminarEspectaculo(long id) {
        espectaculoRepository.deleteById(id);

        String usuario;
        if (sesion.getUsuActual() == null) {
            usuario = "sistema";
        } else if (sesion.getUsuActual().getCredenciales() == null) {
            usuario = "admin"; // usuario admin de memoria
        } else {
            usuario = sesion.getUsuActual().getCredenciales().getNombre();
        }
        logService.registrarOperacion(usuario, TipoOperacion.BORRADO, "Espectaculo", id);
    }

}
