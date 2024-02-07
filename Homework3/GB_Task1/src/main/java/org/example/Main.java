package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;

/**
 * Разработайте класс Student с полями String name, int age, transient double GPA (средний балл).
 * Обеспечьте поддержку сериализации для этого класса.
 * Создайте объект класса Student и инициализируйте его данными.
 * Сериализуйте этот объект в файл.
 * Десериализуйте объект обратно в программу из файла.
 * Выведите все поля объекта, включая GPA, и ответьте на вопрос,
 * почему значение GPA не было сохранено/восстановлено.
 * <p>
 * 2. * Выполнить задачу 1 используя другие типы сериализаторов (в xml и json документы).
 */
public class Main {
    public static void main(String[] args) {
        Student AlexTheStudent = new Student("Alexander", 34, 2.5);

        /**
         * Task1
         */
        try (OutputStream outStream = new FileOutputStream("AlexTheStudent.bin");
             ObjectOutputStream outObjStream = new ObjectOutputStream(outStream)) {
            outObjStream.writeObject(AlexTheStudent);
        } catch (IOException e) {
            System.out.println("Не удалось провести сериализацию");
            e.printStackTrace();
        }
        AlexTheStudent = null;
        try (InputStream inStream = new FileInputStream("AlexTheStudent.bin");
             ObjectInputStream inObjStream = new ObjectInputStream(inStream)) {
            AlexTheStudent = (Student) inObjStream.readObject();
            System.out.println(AlexTheStudent);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Не удалось провести десериализацию");
            e.printStackTrace();
        }

        /**
         * Task2
         */
        AlexTheStudent = new Student("Alexander", 34, 2.5);
        ObjectMapper objectMapper = new ObjectMapper();

        try (OutputStream outStream = new FileOutputStream("AlexTheStudent.json")) {
            objectMapper.writeValue(outStream,AlexTheStudent);
        } catch (IOException e) {
            System.out.println("Не удалось провести сериализацию в JSON");
            e.printStackTrace();
        }
        AlexTheStudent = null;
        try (InputStream inStream = new FileInputStream("AlexTheStudent.json")) {
            AlexTheStudent = objectMapper.readValue(inStream,Student.class);
            System.out.println(AlexTheStudent);
        } catch (IOException e) {
            System.out.println("Не удалось провести десериализацию");
            e.printStackTrace();
        }

        AlexTheStudent = new Student("Alexander", 34, 2.5);
        XmlMapper xmlMapper = new XmlMapper();

        try (OutputStream outStream = new FileOutputStream("AlexTheStudent.xml")) {
            xmlMapper.writeValue(outStream,AlexTheStudent);
        } catch (IOException e) {
            System.out.println("Не удалось провести сериализацию в JSON");
            e.printStackTrace();
        }
        AlexTheStudent = null;
        try (InputStream inStream = new FileInputStream("AlexTheStudent.xml")) {
            AlexTheStudent = xmlMapper.readValue(inStream,Student.class);
            System.out.println(AlexTheStudent);
        } catch (IOException e) {
            System.out.println("Не удалось провести десериализацию");
            e.printStackTrace();
        }
    }
}