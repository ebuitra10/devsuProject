package com.devsu.project.msvc_clientes.repositories;

import com.devsu.project.msvc_clientes.domain.ClientEntity;
import com.devsu.project.msvc_clientes.repositories.jpa.ClientJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements IClientRepository {

    private final ClientJpaRepository clientJpaRepository;

    @Override
    public List<ClientEntity> findAll() {
        return clientJpaRepository.findAll();
    }

    @Override
    public Optional<ClientEntity> findById(Long id) {
        return clientJpaRepository.findById(id);
    }

    @Override
    public Optional<ClientEntity> findByClientId(String clientId) {
        return clientJpaRepository.findByClientId(clientId);
    }

    @Override
    public ClientEntity save(ClientEntity client) {
        return clientJpaRepository.save(client);
    }

    @Override
    public void deleteById(Long id) {
        clientJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByClientId(String clientId) {
        return clientJpaRepository.existsByClientId(clientId);
    }

    @Override
    public boolean existsByIdentification(String identification) {
        return clientJpaRepository.existsByIdentification(identification);
    }

}
