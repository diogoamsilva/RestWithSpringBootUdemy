package com.diogoamsilva.converter;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.diogoamsilva.dto.PersonDTOV2;
import com.diogoamsilva.model.Person;

@Service
public class PersonConverter {
	
	public PersonDTOV2 convertEntityToDTO(Person person) {
		PersonDTOV2 dto = new PersonDTOV2();
		dto.setId(person.getId());
		dto.setAddress(person.getAddress());
		dto.setBirthday(new Date());
		dto.setFirstName(person.getFirstName());
		dto.setLastName(person.getLastName());
		dto.setGender(person.getGender());
		return dto;
	}
	
	public Person convertDTOToEntity(PersonDTOV2 person) {
		Person entity = new Person();
		entity.setId(person.getId());
		entity.setAddress(person.getAddress());
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setGender(person.getGender());
		return entity;
	}

}
