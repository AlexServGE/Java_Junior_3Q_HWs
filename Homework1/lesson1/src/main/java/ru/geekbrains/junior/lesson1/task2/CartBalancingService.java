package ru.geekbrains.junior.lesson1.task2;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class CartBalancingService {


    public static <T extends Food> void cardBalancing(Class<T> clazz, UMarket market, ArrayList<T> foodstuffs) {

        AtomicBoolean proteins = new AtomicBoolean(foodstuffs.stream().anyMatch(Food::getProteins)); //false
        AtomicBoolean fats = new AtomicBoolean(foodstuffs.stream().anyMatch(Food::getFats)); //true
        AtomicBoolean carbohydrates = new AtomicBoolean(foodstuffs.stream().anyMatch(Food::getCarbohydrates)); //true

        if (!proteins.get()) {
            market.getThings(clazz).stream().filter(Food::getProteins).findFirst().ifPresent(food -> {
                foodstuffs.add(food);
                proteins.set(true);
            });
        }
        if (!fats.get()) {
            market.getThings(clazz).stream().filter(Food::getFats).findFirst().ifPresent(food -> {
                foodstuffs.add(food);
                fats.set(true);
            });
        }
        if (!carbohydrates.get()) {
            market.getThings(clazz).stream().filter(Food::getCarbohydrates).findFirst().ifPresent(food -> {
                foodstuffs.add(food);
                carbohydrates.set(true);
            });
        }

        System.out.println(
                (proteins.get() && fats.get() && carbohydrates.get())
                ? "Корзина сбалансирована по БЖУ."
                : "Невозможно сбалансировать корзину по БЖУ!!.");
    }

}
