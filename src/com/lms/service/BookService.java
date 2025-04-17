package com.lms.service;

import com.lms.dao.BookDAO;
import com.lms.dao.PatronDAO;
import com.lms.entity.Book;
import com.lms.entity.Patron;

import java.util.Scanner;

public class BookService {
    private BookDAO bookDAO = new BookDAO();
    private PatronDAO patronDAO = new PatronDAO();

    // Add a new book
    public void addBook(Scanner sc) {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // Clear newline
        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();

        Book book = new Book(id, title, author, true);
        bookDAO.addBook(book);
        System.out.println("Book added.");
    }

    // Show all books
    public void listBooks() {
        Book[] books = bookDAO.getAllBooks();
        System.out.println("---- Book List ----");
        for (Book book : books) {
            book.printBooks();
        }
    }

    // Borrow a book
    public void borrowBook(Scanner sc) {
        System.out.print("Enter Patron ID: ");
        int patronId = sc.nextInt();
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();

        Book book = bookDAO.getBookById(bookId);
        Patron patron = patronDAO.getPatronById(patronId);

        if (book == null || patron == null) {
            System.out.println("Invalid book or patron.");
            return;
        }

        if (!book.isAvailable()) {
            System.out.println("Book is already borrowed.");
            return;
        }

        bookDAO.borrowBook(patronId, bookId);
        book.setAvailable(false);
        System.out.println("Book borrowed.");
    }

    // Return a book
    public void returnBook(Scanner sc) {
        System.out.print("Enter Patron ID: ");
        int patronId = sc.nextInt();
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();

        Book book = bookDAO.getBookById(bookId);
        Patron patron = patronDAO.getPatronById(patronId);

        if (book == null || patron == null) {
            System.out.println("Invalid book or patron.");
            return;
        }

        if (book.isAvailable()) {
            System.out.println("Book was not borrowed.");
            return;
        }

        bookDAO.returnBook(patronId, bookId);
        book.setAvailable(true);
        System.out.println("Book returned.");
    }

    // Show books borrowed by a patron
    public void showBorrowedBooks(Scanner sc) {
        System.out.print("Enter Patron ID: ");
        int patronId = sc.nextInt();

        Patron patron = patronDAO.getPatronById(patronId);
        if (patron == null) {
            System.out.println("Patron not found.");
            return;
        }

        Book[] books = bookDAO.getBorrowedBooksByPatron(patronId);
        System.out.println("Borrowed Books:");
        for (Book book : books) {
            book.printBooks();
        }
    }
}
