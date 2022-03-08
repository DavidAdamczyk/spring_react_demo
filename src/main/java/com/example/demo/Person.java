package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "firstName is mandatory")
    @Size(min = 3, max = 20, message = "firstName must be between 3 and 20 characters")
    private String firstName;

    @NotBlank(message = "lastName is mandatory")
    @Size(min = 3, max = 20, message = "lastName must be between 3 and 20 characters")
    private String lastName;

    @DecimalMin(value="18", message = "min age is 18")
    @DecimalMax(value="60", message = "max age is 60")
    private Integer age;

    public Person() {
    }

    public Person(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
