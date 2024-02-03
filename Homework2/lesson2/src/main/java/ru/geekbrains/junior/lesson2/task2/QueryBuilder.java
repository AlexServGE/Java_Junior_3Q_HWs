package ru.geekbrains.junior.lesson2.task2;

import ru.geekbrains.junior.lesson2.task2.Exceptions.NotOrmClassException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

public class QueryBuilder {

    /**
     * Построить запрос на добавление данных в БД
     *
     * @param obj
     * @return
     */
    public static String buildInsertQuery(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        NotOrmClassException.isTableAnnotatedClass(clazz);

        StringBuilder query = new StringBuilder("INSERT INTO ");

        Table tableAnnotation = clazz.getAnnotation(Table.class);
        query
                .append(tableAnnotation.name())
                .append(" (");

        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .forEach((field) -> {
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    query
                            .append(columnAnnotation.name())
                            .append(", ");

                });

        if (query.charAt(query.length() - 2) == ',') {
            query.delete(query.length() - 2, query.length());
        }
        query.append(") VALUES (");

        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .forEach(field -> {
                    field.setAccessible(true);
                    try {
                        query.append("'").append(field.get(obj)).append("', ");
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });

        if (query.charAt(query.length() - 2) == ',') {
            query.delete(query.length() - 2, query.length());
        }

        query.append(")");

        return query.toString();
    }

    /**
     * Построить запрос на получение данных из БД
     *
     * @param clazz
     * @param primaryKey
     * @return
     */
    public static String buildSelectQuery(Class<?> clazz, UUID primaryKey) {
        NotOrmClassException.isTableAnnotatedClass(clazz);
        StringBuilder query = new StringBuilder("SELECT * FROM ");

        Table tableAnnotation = clazz.getAnnotation(Table.class);
        query.append(tableAnnotation.name()).append(" WHERE ");

        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> field.getAnnotation(Column.class))
                .filter(Column::primaryKey)
                .forEach(columnAnn -> query.append(columnAnn.name())
                        .append(" = ")
                        .append("'")
                        .append(primaryKey)
                        .append("'"));

        return query.toString();
    }

    /**
     * Построить запрос на удаление данных из бд
     *
     * @param obj
     * @return
     */
    public static String buildUpdateQuery(Object obj) {
        Class<?> clazz = obj.getClass();
        NotOrmClassException.isTableAnnotatedClass(clazz);

        StringBuilder query = new StringBuilder("UPDATE ");

        Table tableAnnotation = clazz.getAnnotation(Table.class);
        query.append(tableAnnotation.name()).append(" SET ");

        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .peek(field -> field.setAccessible(true))
                .filter(field -> !field.getAnnotation(Column.class).primaryKey())
                .forEach(field -> {
                    try {
                        query.append(field.getAnnotation(Column.class).name()).append(" = '").append(field.get(obj)).append("', ");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

        if (query.charAt(query.length() - 2) == ',') {
            query.delete(query.length() - 2, query.length());
        }

        query.append(" WHERE ");

        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .peek(field -> field.setAccessible(true))
                .filter(field -> field.getAnnotation(Column.class).primaryKey())
                .forEach(field -> {
                    try {
                        query.append(field.getAnnotation(Column.class).name()).append(" = '").append(field.get(obj)).append("'");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

        return query.toString();
    }

    /**
     * TODO: Доработать метод Delete в рамках выполнения домашней работы
     * DELETE FROM users WHERE id = '...';
     * @return
     */
    public static String buildDeleteQuery(Class<?> clazz, UUID primaryKey) {
        NotOrmClassException.isTableAnnotatedClass(clazz);
        StringBuilder query = new StringBuilder("DELETE FROM ");

        Table tableAnnotation = clazz.getAnnotation(Table.class);
        query.append(tableAnnotation.name()).append(" WHERE ");

        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> field.getAnnotation(Column.class))
                .filter(Column::primaryKey)
                .forEach(columnAnn -> query.append(columnAnn.name())
                        .append(" = ")
                        .append("'")
                        .append(primaryKey)
                        .append("'"));

        return query.toString();
    }


}
