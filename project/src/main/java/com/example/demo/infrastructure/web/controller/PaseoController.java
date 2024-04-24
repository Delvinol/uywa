package com.example.demo.infrastructure.web.controller;

import com.example.demo.application.service.PaseosServices.PaseoService;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.PaseoDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.ReservaDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.PaseoProjection;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.ReservaProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/paseo")
@RequiredArgsConstructor
public class PaseoController {

    private final PaseoService paseoService;

    // Endpoint para registrar Paseos
    @PostMapping("/register")
    public ResponseEntity<PaseoDTO> registrarPaseo(@RequestBody PaseoDTO paseoDTO){
        PaseoDTO paseoRegistrado = paseoService.registrarPaseos(paseoDTO);
        return ResponseEntity.ok(paseoRegistrado);
    }

    // Endpoint para editar paseos
    @PutMapping("/edit/{id}")
    public ResponseEntity<PaseoDTO> editarPaseos(
            @PathVariable("id") Integer id,
            @RequestBody PaseoDTO paseoDTO
    ){
        PaseoDTO paseoActualizado = paseoService.editarPaseo(id, paseoDTO);
        return ResponseEntity.ok(paseoActualizado);
    }

    // Endpoint para listar a todos los paseos usando proyecciones
    @GetMapping("/findAllPaseos")
    public List<PaseoProjection> findAllPaseo(){
        return paseoService.findBy(); // Obtiene todos los paseos con la proyección
    }

    // Endpoint para listar un paseo por un ID
    @GetMapping("/findPaseoById/{idPaseo}")
    public Optional<PaseoProjection> findPaseoById(@PathVariable Integer idPaseo){
        return paseoService.findPaseoByIdPaseo(idPaseo);
    }

    // Endpoint para eliminar una reserva por su ID
    @DeleteMapping("/delete/{idPaseo}")
    public ResponseEntity<String> deletePaseoById(@PathVariable("idPaseo") Integer idPaseo){
        boolean deleted = paseoService.deletePaseoById(idPaseo);

        if (deleted){
            return ResponseEntity.ok("Paseo eliminado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar el paseo a eliminar");
        }
    }
}
