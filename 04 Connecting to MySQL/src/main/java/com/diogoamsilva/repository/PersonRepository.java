package com.diogoamsilva.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diogoamsilva.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

}
