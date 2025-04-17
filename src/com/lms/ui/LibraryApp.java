package com.lms.ui;

import com.lms.DBConnection;
import com.lms.controller.BookController;
import com.lms.controller.PatronController;
import com.lms.service.BookService;
import com.lms.service.PatronService;

import java.util.Scanner;

public class LibraryApp {
    public static void main(String[] args) {
        DBConnection.initializeDatabase();
        BookService bookService = new BookService();
        PatronService patronService = new PatronService();

        BookController bookController = new BookController(bookService);
        PatronController patronController = new PatronController(patronService);

        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("\n------ Library Management System ------");
            System.out.println("1. Add Book");
            System.out.println("2. Add Patron");
            System.out.println("3. List Books");
            System.out.println("4. List Patrons");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. View Borrowed Books by Patron");
            System.out.println("8. Delete Patron");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    bookController.handleAddBook(sc);
                    break;
                case 2:
                    patronController.handleAddPatron(sc);
                    break;
                case 3:
                    bookController.listBooks();
                    break;
                case 4:
                    patronController.listPatrons();
                    break;
                case 5:
                    bookController.handleBorrowBook(sc);
                    break;
                case 6:
                    bookController.handleReturnBook(sc);
                    break;
                case 7:
                    bookController.handleListBorrowedByPatron(sc);
                    break;
                case 8:
                    patronController.deletePatron(sc);
                    break;
                case 9:
                    System.out.println("Thank you for using LMS!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
