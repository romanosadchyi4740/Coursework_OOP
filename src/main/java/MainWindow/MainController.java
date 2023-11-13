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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;

public class MainController {
    private static ObservableList<Book> books = FXCollections.observableArrayList();
    // main window elements
    public static Stage stage;
    public static Scene scene;
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

    public static void tableManager(TableColumn<Book, String> authorColumn, TableColumn<Book, String> titleColumn, TableColumn<Book, Integer> yearColumn, TableColumn<Book, Integer> pagesColumn, TableColumn<Book, Integer> publicationsColumn, TableColumn<Book, Boolean> illustrationsColumn, TableColumn<Book, Boolean> coverColumn, TableColumn<Book, Integer> circulationColumn, TableView<Book> tableView, ObservableList<Book> books) {
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
        tableView.refresh();
    }

    @FXML
    public void saveToFile() {
        fileOutput();
    }

    // task 1
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

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("findBook-view.fxml"));

        scene = MainApplication.stage.getScene();

        Stage findBookStage = new Stage();
        stage = findBookStage;
        Scene scene = new Scene(fxmlLoader.load());

        findBookStage.initModality(Modality.WINDOW_MODAL);
        findBookStage.initOwner(MainApplication.stage);

        findBookStage.setTitle("Find books");
        findBookStage.setResizable(false);
        findBookStage.setScene(scene);
        findBookStage.show();
    }

    // task 2
    @FXML
    public void onSortButtonClick() {
        ObservableList<Book> res = FXCollections.observableArrayList();
        ObservableList<ObservableList<Book>> groups = divideToGroups(books);
        for (int i = 0; i < groups.size(); i++) {
            groups.set(i, quickSort(groups.get(i)));
            res.addAll(groups.get(i));
        }

        books = res;
    }

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

    protected ObservableList<Book> quickSort(ObservableList<Book> list) {
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


    // task 3
    /*public ObservableList<Book> getMax() {
        ObservableList<Book> res = FXCollections.observableArrayList();
        for (Book book : books) {
            if (res.isEmpty()) {
                if (book.isHasImages()) {
                    res.add(book);
                } else {
                    continue;
                }
            }

            if (book.isHasImages() && book.getPagesNum() == res.get(0).getPagesNum()) {
                res.add(book);
            } else if (book.isHasImages() && book.getPagesNum() > res.get(0).getPagesNum()) {
                res.clear();
                res.add(book);
            }
        }

        return res;
    }*/

    public void fileOutput() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Programming\\Coursework"));
        File file = fileChooser.showOpenDialog(MainApplication.stage);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream(file)
        )) {
            outputStream.writeInt(books.size());

            for (Book book : books) {
                book.writeExternal(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fileInput() throws IOException, ClassNotFoundException {
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
                newBook.readExternal(inputStream);
                books.add(newBook);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

        addBookStage.setTitle("Add a book");
        addBookStage.setResizable(false);
        addBookStage.setScene(scene);
        addBookStage.show();
    }

    public static ObservableList<Book> getBooks() {
        return books;
    }

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

        biggestBookStage.setTitle("Get the biggest books");
        biggestBookStage.setResizable(false);
        biggestBookStage.setScene(scene);
        biggestBookStage.show();
    }
}