package com.example.demo.infrastructure.web.controller;

import com.example.demo.application.service.PaseadoresServices.PaseadorService;

import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.PaseadoresDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.PaseadorProjection;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/paseador")
@RequiredArgsConstructor
public class PaseadorController {

    // Inicializamos las clases para trabajar con este constructor con "final"
    private final PaseadorService paseadorService;

    // Endpoint para registrar Paseadores
    @PostMapping("/register")
    public ResponseEntity<PaseadoresDTO> registrarPaseador(
            @RequestBody PaseadoresDTO paseadoresDTO){
        PaseadoresDTO paseadorRegistrado = paseadorService.registrarPaseador(paseadoresDTO);
        return ResponseEntity.ok(paseadorRegistrado);
    }

    // Endpoint para editar Paseadores
    @PutMapping("/edit/{id}")
    public ResponseEntity<PaseadoresDTO> editarPaseador(
            @PathVariable("id") Integer id,
            @RequestBody PaseadoresDTO paseadoresDTO
    ) {
        PaseadoresDTO paseadorActualizado = paseadorService.editarPaseador(id, paseadoresDTO);
        return ResponseEntity.ok(paseadorActualizado);
    }

    // Endpoint para listar a todos los paseadores usando proyecciones
    @GetMapping("/findAllPaseadores")
    public List<PaseadorProjection> findAllPaseadores(){
        return paseadorService.findBy(); // Obtiene todos los paseadores con la proyección
    }

    // Endpoint para listar un paseador por su ID
    @GetMapping("/findPaseadorById/{idPaseador}")
    public Optional<PaseadorProjection> findPaseadorById(@PathVariable Integer idPaseador){
        return paseadorService.findPaseadoresByIdPaseador(idPaseador);
    }

    // Endpoint para eliminar un paseador por su ID
    @DeleteMapping("/delete/{idPaseador}")
    public ResponseEntity<String> deletePaseadorById(@PathVariable("idPaseador") Integer idPaseador) {
        boolean deleted = paseadorService.deletePaseadorById(idPaseador);

        if (deleted) {
            return ResponseEntity.ok("Paseador eliminado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar el paseador a eliminar");
        }
    }
}

    // Creacón de los ENDPOINTS
    // ENDPOINT PARA REGISTRAR NUEVOS PASEADORES

