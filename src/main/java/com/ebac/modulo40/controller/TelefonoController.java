package com.ebac.modulo40.controller;

import com.ebac.modulo40.dto.Telefono;
import com.ebac.modulo40.service.TelefonoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class TelefonoController {

    @Autowired
    TelefonoService telefonoService;

    @GetMapping("/telefonos")
    public ResponseWrapper<List<Telefono>> obtenerTelefonos(){

        List<Telefono> listTelefonos = telefonoService.obtenerTelefonos();
        ResponseEntity<List<Telefono>> responseEntity = ResponseEntity.ok(listTelefonos);
        log.info("Se ejecutó el proceso de regresar todos los teléfonos");
        return new ResponseWrapper<>(true, "Listado de telefonos", responseEntity);
    }

    @GetMapping("/telefonos/{id}")
    public ResponseWrapper<Telefono> obtenerTelefonoPorId(@PathVariable long id){
        System.out.println("Id recibido: " + id);
        Optional<Telefono> telefonoOptional = telefonoService.obtenerTelefonoPorId(id);

        if(telefonoOptional.isEmpty()){
            ResponseEntity<Telefono> responseEntity = telefonoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
            log.info("El telefono con id: {} fue encontrado", id);
            return new ResponseWrapper<>(false, "telefono no encontrado", responseEntity);
        }else{
            log.error("El telefono con id: {} no fue encontrado", id);
            ResponseEntity<Telefono> responseEntity = telefonoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
            return new ResponseWrapper<>(true, "Telefono encontrado", responseEntity);
        }
    }

    @PostMapping("/telefonos")
    public ResponseWrapper<Telefono> crearTelefono(@RequestBody Telefono telefono) throws Exception {
        try{
            log.info("Telefono recibido: {}", telefono);
            telefonoService.crearTelefono(telefono);
            ResponseEntity<Telefono> responseEntity = ResponseEntity.created(new URI("http://localhost/telefonos")).build();
            log.info("El teléfono fue creado");
            return new ResponseWrapper<>(true, "Telefono creado", responseEntity);
        }catch (Exception e){
            ResponseEntity<Telefono> responseEntity = ResponseEntity.badRequest().build();
            log.error("El teléfono no fue creado", e);
            return new ResponseWrapper<>(false, "no fue posible crear el teléfono", responseEntity);
        }
    }

    @PutMapping("telefonos/{id}")
    public ResponseWrapper<Telefono> actualizarTelefono(@PathVariable Long id, @RequestBody Telefono telefonoActualizado){

        Optional<Telefono> telefonoOptional = telefonoService.obtenerTelefonoPorId(id);

        if(telefonoOptional.isPresent()){
            telefonoActualizado.setIdTelefono(telefonoOptional.get().getIdTelefono());
            telefonoService.actualizarTelefono(telefonoActualizado);
            ResponseEntity<Telefono> responseEntity = ResponseEntity.ok(telefonoActualizado);
            log.info("El teléfono {} fue actualizado", telefonoActualizado.getIdTelefono());
            return new ResponseWrapper<>(true, "telefono actualizado", responseEntity);
        }else{
            log.error("El teléfono {} no fue encontrado", telefonoActualizado.getIdTelefono());
            ResponseEntity<Telefono> responseEntity = ResponseEntity.notFound().build();
            return new ResponseWrapper<>(false, "no se encontró el teléfono con ese id", responseEntity);
        }
    }

    @DeleteMapping("/telefonos/{id}")
    public ResponseWrapper<Telefono> eliminarTelefono(@PathVariable Long id) {
        telefonoService.eliminarTelefono(id);
        ResponseEntity<Telefono> responseEntity = ResponseEntity.noContent().build();
        log.info("Proceso de eliminación ejecutado");
        return new ResponseWrapper<>(true, "telefono eliminado", responseEntity);
    }
}
