package com.devsu.project.msvc_cuentas.domain.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountStatementDto {

    private LocalDateTime date;

    private String clientName;

    private String accountNumber;

    private String accountType;

    private Double initialBalance;

    private Boolean status;

    private Double movementValue;

    private Double availableBalance;

}