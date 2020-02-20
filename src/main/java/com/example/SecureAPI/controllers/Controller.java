package com.example.SecureAPI.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SecureAPI.models.Person;
import com.example.SecureAPI.repositories.PersonRepository;

@RestController
@CrossOrigin
public class Controller {

	@Autowired
	PersonRepository repo;

	@GetMapping("/person")
	public Iterable<Person> person() {
		return repo.findAll();
	}

	
	  @GetMapping("/person/{id}") 
	  public Person personById(@PathVariable("id") long id) { 
		  return repo.findById(id).orElse(new Person ());
	  
	  }
	 

	  @GetMapping("/personbyname/{name}") 
	  public List<Person> personByName(@PathVariable("name") String name) { 
		  return  repo.findByName(name);
	  
	  }
	 

	@PostMapping(value = "/person")
	public Person addPerson(@RequestBody Person p) {
		return repo.save(p);
	}
	
	@PutMapping("/person/{id}")
	public Person replaceEmployee(@RequestBody Person p, @PathVariable Long id) {
		
		System.out.println(p.getName());
		System.out.println(p.getSurname());
		System.out.println(p.getEmail());
		System.out.println(p.getGender());
		
	    return repo.findById(id)
	      .map(person -> {
	    	  if (p.getEmail() != null) person.setEmail(p.getEmail());
	    	  if (p.getGender()!= null) person.setGender(p.getGender());
	    	  if (p.getName() != null) person.setName(p.getName());
	    	  if (p.getSurname() != null) person.setSurname(p.getSurname());
	       
	        return repo.save(person);
	      })
	      .orElseGet(() -> {
	        p.setID(id);
	        return repo.save(p);
	      });
	  }
	
	@DeleteMapping("/person/{id}")
	public void deletePerson(@PathVariable Long id) {
		repo.deleteById(id);		
	}

}
