package com.skoold.Foro.Hub.domain.perfil;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroPerfil(
        @NotBlank String nombre
) {
}
