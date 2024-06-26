package com.user.user.adapters.driven.jpa.mysql.repository;

import com.user.user.adapters.driven.jpa.mysql.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByIdRole(Long idRole);
}
