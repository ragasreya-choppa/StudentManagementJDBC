package com.sreya.studentmanagement.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/student_management";
    private static final String USERNAME = "student_app";

    public static Connection getConnection() throws SQLException {
        String password = System.getenv("DB_PASSWORD");

        if (password == null || password.isBlank()) {
            throw new IllegalStateException(
                    "DB_PASSWORD environment variable is not configured");
        }
        return DriverManager.getConnection(URL, USERNAME, password);
    }
}