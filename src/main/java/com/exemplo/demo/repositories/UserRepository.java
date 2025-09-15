package com.exemplo.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exemplo.demo.models.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}