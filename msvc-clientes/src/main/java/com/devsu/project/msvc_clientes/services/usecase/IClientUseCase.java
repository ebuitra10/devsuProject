package com.devsu.project.msvc_clientes.services.usecase;

import com.devsu.project.msvc_clientes.domain.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface IClientUseCase {

    List<ClientEntity> findAll();

    Optional<ClientEntity> findById(Long id);

    Optional<ClientEntity> findByClientId(String clientId);

    ClientEntity save(ClientEntity client);

    ClientEntity update(Long id, ClientEntity client);

    ClientEntity partialUpdate(Long id, ClientEntity client);

    void deleteById(Long id);
}
