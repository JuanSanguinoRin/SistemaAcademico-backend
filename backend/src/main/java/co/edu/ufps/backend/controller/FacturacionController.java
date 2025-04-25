package co.edu.ufps.backend.controller;

import co.edu.ufps.backend.model.EstadoFacturacion;
import co.edu.ufps.backend.model.Facturacion;
import co.edu.ufps.backend.service.FacturacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facturas")
@RequiredArgsConstructor
public class FacturacionController {

    private final FacturacionService facturacionService;

    // CRUD básico

    @GetMapping
    public List<Facturacion> listarTodas() {
        return facturacionService.listarTodas();
    }

    @GetMapping("/{numeroFactura}")
    public ResponseEntity<Facturacion> buscarPorNumero(@PathVariable String numeroFactura) {
        return facturacionService.buscarPorNumeroFactura(numeroFactura)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Facturacion guardar(@RequestBody Facturacion facturacion) {
        return facturacionService.guardar(facturacion);
    }

    @DeleteMapping("/{numeroFactura}")
    public ResponseEntity<Void> eliminar(@PathVariable String numeroFactura) {
        facturacionService.eliminar(numeroFactura);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{numeroFactura}/estado")
    public ResponseEntity<Facturacion> actualizarEstado(@PathVariable String numeroFactura,
                                                        @RequestParam EstadoFacturacion estado) {
        return facturacionService.actualizarEstado(numeroFactura, estado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoints para operaciones específicas

    @PutMapping("/{numeroFactura}/generar")
    public ResponseEntity<Facturacion> generarFactura(@PathVariable String numeroFactura) {
        try {
            Facturacion factura = facturacionService.generarFactura(numeroFactura);
            return ResponseEntity.ok(factura);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{numeroFactura}/descuento")
    public ResponseEntity<Facturacion> aplicarDescuento(@PathVariable String numeroFactura,
                                                        @RequestParam double valor) {
        try {
            Facturacion factura = facturacionService.aplicarDescuento(numeroFactura, valor);
            return ResponseEntity.ok(factura);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{numeroFactura}/calcular-total")
    public ResponseEntity<Facturacion> calcularTotal(@PathVariable String numeroFactura) {
        try {
            Facturacion factura = facturacionService.calcularTotal(numeroFactura);
            return ResponseEntity.ok(factura);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{numeroFactura}/registrar-pago")
    public ResponseEntity<Facturacion> registrarPago(@PathVariable String numeroFactura,
                                                     @RequestParam double monto,
                                                     @RequestParam String metodoPago) {
        try {
            Facturacion factura = facturacionService.registrarPago(numeroFactura, monto, metodoPago);
            return ResponseEntity.ok(factura);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{numeroFactura}/estado")
    public ResponseEntity<String> consultarEstado(@PathVariable String numeroFactura) {
        String estado = facturacionService.consultarEstado(numeroFactura);
        if ("Factura no encontrada".equals(estado)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(estado);
    }
}
