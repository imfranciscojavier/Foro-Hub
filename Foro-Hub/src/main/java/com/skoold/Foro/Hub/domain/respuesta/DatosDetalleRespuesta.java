package com.skoold.Foro.Hub.domain.respuesta;

import com.skoold.Foro.Hub.domain.topico.DatosDetalleTopico;
import com.skoold.Foro.Hub.domain.usuario.DatosTopicoUsuario;

import java.time.LocalDateTime;

public record DatosDetalleRespuesta(
        Long id,
        String mensaje,
        DatosDetalleTopico idTopico,
        LocalDateTime fecha,
        DatosTopicoUsuario idAutor,
        boolean solucion

) {
    public DatosDetalleRespuesta(Respuesta datos){
        this(
                datos.getId(),
                datos.getMensaje(),
                new DatosDetalleTopico(
                        datos.getTopico().getId(),
                        datos.getTopico().getTitulo(),
                        datos.getTopico().getMensaje(),
                        datos.getTopico().getFecha_creacion(),
                        datos.getTopico().getStatus(),
                        new DatosTopicoUsuario(
                                datos.getTopico().getAutor().getId(),
                                datos.getTopico().getAutor().getNombre(),
                                datos.getTopico().getAutor().getCorreo_electronico(),
                                datos.getTopico().getAutor().getPerfil()
                        ),
                        datos.getTopico().getCurso()
                ),
                datos.getFecha_creacion(),
                new DatosTopicoUsuario(
                        datos.getUsuario().getId(),
                        datos.getUsuario().getNombre(),
                        datos.getUsuario().getCorreo_electronico(),
                        datos.getUsuario().getPerfil()
                ),
                datos.isSolucion()
        );
    }
}