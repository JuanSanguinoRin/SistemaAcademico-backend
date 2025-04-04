package co.edu.ufps.backend.service;

import co.edu.ufps.backend.model.Facturacion;
import co.edu.ufps.backend.repository.FacturacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FacturacionService {

    private final FacturacionRepository facturacionRepository;

    @Autowired
    public FacturacionService(FacturacionRepository facturacionRepository) {
        this.facturacionRepository = facturacionRepository;
    }

    // Operaciones CRUD
    public List<Facturacion> listarTodas() {
        return facturacionRepository.findAll();
    }

    public Optional<Facturacion> buscarPorNumeroFactura(String numeroFactura) {
        return facturacionRepository.findById(numeroFactura);
    }

    public Facturacion guardar(Facturacion facturacion) {
        // Se podría inicializar algunos campos si es necesario
        return facturacionRepository.save(facturacion);
    }

    public void eliminar(String numeroFactura) {
        facturacionRepository.deleteById(numeroFactura);
    }

    public Optional<Facturacion> actualizarEstado(String numeroFactura, String estado) {
        Optional<Facturacion> facturaOptional = facturacionRepository.findById(numeroFactura);
        if (facturaOptional.isPresent()) {
            Facturacion factura = facturaOptional.get();
            factura.setEstado(estado);
            return Optional.of(facturacionRepository.save(factura));
        }
        return Optional.empty();
    }

    // Lógica de negocio simulada

    // Genera la factura asignando fecha de emisión y estado inicial.
    public Facturacion generarFactura(String numeroFactura) {
        return facturacionRepository.findById(numeroFactura).map(factura -> {
            factura.setFechaEmision(new Date());
            factura.setEstado("Pendiente");
            // Invoca el método de la entidad que, en una implementación real, podría realizar otras acciones.
            factura.generarFactura();
            return facturacionRepository.save(factura);
        }).orElseThrow(() -> new RuntimeException("Factura no encontrada"));
    }

    // Aplica un descuento, actualiza el campo de descuentos y recalcula el total.
    public Facturacion aplicarDescuento(String numeroFactura, String concepto, double valor) {
        return facturacionRepository.findById(numeroFactura).map(factura -> {
            factura.setDescuentos(valor);
            factura.aplicarDescuento(concepto, valor);
            // Recalcular total, suponiendo que el método de la entidad lo realice.
            factura.calcularTotal();
            return facturacionRepository.save(factura);
        }).orElseThrow(() -> new RuntimeException("Factura no encontrada"));
    }

    // Calcula el total basándose en el subtotal y descuentos.
    public Facturacion calcularTotal(String numeroFactura) {
        return facturacionRepository.findById(numeroFactura).map(factura -> {
            factura.calcularTotal();
            double subtotal = (factura.getSubtotal() != null ? factura.getSubtotal() : 0);
            double descuentos = (factura.getDescuentos() != null ? factura.getDescuentos() : 0);
            factura.setTotal(subtotal - descuentos);
            return facturacionRepository.save(factura);
        }).orElseThrow(() -> new RuntimeException("Factura no encontrada"));
    }

    // Registra un pago actualizando la fecha de pago, el método y el estado.
    public Facturacion registrarPago(String numeroFactura, double monto, String metodoPago) {
        return facturacionRepository.findById(numeroFactura).map(factura -> {
            factura.registrarPago(monto, metodoPago);
            factura.setFechaPago(new Date());
            factura.setMetodoPago(metodoPago);
            factura.setEstado("Pagada");
            return facturacionRepository.save(factura);
        }).orElseThrow(() -> new RuntimeException("Factura no encontrada"));
    }

    // Envía la factura electrónica.
    public Facturacion enviarFacturaElectronica(String numeroFactura) {
        return facturacionRepository.findById(numeroFactura).map(factura -> {
            factura.enviarFacturaElectronica();
            return facturacionRepository.save(factura);
        }).orElseThrow(() -> new RuntimeException("Factura no encontrada"));
    }

    // Genera un recibo digital.
    public Facturacion generarReciboDigital(String numeroFactura) {
        return facturacionRepository.findById(numeroFactura).map(factura -> {
            factura.generarReciboDigital();
            return facturacionRepository.save(factura);
        }).orElseThrow(() -> new RuntimeException("Factura no encontrada"));
    }

    // Consulta y retorna el estado de la factura.
    public String consultarEstado(String numeroFactura) {
        return facturacionRepository.findById(numeroFactura)
                .map(Facturacion::consultarEstado)
                .orElse("Factura no encontrada");
    }
}
