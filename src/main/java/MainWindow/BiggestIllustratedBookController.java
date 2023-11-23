package MainWindow;

import common.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class BiggestIllustratedBookController {
    // Масив ілюстрованих книг з найбільшою кількістю сторінок
    private ObservableList<Book> biggestIllustratedBooks = FXCollections.observableArrayList();

    // Усі візуальні об'єкти вікна пошуку ілюстрованих книг з найбільшою кількістю сторінок
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

    // Функція, яка викликається при натисканні на кнопку
    @FXML
    public void onGetMaxButtonClick() {
        biggestIllustratedBooks.clear();
        biggestIllustratedBooks.addAll(getMax());
    }

    public ObservableList<Book> getMax() {
        // Встановлення відповідності, колонок й полів класу Book
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
            // Якщо масив res порожній, і книга ілюстрована, то додаємо цю книгу до res
            if (res.isEmpty()) {
                if (book.isHasImages()) {
                    res.add(book);
                }
                continue;
            }

            // Якщо книга ілюстрована і кількість сторінок така ж як у записаних у масиві
            // res, то додаємо цю книгу до res

            // Якщо книга ілюстрована і кількість сторінок більша, ніж у записаних у масиві
            // res, то додаємо цю книгу до res, перед тим очистивши попередній вміст масиву
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