package com.example.demo.infrastructure.web.controller;

import com.example.demo.application.service.MascotasServices.MascotaService;
import com.example.demo.application.service.PaseadoresServices.PaseadorService;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.MascotaDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.PaseadoresDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.MascotaProjection;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.PaseadorProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/mascota")
@RequiredArgsConstructor
public class MascotaController {

    // Inicializamos las clases para trabajar con este constructor con "final"
    private final MascotaService mascotaService;

    // Endpoint para registrar Mascotas
    @PostMapping("/register")
    public ResponseEntity<MascotaDTO> registrarMascota(@RequestBody MascotaDTO mascotaDTO){
        MascotaDTO mascotaRegistrada = mascotaService.registrarMascota(mascotaDTO);
        return ResponseEntity.ok(mascotaRegistrada);
    }

    // Endpoint para editar Mascotas
    @PutMapping("/edit/{id}")
    public ResponseEntity<MascotaDTO> editarMascota(
            @PathVariable("id") Integer id,
            @RequestBody MascotaDTO mascotaDTO
    ) {
        MascotaDTO mascotaActualizada = mascotaService.editarMascota(id, mascotaDTO);
        return ResponseEntity.ok(mascotaActualizada);
    }

    // Endpoint para listar a todos las mascotas usando proyecciones
    @GetMapping("/findAllMascotas")
    public List<MascotaProjection> findAllMascotas(){
        return mascotaService.findBy(); // Obtiene todos las mascotas con la proyección
    }

    // Endpoint para listar una mascota por su ID
    @GetMapping("/findMascotaById/{idMascota}")
    public Optional<MascotaProjection> findMascotaById(@PathVariable Integer idMascota){
        return mascotaService.findMascotaByIdMascota(idMascota);
    }

    // Endpoint para eliminar una mascota por su ID
    @DeleteMapping("/delete/{idMascota}")
    public ResponseEntity<String> deleteMascotaById(@PathVariable("idMascota") Integer idMascota) {
        boolean deleted = mascotaService.deleteMascotaById(idMascota);

        if (deleted) {
            return ResponseEntity.ok("Mascota eliminada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar la mascota a eliminar");
        }
    }
}
