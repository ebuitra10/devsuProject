package com.devsu.project.msvc_cuentas;

import com.devsu.project.msvc_cuentas.domain.AccountEntity;
import com.devsu.project.msvc_cuentas.domain.MovementEntity;
import com.devsu.project.msvc_cuentas.repositories.IAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MovementIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private AccountEntity account;

    @BeforeEach
    void setUp() {
        accountRepository.findAll().forEach(a -> accountRepository.deleteById(a.getId()));

        account = new AccountEntity();
        account.setAccountNumber("999001");
        account.setAccountType("Ahorros");
        account.setInitialBalance(1000.00);
        account.setStatus(true);
        account.setClientId(1L);
        account = accountRepository.save(account);
    }

    @Test
    void shouldRegisterMovementAndUpdateBalance() throws Exception {
        MovementEntity movement = new MovementEntity();
        movement.setValue(-575.00);

        mockMvc.perform(post("/movimientos/" + account.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movement)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.balance").value(425.00))
                .andExpect(jsonPath("$.movementType").value("Retiro"));
    }

    @Test
    void shouldReturnErrorWhenInsufficientBalance() throws Exception {
        MovementEntity movement = new MovementEntity();
        movement.setValue(-2000.00);

        mockMvc.perform(post("/movimientos/" + account.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movement)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Saldo no disponible"));
    }

    @Test
    void shouldReturnAccountStatement() throws Exception {
        MovementEntity movement = new MovementEntity();
        movement.setValue(500.00);

        mockMvc.perform(post("/movimientos/" + account.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movement)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/reportes")
                        .param("clientId", "1")
                        .param("startDate", "2026-01-01T00:00:00")
                        .param("endDate", "2026-12-31T23:59:59"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountNumber").value("999001"))
                .andExpect(jsonPath("$[0].movementValue").value(500.00));
    }
}
