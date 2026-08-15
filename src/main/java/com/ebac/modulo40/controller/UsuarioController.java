package com.ebac.modulo40.controller;

import com.ebac.modulo40.dto.Usuario;
import com.ebac.modulo40.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public ResponseWrapper<List<Usuario>> obtenerUsuarios(){
        List<Usuario> usuarioList = usuarioService.obtenerUsuarios();
        ResponseEntity<List<Usuario>> responseEntity = ResponseEntity.ok(usuarioList);
        log.info("Se ejecutó el proceso de regresar todos los usuarios");
        return new ResponseWrapper<>(true, "Listado de usuarios", responseEntity);

    }

    @GetMapping("/usuarios/{id}")
    public ResponseWrapper<Usuario> obtenerUsuarioPorId(@PathVariable long id){
        System.out.println("Id recibido: " + id);
        Optional<Usuario> usuarioOptional = usuarioService.obtenerUsuarioPorId(id);
        ResponseEntity<Usuario> responseEntity = usuarioOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        if(usuarioOptional.isEmpty()){
            log.info("El usuario con id: {} fue encontrado", id);
            return new ResponseWrapper<>(false, "Usuario no encontrado", responseEntity);
        }else{
            log.error("El usuario con id: {} no fue encontrado", id);
            return new ResponseWrapper<>(true, "Información del usuario: " + id, responseEntity);
        }

    }

    @PostMapping("/usuarios")
    public ResponseWrapper<Usuario> crearUsuario(@RequestBody Usuario usuario) throws URISyntaxException {
        try{
            System.out.println("Usuario recibido: " + usuario);
            Usuario usuarioCreado = usuarioService.crearUsuario(usuario);
            ResponseEntity<Usuario> responseEntity = ResponseEntity.created(new URI("http://localhost/usuarios")).build();
            log.info("El usuario fue creado con id: {} fue creado", usuario.getId_usuario());
            return new ResponseWrapper<>(true, "Usuario creado existosamente", responseEntity);
        } catch (Exception e){
            log.error("El usaurio con id: {} no fue creado", usuario.getId_usuario());
            ResponseEntity<Usuario> responseEntity = ResponseEntity.badRequest().build();
            return new ResponseWrapper<>(false, e.getMessage(), responseEntity);
        }
    }

    @PutMapping("usuarios/{id}")
    public ResponseWrapper<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado){

        Optional<Usuario>  usuarioOptional = usuarioService.obtenerUsuarioPorId(id);

        if(usuarioOptional.isPresent()){
            usuarioActualizado.setId_usuario(usuarioOptional.get().getId_usuario());
            usuarioService.actualizarUsuario(usuarioActualizado);
            ResponseEntity<Usuario> responseEntity = ResponseEntity.ok(usuarioActualizado);
            log.info("El usuario {} fue actualizado", usuarioActualizado.getId_usuario());
            return new ResponseWrapper<>(true, "Usuario actualizado correctamente", responseEntity);
        }else{
            ResponseEntity<Usuario> responseEntity = ResponseEntity.notFound().build();
            log.error("El usuario {} no fue actualizado", usuarioActualizado.getId_usuario());
            return new ResponseWrapper<>(false, "El usuario indicado no existe", responseEntity);
        }
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseWrapper<Usuario> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        ResponseEntity<Usuario> responseEntity = ResponseEntity.noContent().build();
        log.info("Proceso de eliminación ejecutado");
        return new ResponseWrapper<>(true, "Usuario eliminado correctamente", responseEntity);
    }

}
