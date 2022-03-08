package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PersonController {

    @Autowired
    PersonRepository repository;

    @GetMapping("/persons")
    List<Person> findAll(){
        return repository.findAll();
    }

    @GetMapping("/persons/{id}")
    Optional<Person> findPerson(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PutMapping("/persons/{id}")
    ResponseEntity<String> createOrUpdate(@Valid @RequestBody Person newPerson, @PathVariable Long id) {
        Person person = repository.findById(id)
            .map(x -> {
                x.setFirstName(newPerson.getFirstName());
                x.setLastName(newPerson.getLastName());
                x.setAge(newPerson.getAge());
                return repository.save(x);
            })
            .orElseGet(() -> {
                return repository.save(newPerson);
            });

        return ResponseEntity.ok("Person is valid");
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Map> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Map> ret = new HashMap<>();
        ret.put("errors", errors);
        return ret;
    }
}
