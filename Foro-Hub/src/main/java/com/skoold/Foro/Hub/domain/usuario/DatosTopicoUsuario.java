package com.skoold.Foro.Hub.domain.usuario;

import com.skoold.Foro.Hub.domain.perfil.Perfil;

public record DatosTopicoUsuario(
        Long id,
        String nombre,
        String correoElectronico,
        Perfil perfil
) {
    public DatosTopicoUsuario(Usuario datos){
        this(datos.getId(), datos.getNombre(), datos.getCorreo_electronico(), datos.getPerfil());
    }
}
