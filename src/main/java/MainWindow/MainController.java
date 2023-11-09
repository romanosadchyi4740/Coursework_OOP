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
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class MainController {
    private ArrayList<Book> books;
    // main window elements
    @FXML
    public Stage stage;
    @FXML
    public Scene scene;
    @FXML
    private TableView tableView;
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

    @FXML
    public void readFromFile() throws IOException, ClassNotFoundException {
        fileInput();

        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("publicationYear"));
        pagesColumn.setCellValueFactory(new PropertyValueFactory<>("pagesNum"));
        publicationsColumn.setCellValueFactory(new PropertyValueFactory<>("publicationNum"));
        illustrationsColumn.setCellValueFactory(new PropertyValueFactory<>("hasImages"));
        coverColumn.setCellValueFactory(new PropertyValueFactory<>("hasSolidCover"));
        circulationColumn.setCellValueFactory(new PropertyValueFactory<>("circulation"));

        tableView.setItems(getBooks(books));
    }

    @FXML
    public void saveToFile() throws IOException {
        fileOutput();
    }

    // task 1
    public ArrayList<Book> findByTitleAndYear(String title, int year) {
        ArrayList<Book> res = new ArrayList<>();
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

    // task 2
    public ArrayList<Book> onSortButtonClick() {
        ArrayList<Book> res = new ArrayList<>();
        ArrayList<ArrayList<Book>> groups = divideToGroups(books);
        for (int i = 0; i < groups.size(); i++) {
            groups.set(i, quickSort(groups.get(i)));
            res.addAll(groups.get(i));
        }

        return res;
    }

    public ArrayList<ArrayList<Book>> divideToGroups(ArrayList<Book> books) {
        ArrayList<ArrayList<Book>> res = new ArrayList<>();
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
                res.add(new ArrayList<>());
                res.get(res.size() - 1).add(book);
            }
        }

        return res;
    }

    protected ArrayList<Book> quickSort(ArrayList<Book> list) {
        ArrayList<Book> sorted;
        ArrayList<Book> smaller = new ArrayList<>();
        ArrayList<Book> bigger = new ArrayList<>();
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
    public ArrayList<Book> getMax() {
        ArrayList<Book> res = new ArrayList<>();
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
    }

    public void fileOutput() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(MainApplication.stage);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream(file)
        )) {
            for (Book book : books) {
                outputStream.writeObject(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fileInput() throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(MainApplication.stage);

        try (ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream(file)
        )) {
            books.clear();
            while (inputStream.available() > 0) {
                books.add((Book) inputStream.readObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onAddBookButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addBooks-view.fxml"));

        MainApplication.stage.getScene();
        Scene scene = new Scene(fxmlLoader.load());

        MainApplication.stage.setTitle("Hello!");
        MainApplication.stage.setScene(scene);
        MainApplication.stage.show();
    }

    public static ObservableList<Book> getBooks(ArrayList<Book> arr) {
        ObservableList<Book> numbers = FXCollections.observableArrayList();
        numbers.addAll(arr);

        return numbers;
    }
}