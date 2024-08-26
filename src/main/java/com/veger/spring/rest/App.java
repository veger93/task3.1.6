package com.veger.spring.rest;

import com.veger.spring.rest.configuration.MyConfig;
import com.veger.spring.rest.entity.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication = context.getBean("communication", Communication.class);
// Получение всех пользователей
        List<User> allUsers = communication.getAllUsers();
        System.out.println(allUsers);
// Добавление пользователя
       User user1 = new User("James", "Brown", (byte) 35);
       communication.saveUser(user1);
// Изменение пользователя
        User user2 = new User("Thomas", "Shelby", (byte) 35);
        user2.setId(3L);
        communication.updateUser(user2);
// Удаление пользователя
        User user3 = new User(3L, "Thomas", "Shelby", (byte) 3);
        communication.deleteUser(user3,3);
    }
}
