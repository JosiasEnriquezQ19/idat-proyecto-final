package idat.api.pe.idat_proyecto_final.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import idat.api.pe.idat_proyecto_final.service.impl.DetalleUsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final IJwtService jwtService;
    private final DetalleUsuarioService detalleUsuarioService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        
        log.info("=== JWT Filter ===");
        log.info("Request URI: {}", request.getRequestURI());
        log.info("Authorization Header: {}", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("No Authorization header or doesn't start with Bearer");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = authHeader.substring(7);
            log.info("Token extraído (primeros 20 chars): {}...", token.substring(0, Math.min(20, token.length())));
            
            final String username = jwtService.extractUsername(token);
            log.info("Username extraído del token: {}", username);

            if (username != null
                    && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails =
                        detalleUsuarioService.loadUserByUsername(username);
                log.info("UserDetails cargado para: {}", username);
                
                if (jwtService.isTokenValid(token, userDetails)) {
                    log.info("Token VÁLIDO - Autenticando usuario");
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request));
                    SecurityContextHolder.getContext()
                            .setAuthentication(authToken);
                    log.info("Usuario autenticado exitosamente");
                } else {
                    log.error("Token INVÁLIDO o expirado");
                }
            }
        } catch (Exception e) {
            log.error("Error procesando JWT: {}", e.getMessage(), e);
        }
        
        filterChain.doFilter(request, response);
    }
}
