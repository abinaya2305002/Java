import java.util.Scanner;

abstract class Book {
    private final String title;
    private final String author;
    private final double price;

    public Book(String title, String author, double price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public abstract void displayDetails();
}
class Ebook extends Book {
    public Ebook(String title, String author, double price) {
        super(title, author, price);
    }

    @Override
    public void displayDetails() {
        System.out.println("E-Book: " + getTitle() + " by " + getAuthor());
        System.out.println("Price: $" + getPrice());
    }
}
class PrintedBook extends Book {
    private final int stock;

    public PrintedBook(String title, String author, double price, int stock) {
        super(title, author, price);
        this.stock = stock;
    }

    @Override
    public void displayDetails() {
        System.out.println("Printed Book: " + getTitle() + " by " + getAuthor());
        System.out.println("Price: $" + getPrice());
        System.out.println("Stock Available: " + stock);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter details for the E-Book:");
        System.out.print("Title: ");
        String ebookTitle = scanner.nextLine();
        System.out.print("Author: ");
        String ebookAuthor = scanner.nextLine();
        System.out.print("Price: ");
        double ebookPrice = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("\nEnter details for the Printed Book:");
        System.out.print("Title: ");
        String printedTitle = scanner.nextLine();
        System.out.print("Author: ");
        String printedAuthor = scanner.nextLine();
        System.out.print("Price: ");
        double printedPrice = scanner.nextDouble();
        System.out.print("Stock Available: ");
        int stock = scanner.nextInt();
        Book ebook = new Ebook(ebookTitle, ebookAuthor, ebookPrice);
        Book printedBook = new PrintedBook(printedTitle, printedAuthor, printedPrice, stock);
        System.out.println("\nBook Details");
        ebook.displayDetails();
        printedBook.displayDetails();

        scanner.close();
    }
}

