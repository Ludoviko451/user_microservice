package com.user.user.adapters.driven.jpa.mysql.repository;

import com.user.user.adapters.driven.jpa.mysql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT * from user WHERE email = :email", nativeQuery = true)
    Optional<UserEntity> findByEmail(String email);

    @Query(value = "SELECT * from user WHERE name = :name", nativeQuery = true)
    Optional<UserEntity> findByName(String name);

    @Query(value = "SELECT * from user WHERE dni = :dni", nativeQuery = true)
    Optional<UserEntity> findByDni(String dni);
}
