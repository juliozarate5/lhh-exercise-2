package com.lhh.ms.token.infrastructure.persistence.repository;

import com.lhh.ms.token.infrastructure.persistence.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenJpaRepository extends JpaRepository<TokenEntity, Long> {

    Optional<TokenEntity> findByUsername(String username);
}
