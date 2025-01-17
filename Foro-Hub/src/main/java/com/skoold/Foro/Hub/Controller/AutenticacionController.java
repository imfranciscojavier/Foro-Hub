package com.skoold.Foro.Hub.Controller;

import com.skoold.Foro.Hub.domain.usuario.DatosAutenticacionUsuario;
import com.skoold.Foro.Hub.domain.usuario.Usuario;
import com.skoold.Foro.Hub.infra.security.DatosJWTToken;
import com.skoold.Foro.Hub.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity autenticacionUsuario(@RequestBody @Valid DatosAutenticacionUsuario datosAutenticacionUsuario){
        var authoken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login(),
                datosAutenticacionUsuario.clave());
        var authenticacion = authenticationManager.authenticate(authoken);
        var JWTtoken = tokenService.generarToken((Usuario) authenticacion.getPrincipal());
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }

}
