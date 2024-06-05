package com.eazybytes.eazyschool.repository;

import com.eazybytes.eazyschool.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.*;

@Repository
public interface PersonRepository extends CrudRepository<Person, Integer> {

    Person readByEmail(String email);
}
