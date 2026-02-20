package idat.api.pe.idat_proyecto_final.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import idat.api.pe.idat_proyecto_final.model.usuario;

public interface usuarioRepository extends JpaRepository<usuario, Integer> {
    
}
