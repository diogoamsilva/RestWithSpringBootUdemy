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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.diogoamsilva.dto.BookDTO;
import com.diogoamsilva.service.BookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"Book Endpoint"})
@RestController
@RequestMapping(value="/api/book/v1")
public class BookController {

	@Autowired
	BookService service;
	
	@Autowired
	private PagedResourcesAssembler<BookDTO> assembler;
	
	@ApiOperation(value = "Find all books recorded")
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml"})
	public ResponseEntity<?> findAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "author"));
		
		Page<BookDTO> bookDTOList =  service.findAll(pageable);
		bookDTOList
			.stream()
			.forEach(
				bookDTO -> bookDTO.add(linkTo(methodOn(BookController.class).findById(bookDTO.getKey())).withSelfRel()));
		
		PagedResources<?> resources = assembler.toResource(bookDTOList);
		
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Find a book by ID")
	@GetMapping( value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml"})
	public BookDTO findById(@PathVariable("id") Long id) {
		BookDTO bookDTO = service.findById(id); 
		bookDTO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return bookDTO;
	}
	
	@ApiOperation(value = "Create a book")
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml"},
			consumes = { "application/json", "application/xml", "application/x-yaml"})
	public BookDTO create(@RequestBody BookDTO book) {
		BookDTO bookDTO = service.create(book);
		bookDTO.add(linkTo(methodOn(BookController.class).findById(bookDTO.getKey())).withSelfRel());
		return bookDTO;
	}
	
	@ApiOperation(value = "Update a book")
	@PutMapping(produces = { "application/json", "application/xml", "application/x-yaml"},
			consumes = { "application/json", "application/xml", "application/x-yaml"})
	public BookDTO update(@RequestBody BookDTO book) {
		BookDTO bookDTO = service.update(book);
		bookDTO.add(linkTo(methodOn(BookController.class).findById(bookDTO.getKey())).withSelfRel());
		return bookDTO;
	}
	
	@ApiOperation(value = "Delete a book by ID")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
}
