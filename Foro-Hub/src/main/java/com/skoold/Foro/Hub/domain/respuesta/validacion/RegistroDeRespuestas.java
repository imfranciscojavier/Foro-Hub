package com.skoold.Foro.Hub.domain.respuesta.validacion;

import com.skoold.Foro.Hub.domain.respuesta.DatosDetalleRespuesta;
import com.skoold.Foro.Hub.domain.respuesta.DatosRegistroRespuesta;
import com.skoold.Foro.Hub.domain.respuesta.Respuesta;
import com.skoold.Foro.Hub.domain.respuesta.RespuestaRepository;
import com.skoold.Foro.Hub.domain.topico.Topico;
import com.skoold.Foro.Hub.domain.topico.TopicoRepository;
import com.skoold.Foro.Hub.domain.usuario.Usuario;
import com.skoold.Foro.Hub.domain.usuario.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistroDeRespuestas {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    public DatosDetalleRespuesta registrar(com.skoold.Foro.Hub.domain.respuesta.@Valid DatosRegistroRespuesta datos){
        boolean existeDuplicado = respuestaRepository.existsByMensaje(datos.mensaje());
        if(existeDuplicado){
            throw new IllegalArgumentException("Ya existe una respuesta con el mismo mensaje.");
        }

        Topico topico = topicoRepository.findById(datos.idTopico())
                .orElseThrow(() -> new EntityNotFoundException("Topico no encontrado"));

        Usuario usuario = usuarioRepository.findById(datos.idAutor())
                .orElseThrow(() -> new EntityNotFoundException("Usuario No encontrado"));

        Respuesta respuesta = new Respuesta(datos, topico, usuario);
        respuestaRepository.save(respuesta);
        return new DatosDetalleRespuesta(respuesta);


    }

}