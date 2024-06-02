import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Book {
    int id;
    String title;
    String author;
    String category;
    boolean isAvailable;

    public Book(int id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isAvailable = true;
    }

    @Override
    public String toString() {
        return id + ". " + title + " by " + author + " [" + category + "] " + (isAvailable ? "Available" : "Not Available");
    }
}

class User {
    int id;
    String name;
    String email;
    ArrayList<Book> issuedBooks;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.issuedBooks = new ArrayList<>();
    }

    @Override
    public String toString() {
        return id + ". " + name + " (" + email + ")";
    }
}

public class LibraryManagementSystem {
    static HashMap<Integer, Book> books = new HashMap<>();
    static HashMap<Integer, User> users = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);
    static int bookIdCounter = 1;
    static int userIdCounter = 1;

    public static void main(String[] args) {
        initializeSampleData();
        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    userMenu();
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    public static void adminMenu() {
        while (true) {
            System.out.println("Admin Menu");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. View All Books");
            System.out.println("5. Add User");
            System.out.println("6. View All Users");
            System.out.println("7. Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    updateBook();
                    break;
                case 3:
                    deleteBook();
                    break;
                case 4:
                    viewAllBooks();
                    break;
                case 5:
                    addUser();
                    break;
                case 6:
                    viewAllUsers();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    public static void userMenu() {
        System.out.print("Enter User ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        User user = users.get(userId);
        if (user == null) {
            System.out.println("Invalid User ID!");
            return;
        }
        while (true) {
            System.out.println("User Menu");
            System.out.println("1. View Books");
            System.out.println("2. Search Book");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Query");
            System.out.println("6. Back");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    viewAllBooks();
                    break;
                case 2:
                    searchBook();
                    break;
                case 3:
                    issueBook(user);
                    break;
                case 4:
                    returnBook(user);
                    break;
                case 5:
                    query(user);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    public static void addBook() {
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Book Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter Book Category: ");
        String category = scanner.nextLine();
        Book book = new Book(bookIdCounter++, title, author, category);
        books.put(book.id, book);
        System.out.println("Book added successfully!");
    }

    public static void updateBook() {
        System.out.print("Enter Book ID to update: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        Book book = books.get(bookId);
        if (book == null) {
            System.out.println("Invalid Book ID!");
            return;
        }
        System.out.print("Enter new Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter new Book Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter new Book Category: ");
        String category = scanner.nextLine();
        book.title = title;
        book.author = author;
        book.category = category;
        System.out.println("Book updated successfully!");
    }

    public static void deleteBook() {
        System.out.print("Enter Book ID to delete: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        if (books.remove(bookId) != null) {
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Invalid Book ID!");
        }
    }

    public static void viewAllBooks() {
        for (Book book : books.values()) {
            System.out.println(book);
        }
    }

    public static void addUser() {
        System.out.print("Enter User Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter User Email: ");
        String email = scanner.nextLine();
        User user = new User(userIdCounter++, name, email);
        users.put(user.id, user);
        System.out.println("User added successfully!");
    }

    public static void viewAllUsers() {
        for (User user : users.values()) {
            System.out.println(user);
        }
    }

    public static void searchBook() {
        System.out.print("Enter Book Title to search: ");
        String title = scanner.nextLine();
        for (Book book : books.values()) {
            if (book.title.equalsIgnoreCase(title)) {
                System.out.println(book);
            }
        }
    }

    public static void issueBook(User user) {
        System.out.print("Enter Book ID to issue: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        Book book = books.get(bookId);
        if (book == null || !book.isAvailable) {
            System.out.println("Invalid Book ID or Book is not available!");
            return;
        }
        book.isAvailable = false;
        user.issuedBooks.add(book);
        System.out.println("Book issued successfully!");
    }

    public static void returnBook(User user) {
        System.out.print("Enter Book ID to return: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        Book book = books.get(bookId);
        if (book == null || book.isAvailable) {
            System.out.println("Invalid Book ID or Book was not issued!");
            return;
        }
        book.isAvailable = true;
        user.issuedBooks.remove(book);
        System.out.println("Book returned successfully!");
    }

    public static void query(User user) {
        System.out.print("Enter your query: ");
        String query = scanner.nextLine();
        System.out.println("Query received: " + query);
        System.out.println("An email will be sent to " + user.email);
    }

    public static void initializeSampleData() {
        books.put(bookIdCounter++, new Book(1, "The Great Gatsby", "F. Scott Fitzgerald", "Fiction"));
        books.put(bookIdCounter++, new Book(2, "1984", "George Orwell", "Dystopian"));
        books.put(bookIdCounter++, new Book(3, "To Kill a Mockingbird", "Harper Lee", "Classic"));
        users.put(userIdCounter++, new User(1, "Alice", "alice@example.com"));
        users.put(userIdCounter++, new User(2, "Bob", "bob@example.com"));
    }
}


