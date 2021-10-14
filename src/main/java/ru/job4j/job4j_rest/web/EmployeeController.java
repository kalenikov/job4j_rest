package ru.job4j.job4j_rest.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.job4j_rest.exception.InvalidEmployeeException;
import ru.job4j.job4j_rest.model.Employee;
import ru.job4j.job4j_rest.model.Person;
import ru.job4j.job4j_rest.service.EmployeeService;
import ru.job4j.job4j_rest.service.PersonService;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private PersonService personService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public List<Employee> findAllEmployees() throws InvalidEmployeeException {
        List<Employee> employees = employeeService.findAll();
        List<Person> persons = personService.findAll();
        for (Person person : persons) {
            employeeService.checkId(person.getEmployeeId());
            Employee employee = employees.stream()
                    .filter(e -> e.getId() == person.getEmployeeId())
                    .findFirst().get();
            employee.getPersons().add(person);
        }
        return employees;
    }

    @PostMapping("/")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) throws InvalidEmployeeException {
        employeeService.checkId(person.getEmployeeId());
        return new ResponseEntity<>(
                personService.save(person),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> updatePerson(@RequestBody Person person) throws InvalidEmployeeException {
        employeeService.checkId(person.getEmployeeId());
        personService.update(person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable int id) throws InvalidEmployeeException {
        employeeService.checkId(id);
        personService.delete(id);
        return ResponseEntity.ok().build();
    }
}
