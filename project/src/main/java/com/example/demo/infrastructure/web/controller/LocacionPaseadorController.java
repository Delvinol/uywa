package com.example.demo.infrastructure.web.controller;

import com.example.demo.application.service.PaseadoresServices.LocacionPaseadorService;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.LocacionPaseadorDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.LocacionPaseadorProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/LocacionPaseador")
@RequiredArgsConstructor
public class LocacionPaseadorController {

    private final LocacionPaseadorService locacionPaseadorService;

    // Endpoint para registrar Locación de Paseadores
    @PostMapping("/register")
    public ResponseEntity<LocacionPaseadorDTO> registrarLocacionPaseador(@RequestBody LocacionPaseadorDTO locacionPaseadorDTO){
        LocacionPaseadorDTO locacionPaseadorRegistrado = locacionPaseadorService.registrarLocacionPaseador(locacionPaseadorDTO);
        return ResponseEntity.ok(locacionPaseadorRegistrado);
    }

    // Endpoint para editar Locación de Paseadores
    @PutMapping("/edit/{id}")
    public ResponseEntity<LocacionPaseadorDTO> editarLocacionPaseador(
            @PathVariable("id") Integer id,
            @RequestBody LocacionPaseadorDTO locacionPaseadorDTO
    ){
        LocacionPaseadorDTO locacionPaseadorActualizado = locacionPaseadorService.editarLocacionPaseador(id, locacionPaseadorDTO);
        return ResponseEntity.ok(locacionPaseadorActualizado);
    }

    // Endpoint para listar todas las locaciones de los paseadores usando proyecciones
    @GetMapping("/findAllLocacionPaseadores")
    public List<LocacionPaseadorProjection> findAllLocacionPaseador(){
        return locacionPaseadorService.findBy();
    }

    // Endpoint para listar una locación de paseador por su ID
    @GetMapping("/findLocacionPaseador/{idLocacionPaseador}")
    public Optional<LocacionPaseadorProjection> findLocacionPaseadorById(@PathVariable Integer idLocacionPaseador){
        return locacionPaseadorService.findLocacionPaseadorByIdLocacionPaseador(idLocacionPaseador);
    }

    // Endpoint para eliminar una locación de paseador por su ID
    @DeleteMapping("/delete/{idLocacionPaseador}")
    public ResponseEntity<String> deleteLocacionPaseadorById(@PathVariable("idLocacionPaseador") Integer idLocacionPaseador){
        boolean deleted = locacionPaseadorService.deleteLocacionPaseadorById(idLocacionPaseador);

        if (deleted) {
            return ResponseEntity.ok("Locacion de paseador eliminada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar la locación del paseador a eliminar");
        }

    }

}
