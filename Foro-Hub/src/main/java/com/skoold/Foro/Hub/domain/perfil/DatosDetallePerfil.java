package com.skoold.Foro.Hub.domain.perfil;

public record DatosDetallePerfil(
        Long id,
        String nombre
) {
    public DatosDetallePerfil(Perfil perfil) {
        this(perfil.getId(), perfil.getNombre());
    }
}
