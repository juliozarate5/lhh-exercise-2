package com.lhh.ms.token.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "tokens")
@FieldDefaults(level = PRIVATE)
@Getter
@Setter
public class TokenEntity implements Serializable {

    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(unique = true)
    String token;

    @Column
    LocalDateTime timestamp;

    @Column
    LocalDateTime expiration;

    @Column
    LocalDateTime expirationToken;

    @Column(unique = true)
    String username;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if(timestamp == null) {
            timestamp = LocalDateTime.now();
        }
        if(expiration == null) {
            expiration = timestamp.plusSeconds(60);
        }

    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
        if(timestamp != null) {
            timestamp = LocalDateTime.now();
            if(expiration != null) {
                expiration = timestamp.plusSeconds(60);
            }
        }

    }
}
