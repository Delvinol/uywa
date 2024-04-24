package com.example.demo.domain.entity.paseos;

import com.example.demo.domain.entity.paseadores.Paseadores;
import com.example.demo.domain.entity.propietarios.Propietarios;
import com.example.demo.domain.entity.usuarios.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "Reservas")
public class Reservas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Integer idReserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_propietario")
    private Propietarios propietarios;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paseador")
    private Paseadores paseadores;

    @Column(name = "monto", precision = 10, scale = 2, nullable = false)
    private BigDecimal monto;

    @Column(name = "fecha_reserva", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp fechaReserva;

    @Column(name = "duracion_paseo")
    private LocalTime duracionPaseo;

    @Column(name = "detalles", columnDefinition = "TEXT", nullable = false)
    private String detalles;

    @Column(name = "punto_encuentro", nullable = false, length = 25)
    private String puntoEncuentro;

    @Column(name = "lugar_paseo", nullable = false, length = 20)
    private String lugarPaseo;

    @Column(name = "estado", columnDefinition = "TINYINT DEFAULT 1")
    private Short estado;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at",  columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", referencedColumnName = "id")
    private User updatedBy;

    ////////////// Mapeando cardinalidades //////////////////////

    @OneToOne(mappedBy = "reservas", cascade = CascadeType.ALL)
    private Paseos paseos;

}
