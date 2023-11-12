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
    ObservableList<Book> foundBooks = FXCollections.observableArrayList();
    public Stage stage;
    public Scene scene;
    @FXML
    public TableView tableView;
    @FXML
    public TableColumn<Book, String> authorColumn;
    @FXML
    public TableColumn<Book, String> titleColumn;
    @FXML
    public TableColumn<Book, Integer> yearColumn;
    @FXML
    public TableColumn<Book, Integer> pagesColumn;
    @FXML
    public TableColumn<Book, Integer> publicationsColumn;
    @FXML
    public TableColumn<Book, Boolean> illustrationsColumn;
    @FXML
    public TableColumn<Book, Boolean> coverColumn;
    @FXML
    public TableColumn<Book, Integer> circulationColumn;
    @FXML
    private TextField titleField;
    @FXML
    private TextField yearField;
    @FXML
    private Button findButton;

    @FXML
    public void onFindButtonClick() {
        manageTableColumns();

        if (titleField.getText().isEmpty() && yearField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Both text fields can't be empty!");
            alert.show();
        } else if (titleField.getText().isEmpty()) {
            int year;

            try {
                year = Integer.parseInt(yearField.getText());
                foundBooks.clear();
                foundBooks.addAll(findBook(year));
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
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
                Alert alert = new Alert(Alert.AlertType.WARNING);
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

    private void manageTableColumns() {
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
        pagesColumn.setCellValueFactory(new PropertyValueFactory<>("pagesNum"));
        publicationsColumn.setCellValueFactory(new PropertyValueFactory<>("publicationNum"));
        illustrationsColumn.setCellValueFactory(new PropertyValueFactory<>("hasImages"));
        coverColumn.setCellValueFactory(new PropertyValueFactory<>("hasSolidCover"));
        circulationColumn.setCellValueFactory(new PropertyValueFactory<>("circulation"));

        tableView.setItems(foundBooks);
        tableView.refresh();
    }
}