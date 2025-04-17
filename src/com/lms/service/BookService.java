package com.lms.service;

import com.lms.dao.BookDAO;
import com.lms.dao.PatronDAO;
import com.lms.entity.Book;
import com.lms.entity.Patron;

import java.util.Scanner;

public class BookService {
    private final BookDAO bookDAO;
    private final PatronDAO patronDAO;

    public BookService() {
        this.bookDAO = new BookDAO();
        this.patronDAO = new PatronDAO(); 
    }

    public void addBook(Book book) {
        bookDAO.addBook(book);
    }

    public Book[] listBooks() {
        Book[] books = bookDAO.getAllBooks();
        System.out.println("--------Book List -------");
        for (Book book : books) {
            book.printBooks();
        }
        return books;
    }

    public boolean borrowBook(int patronId, int bookId) {
        Patron patron = patronDAO.getPatronById(patronId);
        if (patron == null) {
            System.out.println("Patron not found or does not exist.");
            return false;
        }

        Book book = bookDAO.getBookById(bookId);
        if (book == null) {
            System.out.println("Book not found or does not exist.");
            return false;
        }

        if (!book.isAvailable()) {
            System.out.println("Book is already borrowed by someone else.");
            return false;
        }

        bookDAO.borrowBook(patronId, bookId);
        book.setAvailable(false);
        return true;
    }

    public boolean returnBook(int patronId, int bookId) {
        Patron patron = patronDAO.getPatronById(patronId); 
        if (patron == null) {
            System.out.println("Patron not found or does not exist.");
            return false;
        }

        Book book = bookDAO.getBookById(bookId);
        if (book == null) {
            System.out.println("Book not found or does not exist.");
            return false;
        }

        if (book.isAvailable()) {
            System.out.println("Book is not currently borrowed.");
            return false;
        }

        bookDAO.returnBook(patronId, bookId);
        book.setAvailable(true);
        return true;
    }

    public Book[] getBorrowedBooksByPatron(int patronId) {
        Patron patron = patronDAO.getPatronById(patronId);
        if (patron == null) {
            System.out.println("OOPS! Patron does not exist.");
            return new Book[0];
        }

        Book[] books = bookDAO.getBorrowedBooksByPatron(patronId);
        for (Book book : books) {
            book.printBooks();
        }
        return books;
    }
    public void handleAddBook(Scanner sc) {
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Book Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Book Author: ");
        String author = sc.nextLine();

        if (title.trim().isEmpty() || author.trim().isEmpty()) {
            System.out.println("Title/Author can't be empty.");
            return;
        }

        Book book = new Book(bookId, title.trim(), author.trim(), true);
        addBook(book);
    }

    public void handleBorrowBook(Scanner sc) {
        System.out.print("Enter Patron ID: ");
        int patronId = sc.nextInt();
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();

        if (borrowBook(patronId, bookId)) {
            System.out.println("Book borrowed successfully.");
        } else {
            System.out.println("Failed to borrow book.");
        }
    }

    public void handleReturnBook(Scanner sc) {
        System.out.print("Enter Patron ID: ");
        int patronId = sc.nextInt();
        System.out.print("Enter Book ID: ");
        int bookId = sc.nextInt();

        if (returnBook(patronId, bookId)) {
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Return failed.");
        }
    }

    public void handleListBorrowedByPatron(Scanner sc) {
        System.out.print("Enter Patron ID: ");
        int patronId = sc.nextInt();
        getBorrowedBooksByPatron(patronId);
    }
}
