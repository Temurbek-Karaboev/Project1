package com.example.project1.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
@Component
public class DatabaseCreator {
    @Value("${database.url}")
    private String url1;
    @Value("${database.username}")
    private String username1;
    @Value("${database.password}")
    private String password1;
    public static Connection connection = null;
    public static Statement statement = null;
    @Bean
    public void createDatabase() throws SQLException {
        connection = DriverManager.getConnection(url1, username1, password1);
        System.out.println("Connection to database is success");
        statement = connection.createStatement();
        try {
            statement.execute("create table person\n" +
                    "(\n" +
                    "    id           bigserial,\n" +
                    "    name         varchar not null,\n" +
                    "    password     varchar not null,\n" +
                    "    phone_number varchar,\n" +
                    "    role         varchar not null\n" +
                    ");\n" +
                    "\n" +
                    "alter table person\n" +
                    "    owner to postgres;\n" +
                    "\n" +
                    "create unique index person_id_uindex\n" +
                    "    on person (id);");

            System.out.println("Person table is created");

        } catch (Exception e) {
            System.out.println("Person table is already created");
        }

        try {
            statement.execute("INSERT INTO person(id, name , password, phone_number , role) values\n" +
                    "                                                                         (0, 'admin', '$2a$10$1fGl7V0Z9xb6gC0AVtcXfu2Rjy3HsQyaqCjQ7mW0Fg6mkyD9DtnjO', '+12345', 'ROLE_ADMIN')");
            System.out.println("Admin is created");
            System.out.println("DEFAULT USERNAME: ADMIN  \n DEFAULT PASSWORD: 123");
        }
        catch (Exception e) {
            System.out.println("Admin is already created");
            System.out.println("DEFAULT USERNAME: ADMIN  \n DEFAULT PASSWORD: 123");
        }
    }
}
