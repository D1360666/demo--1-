package com.example.demo.Repository;

import com.example.demo.Model.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IRoleJpaRepository extends JpaRepository<Role,Long>{
    Role findByName(String name);

    Iterable<Role> findByNameContaining(String name);

}
