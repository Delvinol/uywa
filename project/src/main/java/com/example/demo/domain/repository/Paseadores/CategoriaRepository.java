package com.example.demo.domain.repository.Paseadores;

import com.example.demo.domain.entity.paseadores.Categorias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categorias, Integer> {
}
