package org.example;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

/**
 * Создайте абстрактный класс "Animal" с полями "name" и "age".
 * Реализуйте два класса-наследника от "Animal" (например, "Dog" и "Cat") с уникальными полями и методами.
 * Создайте массив объектов типа "Animal" и с использованием Reflection API выполните следующие действия:
 * Выведите на экран информацию о каждом объекте.
 * Вызовите метод "makeSound()" у каждого объекта, если такой метод присутствует.
 */

public class Main {
    public static void main(String[] args) {
        List<Animal> myAnimals = List.of(
                new Dog("Шарик", 2),
                new Dog("Пинки", 1),
                new Dog("Вайк ", 4),
                new Cat("Мурзик", 1),
                new Cat("Гaйдэ", 1),
                new Cat("Гaлaктикa", 1)
        );

        for (Animal myAnimal : myAnimals) {
            Class<?> clazz = myAnimal.getClass();
            Class<?> superClazz = clazz.getSuperclass();
            System.out.println(clazz.getSimpleName());
            Arrays.stream(superClazz.getDeclaredFields())
                    .forEach((field) -> {
                try {
                    System.out.println(field.getName() + " - " + field.get(myAnimal));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
            Arrays.stream(clazz.getDeclaredMethods())
                    .filter((method) -> method.getName().equals("makeSound"))
                    .findAny()
                    .ifPresentOrElse((method) -> {
                        try {
                            method.invoke(myAnimal);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    }, () -> System.out.println("Метод makeSound отсутсвует у данного объекта"));
        }
    }
}
