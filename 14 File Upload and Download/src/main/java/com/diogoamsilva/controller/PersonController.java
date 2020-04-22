package com.diogoamsilva.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diogoamsilva.dto.PersonDTO;
import com.diogoamsilva.service.PersonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//@CrossOrigin
@Api(tags = {"Person Endpoint"})
@RestController
@RequestMapping(value="/api/person/v1")
public class PersonController {
	
	@Autowired
	private PersonService service;
	
	@Autowired
	private PagedResourcesAssembler<PersonDTO> assembler;

	@ApiOperation(value = "Find all people recorded")
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<?> findAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));
		
		Page<PersonDTO> personDTOList =  service.findAll(pageable);
		personDTOList
			.stream()
			.forEach(
				personDTO -> personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getKey())).withSelfRel()));
		
		PagedResources<?> resources = assembler.toResource(personDTOList);
		
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Find person by name")
	@GetMapping(value = "/findPersonByName/{firstName}", produces = { "application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<?> findPersonByName(
			@PathVariable("firstName") String firstName,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));
		
		Page<PersonDTO> personDTOList =  service.findPersonByName(firstName, pageable);
		personDTOList
			.stream()
			.forEach(
				personDTO -> personDTO.add(linkTo(methodOn(PersonController.class).findById(personDTO.getKey())).withSelfRel()));
		
		PagedResources<?> resources = assembler.toResource(personDTOList);
		
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}
	
	//@CrossOrigin(origins = "http://localhost:8080")
	@ApiOperation(value = "Find a person by ID")
	@GetMapping( value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml"})
	public PersonDTO findById(@PathVariable("id") Long id) {
		PersonDTO personDTO = service.findById(id); 
		personDTO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personDTO;
	}
	
	//@CrossOrigin(origins" = {"http://localhost:8080", "com.diogoamsilva"})
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
	
	@ApiOperation(value = "Disable a person by ID")
	@PatchMapping( value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml"})
	public PersonDTO disablePerson(@PathVariable("id") Long id) {
		PersonDTO personDTO = service.disablePerson(id); 
		personDTO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personDTO;
	}
	
	@ApiOperation(value = "Delete a person by ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	
}
