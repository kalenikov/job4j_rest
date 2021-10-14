package ru.job4j.job4j_rest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "person")
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;
    private int employeeId;

    public static Person of(int id, String login, String password) {
        Person r = new Person();
        r.id = id;
        r.login = login;
        r.password = password;
        return r;
    }
}