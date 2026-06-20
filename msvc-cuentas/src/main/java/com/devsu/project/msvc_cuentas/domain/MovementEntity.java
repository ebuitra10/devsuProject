package com.devsu.project.msvc_cuentas.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "movement")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    //@NotBlank(message = "Movement type is required")
    @Column(name = "movement_type", nullable = false)
    private String movementType;

    @NotNull(message = "Value is required")
    @Column(name = "movement_value", nullable = false)
    private Double value;

    @Column(nullable = false)
    private Double balance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private AccountEntity account;

    @PrePersist
    public void prePersist() {
        this.date = LocalDateTime.now();
    }
}
