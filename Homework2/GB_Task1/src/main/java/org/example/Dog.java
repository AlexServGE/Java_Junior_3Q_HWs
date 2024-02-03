package org.example;

public class Dog extends Animal{

    public Dog(String name, int age) {
        super(name, age);
    }

    public void bark(){
        System.out.printf("%s гавкает !!! Гав-гав%n", this.name);
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
