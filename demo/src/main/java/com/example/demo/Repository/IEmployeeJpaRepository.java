package com.example.demo.Repository;

import java.util.List;

import com.example.demo.Model.Employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeJpaRepository extends JpaRepository<Employee, Long>{
    //select fields from employee where employeeid='[parameter]'
    
    
    Employee findByEmployeeid(String employeeid);

    List<Employee> findByFirstName(String firstName);

    List<Employee> findByLastName(String lastName);

    Iterable<Employee> findByNameContaining(String employeedId);


}
