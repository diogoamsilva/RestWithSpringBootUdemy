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
	
	@ApiOperation(value = "Find all books recorded")
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml"})
	public List<BookDTO> findAll() {
		List<BookDTO> bookDTOList =  service.findAll();
		bookDTOList
			.stream()
			.forEach(
				bookDTO -> bookDTO.add(linkTo(methodOn(BookController.class).findById(bookDTO.getKey())).withSelfRel()));
		return bookDTOList;
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
