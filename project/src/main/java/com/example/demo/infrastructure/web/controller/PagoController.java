package com.example.demo.infrastructure.web.controller;

import com.example.demo.application.exceptions.PaseosExceptions.NotFound.SaldoInsuficienteException;
import com.example.demo.application.exceptions.TransaccionesExceptions.NotFound.EstadoTransaccionNotFoundException;
import com.example.demo.application.exceptions.TransaccionesExceptions.NotFound.TipoTransaccionNotFoundException;
import com.example.demo.application.service.PaseosServices.PagoService;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.PagoRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping("/procesar")
    public ResponseEntity<String> procesarPago(@RequestBody @Valid PagoRequestDTO pagoRequest) {
        try {
            pagoService.procesarPago(pagoRequest);
            return ResponseEntity.ok("Pago procesado exitosamente");
        } catch (SaldoInsuficienteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo insuficiente para realizar el pago");
        } catch (EntityNotFoundException | EstadoTransaccionNotFoundException | TipoTransaccionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró algún elemento necesario para el pago");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al procesar el pago");
        }
    }
}
