package com.ebac.modulo40.controller;

import com.ebac.modulo40.dto.Telefono;
import com.ebac.modulo40.dto.Usuario;
import com.ebac.modulo40.service.TelefonoService;
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
class TelefonoControllerTest {

    @Mock
    TelefonoService telefonoService;

    @InjectMocks
    TelefonoController telefonoController;


    @Test
    void obtenerTelefonos() {
        int telefonos = 5;
        List<Telefono> telefonosListExpected = crearTelefonos(telefonos);
        System.out.println("Test telefonos");
        when(telefonoService.obtenerTelefonos()).thenReturn(telefonosListExpected);

        ResponseWrapper<List<Telefono>> telefonosListActual = telefonoController.obtenerTelefonos();

        assertEquals(telefonosListExpected, telefonosListActual.getResponseEntity().getBody());
        assertEquals(5, telefonosListExpected.size());

    }

    @Test
    void obtenerTelefonosCuandoNoExisten(){
        when(telefonoService.obtenerTelefonos()).thenReturn(List.of());

        ResponseWrapper<List<Telefono>> telefonoListActual = telefonoController.obtenerTelefonos();


        verify(telefonoService, times(1)).obtenerTelefonos();
        assert telefonoListActual.getResponseEntity().getBody() != null;
        assertEquals(0, telefonoListActual.getResponseEntity().getBody().size());
    }

    @Test
    void obtenerTelefonoPorId() {
        long id_telefono = 1;
        Optional<Telefono> telefonoExpected = Optional.of(crearTelefonos(1).get(0));

        when(telefonoService.obtenerTelefonoPorId(id_telefono)).thenReturn(telefonoExpected);

        ResponseWrapper<Telefono> telefonoResponseWrapper = telefonoController.obtenerTelefonoPorId(1);
        Telefono telefonoActual = telefonoResponseWrapper.getResponseEntity().getBody();

        assertTrue(telefonoResponseWrapper.isSuccess());
        assertNotNull(telefonoActual);
        assertEquals("Casa 1", telefonoActual.getTipoTelefono());
    }

    @Test
    void obtenerTelefonoPorIdCuandoNoExiste(){
        long id_telefono = 1;
        when(telefonoService.obtenerTelefonoPorId(id_telefono)).thenReturn(Optional.empty());
        ResponseWrapper<Telefono> telefonoResponseEntity = telefonoController.obtenerTelefonoPorId(id_telefono);
        Telefono telefonoActual = telefonoResponseEntity.getResponseEntity().getBody();

        assertEquals(404, telefonoResponseEntity.getResponseEntity().getStatusCode().value());
        assertTrue(Objects.isNull(telefonoActual));
    }

    @Test
    void crearTelefono() throws Exception {
        Telefono telefonoExpected = crearTelefonos(1).get(0);

        when(telefonoService.crearTelefono(telefonoExpected)).thenReturn(telefonoExpected);

        ResponseWrapper<Telefono> telefonoResponseEntity = telefonoController.crearTelefono(telefonoExpected);
        Telefono telefonoActual = telefonoResponseEntity.getResponseEntity().getBody();

        assertEquals(201, telefonoResponseEntity.getResponseEntity().getStatusCode().value());
        assertTrue(Objects.isNull(telefonoActual));
    }

    @Test
    void actualizarTelefono() {
        long id_telefono = 5;
        String numeroActualizado = "56";
        String tipoActualizado = "Movil";
        int ladaActualizada = 58;
        int id_usuarioActualizado = 2;
        String nombreActualizado = "Pedro";
        int edadActualizada = 55;
        Usuario usuario = new Usuario();
        usuario.setId_usuario(id_usuarioActualizado);
        usuario.setNombre(nombreActualizado);
        usuario.setEdad(edadActualizada);

        Telefono telefonoAntiguo = new Telefono();
        telefonoAntiguo.setIdTelefono(id_telefono);
        telefonoAntiguo.setNumero(numeroActualizado);
        telefonoAntiguo.setTipoTelefono(tipoActualizado);
        telefonoAntiguo.setLada(ladaActualizada);
        telefonoAntiguo.setUsuario(usuario);

        Telefono telefonoActualizado = new Telefono();
        telefonoActualizado.setNumero(numeroActualizado);
        telefonoActualizado.setTipoTelefono(tipoActualizado);
        telefonoActualizado.setLada(ladaActualizada);
        telefonoActualizado.setUsuario(usuario);

        when(telefonoService.obtenerTelefonoPorId(id_telefono)).thenReturn(Optional.of(telefonoAntiguo));
        doNothing().when(telefonoService).actualizarTelefono(telefonoActualizado);

        ResponseWrapper<Telefono> telefonoResponseEntity = telefonoController.actualizarTelefono(id_telefono, telefonoActualizado);
        Telefono telefonoActual = telefonoResponseEntity.getResponseEntity().getBody();

        assertEquals(200, telefonoResponseEntity.getResponseEntity().getStatusCode().value());
        assertNotNull(telefonoActual);
        assertEquals(id_telefono, telefonoActual.getIdTelefono());
        assertEquals(numeroActualizado, telefonoActual.getNumero());
        assertEquals(tipoActualizado, telefonoActual.getTipoTelefono());
    }

    @Test
    void eliminarTelefono() {
        long id_telefono = 1;

        doNothing().when(telefonoService).eliminarTelefono(id_telefono);
        ResponseWrapper<Telefono> responseEntity = telefonoController.eliminarTelefono(id_telefono);
        assertEquals(204, responseEntity.getResponseEntity().getStatusCode().value());
        verify(telefonoService, atLeastOnce()).eliminarTelefono(id_telefono);

    }

    private List<Telefono> crearTelefonos(int elementos){
        return IntStream.range(1, elementos+1)
                .mapToObj(i ->{
                    Telefono telefono = new Telefono();
                    telefono.setIdTelefono(i);
                    telefono.setTipoTelefono("Casa " + i);
                    telefono.setLada(55);
                    telefono.setNumero("55");
                    Usuario usuario = new Usuario();
                    usuario.setId_usuario(i);
                    usuario.setNombre("Nombre " + i);
                    usuario.setEdad(18 + i);
                    telefono.setUsuario(usuario);
                    return telefono;
        }).collect(Collectors.toList());
    }

}