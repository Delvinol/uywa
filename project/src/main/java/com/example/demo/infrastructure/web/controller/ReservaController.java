package com.example.demo.infrastructure.web.controller;

import com.example.demo.application.service.PaseosServices.ReservaService;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.ReservaDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.TransaccionesDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.ReservaProjection;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.TransaccionProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reserva")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    // Endpoint para registrar Reservas
    @PostMapping("/register")
    public ResponseEntity<ReservaDTO> registrarReserva(@RequestBody ReservaDTO reservaDTO){
        ReservaDTO reservaRegistrada = reservaService.registrarReservas(reservaDTO);
        return ResponseEntity.ok(reservaRegistrada);
    }

    // Endpoint para editar Transacciones
    @PutMapping("/edit/{id}")
    public ResponseEntity<ReservaDTO> editarReserva(
            @PathVariable("id") Integer id,
            @RequestBody ReservaDTO reservaDTO
    ){
        ReservaDTO reservaActualizada = reservaService.editarReservas(id, reservaDTO);
        return ResponseEntity.ok(reservaActualizada);
    }

    // Endpoint para listar a todos las transacciones usando proyecciones
    @GetMapping("/findAllReservas")
    public List<ReservaProjection> findAllReserva(){
        return reservaService.findBy(); // Obtiene todos las transacciones con la proyección
    }

    // Endpoint para listar una reserva por un ID
    @GetMapping("/findReservaById/{idReserva}")
    public Optional<ReservaProjection> findReservaById(@PathVariable Integer idReserva){
        return reservaService.findReservaByIdReserva(idReserva);
    }

    // Endpoint para eliminar una reserva por su ID
    @DeleteMapping("/delete/{idReserva}")
    public ResponseEntity<String> deleteReservaById(@PathVariable("idReserva") Integer idReserva){
        boolean deleted = reservaService.deleteReservaById(idReserva);

        if (deleted){
            return ResponseEntity.ok("Reserva eliminada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar la reserva a eliminar");
        }
    }

}
