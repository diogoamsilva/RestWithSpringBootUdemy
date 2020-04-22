package com.diogoamsilva.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.diogoamsilva.model.Person;

@Service
public class PersonService {
	
	private final AtomicLong counter = new AtomicLong();
	
	public Person create (Person person) {
		return person;
	}
	
	public Person update (Person person) {
		return person;
	}
	
	public void delete(String id) {
	}
	
	public Person findById(String id) {
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Diogo");
		person.setLastName("Silva");
		person.setAddress("Rua Portugal");
		person.setGender("Male");
		return person;
	}
	
	public List<Person> findAll() {
		List<Person> people = new ArrayList<Person>();
		for (int i = 0; i < 5; i++) {
			Person person = mockPerson(i);
			people.add(person);
		}
		return people;
	}
	
	private Person mockPerson(int i) {
		Person person = new Person();
		person.setId(counter.incrementAndGet());
		person.setFirstName("Diogo" + i);
		person.setLastName("Silva" + i);
		person.setAddress("Rua Portugal" + i);
		person.setGender("Male" + i);
		return person;
	}

}
