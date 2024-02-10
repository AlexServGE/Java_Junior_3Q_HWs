package org.example;

import org.hibernate.SessionFactory;
import org.hibernate.dialect.Database;
import org.hibernate.id.enhanced.DatabaseStructure;

import javax.xml.crypto.Data;

/**
 * Создайте базу данных (например, SchoolDB).
 * В этой базе данных создайте таблицу Courses с полями id (ключ), title, и duration.
 * Настройте Hibernate для работы с вашей базой данных.
 * Создайте Java-класс Course, соответствующий таблице Courses, с необходимыми аннотациями Hibernate.
 * Используя Hibernate, напишите код для вставки, чтения, обновления и удаления данных в таблице Courses.
 * Убедитесь, что каждая операция выполняется в отдельной транзакции.
 */

public class Main {
    public static void main(String[] args) {
        try {
            Course itCourse = new Course("Java", 20);
            //Insert
            DatabaseManager.insertIntoDBNewCourse(itCourse);
            //Select
            Course itCourseSelected = DatabaseManager.selectFromDBCourse(Course.class, 1);
            //Update
            itCourseSelected.setDuration(100);
            itCourseSelected.setTitle("Python");
            DatabaseManager.updateInDBCourse(itCourseSelected);
            //Delete
//            DatabaseManager.deleteInDBCourse(itCourseSelected);
        } finally {
            DatabaseManager.sessionFactoryClose();
        }
    }
}