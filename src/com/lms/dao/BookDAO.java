package com.lms.dao;

import com.lms.DBConnection;
import com.lms.entity.Book;

import java.sql.*;

public class BookDAO {

    // Add a new book
    public void addBook(Book book) {
        String sql = "insert into books values (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, book.getId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setBoolean(4, book.isAvailable());
            stmt.executeUpdate();

            System.out.println("Book added.");
        } catch (SQLException e) {
            System.out.println("Add book error: " + e.getMessage());
        }
    }

    // List all books
    public Book[] getAllBooks() {
        Book[] books = new Book[100];
        int index = 0;
        String sql = "select * from books";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                books[index++] = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getBoolean("is_available")
                );
            }
        } catch (SQLException e) {
            System.out.println("Fetch books error: " + e.getMessage());
        }

        Book[] result = new Book[index];
        System.arraycopy(books, 0, result, 0, index);
        return result;
    }

    // Get book by ID
    public Book getBookById(int id) {
        String sql = "select * from books where book_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getBoolean("is_available")
                );
            }
        } catch (SQLException e) {
            System.out.println("Get book error: " + e.getMessage());
        }

        return null;
    }

    // Borrow a book
    public void borrowBook(int patronId, int bookId) {
        String updateBook = "update books set is_available = false where book_id = ?";
        String insertBorrow = "insert into borrowed_books values (?, ?, current_timestamp)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(updateBook)) {
                stmt1.setInt(1, bookId);
                stmt1.executeUpdate();
            }

            try (PreparedStatement stmt2 = conn.prepareStatement(insertBorrow)) {
                stmt2.setInt(1, bookId);
                stmt2.setInt(2, patronId);
                stmt2.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            System.out.println("Borrow error: " + e.getMessage());
        }
    }

    // Return a book
    public void returnBook(int patronId, int bookId) {
        String updateBook = "update books set is_available = true where book_id = ?";
        String updateReturn = "update borrowed_books set return_date = current_timestamp where book_id = ? and patron_id = ? and return_date is null";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt1 = conn.prepareStatement(updateBook)) {
                stmt1.setInt(1, bookId);
                stmt1.executeUpdate();
            }

            try (PreparedStatement stmt2 = conn.prepareStatement(updateReturn)) {
                stmt2.setInt(1, bookId);
                stmt2.setInt(2, patronId);
                stmt2.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            System.out.println("Return error: " + e.getMessage());
        }
    }

    // Get borrowed books by patron
    public Book[] getBorrowedBooksByPatron(int patronId) {
        Book[] books = new Book[100];
        int index = 0;
        String sql = "select b.book_id, b.title, b.author, b.is_available " +
                     "from books b join borrowed_books bb on b.book_id = bb.book_id " +
                     "where bb.patron_id = ? and bb.return_date is null";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patronId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                books[index++] = new Book(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getBoolean("is_available")
                );
            }

            if (index == 0) {
                System.out.println("No borrowed books.");
            }

        } catch (SQLException e) {
            System.out.println("Fetch borrowed books error: " + e.getMessage());
        }

        Book[] result = new Book[index];
        System.arraycopy(books, 0, result, 0, index);
        return result;
    }

    // Update book availability
    public void updateAvailability(int bookId, boolean available) {
        String sql = "update books set is_available = ? where book_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, available);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update availability error: " + e.getMessage());
        }
    }
}
