package com.lhh.ms.token.infrastructure.persistence.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class TokenEntityTest {
    @Autowired
    private TestEntityManager entityManager;

    private TokenEntity tokenEntity;

    @BeforeEach
    void setUp() {
        // Configura la entidad de prueba antes de cada prueba
        tokenEntity = new TokenEntity();
        tokenEntity.setToken("testToken");
        tokenEntity.setUsername("testUser");
    }

    @Test
    void testPrePersistDefaults() {
        // Persistimos el objeto en la base de datos
        entityManager.persistAndFlush(tokenEntity);

        // Verificamos el comportamiento esperado
        assertNotNull(tokenEntity.getTimestamp());
        assertNotNull(tokenEntity.getExpiration());
        assertEquals(tokenEntity.getTimestamp().plusSeconds(60), tokenEntity.getExpiration());
    }
}