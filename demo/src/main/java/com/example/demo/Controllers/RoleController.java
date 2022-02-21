package com.example.demo.Controllers;

import com.example.demo.Model.Role;
import com.example.demo.Repository.IRoleJpaRepository;

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
public class RoleController {

    @Autowired
    IRoleJpaRepository repo;


    /**
     * Find All Rols
     * @param rol
     * @return Request
     */

    @GetMapping("/rols")
    public ResponseEntity<List<Role>> getAllProjects(@RequestParam(required = false) String name){
        try{
            List<Role> roles = new ArrayList();

            if(name == null)
            repo.findAll().forEach(roles::add);
            else
                repo.findByNameContaining(name).forEach(roles::add);

            if(roles.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(roles, HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Función para guardar un Rol
     * @param role
     * @return
     */

    @PostMapping("/rols")
    public ResponseEntity<Role> createProject(@RequestBody Role role){
        try{
            Role _role = repo
                .save(new Role(role.getName()));
            return new ResponseEntity<>(_role, HttpStatus.CREATED);
        }catch(Exception ex){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * Función para Actualizar
     * @param id
     * @param role
     * @return
     */
    @PutMapping("rols/{id}")
    public ResponseEntity<Role> updateProject(@PathVariable("id") long id, @RequestBody Role role){
        Optional<Role> roleData = repo.findById(id);

        try{
            if(roleData.isPresent()){
                Role _role = roleData.get();
                _role.setName(role.getName());
                
                return new ResponseEntity<>(repo.save(_role),HttpStatus.OK);
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
    @DeleteMapping("/rols/{id}")
	public ResponseEntity<String> deleteRole(@PathVariable("id") long id) {
		try {
			repo.deleteById(id);
				return new ResponseEntity<>("Role DELETE!! ",HttpStatus.NO_CONTENT);
			} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
    
}
