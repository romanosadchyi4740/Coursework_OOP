package MainWindow;

import common.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class FindBookController {
    // Масив знайдених книг, що відповідають заданим умовам
    private ObservableList<Book> foundBooks = FXCollections.observableArrayList();
    public Stage stage;
    public Scene scene;
    // Усі візуальні об'єкти вікна пошуку книг
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

    // Функція, що виконується при натисканні на кнопку
    @FXML
    public void onFindButtonClick() {
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
                foundBooks
        );

        Alert alert;
        if (titleField.getText().isEmpty() && yearField.getText().isEmpty()) {
            // Якщо обидва поля пошуку пусті, то записуємо відповідну інформацію у вікно помилки й виводимо його.
            // Функція завершується
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error: empty fields");
            alert.setContentText("Both text fields can't be empty!");
            alert.show();
        } else if (titleField.getText().isEmpty()) {
            // Якщо пусте лише поле назви книги, то пошук здійснюється за роком видання
            int year;

            try {
                year = Integer.parseInt(yearField.getText());
                foundBooks.clear();
                foundBooks.addAll(findBook(year));
            } catch (NumberFormatException e) {
                // Якщо ж при цьому значення в полі введення року не є числом, то записуємо відповідну інформацію
                // у вікно помилки й виводимо його. Функція завершується
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error: incompatible type");
                alert.setContentText("\"Year\" field can only contain numbers!");
                alert.show();
            }
        } else if (yearField.getText().isEmpty()) {
            // Якщо пусте лише поле року видання, то пошук здійснюється за назвою книги
            foundBooks.clear();
            foundBooks.addAll(findBook(titleField.getText()));
        } else {
            // Якщо обидва поля заповнені, то додаються до списку лише ті книжки, для котрих правдиві обидві умови
            // одночасно
            String title = titleField.getText();
            int year;

            try {
                year = Integer.parseInt(yearField.getText());
                foundBooks.clear();
                foundBooks.addAll(findBook(title, year));
            } catch (NumberFormatException e) {
                // Якщо ж при цьому значення в полі введення року не є числом, то записуємо відповідну інформацію
                // у вікно попередження й виводимо його. Функція продовжує виконання (відбувається пошук лише за назвою)
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Warning: incompatible type");
                alert.setContentText("\"Year\" field can only contain numbers!\nSearch will be executed by title.");
                alert.show();

                foundBooks.clear();
                foundBooks.addAll(findBook(title));
            }
        }
    }

    // Далі наведена реалізація трьох варіацій перевантаженої функції пошуку книг

    // Пошук за назвою та за роком видання
    private ObservableList<Book> findBook(String title, int year) {
        ObservableList<Book> res = FXCollections.observableArrayList();
        ObservableList<Book> books = MainController.getBooks();

        // Перевірка на те, чи не є загальний список книг пустим
        if (books == null || books.isEmpty()) {
            return res;
        }

        // Додаємо до списку знайдених книг ті, котрі відповідають обидвом умовам одночасно
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

    // Реалізація практично така сама, як і в попередньої функції, за винятком того, що додавання книг відбувається на
    // основі лише умови рівності назви книг
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

    // Реалізація практично така сама, як і в попередньої функції, за винятком того, що додавання книг відбувається на
    // основі лише умови рівності року видання
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