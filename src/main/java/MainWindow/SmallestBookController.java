package MainWindow;

import common.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SmallestBookController {
    // Масив книг з найменшою кількістю сторінок та з найбільшою кількістю примірників за рік
    private ObservableList<Book> foundBooks = FXCollections.observableArrayList();

    // Усі візуальні об'єкти вікна пошуку найменших книг та книг з найбільшою кількістю примірників за рік
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

    // Функція, яка викликається при натисканні на кнопку
    @FXML
    public void onFindButtonClick() {
        foundBooks.clear();

        // Якщо поле року видання пусте, виводиться вікно про помилку й функція завершується
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
            // Якщо поле року видання містить не числове значення, то виводиться вікно про помилку
            // й функція завершується
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error: incompatible type");
            alert.setContentText("\"Year\" field can contain only numbers.");
            alert.show();
            return;
        }
    }

    public ObservableList<Book> getMinPagesAndMaxCirculation(int year) {
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

        // У окремі масиви записуються найменші та найпопулярніші книги
        ObservableList<Book> minPages = FXCollections.observableArrayList();
        ObservableList<Book> maxCirculation = FXCollections.observableArrayList();
        ObservableList<Book> books = MainController.getBooks();
        for (Book book : books) {
            // Якщо масив minPages порожній, і рік книги збігається з вказаним, то додаємо цю книгу до minPages
            if (minPages.isEmpty()) {
                if (book.getPublicationYear() == year) {
                    minPages.add(book);
                }
                continue;
            }

            // Якщо книга з того ж року видання, що й вказано, і кількість сторінок така ж як у записаних у масиві
            // minPages, то додаємо цю книгу до minPages

            // Якщо книга з того ж року видання, що й вказано, і кількість сторінок менша, ніж у записаних у масиві
            // minPages, то додаємо цю книгу до minPages, перед тим очистивши попередній вміст масиву
            if (book.getPublicationYear() == year && book.getPagesNum() == minPages.get(0).getPagesNum()) {
                minPages.add(book);
            } else if (book.getPublicationYear() == year && book.getPagesNum() < minPages.get(0).getPagesNum()) {
                minPages.clear();
                minPages.add(book);
            }
        }

        // Така сама логіка застосовується для пошуку найпопулярніших книг (лише тепер ведеться пошук максимального,
        // а не мінімального значення)
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

        // Видаляються книги, спільні для обох масивів, щоб уникнути дублювання даних
        for (int i = 0; i < maxCirculation.size(); i++) {
            if (minPages.contains(maxCirculation.get(i))) {
                maxCirculation.remove(maxCirculation.get(i));
                --i;
            }
        }

        // Вміст обох масивів об'єднується в один загальний масив
        ObservableList<Book> res = FXCollections.observableArrayList();
        res.addAll(minPages);
        res.addAll(maxCirculation);

        return res;
    }
}