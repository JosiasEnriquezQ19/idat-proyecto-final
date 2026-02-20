package idat.api.pe.idat_proyecto_final.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(
    name = "evaluacion",
    uniqueConstraints = @UniqueConstraint(columnNames = {"asignatura_id", "ponderacion_id"})
)
@Data
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignatura_id", nullable = false)
    private Asignatura asignatura;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ponderacion_id", nullable = false)
    private Ponderacion ponderacion;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, precision = 4, scale = 2)
    private BigDecimal nota;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
}
