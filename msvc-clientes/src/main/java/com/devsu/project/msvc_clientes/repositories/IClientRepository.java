package com.devsu.project.msvc_clientes.repositories;

import com.devsu.project.msvc_clientes.domain.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface IClientRepository {

    List<ClientEntity> findAll();

    Optional<ClientEntity> findById(Long id);

    Optional<ClientEntity> findByClientId(String clientId);

    ClientEntity save(ClientEntity client);

    void deleteById(Long id);

    boolean existsByClientId(String clientId);

    boolean existsByIdentification(String identification);
}
