package com.example.fraud;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FraudUserRepository extends JpaRepository<FraudUser, Integer> {

    Optional<FraudUser> findByEmail(String email);

}
