package org.example;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * Разработайте класс Student с полями String name, int age, transient double GPA (средний балл).
 * Обеспечьте поддержку сериализации для этого класса.
 * Создайте объект класса Student и инициализируйте его данными.
 * Сериализуйте этот объект в файл.
 * Десериализуйте объект обратно в программу из файла.
 * Выведите все поля объекта, включая GPA, и ответьте на вопрос,
 * почему значение GPA не было сохранено/восстановлено.
 *
 * 2. * Выполнить задачу 1 используя другие типы сериализаторов (в xml и json документы).
 */
@Getter
@Setter
@NoArgsConstructor(force = true)
public class Student implements Serializable {
    @Serial
    private static final long serialVersionUID = -8375806237352043999L;
    private final String name;
    private final int age;
    private transient double gpa;


    public Student(String name, int age, double gpa) {
        this.name = name;
        this.age = age;
        this.gpa = gpa;
    }
    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", GPA=" + gpa +
                '}';
    }

}
