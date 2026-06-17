package com.devsu.project.msvc_clientes;


import com.devsu.project.msvc_clientes.domain.ClientEntity;
import com.devsu.project.msvc_clientes.messaging.ClientEventPublisher;
import com.devsu.project.msvc_clientes.repositories.IClientRepository;
import com.devsu.project.msvc_clientes.services.ClientUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientTest {

    @Mock
    private IClientRepository clientRepository;

    @Mock
    private ClientEventPublisher clientEventPublisher;

    @InjectMocks
    private ClientUseCase clientUseCase;

    private ClientEntity client;

    @BeforeEach
    void setUp() {
        client = new ClientEntity();
        client.setId(1L);
        client.setName("Jose Lema");
        client.setGender("Masculino");
        client.setAge(35);
        client.setIdentification("1001");
        client.setAddress("Otavalo sn y principal");
        client.setPhone("098254785");
        client.setClientId("joselema");
        client.setPassword("1234");
        client.setStatus(true);
    }

    @Test
    void shouldReturnAllClients() {
        when(clientRepository.findAll()).thenReturn(List.of(client));

        List<ClientEntity> result = clientUseCase.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Jose Lema", result.get(0).getName());
        verify(clientRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnClientById() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Optional<ClientEntity> result = clientUseCase.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("joselema", result.get().getClientId());
        verify(clientRepository, times(1)).findById(1L);
    }

    @Test
    void shouldSaveClientSuccessfully() {
        when(clientRepository.existsByClientId("joselema")).thenReturn(false);
        when(clientRepository.existsByIdentification("1001")).thenReturn(false);
        when(clientRepository.save(client)).thenReturn(client);

        ClientEntity result = clientUseCase.save(client);

        assertNotNull(result);
        assertEquals("joselema", result.getClientId());
        verify(clientRepository, times(1)).save(client);
        verify(clientEventPublisher, times(1)).publishClientCreated(client);
    }

    @Test
    void shouldThrowExceptionWhenClientIdAlreadyExists() {
        when(clientRepository.existsByClientId("joselema")).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> clientUseCase.save(client));

        assertEquals("Client ID already exists: joselema", exception.getMessage());
        verify(clientRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenClientNotFound() {
        when(clientRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> clientUseCase.deleteById(99L));

        assertEquals("Client not found with id: 99", exception.getMessage());
        verify(clientRepository, never()).deleteById(any());
    }
}
