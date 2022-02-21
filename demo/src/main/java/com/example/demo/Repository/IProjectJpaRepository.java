package com.example.demo.Repository;

import com.example.demo.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface IProjectJpaRepository extends JpaRepository<Project,Long> {
    Project findByName(String name);

    Iterable<Project> findByNameContaining(String name);
}
