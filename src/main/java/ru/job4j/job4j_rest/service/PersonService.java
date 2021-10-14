package ru.job4j.job4j_rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.job4j.job4j_rest.model.Person;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private RestTemplate rest;

    private static final String API = "http://localhost:8080/person/";
    private static final String API_ID = "http://localhost:8080/person/{id}";

    public List<Person> findAll() {
        return rest.exchange(
                API,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Person>>() {
                }
        ).getBody();
    }

    public Person save(Person person) {
        return rest.postForObject(API, person, Person.class);
    }

    public void update(Person person) {
        rest.put(API, person);
    }

    public void delete(int id) {
        rest.delete(API_ID, id);
    }
}
