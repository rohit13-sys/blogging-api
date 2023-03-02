package com.example.blogging.repository;

import com.example.blogging.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReposiory extends JpaRepository<Users,Integer> {
    Optional<Users> findByEmail(String userName);

    Optional<Users> findRolesByEmail(String email);
}
