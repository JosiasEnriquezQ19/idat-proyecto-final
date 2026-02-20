package idat.api.pe.idat_proyecto_final.service;

import idat.api.pe.idat_proyecto_final.model.Usuario;

public interface IUsuarioService {

    Usuario getUsuarioByEmail(String email);
}
