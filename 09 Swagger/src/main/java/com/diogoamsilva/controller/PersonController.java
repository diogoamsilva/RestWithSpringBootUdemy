package com.diogoamsilva.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

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
import com.diogoamsilva.service.PersonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"Person Endpoint"})
@RestController
@RequestMapping(value="/api/person/v1")
public class PersonController {
	
	@Autowired
	private PersonService service;

	@ApiOperation(value = "Find all people recorded")
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml"})
	public List<PersonDTO> findAll() {
		List<PersonDTO> personDTOList =  service.findAll();
		personDTOList
			.stream()
			.forEach(
				personDTO -> personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getKey())).withSelfRel()));
		return personDTOList;
	}
	
	@ApiOperation(value = "Find a person by ID")
	@GetMapping( value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml"})
	public PersonDTO findById(@PathVariable("id") Long id) {
		PersonDTO personDTO = service.findById(id); 
		personDTO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personDTO;
	}
	
	@ApiOperation(value = "Create a person")
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml"},
			consumes = { "application/json", "application/xml", "application/x-yaml"})
	public PersonDTO create(@RequestBody PersonDTO person) {
		PersonDTO personDTO = service.create(person);
		personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getKey())).withSelfRel());
		return personDTO;
	}
	
	@ApiOperation(value = "Update a person")
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml"},
			consumes = { "application/json", "application/xml", "application/x-yaml"})
	public PersonDTO update(@RequestBody PersonDTO person) {
		PersonDTO personDTO = service.update(person);
		personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getKey())).withSelfRel());
		return personDTO;
	}
	
	@ApiOperation(value = "Delete a person by ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	
}
