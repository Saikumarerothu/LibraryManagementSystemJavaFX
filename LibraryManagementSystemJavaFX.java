import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;

class Book {
    private String title;
    private String author;
    private boolean isBorrowed;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.isBorrowed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void borrowBook() {
        this.isBorrowed = true;
    }

    public void returnBook() {
        this.isBorrowed = false;
    }
}

class Library {
    private ArrayList<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public String displayAvailableBooks() {
        StringBuilder availableBooks = new StringBuilder("Available Books:\n");
        for (Book book : books) {
            if (!book.isBorrowed()) {
                availableBooks.append(book.getTitle()).append(" by ").append(book.getAuthor()).append("\n");
            }
        }
        return availableBooks.toString();
    }

    public String borrowBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (!book.isBorrowed()) {
                    book.borrowBook();
                    return "You borrowed: " + book.getTitle();
                } else {
                    return "Sorry, the book is already borrowed.";
                }
            }
        }
        return "Sorry, the book is not available.";
    }

    public String returnBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                if (book.isBorrowed()) {
                    book.returnBook();
                    return "You returned: " + book.getTitle();
                } else {
                    return "This book wasn't borrowed.";
                }
            }
        }
        return "Sorry, the book is not part of our library.";
    }
}

public class LibraryManagementSystemJavaFX extends Application {
    Library library = new Library();

    @Override
    public void start(Stage primaryStage) {
        // UI Components
        Label titleLabel = new Label("Book Title:");
        TextField titleInput = new TextField();
        
        Label authorLabel = new Label("Book Author:");
        TextField authorInput = new TextField();

        Button addButton = new Button("Add Book");
        Button displayButton = new Button("Display Available Books");
        Button borrowButton = new Button("Borrow Book");
        Button returnButton = new Button("Return Book");

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);

        // Set button actions
        addButton.setOnAction(e -> {
            String title = titleInput.getText().trim();
            String author = authorInput.getText().trim();
            if (!title.isEmpty() && !author.isEmpty()) {
                library.addBook(new Book(title, author));
                outputArea.setText("Book added: " + title);
            } else {
                outputArea.setText("Please enter both title and author.");
            }
            titleInput.clear();
            authorInput.clear();
        });

        displayButton.setOnAction(e -> {
            outputArea.setText(library.displayAvailableBooks());
        });

        borrowButton.setOnAction(e -> {
            String title = titleInput.getText().trim();
            if (!title.isEmpty()) {
                outputArea.setText(library.borrowBook(title));
            } else {
                outputArea.setText("Please enter a title to borrow.");
            }
            titleInput.clear();
        });

        returnButton.setOnAction(e -> {
            String title = titleInput.getText().trim();
            if (!title.isEmpty()) {
                outputArea.setText(library.returnBook(title));
            } else {
                outputArea.setText("Please enter a title to return.");
            }
            titleInput.clear();
        });

        // Layout
        VBox inputLayout = new VBox(10);
        inputLayout.getChildren().addAll(titleLabel, titleInput, authorLabel, authorInput);

        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(addButton, displayButton, borrowButton, returnButton);

        VBox layout = new VBox(20);
        layout.getChildren().addAll(inputLayout, buttonLayout, outputArea);
        layout.setPadding(new Insets(20));

        // Set the scene
        Scene scene = new Scene(layout, 500, 400);
        primaryStage.setTitle("Library Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
