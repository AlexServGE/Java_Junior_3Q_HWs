package org.example;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * Напишите программу, которая использует Stream API для обработки списка чисел.
 * Программа должна вывести на экран среднее значение всех четных чисел в списке.
 */

public class Main {
    public static void main(String[] args) {
        int result = IntStream.range(ThreadLocalRandom.current().nextInt(1),ThreadLocalRandom.current().nextInt(10))
                .peek(System.out::print)
                .filter(number -> number % 2 == 0)
                .sum();
        System.out.println();
        System.out.println(result);
    }
}