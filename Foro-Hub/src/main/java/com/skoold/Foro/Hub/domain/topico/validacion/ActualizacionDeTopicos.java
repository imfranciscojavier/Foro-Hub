package com.skoold.Foro.Hub.domain.topico.validacion;

import com.skoold.Foro.Hub.domain.curso.CursoRepository;
import com.skoold.Foro.Hub.domain.topico.DatosActualizarTopico;
import com.skoold.Foro.Hub.domain.topico.DatosDetalleTopico;
import com.skoold.Foro.Hub.domain.topico.TopicoRepository;
import com.skoold.Foro.Hub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActualizacionDeTopicos {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;


    @Transactional
    public DatosDetalleTopico actualizar(Long id, DatosActualizarTopico datos){
        var topico = topicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró un tópico con el id proporcionado"));
        if(datos.titulo() != null){
            topico.setTitulo(datos.titulo());
        }
        if(datos.mensaje() != null){
            topico.setMensaje(datos.mensaje());
        }
        if(datos.status() != null){
            topico.setStatus(datos.status());
        }

        if(datos.autor() != null){
            var autor = usuarioRepository.findById(datos.autor()).orElseThrow(()-> new RuntimeException("Autor no encontrado"));
            topico.setAutor(autor);
        }
        if(datos.curso() != null){
            var curso = cursoRepository.findById(datos.curso()).orElseThrow(
                    () -> new RuntimeException("Curso no encontrado")
            );
            topico.setCurso(curso);
        }

        return new DatosDetalleTopico(topico);

    }

}