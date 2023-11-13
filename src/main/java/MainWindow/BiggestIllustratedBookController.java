package MainWindow;

import common.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class BiggestIllustratedBookController {
    private ObservableList<Book> biggestIllustratedBooks = FXCollections.observableArrayList();
    @FXML
    public TableView<Book> tableView;
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
    public Button getMaxButton;

    @FXML
    public void onGetMaxButtonClick() {
        biggestIllustratedBooks.clear();
        biggestIllustratedBooks.addAll(getMax());
    }

    public ObservableList<Book> getMax() {
        MainController.tableManager(authorColumn,
                titleColumn,
                yearColumn,
                pagesColumn,
                publicationsColumn,
                illustrationsColumn,
                coverColumn,
                circulationColumn,
                tableView,
                biggestIllustratedBooks
        );

        ObservableList<Book> res = FXCollections.observableArrayList();
        ObservableList<Book> books = MainController.getBooks();
        for (Book book : books) {
            if (res.isEmpty()) {
                if (book.isHasImages()) {
                    res.add(book);
                }
                continue;
            }

            if (book.isHasImages() && book.getPagesNum() == res.get(0).getPagesNum()) {
                res.add(book);
            } else if (book.isHasImages() && book.getPagesNum() > res.get(0).getPagesNum()) {
                res.clear();
                res.add(book);
            }
        }

        return res;
    }
}