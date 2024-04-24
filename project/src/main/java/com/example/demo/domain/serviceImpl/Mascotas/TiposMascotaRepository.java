package com.example.demo.domain.serviceImpl.Mascotas;

import com.example.demo.domain.entity.mascotas.TiposMascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TiposMascotaRepository extends JpaRepository<TiposMascota, Integer> {
}
