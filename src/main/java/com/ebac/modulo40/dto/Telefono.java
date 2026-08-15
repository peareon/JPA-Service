package com.ebac.modulo40.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class Telefono {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTelefono;
    private String tipoTelefono;
    private int lada;
    private String numero;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Usuario usuario;

}
