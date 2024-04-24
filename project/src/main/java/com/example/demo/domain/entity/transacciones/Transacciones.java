package com.example.demo.domain.entity.transacciones;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Transacciones")
public class Transacciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Integer idTransaccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_paseador", nullable = false)
    private Paseadores paseadores;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_propietario", nullable = false)
    private Propietarios propietarios;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_transaccion", nullable = false)
    private TiposTransaccion tiposTransaccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estado_transaccion")
    private EstadosTransaccion estadoTransaccion;

    @Column(name = "monto", precision = 8, scale = 2, nullable = false)
    private BigDecimal monto;

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
}
