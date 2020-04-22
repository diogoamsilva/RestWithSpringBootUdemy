package com.diogoamsilva.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diogoamsilva.dto.PersonDTO;
import com.diogoamsilva.dto.PersonDTOV2;
import com.diogoamsilva.service.PersonService;

@RestController
@RequestMapping(value="/person")
public class PersonController {
	
	@Autowired
	private PersonService service;

	@GetMapping()
	public List<PersonDTO> findAll() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public PersonDTO findById(@PathVariable("id") Long id) {
		return service.findById(id);
	}
	
	@PostMapping()
	public PersonDTO create(@RequestBody PersonDTO person) {
		return service.create(person);
	} 
	
	@PostMapping("/v2")
	public PersonDTOV2 createV2(@RequestBody PersonDTOV2 person) {
		return service.createV2(person);
	} 
	
	@PutMapping()
	public PersonDTO update(@RequestBody PersonDTO person) {
		return service.update(person);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	
}
