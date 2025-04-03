package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Facturacion;
import co.edu.ufps.backend.repository.FacturacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacturacionService {

    private final FacturacionRepository facturacionRepository;

    @Autowired
    public FacturacionService(FacturacionRepository facturacionRepository) {
        this.facturacionRepository = facturacionRepository;
    }

    /**
     * Listar todas las facturas.
     *
     * @return lista de todas las facturas
     */
    public List<Facturacion> listarTodas() {
        return facturacionRepository.findAll();
    }

    /**
     * Buscar una factura por su número.
     *
     * @param numeroFactura número de la factura
     * @return factura encontrada o un Optional vacío si no existe
     */
    public Optional<Facturacion> buscarPorNumeroFactura(String numeroFactura) {
        return facturacionRepository.findById(numeroFactura);
    }

    /**
     * Guardar o actualizar una factura.
     *
     * @param facturacion factura a guardar o actualizar
     * @return la factura guardada
     */
    public Facturacion guardar(Facturacion facturacion) {
        return facturacionRepository.save(facturacion);
    }

    /**
     * Eliminar una factura por su número.
     *
     * @param numeroFactura número de la factura a eliminar
     */
    public void eliminar(String numeroFactura) {
        facturacionRepository.deleteById(numeroFactura);
    }

    /**
     * Actualizar el estado de una factura.
     *
     * @param numeroFactura número de la factura
     * @param estado nuevo estado de la factura
     * @return la factura actualizada
     */
    public Optional<Facturacion> actualizarEstado(String numeroFactura, String estado) {
        Optional<Facturacion> facturaOptional = facturacionRepository.findById(numeroFactura);
        if (facturaOptional.isPresent()) {
            Facturacion factura = facturaOptional.get();
            factura.setEstado(estado);
            return Optional.of(facturacionRepository.save(factura));
        }
        return Optional.empty();
    }
}