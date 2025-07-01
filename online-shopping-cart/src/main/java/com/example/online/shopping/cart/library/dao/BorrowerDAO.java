package com.example.online.shopping.cart.library.dao;

import com.example.online.shopping.cart.library.model.Borrower;
import com.example.online.shopping.cart.library.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowerDAO {

    public void addBorrower(Borrower borrower) {
        String sql = "INSERT INTO borrowers (borrower_id, name, email) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, borrower.getBorrowerId());
            stmt.setString(2, borrower.getName());
            stmt.setString(3, borrower.getEmail());
            stmt.executeUpdate();

            System.out.println("✅ Borrower added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Borrower> getAllBorrowers() {
        List<Borrower> borrowers = new ArrayList<>();
        String sql = "SELECT * FROM borrowers";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Borrower b = new Borrower(
                        rs.getInt("borrower_id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                borrowers.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrowers;
    }

    public void deleteBorrower(int borrowerId) {
        String sql = "DELETE FROM borrowers WHERE borrower_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, borrowerId);
            stmt.executeUpdate();
            System.out.println("✅ Borrower deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
