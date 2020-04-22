package com.diogoamsilva.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diogoamsilva.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

}
