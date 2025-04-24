package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Publicacion;
import co.edu.ufps.backend.repository.PublicacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PublicacionService {

    @Autowired
    private final PublicacionRepository publicacionRepository;

    /**
     * Obtener todas las publicaciones
     * @return Lista de todas las publicaciones
     */
    public List<Publicacion> getAllPublicaciones() {
        return publicacionRepository.findAll();
    }

    /**
     * Obtener una publicación por su ID
     * @param id ID de la publicación
     * @return Publicación encontrada o un Optional vacío
     */
    public Optional<Publicacion> getPublicacionById(Long id) {
        return publicacionRepository.findById(id);
    }


    /**
     * Actualizar una publicación existente
     * @param id ID de la publicación a actualizar
     * @param publicacionDetails Nuevos detalles de la publicación
     * @return Publicación actualizada
     */
    public Publicacion updatePublicacion(Long id, Publicacion publicacionDetails) {
        return publicacionRepository.findById(id).map(publicacion -> {
            publicacion.setFechaEnvio(publicacionDetails.getFechaEnvio());
            publicacion.setAutor(publicacionDetails.getAutor());
            publicacion.setForo(publicacionDetails.getForo());
            return publicacionRepository.save(publicacion);
        }).orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
    }

    /**
     * Eliminar una publicación por su ID
     * @param id ID de la publicación a eliminar
     */
    public void deletePublicacion(Long id) {
        publicacionRepository.deleteById(id);
    }

    /**
     * Listar publicaciones por un foro específico
     * @param foroId ID del foro
     * @return Lista de publicaciones pertenecientes al foro
     */
    public List<Publicacion> getPublicacionesByForo(Long foroId) {
        return publicacionRepository.findAll();
    }

    /**
     * Listar publicaciones por un autor específico
     * @param autorCedula Cédula del autor
     * @return Lista de publicaciones del autor
     */
    public List<Publicacion> getPublicacionesByAutor(String autorCedula) {
        return publicacionRepository.findAll();
    }

    public Publicacion publicar(Persona autor, Foro foro, String contenido) {
        Publicacion publicacion = new Publicacion();
        publicacion.setAutor(autor);
        publicacion.setForo(foro);
        publicacion.setContenido(contenido);
        publicacion.setFechaEnvio(new Date());

        return publicacionRepository.save(publicacion);
    }
}