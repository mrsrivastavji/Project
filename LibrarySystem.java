import java.util.*;

class Book {
    private String id;
    private String title;
    private String author;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }

    @Override
    public String toString() {
        return "ID: " + id + " | Title: " + title + " | Author: " + author;
    }
}

public class LibrarySystem {

    private static ArrayList<Book> books = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;

        while (true) {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Search Book");
            System.out.println("4. View All Books");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            // Validate integer input
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Please enter a number: ");
                sc.next();
            }
            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1->addBook();
                case 2->removeBook();
                case 3->searchBook();
                case 4->viewBooks();
                case 5-> {
                System.out.println("Exiting the system... Goodbye!");
                return;
                }
                default->System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // Add Book
    private static void addBook() {
        System.out.print("Enter Book ID: ");
        String id = sc.nextLine().trim();

        // Check duplicate ID
        for (Book b : books) {
            if (b.getId().equalsIgnoreCase(id)) {
                System.out.println("Book ID already exists!");
                return;
            }
        }

        System.out.print("Enter Book Title: ");
        String title = sc.nextLine().trim();

        System.out.print("Enter Book Author: ");
        String author = sc.nextLine().trim();

        if (id.isEmpty() || title.isEmpty() || author.isEmpty()) {
            System.out.println("Fields cannot be empty!");
            return;
        }

        books.add(new Book(id, title, author));
        System.out.println("Book added successfully!");
    }

    // Remove Book
    private static void removeBook() {
        System.out.print("Enter Book ID to remove: ");
        String id = sc.nextLine().trim();

        for (Book b : books) {
            if (b.getId().equalsIgnoreCase(id)) {
                books.remove(b);
                System.out.println("Book removed successfully!");
                return;
            }
        }
        System.out.println("Book not found!");
    }

    // Search Book
    private static void searchBook() {
        System.out.print("Enter keyword (ID/Title/Author): ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;

        for (Book b : books) {
            if (b.getId().toLowerCase().contains(keyword) ||
                b.getTitle().toLowerCase().contains(keyword) ||
                b.getAuthor().toLowerCase().contains(keyword)) {

                System.out.println(b);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching book found!");
        }
    }

    // View All Books
    private static void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library!");
            return;
        }

        System.out.println("\n---- Book List ----");
        for (Book b : books) {
            System.out.println(b);
        }
    }
}