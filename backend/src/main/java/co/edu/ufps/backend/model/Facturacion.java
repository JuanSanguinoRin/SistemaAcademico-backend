package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Facturacion {
    @Id
    private String numeroFactura; // Clave primaria

    @Temporal(TemporalType.DATE)
    private Date fechaEmision;

    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;

    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    private Double subtotal;
    private Double descuentos;
    private Double total;

    private String estado; // "Pendiente", "Pagada", "Vencida", "Anulada"
    private String metodoPago;

    @Temporal(TemporalType.DATE)
    private Date fechaPago;

    // Métodos específicos
    public void generarFactura() {
        // Implementación del método
    }
    
    public void aplicarDescuento(String concepto, double valor) {
        // Implementación del método
    }
    
    public void calcularTotal() {
        // Implementación del método
    }
    
    public void registrarPago(double monto, String metodoPago) {
        // Implementación del método
    }
    
    public void enviarFacturaElectronica() {
        // Implementación del método
    }
    
    public void generarReciboDigital() {
        // Implementación del método
    }
    
    public String consultarEstado() {
        // Implementación del método
        return estado;
    }

}