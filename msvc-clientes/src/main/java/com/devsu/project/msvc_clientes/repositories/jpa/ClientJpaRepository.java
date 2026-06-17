package com.devsu.project.msvc_clientes.repositories.jpa;

import com.devsu.project.msvc_clientes.domain.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientJpaRepository extends JpaRepository<ClientEntity, Long> {

    Optional<ClientEntity> findByClientId(String clientId);

    boolean existsByClientId(String clientId);

    boolean existsByIdentification(String identification);
}
