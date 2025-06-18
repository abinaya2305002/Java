

import java.sql.*;
import java.util.Scanner;

class Book {
    int id;
    String title;
    String author;
    int quantity;

    public Book() {}

    public Book(String title, String author, int quantity) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }
}

class BookQueries {
    public static void addBook(Connection conn, Book b) {
        try {
            String sql = "INSERT INTO books (title, author, quantity) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, b.title);
            stmt.setString(2, b.author);
            stmt.setInt(3, b.quantity);
            stmt.executeUpdate();
            System.out.println("Book added successfully.");
        } catch (SQLException e) {
            System.err.println("Error adding book: " + e.getMessage());
        }
    }

    public static void displayBooks(Connection conn) {
        try {
            String sql = "SELECT * FROM books";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("Book List:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Title: " + rs.getString("title") +
                        ", Author: " + rs.getString("author") +
                        ", Quantity: " + rs.getInt("quantity"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving books: " + e.getMessage());
        }
    }

    public static void deleteBook(Connection conn, int id) {
        try {
            String sql = "DELETE FROM books WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int affected = stmt.executeUpdate();
            if (affected > 0)
                System.out.println("Book deleted successfully.");
            else
                System.out.println("Book with ID " + id + " not found.");
        } catch (SQLException e) {
            System.err.println("Error deleting book: " + e.getMessage());
        }
    }

    public static void updateQuantity(Connection conn, int id, int quantity) {
        try {
            String sql = "UPDATE books SET quantity = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, quantity);
            stmt.setInt(2, id);
            int affected = stmt.executeUpdate();
            if (affected > 0)
                System.out.println("Quantity updated successfully.");
            else
                System.out.println("Book with ID " + id + " not found.");
        } catch (SQLException e) {
            System.err.println("Error updating quantity: " + e.getMessage());
        }
    }
}

public class Main{
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/bookstore";
        String user = "root";
        String password = "Abi@6911";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the database.");

            while (true) {
                System.out.println("\nBookstore Management System");
                System.out.println("1. Add Book");
                System.out.println("2. View All Books");
                System.out.println("3. Delete Book");
                System.out.println("4. Update Book Quantity");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter Book Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter Author Name: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter Quantity: ");
                        int qty = scanner.nextInt();
                        scanner.nextLine();
                        Book newBook = new Book(title, author, qty);
                        BookQueries.addBook(conn, newBook);
                        break;

                    case 2:
                        BookQueries.displayBooks(conn);
                        break;

                    case 3:
                        System.out.print("Enter Book ID to delete: ");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine();
                        BookQueries.deleteBook(conn, deleteId);
                        break;

                    case 4:
                        System.out.print("Enter Book ID to update quantity: ");
                        int updateId = scanner.nextInt();
                        System.out.print("Enter new quantity: ");
                        int newQty = scanner.nextInt();
                        scanner.nextLine();
                        BookQueries.updateQuantity(conn, updateId, newQty);
                        break;

                    case 5:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }
}
