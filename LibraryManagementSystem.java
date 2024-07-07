package s_java_project;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Book {
    String title;
    String author;
    String ISBN;
    boolean availability;
    String borrowedBy;

    public Book(String title, String author, String ISBN, boolean availability) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.availability = availability;
        this.borrowedBy = null;
    }
}

class Library {
    ArrayList<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(String ISBN) {
        boolean removed = books.removeIf(book -> book.ISBN.equals(ISBN));

        if (removed) {
            System.out.println("Book with ISBN " + ISBN + " has been removed.");
        } else {
            System.out.println("Book with ISBN " + ISBN + " was not found in the library.");
        }
    }

    public void displayBooks() {
        for (Book book : books) {
            System.out.println("Title: " + book.title);
            System.out.println("Author: " + book.author);
            System.out.println("ISBN: " + book.ISBN);
            System.out.println("Availability: " + (book.availability ? "Available" : "Not Available"));
            if (!book.availability) {
                System.out.println("Borrowed by: " + book.borrowedBy);
            }
            System.out.println();
        }
    }

    public void displayBorrowedBooks() {
        System.out.println("All Borrowed Books:");
        boolean foundBorrowed = false;
        for (Book book : books) {
            if (!book.availability) {
                System.out.println("Title: " + book.title);
                System.out.println("Author: " + book.author);
                System.out.println("ISBN: " + book.ISBN);
                System.out.println("Borrowed by: " + book.borrowedBy);
                System.out.println();
                foundBorrowed = true;
            }
        }
        if (!foundBorrowed) {
            System.out.println("No books are currently borrowed.");
        }
    }

    public void borrowBookToUser(String borrowerName, String ISBN) {
        Book book = findBookByISBN(ISBN);
        if (book != null && book.availability) {
            book.availability = false;
            book.borrowedBy = borrowerName;
            System.out.println("Book with ISBN " + ISBN + " has been borrowed by " + borrowerName + ".");
        } else {
            System.out.println("Book with ISBN " + ISBN + " is either not available or does not exist.");
        }
    }

    public void returnBookToLibrary(String ISBN) {
        Book book = findBookByISBN(ISBN);
        if (book != null && !book.availability) {
            book.availability = true;
            book.borrowedBy = null;
            System.out.println("Book with ISBN " + ISBN + " has been returned to the library.");
        } else {
            System.out.println("Book with ISBN " + ISBN + " is either already available or does not exist.");
        }
    }

    private Book findBookByISBN(String ISBN) {
        for (Book book : books) {
            if (book.ISBN.equals(ISBN)) {
                return book;
            }
        }
        return null;
    }
}

public class LibraryManagementSystem {
    private static final String ADMIN_ID = "admin";
    private static final String ADMIN_PASSWORD = "password";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        library.addBook(new Book("Java Programming", "John Doe", "1", true));
        library.addBook(new Book("Python Basics", "Jane Smith", "2", true));
        library.addBook(new Book("Data Structures and Algorithms", "Alice Johnson", "3", true));

        System.out.println("+-------------------------------------+");
        System.out.println("|          Library Management         |");
        System.out.println("+-------------------------------------+");

        adminLogin(scanner, library);

        scanner.close();
        System.out.println("Exiting Library Management System.");
    }

    private static void adminLogin(Scanner scanner, Library library) {
        System.out.println("Admin Login");
        System.out.print("Enter Admin ID: ");
        String adminIdInput = scanner.nextLine();
        System.out.print("Enter Admin Password: ");
        String adminPasswordInput = scanner.nextLine();

        if (adminIdInput.equals(ADMIN_ID) && adminPasswordInput.equals(ADMIN_PASSWORD)) {
            adminMenu(scanner, library);
        } else {
            System.out.println("Invalid Admin ID or Password.");
        }
    }

    private static void adminMenu(Scanner scanner, Library library) {
        while (true) {
            try {
                System.out.println("+-------------------------------------+");
                System.out.println("|             Admin Menu              |");
                System.out.println("+-------------------------------------+");
                System.out.println("| 1. Add Book                         |");
                System.out.println("| 2. Remove Book                      |");
                System.out.println("| 3. Display Books                    |");
                System.out.println("| 4. Check All Borrowed Books         |");
                System.out.println("| 5. Borrow Book to User              |");
                System.out.println("| 6. Return Book to Library           |");
                System.out.println("| 7. Logout                           |");
                System.out.println("+-------------------------------------+");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter author: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter ISBN: ");
                        String ISBN = scanner.nextLine();
                        System.out.print("Enter availability (true/false): ");
                        boolean availability = scanner.nextBoolean();
                        scanner.nextLine();
                        library.addBook(new Book(title, author, ISBN, availability));
                        break;
                    case 2:
                        System.out.print("Enter ISBN of the book to remove: ");
                        String removeISBN = scanner.nextLine();
                        library.removeBook(removeISBN);
                        break;
                    case 3:
                        library.displayBooks();
                        break;
                    case 4:
                        library.displayBorrowedBooks();
                        break;
                    case 5:
                        System.out.print("Enter borrower name: ");
                        String borrowerName = scanner.nextLine();
                        System.out.print("Enter ISBN of the book to borrow: ");
                        String borrowISBN = scanner.nextLine();
                        library.borrowBookToUser(borrowerName, borrowISBN);
                        break;
                    case 6:
                        System.out.print("Enter ISBN of the book to return: ");
                        String returnISBN = scanner.nextLine();
                        library.returnBookToLibrary(returnISBN);
                        break;
                    case 7:
                        System.out.println("Logging out from Admin.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 7.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Input mismatch. Please enter a valid input.");
                scanner.nextLine();
            }
        }
    }
}
