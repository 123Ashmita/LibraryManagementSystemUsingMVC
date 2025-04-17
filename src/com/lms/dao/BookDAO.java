package com.lms.dao;

import com.lms.DBConnection;
import com.lms.entity.Book;

import java.sql.*;

public class BookDAO {

    public void addBook(Book book) {
        String sql = "insert into books values(?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, book.getId());
            stmt.setString(2, book.getTitle());
            stmt.setString(3, book.getAuthor());
            stmt.setBoolean(4, book.isAvailable());
            stmt.executeUpdate();
            System.out.println("Book added successfully!!!!");

        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

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
            System.out.println("Error fetching books: " + e.getMessage());
        }

        Book[] result = new Book[index];
        System.arraycopy(books, 0, result, 0, index);
        return result;
    }

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
            System.out.println("Error retrieving book: " + e.getMessage());
        }

        return null;
    }

    public void updateAvailability(int bookId, boolean available) {
        String sql = "update books set is_available = ? where book_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, available);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating availability: " + e.getMessage());
        }
    }

    public Book[] getBorrowedBooksByPatron(int patronId) {
        Book[] borrowedBooks = new Book[100];  
        int index = 0;
        String sql = "SELECT b.book_id, b.title, b.author, b.is_available " +
                     "FROM books b " +
                     "JOIN borrowed_books bb ON b.book_id = bb.book_id " +
                     "WHERE bb.patron_id = ? AND bb.return_date IS NULL"; 
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patronId);
            ResultSet rs = stmt.executeQuery();
            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;
                borrowedBooks[index++] = new Book(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getBoolean("is_available")
                );
            }

            if (!hasResults) {
                System.out.println("No books borrowed by this patron.");
            }

        } catch (SQLException e) {
            System.out.println("Error fetching borrowed books: " + e.getMessage());
        }

        Book[] result = new Book[index];
        System.arraycopy(borrowedBooks, 0, result, 0, index);
        return result;
    }

    public void returnBook(int patronId, int bookId) {
        String sqlUpdateAvailability = "update books set is_available = true where book_id = ?";
        String sqlUpdateReturnDate = "update borrowed_books set return_date = CURRENT_TIMESTAMP where book_id = ? and patron_id = ? and return_date is null";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt1 = conn.prepareStatement(sqlUpdateAvailability)) {
                stmt1.setInt(1, bookId);
                stmt1.executeUpdate();
            }

            try (PreparedStatement stmt2 = conn.prepareStatement(sqlUpdateReturnDate)) {
                stmt2.setInt(1, bookId);
                stmt2.setInt(2, patronId);
                stmt2.executeUpdate();
            }

            conn.commit();  

        } catch (SQLException e) {
            System.out.println("Error returning book: " + e.getMessage());
            try (Connection conn = DBConnection.getConnection()) {
                conn.rollback(); 
                System.out.println("Rollback successful.");
            } catch (SQLException ex) {
                System.out.println("Error rolling back: " + ex.getMessage());
            }
        }
    }


    public void borrowBook(int patronId, int bookId) {
        String updateBook = "update books set is_available = false where book_id = ?";
        String insertBorrow = "insert into borrowed_books values (?, ?, CURRENT_TIMESTAMP)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); 

            try (PreparedStatement updateStmt = conn.prepareStatement(updateBook)) {
                updateStmt.setInt(1, bookId);
                int updated = updateStmt.executeUpdate();

                if (updated == 0) {
                    System.out.println("Book not found or already borrowed.!!");
                    conn.rollback();  
                    return;
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertBorrow)) {
                insertStmt.setInt(1, bookId);
                insertStmt.setInt(2, patronId);
                insertStmt.executeUpdate();
            }

            conn.commit();  

        } catch (SQLException e) {
            System.out.println("Error borrowing book: " + e.getMessage());
            try (Connection conn = DBConnection.getConnection()) {
                conn.rollback(); 
                System.out.println("Rollback successful.");
            } catch (SQLException ex) {
                System.out.println("Error rolling back: " + ex.getMessage());
            }
      }
    }
}

