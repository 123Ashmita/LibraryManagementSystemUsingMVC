//package com.lms;
//
//import java.sql.*;
//
//public class DBConnection {
//    private static final String URL = "jdbc:mysql://localhost:3306/lms";
//    private static final String USER = "root"; 
//    private static final String PASSWORD = "mysql"; 
//
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }
//}
package com.lms;

import java.sql.*;

public class DBConnection {
    private static final String BASE_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "lms2";
    private static final String URL = BASE_URL + DB_NAME;
    private static final String USER = "root";
    private static final String PASSWORD = "mysql";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(BASE_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Step 1: Create the database if it doesn't exist
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            System.out.println("✅ Database checked/created.");

        } catch (SQLException e) {
            System.out.println("❌ Failed to create database: " + e.getMessage());
            return;
        }

        // Step 2: Connect to the new DB and create tables
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            // Books table
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS books (" +
                "book_id INT PRIMARY KEY," +
                "title VARCHAR(255)," +
                "author VARCHAR(255)," +
                "is_available BOOLEAN DEFAULT TRUE" +
                ")"
            );

            // Patrons table
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS patrons (" +
                "patron_id INT PRIMARY KEY," +
                "name VARCHAR(255)" +
                ")"
            );

            // Borrowed books table
            stmt.executeUpdate(
            	    "CREATE TABLE IF NOT EXISTS borrowed_books (" +
//            	    "id INT AUTO_INCREMENT PRIMARY KEY," +  
            	    "patron_id INT NOT NULL," +
            	    "book_id INT NOT NULL," +
            	    "borrow_date DATETIME DEFAULT CURRENT_TIMESTAMP," +  
            	    "return_date DATETIME," +                            
            	    "FOREIGN KEY (patron_id) REFERENCES patrons(patron_id) ON DELETE CASCADE," +
            	    "FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE" +
            	    ")"
            	);


            System.out.println("✅ Tables checked/created.");

        } catch (SQLException e) {
            System.out.println("❌ Failed to create tables: " + e.getMessage());
        }
    }
}
