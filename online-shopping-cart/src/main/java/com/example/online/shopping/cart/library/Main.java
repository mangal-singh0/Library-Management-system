package com.example.online.shopping.cart.library;

import com.example.online.shopping.cart.library.dao.BookDAO;
import com.example.online.shopping.cart.library.dao.BorrowService;
import com.example.online.shopping.cart.library.dao.BorrowerDAO;
import com.example.online.shopping.cart.library.model.Book;
import com.example.online.shopping.cart.library.model.Borrower;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BookDAO bookDAO = new BookDAO();
        Scanner scanner = new Scanner(System.in);
        BorrowService borrowService = new BorrowService();
        BorrowerDAO borrower = new BorrowerDAO();

        boolean running = true;

        while (running) {
            System.out.println("\n Book Management Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book Quantity");
            System.out.println("3. View All Books");
            System.out.println("4. Delete Book");
            System.out.println("5. Exit");
            System.out.println("6. Borrow Book");
            System.out.println("7. Return Book");
            System.out.println("8. Add Borrower");
            System.out.println("9. View All Borrowers");
            System.out.println("10. Delete Borrower");
            System.out.println("11. View All Borrowed Books");

            System.out.print(" Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter Genre: ");
                    String genre = scanner.nextLine();
                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();

                    Book book = new Book(id, title, author, genre, quantity);
                    bookDAO.addBook(book);
                    break;

                case 2:
                    System.out.print("Enter Book ID to update: ");
                    int updateId = scanner.nextInt();
                    System.out.print("Enter new Quantity: ");
                    int newQty = scanner.nextInt();
                    bookDAO.updateBookQuantity(updateId, newQty);
                    break;

                case 3:
                    List<Book> books = bookDAO.getAllBooks();
                    System.out.println(" Available Books:");
                    for (Book b : books) {
                        System.out.println(b);
                    }
                    break;

                case 4:
                    System.out.print("Enter Book ID to delete: ");
                    int deleteId = scanner.nextInt();
                    bookDAO.deleteBook(deleteId);
                    break;

                case 5:
                    running = false;
                    System.out.println(" Exiting... Goodbye!");
                    break;

                case 6:
                    System.out.print("Enter Borrower ID: ");
                    int borrowerId = scanner.nextInt();
                    System.out.print("Enter Book ID: ");
                    int bookId = scanner.nextInt();
                    borrowService.borrowBook(borrowerId, bookId);
                    break;

                case 7:
                    System.out.print("Enter Borrower ID: ");
                    borrowerId = scanner.nextInt();
                    System.out.print("Enter Book ID: ");
                    bookId = scanner.nextInt();
                    borrowService.returnBook(borrowerId, bookId);
                    break;

                case 8:
                    System.out.print("Enter Borrower ID: ");
                    borrowerId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter Borrower Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    Borrower b = new Borrower(borrowerId, name, email);
                    borrower.addBorrower(b);
                    break;

                case 9:
                    List<Borrower> borrowerList = borrower.getAllBorrowers();
                    System.out.println(" All Borrowers:");
                    for (Borrower bb : borrowerList) {
                        System.out.println(bb);
                    }
                    break;

                case 10:
                    System.out.print("Enter Borrower ID to delete: ");
                    borrowerId = scanner.nextInt();
                    borrower.deleteBorrower(borrowerId);
                    break;

                case 11:
                    borrowService.getAllBorrowedBooks();
                    break;

                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}
