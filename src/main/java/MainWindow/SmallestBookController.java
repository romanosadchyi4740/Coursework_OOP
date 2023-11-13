package MainWindow;

import common.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SmallestBookController {
    private ObservableList<Book> foundBooks = FXCollections.observableArrayList();
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
    private TextField yearTextField;
    @FXML
    private Button findButton;

    @FXML
    public void onFindButtonClick() {
        foundBooks.clear();

        if (yearTextField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error: empty fields");
            alert.setContentText("\"Year\" field can't be empty!");
            alert.show();
            return;
        }

        int year = 0;
        try {
            year = Integer.parseInt(yearTextField.getText());
            foundBooks.addAll(getMinPagesAndMaxCirculation(year));
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error: incompatible type");
            alert.setContentText("\"Year\" field can contain only numbers.");
            alert.show();
            return;
        }
    }

    public ObservableList<Book> getMinPagesAndMaxCirculation(int year) {
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

        ObservableList<Book> minPages = FXCollections.observableArrayList();
        ObservableList<Book> maxCirculation = FXCollections.observableArrayList();
        ObservableList<Book> books = MainController.getBooks();
        for (Book book : books) {
            if (minPages.isEmpty()) {
                if (book.getPublicationYear() == year) {
                    minPages.add(book);
                }
                continue;
            }

            if (book.getPublicationYear() == year && book.getPagesNum() == minPages.get(0).getPagesNum()) {
                minPages.add(book);
            } else if (book.getPublicationYear() == year && book.getPagesNum() < minPages.get(0).getPagesNum()) {
                minPages.clear();
                minPages.add(book);
            }
        }

        for (Book book : books) {
            if (maxCirculation.isEmpty()) {
                if (book.getPublicationYear() == year) {
                    maxCirculation.add(book);
                }
                continue;
            }

            if (book.getPublicationYear() == year && book.getCirculation() == maxCirculation.get(0).getCirculation()) {
                maxCirculation.add(book);
            } else if (book.getPublicationYear() == year && book.getCirculation() > maxCirculation.get(0).getCirculation()) {
                maxCirculation.clear();
                maxCirculation.add(book);
            }
        }

        for (int i = 0; i < maxCirculation.size(); i++) {
            if (minPages.contains(maxCirculation.get(i))) {
                maxCirculation.remove(maxCirculation.get(i));
                --i;
            }
        }

        ObservableList<Book> res = FXCollections.observableArrayList();
        res.addAll(minPages);
        res.addAll(maxCirculation);

        return res;
    }
}