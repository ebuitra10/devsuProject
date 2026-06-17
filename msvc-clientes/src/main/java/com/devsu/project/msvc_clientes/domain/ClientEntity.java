package com.devsu.project.msvc_clientes.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "client")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity extends PersonEntity {

    @NotBlank(message = "Client ID is required")
    @Column(name = "client_id", nullable = false, unique = true)
    private String clientId;

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean status = true;

}
