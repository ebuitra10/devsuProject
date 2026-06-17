package com.devsu.project.msvc_clientes.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(max = 100)
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Gender is required")
    @Column(nullable = false)
    private String gender;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age must be positive")
    @Column(nullable = false)
    private Integer age;

    @NotBlank(message = "Identification is required")
    @Column(nullable = false, unique = true)
    private String identification;

    @NotBlank(message = "Address is required")
    @Column(nullable = false)
    private String address;

    @NotBlank(message = "Phone is required")
    @Column(nullable = false)
    private String phone;


}
