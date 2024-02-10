package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.IOException;

public class DatabaseManager {

    final static SessionFactory sessionFactory = new Configuration()
            .configure()
            .buildSessionFactory();

    private static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void sessionFactoryClose() {
        sessionFactory.close();
    }

    public static void insertIntoDBNewCourse(Course courseToIns) {
        Transaction transaction = null;
        try (Session curSession = getSession()) {
            transaction = curSession.beginTransaction();
            curSession.save(courseToIns);
            transaction.commit();
            System.out.println(courseToIns + " успешно записан в базу данных под id = " + courseToIns.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Возникла ошибка при записи в базу данных сущности" + courseToIns + "\n" + e.getMessage());
        }
    }

    public static Course selectFromDBCourse(Class<Course> clazz, Integer id) {
        Course selectedCourse = null;
        try (Session curSession = getSession()) {
            selectedCourse = curSession.get(clazz, id);
            System.out.println(selectedCourse + " успешно получен из базы данных с id = " + selectedCourse.getId());
        } catch (Exception e) {
            System.out.println("Возникла ошибка при обращении к базе данных по id = " + id + "\n" + e.getMessage());
        }
        return selectedCourse;
    }

    public static void updateInDBCourse(Course courseToUpdate) {
        Transaction transaction = null;
        try (Session curSession = getSession()) {
            transaction = curSession.beginTransaction();
            curSession.update(courseToUpdate);
            transaction.commit();
            System.out.println("Курс успешно обновлён в базе данных " + courseToUpdate);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Возникла ошибка при обращении к базе данных c сущностью " + courseToUpdate + "\n" + e.getMessage());
        }
    }

    public static void deleteInDBCourse(Course courseToDelete) {
        Transaction transaction = null;
        try (Session curSession = getSession()) {
            transaction = curSession.beginTransaction();
            curSession.delete(courseToDelete);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Возникла ошибка при обращении к базе данных c сущностью" + courseToDelete + "\n" + e.getMessage());
        }
        System.out.println("Курс " + courseToDelete + "успешно удалён из базы данных.");
    }
}
