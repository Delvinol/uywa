package com.example.demo.infrastructure.web.controller;

import com.example.demo.application.service.UsuariosServices.UserService;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.classBased.UserDTO;
import com.example.demo.infrastructure.web.projection.UsuarioProjections.interfaceBased.closed.UserProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ENDPOINT para listar usuarios haciendo uso de proyección
    @GetMapping("/findAllUsers")
    public List<UserProjection> findAllUsers(){
        return  userService.findBy(); // Obtiene todos los usuarios con la proyección
    }

    // ENDPOINT para listar a un Usuario por su ID
    @GetMapping("/findUserById/{id}")
    public Optional<UserProjection> findUserById(@PathVariable Integer id){
        return userService.findUserById(id);
    }

    // ENDPOINT para editar a un usuario por su ID
    @PutMapping("/editUser/{id}")
    public ResponseEntity<String> editUser(@PathVariable("id") Integer id, @RequestBody UserDTO request) {
        String token = userService.ediUserDetails(id, request);
        if (token != null) {
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    // ENDPOINT PARA ELIMINAR UN USUARIO POR SU ID
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable("userId") Integer userId) {
        // Llamada al servicio para eliminar el usuario
        boolean deleted = userService.deleteUserById(userId);

        if (deleted) {
            return ResponseEntity.ok("Usuario eliminado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar el usuario para eliminar");
        }
    }

}
