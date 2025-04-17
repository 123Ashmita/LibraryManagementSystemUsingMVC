package com.lms.dao;

import com.lms.DBConnection;
import com.lms.entity.Patron;

import java.sql.*;

public class PatronDAO {

    public void addPatron(Patron patron) {
        String sql = "insert into patrons values (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patron.getPid());
            stmt.setString(2, patron.getName());
            stmt.executeUpdate();
            System.out.println("Patron added successfully!");

        } catch (SQLException e) {
            System.out.println("Error adding patron: " + e.getMessage());
        }
    }

    public Patron[] getAllPatrons() {
        Patron[] patrons = new Patron[100];
        int index = 0;
        String sql = "select * from patrons";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                patrons[index++] = new Patron(
                        rs.getInt("patron_id"),
                        rs.getString("name")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error fetching patrons: " + e.getMessage());
        }

        Patron[] result = new Patron[index];
        System.arraycopy(patrons, 0, result, 0, index);
        return result;
    }

    public Patron getPatronById(int id) {
        String sql = "select * from patrons where patron_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Patron(rs.getInt("patron_id"), rs.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving patron: " + e.getMessage());
        }

        return null;
    }

    public boolean deletePatron(int patronId) {
        String checkBorrowed = "select * from borrowed_books where patron_id = ?";
        String delete = "delete from patrons where patron_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkBorrowed)) {

            checkStmt.setInt(1, patronId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                return false;
            }

            try (PreparedStatement deleteStmt = conn.prepareStatement(delete)) {
                deleteStmt.setInt(1, patronId);
                int rows = deleteStmt.executeUpdate();
                return rows > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error deleting patron: " + e.getMessage());
            return false;
        }
    }
}
