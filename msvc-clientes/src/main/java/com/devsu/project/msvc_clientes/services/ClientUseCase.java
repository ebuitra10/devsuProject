package com.devsu.project.msvc_clientes.services;

import com.devsu.project.msvc_clientes.domain.ClientEntity;
import com.devsu.project.msvc_clientes.repositories.IClientRepository;
import com.devsu.project.msvc_clientes.services.usecase.IClientUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientUseCase implements IClientUseCase {

    private final IClientRepository clientRepository;

    @Override
    public List<ClientEntity> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<ClientEntity> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Optional<ClientEntity> findByClientId(String clientId) {
        return clientRepository.findByClientId(clientId);
    }

    @Override
    public ClientEntity save(ClientEntity client) {

        if (clientRepository.existsByClientId(client.getClientId())) {
            throw new IllegalArgumentException("Client ID already exists: " + client.getClientId());
        }
        if (clientRepository.existsByIdentification(client.getIdentification())) {
            throw new IllegalArgumentException("Identification already exists: " + client.getIdentification());
        }
        return clientRepository.save(client);
    }

    @Override
    public ClientEntity update(Long id, ClientEntity client) {

        ClientEntity existing = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));

        existing.setName(client.getName());
        existing.setGender(client.getGender());
        existing.setAge(client.getAge());
        existing.setIdentification(client.getIdentification());
        existing.setAddress(client.getAddress());
        existing.setPhone(client.getPhone());
        existing.setClientId(client.getClientId());
        existing.setPassword(client.getPassword());
        existing.setStatus(client.getStatus());

        return clientRepository.save(existing);
    }

    @Override
    public ClientEntity partialUpdate(Long id, ClientEntity client) {

        ClientEntity existing = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));

        if (client.getName() != null) existing.setName(client.getName());
        if (client.getGender() != null) existing.setGender(client.getGender());
        if (client.getAge() != null) existing.setAge(client.getAge());
        if (client.getIdentification() != null) existing.setIdentification(client.getIdentification());
        if (client.getAddress() != null) existing.setAddress(client.getAddress());
        if (client.getPhone() != null) existing.setPhone(client.getPhone());
        if (client.getClientId() != null) existing.setClientId(client.getClientId());
        if (client.getPassword() != null) existing.setPassword(client.getPassword());
        if (client.getStatus() != null) existing.setStatus(client.getStatus());

        return clientRepository.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        clientRepository.deleteById(id);
    }
}
