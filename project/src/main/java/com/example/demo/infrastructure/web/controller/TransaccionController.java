package com.example.demo.infrastructure.web.controller;

import com.example.demo.application.service.TransaccionesServices.TransaccionService;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.PropietarioDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.TransaccionesDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.PropietarioProjection;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.TransaccionProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/transaccion")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransaccionService transaccionService;

    // Endpoint para registrar Transacciones
    @PostMapping("/register")
    public ResponseEntity<TransaccionesDTO> registrarTransaccion(@RequestBody TransaccionesDTO transaccionesDTO){
        TransaccionesDTO transaccionRegistrada = transaccionService.registrarTransacciones(transaccionesDTO);
        return ResponseEntity.ok(transaccionRegistrada);
    }

    // Endpoint para editar Transacciones
    @PutMapping("/edit/{id}")
    public ResponseEntity<TransaccionesDTO> editarTransaccion(
            @PathVariable("id") Integer id,
            @RequestBody TransaccionesDTO transaccionesDTO
    ){
        TransaccionesDTO transaccionActualizada = transaccionService.editarTransacciones(id, transaccionesDTO);
        return ResponseEntity.ok(transaccionActualizada);
    }

    // Endpoint para listar a todos las transacciones usando proyecciones
    @GetMapping("/findAllTransacciones")
    public List<TransaccionProjection> findAllTransacciones(){
        return transaccionService.findBy(); // Obtiene todos las transacciones con la proyección
    }

    // Endpoint para listar una transaccion por un ID
    @GetMapping("/findTransaccionById/{idTransaccion}")
    public Optional<TransaccionProjection> findTransaccionById(@PathVariable Integer idTransaccion){
        return transaccionService.findTransaccionByIdTransaccionr(idTransaccion);
    }

    // Endpoint para eliminar una transaccion por su ID
    @DeleteMapping("/delete/{idTransaccion}")
    public ResponseEntity<String> deleteTransaccionById(@PathVariable("idTransaccion") Integer idTransaccion){
        boolean deleted = transaccionService.deleteTransaccionById(idTransaccion);

        if (deleted){
            return ResponseEntity.ok("Transacción eliminada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar la transacción a eliminar");
        }
    }
}
