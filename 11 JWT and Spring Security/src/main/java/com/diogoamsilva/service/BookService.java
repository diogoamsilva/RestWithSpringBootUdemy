package com.diogoamsilva.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diogoamsilva.converter.DozerConverter;
import com.diogoamsilva.dto.BookDTO;
import com.diogoamsilva.exception.ResourceNotFoundException;
import com.diogoamsilva.model.Book;
import com.diogoamsilva.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	BookRepository repository;
	
	public BookDTO create (BookDTO bookDTO) {
		Book book = DozerConverter.parseObject(bookDTO, Book.class);
		return DozerConverter.parseObject(repository.save(book), BookDTO.class);
	}
	
	public List<BookDTO> findAll() {
		return DozerConverter.parseObjectList(repository.findAll(),  BookDTO.class);
	}
	
	public BookDTO findById(Long id) {
		Book book = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No record found for this ID"));
		return DozerConverter.parseObject(book, BookDTO.class);
	}
	
	public BookDTO update (BookDTO bookDTO) {
		Book book = repository.findById(bookDTO.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("No record found for this ID"));

		book.setAuthor(bookDTO.getAuthor());
		book.setLaunchDate(bookDTO.getLaunchDate());
		book.setPrice(bookDTO.getPrice());
		book.setTitle(bookDTO.getTitle());
		
		return DozerConverter.parseObject(repository.save(book), BookDTO.class);
	}
	
	public void delete(Long id) {
		Book entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No record found for this ID"));
		repository.delete(entity);
	}
	
}
