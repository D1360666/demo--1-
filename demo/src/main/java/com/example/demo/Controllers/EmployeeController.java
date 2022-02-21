package com.example.demo.Controllers;

import com.example.demo.Model.Employee;
import com.example.demo.Repository.IEmployeeJpaRepository;

import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    IEmployeeJpaRepository repo;


    /**
     * Find All Employees
     * @param employeeId
     * @return Request
     */

    @GetMapping("/employess")
    public ResponseEntity<List<Employee>> getAllProjects(@RequestParam(required = false) String employeeId){
        try{
            List<Employee> employees = new ArrayList();

            if(employeeId == null)
            repo.findAll().forEach(employees::add);
            else
                repo.findByNameContaining(employeeId).forEach(employees::add);

            if(employees.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(employees, HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Función para guardar un proyecto
     * @param employee
     * @return
     */

    @PostMapping("/employees")
    public ResponseEntity<Employee> createProject(@RequestBody Employee employee){
        try{
            Employee _employee = repo
                .save(new Employee(employee.getFirstName(), employee.getLastName(), employee.getEmployeeid(), employee.getRole()));
            return new ResponseEntity<>(_employee, HttpStatus.CREATED);
        }catch(Exception ex){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * Función para Actualizar
     * @param id
     * @param employee
     * @return
     */
    @PutMapping("employees/{id}")
    public ResponseEntity<Employee> updateProject(@PathVariable("id") long id, @RequestBody Employee employee){
        Optional<Employee> employeeData = repo.findById(id);

        try{
            if(employeeData.isPresent()){
                Employee _Employee = employeeData.get();
                _Employee.setFirstName(employee.getFirstName());
                _Employee.setLastName(employee.getLastName());
                _Employee.setEmployeeid(employee.getEmployeeid());
                _Employee.setRole(employee.getRole());
                return new ResponseEntity<>(repo.save(_Employee),HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch(Exception ex){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);            
        }
    }

    /**
     * Función eliminar
     * @param id
     * @return
     */
    @DeleteMapping("/employees/{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id) {
		try {
			repo.deleteById(id);
				return new ResponseEntity<>("Employee DELETE!! ",HttpStatus.NO_CONTENT);
			} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
    
}
