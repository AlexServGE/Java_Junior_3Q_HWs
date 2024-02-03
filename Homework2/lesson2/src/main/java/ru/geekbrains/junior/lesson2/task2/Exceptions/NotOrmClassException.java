package ru.geekbrains.junior.lesson2.task2.Exceptions;
import ru.geekbrains.junior.lesson2.task2.Table;

public class NotOrmClassException extends RuntimeException {

    public NotOrmClassException(String message, Throwable cause) {
        super(message, cause);
    }

    public static void isTableAnnotatedClass(Class<?> clazz){
        if (clazz.getAnnotation(Table.class) == null){
            throw new NotOrmClassException("Класс" + clazz + "не является ORM классом", new RuntimeException());
        }
    }
}
