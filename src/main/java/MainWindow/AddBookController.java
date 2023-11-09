package MainWindow;

import common.Book;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddBookController {
    public Stage stage;
    public Scene scene;
    @FXML
    private TextField authorField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField yearField;
    @FXML
    private TextField pagesNumField;
    @FXML
    private TextField publicationsNumField;
    @FXML
    private CheckBox hasImagesCheckBox;
    @FXML
    private CheckBox hasSolidCoverCheckBox;
    @FXML
    private TextField circulationField;
    @FXML
    private Button addButton;

    @FXML
    public void onAddButtonClick() throws IOException {
        if (authorField.getText().isEmpty() ||
                titleField.getText().isEmpty() ||
                yearField.getText().isEmpty() ||
                pagesNumField.getText().isEmpty() ||
                publicationsNumField.getText().isEmpty() ||
                circulationField.getText().isEmpty()) {
            return;
        }

        String author = authorField.getText();
        String title = titleField.getText();
        int year = Integer.parseInt(yearField.getText());
        int pagesNum = Integer.parseInt(pagesNumField.getText());
        int publications = Integer.parseInt(publicationsNumField.getText());
        boolean hasImages = hasImagesCheckBox.isSelected();
        boolean hasSolidCover = hasSolidCoverCheckBox.isSelected();
        int circulation = Integer.parseInt(circulationField.getText());

        Book book = new Book(author, title, year, pagesNum, publications, hasImages, hasSolidCover, circulation);
        MainController.getBooks().add(book);

        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        MainApplication.stage.getScene();
        //Scene scene = new Scene(fxmlLoader.load());

        MainApplication.stage.setTitle("Main window");
        MainApplication.stage.setScene(MainController.scene);
        MainApplication.stage.show();
    }
}