package com.example.demo.domain.entity.mascotas;

import com.example.demo.domain.entity.usuarios.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Tipos_Mascota")
public class TiposMascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_mascota")
    private Integer idTipoMascota;

    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;

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

    ///////// MAPEANDO CARDINALIDADES ///////////////////////////

    @OneToMany(mappedBy = "tiposMascota", cascade = CascadeType.ALL)
    private List<Mascotas> mascotas;

}
