package ru.job4j.job4j_rest.store;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.job4j_rest.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
}