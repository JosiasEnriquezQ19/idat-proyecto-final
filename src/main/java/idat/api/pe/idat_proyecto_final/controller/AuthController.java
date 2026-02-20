package idat.api.pe.idat_proyecto_final.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import idat.api.pe.idat_proyecto_final.dto.GenericResponse;
import idat.api.pe.idat_proyecto_final.dto.LoginRequest;
import idat.api.pe.idat_proyecto_final.security.IJwtService;
import idat.api.pe.idat_proyecto_final.service.impl.DetalleUsuarioService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final DetalleUsuarioService detalleUsuarioService;
    private final IJwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<String>> login(
            @RequestBody LoginRequest request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));

        UserDetails userDetails =
                detalleUsuarioService.loadUserByUsername(request.getEmail());

        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(
                GenericResponse.<String>builder()
                        .response(token)
                        .build());
    }
}
