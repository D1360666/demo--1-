package com.example.demo.Controllers;

import com.example.demo.Model.Project;
import com.example.demo.Repository.IProjectJpaRepository;

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
public class ProjectController {

    @Autowired
    IProjectJpaRepository repo;


    /**
     * Find All Projects
     * @param name
     * @return Request
     */

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects(@RequestParam(required = false) String name){
        try{
            List<Project> projects = new ArrayList();

            if(name == null)
            repo.findAll().forEach(projects::add);
            else
                repo.findByNameContaining(name).forEach(projects::add);

            if(projects.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(projects, HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Función para guardar un proyecto
     * @param project
     * @return
     */

    @PostMapping("/projects")
    public ResponseEntity<Project> createProject(@RequestBody Project project){
        try{
            Project _project = repo
                .save(new Project(project.getName()));
            return new ResponseEntity<>(_project, HttpStatus.CREATED);
        }catch(Exception ex){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * Función para Actualizar
     * @param id
     * @param project
     * @return
     */
    @PutMapping("projects/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable("id") long id, @RequestBody Project project){
        Optional<Project> projectData = repo.findById(id);

        try{
            if(projectData.isPresent()){
                Project _Project = projectData.get();
                _Project.setName(project.getName());

                return new ResponseEntity<>(repo.save(_Project),HttpStatus.OK);
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
    @DeleteMapping("/projects/{id}")
	public ResponseEntity<String> deleteProject(@PathVariable("id") long id) {
		try {
			repo.deleteById(id);
				return new ResponseEntity<>("Projects DELETE!! ",HttpStatus.NO_CONTENT);
			} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}
    
}
