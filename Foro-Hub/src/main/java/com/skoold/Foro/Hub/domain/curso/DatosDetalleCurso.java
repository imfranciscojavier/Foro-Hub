package com.skoold.Foro.Hub.domain.curso;

public record DatosDetalleCurso(
        Long id,
        String nombre,
        Categoria categoria
) {
    public DatosDetalleCurso(Curso datos){
        this(datos.getId(), datos.getNombre(), datos.getCategoria());
    }
}
