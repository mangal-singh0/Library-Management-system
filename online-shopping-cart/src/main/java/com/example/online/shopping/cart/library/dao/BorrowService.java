package com.example.online.shopping.cart.library.dao;

import com.example.online.shopping.cart.library.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowService {

    public void borrowBook(int borrowerId, int bookId) {
        try (Connection conn = DBConnection.getConnection()) {
            // Step 1: Insert into borrowed_books
            String insertSql = "INSERT INTO borrowed_books (borrower_id, book_id) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                stmt.setInt(1, borrowerId);
                stmt.setInt(2, bookId);
                stmt.executeUpdate();
            }

            // Step 2: Decrease book quantity
            String updateSql = "UPDATE books SET quantity = quantity - 1 WHERE book_id = ? AND quantity > 0";
            try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                stmt.setInt(1, bookId);
                int rows = stmt.executeUpdate();
                if (rows == 0) {
                    System.out.println("❌ Book not available.");
                } else {
                    System.out.println("✅ Book borrowed successfully.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook(int borrowerId, int bookId) {
        try (Connection conn = DBConnection.getConnection()) {
            // Step 1: Remove from borrowed_books
            String deleteSql = "DELETE FROM borrowed_books WHERE borrower_id = ? AND book_id = ? LIMIT 1";
            try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
                stmt.setInt(1, borrowerId);
                stmt.setInt(2, bookId);
                stmt.executeUpdate();
            }

            // Step 2: Increase book quantity
            String updateSql = "UPDATE books SET quantity = quantity + 1 WHERE book_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateSql)) {
                stmt.setInt(1, bookId);
                stmt.executeUpdate();
                System.out.println("✅ Book returned successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllBorrowedBooks() {
        String query = """
            SELECT bb.borrower_id, b.name, b.email, bo.title 
            FROM borrowed_books bb
            JOIN borrowers b ON bb.borrower_id = b.borrower_id
            JOIN books bo ON bb.book_id = bo.book_id
            ORDER BY bb.borrower_id
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("📋 List of Borrowed Books:");
            while (rs.next()) {
                int borrowerId = rs.getInt("borrower_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String bookTitle = rs.getString("title");

                System.out.printf("👤 %s (ID: %d, Email: %s) ➡️ \"%s\"%n", name, borrowerId, email, bookTitle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
