package org.example;

public class Cat extends Animal{

    public Cat(String name, int age) {
        super(name, age);
    }

    public void meow(){
        System.out.printf("%s мяукает ... Мяу-Мяу%n", this.name);
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
