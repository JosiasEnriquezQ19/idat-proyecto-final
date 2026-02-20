package idat.api.pe.idat_proyecto_final.service.impl;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import idat.api.pe.idat_proyecto_final.model.Usuario;
import idat.api.pe.idat_proyecto_final.repository.UsuarioRepository;
import idat.api.pe.idat_proyecto_final.service.IUsuarioService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Usuario no encontrado: " + email));
    }
}
