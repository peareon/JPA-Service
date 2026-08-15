package com.ebac.modulo40.service;

import com.ebac.modulo40.dto.Telefono;
import com.ebac.modulo40.dto.Usuario;
import com.ebac.modulo40.repository.TelefonoRepository;
import com.ebac.modulo40.repository.UsuarioRepository;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TelefonoService {

    @Autowired
    TelefonoRepository telefonoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    public Telefono crearTelefono(Telefono telefono) throws Exception{
        System.out.println("Telefono en service:" + telefono);
        System.out.println("user id: " + telefono.getUsuario().getId_usuario());
        Optional<Usuario> usuario = usuarioRepository.findById(telefono.getUsuario().getId_usuario());
        System.out.println("usuario tras query" + usuario);
        if(usuario.isPresent()){
            log.info("Usuario correlacionado");
        }else{
            log.error("Usuario no encontrado con id: %{}", telefono.getUsuario().getId_usuario());
            throw new Exception("Usuario no encontrado");
        }
        if(telefono.getNumero().length() <= 15){
            log.info("Longitud de teléfono válida");
            return telefonoRepository.save(telefono);
        }
        log.error("Longitud de teléfono inválida");
        throw new Exception("Telefono inválido");
    }

    public Optional<Telefono> obtenerTelefonoPorId(Long idTelefono){ return telefonoRepository.findById(idTelefono);}

    public List<Telefono> obtenerTelefonos(){
        return telefonoRepository.findAll();
    }

    public void actualizarTelefono(Telefono telefono){
        telefonoRepository.save(telefono);
    }

    public void eliminarTelefono(Long id){
        telefonoRepository.deleteById(id);
    }





}
