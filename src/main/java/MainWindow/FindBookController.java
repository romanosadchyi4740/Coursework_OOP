package MainWindow;

import common.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FindBookController {
    private ObservableList<Book> foundBooks = FXCollections.observableArrayList();
    public Stage stage;
    public Scene scene;
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, Integer> yearColumn;
    @FXML
    private TableColumn<Book, Integer> pagesColumn;
    @FXML
    private TableColumn<Book, Integer> publicationsColumn;
    @FXML
    private TableColumn<Book, Boolean> illustrationsColumn;
    @FXML
    private TableColumn<Book, Boolean> coverColumn;
    @FXML
    private TableColumn<Book, Integer> circulationColumn;
    @FXML
    private TextField titleField;
    @FXML
    private TextField yearField;
    @FXML
    private Button findButton;

    @FXML
    public void onFindButtonClick() {
        MainController.tableManager(authorColumn,
                titleColumn,
                yearColumn,
                pagesColumn,
                publicationsColumn,
                illustrationsColumn,
                coverColumn,
                circulationColumn,
                tableView,
                foundBooks
        );

        Alert alert;
        if (titleField.getText().isEmpty() && yearField.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error: empty fields");
            alert.setContentText("Both text fields can't be empty!");
            alert.show();
        } else if (titleField.getText().isEmpty()) {
            int year;

            try {
                year = Integer.parseInt(yearField.getText());
                foundBooks.clear();
                foundBooks.addAll(findBook(year));
            } catch (NumberFormatException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error: incompatible type");
                alert.setContentText("\"Year\" field can only contain numbers!");
                alert.show();
            }
        } else if (yearField.getText().isEmpty()) {
            foundBooks.clear();
            foundBooks.addAll(findBook(titleField.getText()));
        } else {
            String title = titleField.getText();
            int year;

            try {
                year = Integer.parseInt(yearField.getText());
                foundBooks.clear();
                foundBooks.addAll(findBook(title, year));
            } catch (NumberFormatException e) {
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Warning: incompatible type");
                alert.setContentText("\"Year\" field can only contain numbers!\nSearch will be executed by title.");
                alert.show();

                foundBooks.clear();
                foundBooks.addAll(findBook(title));
            }
        }
    }

    private ObservableList<Book> findBook(String title, int year) {
        ObservableList<Book> res = FXCollections.observableArrayList();
        ObservableList<Book> books = MainController.getBooks();
        if (books == null || books.isEmpty()) {
            return res;
        }

        for (Book book : books) {
            if (book.getTitle().equals(title) && book.getPublicationYear() == year) {
                res.add(book);
            }
        }

        if (res.isEmpty()) {
            return null;
        }

        return res;
    }

    private ObservableList<Book> findBook(String title) {
        ObservableList<Book> res = FXCollections.observableArrayList();
        ObservableList<Book> books = MainController.getBooks();
        if (books == null || books.isEmpty()) {
            return res;
        }

        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                res.add(book);
            }
        }

        if (res.isEmpty()) {
            return null;
        }

        return res;
    }

    private ObservableList<Book> findBook(int year) {
        ObservableList<Book> res = FXCollections.observableArrayList();
        ObservableList<Book> books = MainController.getBooks();
        if (books == null || books.isEmpty()) {
            return res;
        }

        for (Book book : books) {
            if (book.getPublicationYear() == year) {
                res.add(book);
            }
        }

        if (res.isEmpty()) {
            return null;
        }

        return res;
    }
}