package com.ebac.modulo40.controller;

import com.ebac.modulo40.dto.Usuario;
import com.ebac.modulo40.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    UsuarioService usuarioService;

    @InjectMocks
    UsuarioController usuarioController;


    @Test
    void obtenerUsuarios() {
        int usuarios = 5;
        List<Usuario> usuariosListExpected = crearUsuarios(usuarios);

        when(usuarioService.obtenerUsuarios()).thenReturn(usuariosListExpected);

        ResponseWrapper<List<Usuario>> usuariosListActual = usuarioController.obtenerUsuarios();

        assertEquals(usuariosListExpected, usuariosListActual.getResponseEntity().getBody());
        assertEquals(5, usuariosListExpected.size());

    }

    @Test
    void obtenerUsuatiosCuandoNoExisten(){
        when(usuarioService.obtenerUsuarios()).thenReturn(List.of());

        ResponseWrapper<List<Usuario>> usuarioListActual = usuarioController.obtenerUsuarios();


        verify(usuarioService, times(1)).obtenerUsuarios();
        assert usuarioListActual.getResponseEntity().getBody() != null;
        assertEquals(0, usuarioListActual.getResponseEntity().getBody().size());
    }

    @Test
    void obtenerUsuarioPorId() {
        long id_usuario = 1;
        Optional<Usuario> usuarioExpected = Optional.of(crearUsuarios(1).get(0));

        when(usuarioService.obtenerUsuarioPorId(id_usuario)).thenReturn(usuarioExpected);

        ResponseWrapper<Usuario> usuarioResponseWrapper = usuarioController.obtenerUsuarioPorId(1);
        Usuario usuarioActual = usuarioResponseWrapper.getResponseEntity().getBody();

        assertTrue(usuarioResponseWrapper.isSuccess());
        assertNotNull(usuarioActual);
        assertEquals("Nombre 1", usuarioActual.getNombre());
    }

    @Test
    void obtenerUsuarioPorIdCuandoNoExiste(){
        long id_usuario = 1;
        when(usuarioService.obtenerUsuarioPorId(id_usuario)).thenReturn(Optional.empty());
        ResponseWrapper<Usuario> usuarioResponseEntity = usuarioController.obtenerUsuarioPorId(id_usuario);
        Usuario usuarioActual = usuarioResponseEntity.getResponseEntity().getBody();

        assertEquals(404, usuarioResponseEntity.getResponseEntity().getStatusCode().value());
        assertTrue(Objects.isNull(usuarioActual));
    }

    @Test
    void crearUsuario() throws Exception {
        Usuario usuarioExpected = crearUsuarios(1).get(0);

        when(usuarioService.crearUsuario(usuarioExpected)).thenReturn(usuarioExpected);

        ResponseWrapper<Usuario> usuarioResponseEntity = usuarioController.crearUsuario(usuarioExpected);
        Usuario usuarioActual = usuarioResponseEntity.getResponseEntity().getBody();

        assertEquals(201, usuarioResponseEntity.getResponseEntity().getStatusCode().value());
        assertTrue(Objects.isNull(usuarioActual));
    }

    @Test
    void actualizarUsuario() {
        long id_usuario = 5;
        String nombreActualizado = "Juan";
        int edadActualizada = 29;

        Usuario usuarioAntiguo = new Usuario();
        usuarioAntiguo.setId_usuario(id_usuario);
        usuarioAntiguo.setNombre(nombreActualizado);
        usuarioAntiguo.setEdad(edadActualizada);

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre(nombreActualizado);
        usuarioActualizado.setEdad(edadActualizada);

        when(usuarioService.obtenerUsuarioPorId(id_usuario)).thenReturn(Optional.of(usuarioAntiguo));
        doNothing().when(usuarioService).actualizarUsuario(usuarioActualizado);

        ResponseWrapper<Usuario> usuarioResponseEntity = usuarioController.actualizarUsuario(id_usuario, usuarioActualizado);
        Usuario usuarioActual = usuarioResponseEntity.getResponseEntity().getBody();

        assertEquals(200, usuarioResponseEntity.getResponseEntity().getStatusCode().value());
        assertNotNull(usuarioActual);
        assertEquals(id_usuario, usuarioActual.getId_usuario());
        assertEquals(nombreActualizado, usuarioActual.getNombre());
        assertEquals(edadActualizada, usuarioActual.getEdad());
    }

    @Test
    void eliminarUsuario() {
        long id_usuario = 1;

        doNothing().when(usuarioService).eliminarUsuario(id_usuario);
        ResponseWrapper<Usuario> responseEntity = usuarioController.eliminarUsuario(id_usuario);
        assertEquals(204, responseEntity.getResponseEntity().getStatusCode().value());
        verify(usuarioService, atLeastOnce()).eliminarUsuario(id_usuario);

    }

    private List<Usuario> crearUsuarios(int elementos){
        return IntStream.range(1, elementos+1)
                .mapToObj(i ->{
                    Usuario usuario = new Usuario();
                    usuario.setId_usuario(i);
                    usuario.setNombre("Nombre " + i);
                    usuario.setEdad(15 + i);
                    return usuario;
        }).collect(Collectors.toList());
    }

}