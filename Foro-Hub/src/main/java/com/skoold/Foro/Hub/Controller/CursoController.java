package com.skoold.Foro.Hub.Controller;

import com.skoold.Foro.Hub.domain.curso.Curso;
import com.skoold.Foro.Hub.domain.curso.CursoRepository;
import com.skoold.Foro.Hub.domain.curso.DatosDetalleCurso;
import com.skoold.Foro.Hub.domain.curso.DatosRegistroCurso;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroCurso datos,
                                    UriComponentsBuilder uriComponentsBuilder){
        var curso = new Curso(datos);
        cursoRepository.save(curso);

        var datosDetalleCurso = new DatosDetalleCurso(curso);
        var uri = uriComponentsBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();
        // forma de crar sin uri = return ResponseEntity.status(HttpStatus.CREATED).body(datosDetalleCurso);
        return ResponseEntity.created(uri).body(datosDetalleCurso);
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleCurso>> listar(@PageableDefault(page = 0, size = 10)Pageable paginacion){
        var page = cursoRepository.findAll(paginacion).map(DatosDetalleCurso::new);
        return ResponseEntity.ok(page);
    }
}
