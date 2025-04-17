package com.lms.controller;

import com.lms.service.*;

import java.util.Scanner;

public class BookController {
    private final BookService bookService;

    public BookController(BookService service) {
        this.bookService = service;
    }

    public void handleAddBook(Scanner sc) {
    	bookService.handleAddBook(sc);
    }

    public void listBooks() {
    	bookService.listBooks();
    }

    public void handleBorrowBook(Scanner sc) {
    	bookService.handleBorrowBook(sc);
    }

    public void handleReturnBook(Scanner sc) {
    	bookService.handleReturnBook(sc);
    }

    public void handleListBorrowedByPatron(Scanner sc) {
    	bookService.handleListBorrowedByPatron(sc);
    }
}
