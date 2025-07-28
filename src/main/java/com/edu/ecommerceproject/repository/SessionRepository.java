package com.edu.ecommerceproject.repository;


import com.edu.ecommerceproject.models.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session,Integer> {
    Optional<Session> findByUserId(Integer userId);
    Optional<Session> findByToken(String token);
}
