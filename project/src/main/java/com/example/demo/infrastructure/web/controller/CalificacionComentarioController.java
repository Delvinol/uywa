package com.example.demo.infrastructure.web.controller;

import com.example.demo.application.service.PaseosServices.CalificacionComentarioService;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.CalificacionesComentariosDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.PaseadoresDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.CalificacionesComentariosProjection;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.PaseadorProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/calificacionComentario")
@RequiredArgsConstructor
public class CalificacionComentarioController {

    private final CalificacionComentarioService calificacionComentarioService;

    // Endpoint para registrar la calificaciony comentario
    @PostMapping("/register")
    public ResponseEntity<CalificacionesComentariosDTO> registrarCalificacionComentario(@RequestBody CalificacionesComentariosDTO calificacionesComentariosDTO){
        CalificacionesComentariosDTO calificacionComentarioRegistrado = calificacionComentarioService.registrarCalificacionesComentarios(calificacionesComentariosDTO);
        return ResponseEntity.ok(calificacionComentarioRegistrado);
    }

    // Endpoint para editar Paseadores
    @PutMapping("/edit/{id}")
    public ResponseEntity<CalificacionesComentariosDTO> editarCalificacionComentario(
            @PathVariable("id") Integer id,
            @RequestBody CalificacionesComentariosDTO calificacionesComentariosDTO
    ) {
        CalificacionesComentariosDTO calificacionComentarioActualizado = calificacionComentarioService.editarCalificacionesComentarios(id, calificacionesComentariosDTO);
        return ResponseEntity.ok(calificacionComentarioActualizado);
    }

    // Endpoint para listar a todos los paseadores usando proyecciones
    @GetMapping("/findAllCalificacionComentario")
    public List<CalificacionesComentariosProjection> findAllCalificacionesComentarios(){
        return calificacionComentarioService.findBy(); // Obtiene todos los paseadores con la proyección
    }

    // Endpoint para listar un paseador por su ID
    @GetMapping("/findCalificacionComentarioById/{idCalificacioncomentario}")
    public Optional<CalificacionesComentariosProjection> findCalificacionComentarioById(@PathVariable Integer idCalificacioncomentario){
        return calificacionComentarioService.findCalificacionComentarioByIdCalificaionComentario(idCalificacioncomentario);
    }

    // Endpoint para eliminar una calificacion y comentario por su ID
    @DeleteMapping("/delete/{idCalificacionComentario}")
    public ResponseEntity<String> deleteCalificacionComentariorById(@PathVariable("idCalificacionComentario") Integer idCalificacionComentario) {
        boolean deleted = calificacionComentarioService.deleteCalificacionComentarioById(idCalificacionComentario);

        if (deleted) {
            return ResponseEntity.ok("Locacion y comentario  eliminados exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar la calificacion y comentario a eliminar");
        }
    }
}
