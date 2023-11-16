package MainWindow;

import common.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

public class MainController {
    // Масив для зберігання усіх створених об'єктів Book. У Java, звісно, є можливість створити "традиційний" масив
    // (Book[]), але бібліотека java.util.* надає також дуже зручний клас-замінник звичайних масивів, який називається
    // ArrayList. Він є Generic (шаблонним) класом, що дає можливість зберігати в ньому дані будь-яких типів, окрім
    // примітивів (замість них для вирішення цієї проблеми використовуються стандартні класи обгортки примітивних типів)
    // В даному випадку використовується клас ObservableList. Він містить той самий функціонал, що й ArrayList, але є
    // створеним спеціально для фреймворку JavaFX, і виконує одну важливу функцію, якої немає у ArrayList: щоразу,
    // коли його вміст змінюється, UI змінює свій стан автоматично, без жодних додаткових зусиль з боку програміста.
    private static ObservableList<Book> books = FXCollections.observableArrayList();
    // main window elements
    public static Stage stage;
    public static Scene scene;

    // Усі поля з анотацією @FXML, ініціалізуються автоматично за допомогою так званого Field Injection. Це значить, що
    // вони ініціалізуються одразу після створення відповідних об'єктів у вікні програми.
    @FXML
    private TableView<Book> tableView; // TableView<Book> - таблиця, яка містить об'єкти Book

    // TableColumn - колонка таблиці TableView
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

    // Кнопки головного вікна
    @FXML
    private Button loadTableButton;
    @FXML
    private Button saveTableButton;
    @FXML
    private Button addBookButton;
    @FXML
    private Button sortButton;
    @FXML
    private Button findBookButton;
    @FXML
    private Button biggestBookButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button smallestBookButton;

    // Для початку роботи з таблицею потрібно вказати, які саме дані повинен містити кожен стовпець.
    // new PropertyValueFactory<>("field") означає, що дана колонка буде містити інформацію про вміст поля field класу,
    // зазначеного при створенні колонки. "field" має мати таку ж назву як і відповідне поле класу
    // (але починатися з малої літери)
    public static void tableManager(TableColumn<Book, String> authorColumn,
                                    TableColumn<Book, String> titleColumn,
                                    TableColumn<Book, Integer> yearColumn,
                                    TableColumn<Book, Integer> pagesColumn,
                                    TableColumn<Book, Integer> publicationsColumn,
                                    TableColumn<Book, Boolean> illustrationsColumn,
                                    TableColumn<Book, Boolean> coverColumn,
                                    TableColumn<Book, Integer> circulationColumn,
                                    TableView<Book> tableView,
                                    ObservableList<Book> books) {
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
        pagesColumn.setCellValueFactory(new PropertyValueFactory<>("pagesNum"));
        publicationsColumn.setCellValueFactory(new PropertyValueFactory<>("publicationNum"));
        illustrationsColumn.setCellValueFactory(new PropertyValueFactory<>("hasImages"));
        coverColumn.setCellValueFactory(new PropertyValueFactory<>("hasSolidCover"));
        circulationColumn.setCellValueFactory(new PropertyValueFactory<>("circulation"));

        tableView.setItems(books);
        tableView.refresh();
    }

    // Методи, позначені @FXML, можна використовувати як поведінку при настанні заданої події.

    // Метод, який відбувається при натисканні кнопки читання з файлу
    @FXML
    public void readFromFile() throws IOException, ClassNotFoundException {
        tableManager(authorColumn,
                titleColumn,
                yearColumn,
                pagesColumn,
                publicationsColumn,
                illustrationsColumn,
                coverColumn,
                circulationColumn,
                tableView,
                books
        );

        fileInput();
    }

    // Метод, який відбувається при натисканні кнопки запису у файл
    @FXML
    public void saveToFile() {
        fileOutput();
    }

    // Метод, який відбувається при натисканні кнопки пошуку книги. Відкриває вікно пошуку. (Код створення кожного
    // нового дочірнього вікна ідентичний, тобто всі інші функції, які відкривають нове вікно працюють точно так само)
    @FXML
    public void onFindBookButtonClick() throws IOException {
        tableManager(authorColumn,
                titleColumn,
                yearColumn,
                pagesColumn,
                publicationsColumn,
                illustrationsColumn,
                coverColumn,
                circulationColumn,
                tableView,
                books
        );

        // Цей рядок відповідає за створення вікна за макетом, описаним у файлі findBook-view.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("findBook-view.fxml"));

        Stage findBookStage = new Stage();
        stage = findBookStage;
        Scene scene = new Scene(fxmlLoader.load());

        // Блокує доступ до головного вікна програми, поки не закриється дочірнє
        findBookStage.initModality(Modality.WINDOW_MODAL);
        findBookStage.initOwner(MainApplication.stage);
        // Втановлення іконки вікна
        findBookStage.getIcons().add(new Image(
                "C:\\Users\\Роман3071\\IdeaProjects\\CourseworkFX\\src\\main\\resources\\Pictures\\img.png"));

        // Додаткові налаштування та вивід вікна
        findBookStage.setTitle("Find books");
        findBookStage.setResizable(false);
        findBookStage.setScene(scene);
        findBookStage.show();
    }

    // Метод, який відбувається при натисканні кнопки сортування
    @FXML
    public void onSortButtonClick() {
        ObservableList<Book> res = FXCollections.observableArrayList();
        ObservableList<ObservableList<Book>> groups = divideToGroups(books);
        for (int i = 0; i < groups.size(); i++) {
            groups.set(i, quickSort(groups.get(i)));
            res.addAll(groups.get(i));
        }

        books.clear();
        books.addAll(res);
    }

    // Поділ основного масиву на групи (підмасиви) з однаковими авторами
    public ObservableList<ObservableList<Book>> divideToGroups(ObservableList<Book> books) {
        ObservableList<ObservableList<Book>> res = FXCollections.observableArrayList();
        for (Book book : books) {
            boolean containsAuthor = false;
            for (int i = 0; i < res.size(); i++) {
                if (res.get(i).get(0).getAuthor().equals(book.getAuthor())) {
                    containsAuthor = true;
                    res.get(i).add(book);
                    break;
                }
            }

            if (!containsAuthor) {
                res.add(FXCollections.observableArrayList());
                res.get(res.size() - 1).add(book);
            }
        }

        return res;
    }

    // Швидке сортування масиву книг за кількістю сторінок
    protected ObservableList<Book> quickSort(ObservableList<Book> list) {
        if (list.size() <= 1) {
            return list;
        }

        ObservableList<Book> sorted;
        ObservableList<Book> smaller = FXCollections.observableArrayList();
        ObservableList<Book> bigger = FXCollections.observableArrayList();
        Book pivot = list.get(0);
        int i;
        Book j;
        for (i = 1; i < list.size(); i++) {
            j = list.get(i);
            if (j.getPagesNum() < pivot.getPagesNum()) {
                smaller.add(j);
            } else {
                bigger.add(j);
            }
        }

        smaller = quickSort(smaller);
        bigger = quickSort(bigger);
        smaller.add(pivot);
        smaller.addAll(bigger);
        sorted = smaller;

        return sorted;
    }

    // Запис масиву книг у файл
    public void fileOutput() {
        // Клас FileChooser надає можливість вибрати файл за допомогою стандартного провідника
        // Це зменшує ймовірність помилки під час використання програми, оскільки так користувач не може помилитися
        // набираючи шлях до файлу, а натомість використовує уже звичний та зручний спосіб пошуку файлів
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Programming\\Coursework"));
        File file = fileChooser.showOpenDialog(MainApplication.stage);

        // Тут використано механізм try-catch з ресурсами - це покращений try-catch, який автоматично закриває усі
        // потоки, створені в дужках після ключового слова try. Тобто, навіть у випадку помилки, використані ресурси
        // звільняються.
        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream(file)
        )) {
            outputStream.writeInt(books.size());

            for (Book book : books) {
                // використання механізму серіалізації, описаного у самому класі Book
                book.writeExternal(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Читання масиву книг із файлу
    public void fileInput() throws ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Programming\\Coursework"));
        File file = fileChooser.showOpenDialog(MainApplication.stage);

        try (ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream(file)
        )) {
            books.clear();
            int size = inputStream.readInt();
            for (int i = 0; i < size; i++) {
                Book newBook = new Book();
                // використання механізму серіалізації, описаного у самому класі Book
                newBook.readExternal(inputStream);
                books.add(newBook);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод, який відбувається при натисканні кнопки додавання книги.
    // Відкриває відповідне вікно.
    @FXML
    public void onAddBookButtonClick() throws IOException {
        tableManager(authorColumn,
                titleColumn,
                yearColumn,
                pagesColumn,
                publicationsColumn,
                illustrationsColumn,
                coverColumn,
                circulationColumn,
                tableView,
                books
        );

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addBooks-view.fxml"));

        scene = MainApplication.stage.getScene();

        Stage addBookStage = new Stage();
        stage = addBookStage;
        Scene scene = new Scene(fxmlLoader.load());

        addBookStage.initModality(Modality.WINDOW_MODAL);
        addBookStage.initOwner(MainApplication.stage);
        addBookStage.getIcons().add(new Image(
                "C:\\Users\\Роман3071\\IdeaProjects\\CourseworkFX\\src\\main\\resources\\Pictures\\img.png"));

        addBookStage.setTitle("New book");
        addBookStage.setResizable(false);
        addBookStage.setScene(scene);
        addBookStage.show();
    }

    public static ObservableList<Book> getBooks() {
        return books;
    }

    // Метод, який відбувається при натисканні кнопки знаходження найбільшої ілюстрованої книги.
    // Відкриває відповідне вікно.
    @FXML
    public void onBiggestBookButtonClick() throws IOException {
        tableManager(authorColumn,
                titleColumn,
                yearColumn,
                pagesColumn,
                publicationsColumn,
                illustrationsColumn,
                coverColumn,
                circulationColumn,
                tableView,
                books
        );

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("biggestIllustratedBook-view.fxml"));

        scene = MainApplication.stage.getScene();

        Stage biggestBookStage = new Stage();
        stage = biggestBookStage;
        Scene scene = new Scene(fxmlLoader.load());

        biggestBookStage.initModality(Modality.WINDOW_MODAL);
        biggestBookStage.initOwner(MainApplication.stage);
        biggestBookStage.getIcons().add(new Image(
                "C:\\Users\\Роман3071\\IdeaProjects\\CourseworkFX\\src\\main\\resources\\Pictures\\img.png"));

        biggestBookStage.setTitle("The biggest books");
        biggestBookStage.setResizable(false);
        biggestBookStage.setScene(scene);
        biggestBookStage.show();
    }

    // Метод, який очищує вміст таблиці
    @FXML
    public void onClearButtonClick() {
        books.clear();
    }

    // Метод, який відбувається при натисканні кнопки знаходження найменшої книги та найпопулярнішої книги за рік.
    // Відкриває відповідне вікно.
    @FXML
    public void onSmallestBookButtonClick() throws IOException {
        tableManager(authorColumn,
                titleColumn,
                yearColumn,
                pagesColumn,
                publicationsColumn,
                illustrationsColumn,
                coverColumn,
                circulationColumn,
                tableView,
                books
        );

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("smallestBook-view.fxml"));

        scene = MainApplication.stage.getScene();

        Stage smallestBookStage = new Stage();
        stage = smallestBookStage;
        Scene scene = new Scene(fxmlLoader.load());

        smallestBookStage.initModality(Modality.WINDOW_MODAL);
        smallestBookStage.initOwner(MainApplication.stage);
        smallestBookStage.getIcons().add(new Image(
                "C:\\Users\\Роман3071\\IdeaProjects\\CourseworkFX\\src\\main\\resources\\Pictures\\img.png"));

        smallestBookStage.setTitle("The smallest and the most popular books");
        smallestBookStage.setResizable(false);
        smallestBookStage.setScene(scene);
        smallestBookStage.show();
    }
}