package co.edu.ufps.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Facturacion {
    @Id
    private String numeroFactura; // Clave primaria

    private LocalTime fechaEmision;

    private LocalTime fechaVencimiento;

    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    private Double subtotal;
    private Double descuentos;
    private Double total;

    private EstadoFacturacion estado; // "Pendiente", "Pagada", "Vencida", "Anulada"
    private String metodoPago;

    private LocalTime fechaPago;

}