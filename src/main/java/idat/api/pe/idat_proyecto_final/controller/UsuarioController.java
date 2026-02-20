package idat.api.pe.idat_proyecto_final.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idat.api.pe.idat_proyecto_final.dto.GenericResponse;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {
    
    @GetMapping("/test")
    public ResponseEntity<GenericResponse<String>> testEndpoint(Authentication authentication) {
        String message = "¡Token válido! Usuario autenticado: " + authentication.getName();
        return ResponseEntity.ok(
            GenericResponse.<String>builder()
                .response(message)
                .build()
        );
    }
}
