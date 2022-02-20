package com.example.demo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.Model.Employee;

import com.example.demo.Repository.IEmployeeJpaRepository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest

@AutoConfigureTestDatabase(replace = Replace.NONE)
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class EmployeeJpaRepositoryTest {
    @Autowired
    private IEmployeeJpaRepository repo;


    @Test
    public void saveEmployee() {

        Employee john = new Employee("John", "Smith", "empl123");
        Employee claire = new Employee("Claire", "Simpson", "empl124");

        repo.save(john);
        repo.save(claire);

        repo.flush();

        
        assertEquals(2, repo.findAll().size());
        

    }

    
}
