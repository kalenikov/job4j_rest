package ru.job4j.job4j_rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.job4j.job4j_rest.exception.InvalidEmployeeException;
import ru.job4j.job4j_rest.model.Employee;
import ru.job4j.job4j_rest.store.EmployeeRepository;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repo;

    public List<Employee> findAll() {
        return (List<Employee>) repo.findAll();
    }

    public void checkId(int id) throws InvalidEmployeeException {
        if (!repo.existsById(id)) {
            throw new InvalidEmployeeException("Invalid employee id");
        }
    }
}
