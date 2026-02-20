package idat.api.pe.idat_proyecto_final.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import idat.api.pe.idat_proyecto_final.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);
}
