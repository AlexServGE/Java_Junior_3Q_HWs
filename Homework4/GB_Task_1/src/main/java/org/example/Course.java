package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * Создайте базу данных (например, SchoolDB).
 * В этой базе данных создайте таблицу Courses с полями id (ключ), title, и duration.
 * Настройте Hibernate для работы с вашей базой данных.
 * Создайте Java-класс Course, соответствующий таблице Courses, с необходимыми аннотациями Hibernate.
 * Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Courses.
 * Убедитесь, что каждая операция выполняется в отдельной транзакции.
 *
 * CREATE TABLE courses(
 * id serial PRIMARY KEY,
 * title varchar(255) NOT NULL,
 * duration int NOT NULL);
 *
 */
@Getter
@Setter
@NoArgsConstructor(force = true)
@ToString
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "duration")
    private Integer duration;

    public Course(String title, Integer duration) {
        this.title = title;
        this.duration = duration;
    }
}
