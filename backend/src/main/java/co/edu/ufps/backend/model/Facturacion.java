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

}