package com.ebac.modulo40.dto;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_admin;
    private String username;
    private String password;
}
