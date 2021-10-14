package ru.job4j.job4j_rest.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "employee")
@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String lastname;
    private String inn;
    private Timestamp created;
    @Transient
    private List<Person> persons = new ArrayList<>();
}
