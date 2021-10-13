package ru.job4j.job4j_rest.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.job4j_rest.Main;
import ru.job4j.job4j_rest.model.Person;
import ru.job4j.job4j_rest.store.PersonRepository;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class PersonControllerTest {
    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PersonRepository repo;

    @Test
    void whenFindAll() throws Exception {
        when(repo.findAll()).thenReturn(
                List.of(Person.of(1, "person1", "password1"),
                        Person.of(1, "person2", "password2"))
        );

        mvc.perform(get("/person/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].login", is("person1")))
                .andExpect(jsonPath("$[0].password", is("password1")))
                .andExpect(jsonPath("$[1].login", is("person2")))
                .andExpect(jsonPath("$[1].password", is("password2")));
    }

    @Test
    void whenFindById() throws Exception {
        when(repo.findById(1)).thenReturn(Optional.of(Person.of(1, "person1", "password1")));

        mvc.perform(get("/person/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.login", is("person1")))
                .andExpect(jsonPath("$.password", is("password1")));
    }

    @Test
    public void whenFindByIdAndNotFound() throws Exception {
        mvc.perform(get("/person/5"))
                .andExpect(status().isNotFound());
    }


    @Test
    void whenSave() throws Exception {
        Person person = Person.of(1, "person1", "password1");
        when(repo.save(any(Person.class))).thenReturn(person);

        mvc.perform(post("/person/")
                        .content(om.writeValueAsString(person))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.login", is("person1")))
                .andExpect(jsonPath("$.password", is("password1")));
    }

    @Test
    void whenUpdate() throws Exception {
        Person person = Person.of(1, "person1 (edit)", "password1");
        when(repo.save(any(Person.class))).thenReturn(person);

        mvc.perform(put("/person/")
                        .content(om.writeValueAsString(person))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenDelete() throws Exception {
        doNothing().when(repo).deleteById(1);

        mvc.perform(delete("/person/1"))
                .andExpect(status().isOk());
    }
}