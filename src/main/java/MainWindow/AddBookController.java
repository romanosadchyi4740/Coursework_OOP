package MainWindow;

import common.Book;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    public void onAddButtonClick() {
        Alert alert = null;
        if (authorField.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error: empty field");
            alert.setContentText("\"Author\" field can't be empty!");
        } else if (titleField.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error: empty field");
            alert.setContentText("\"Title\" field can't be empty!");
        } else if (yearField.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error: empty field");
            alert.setContentText("\"Year\" field can't be empty!");
        } else if (pagesNumField.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error: empty field");
            alert.setContentText("\"Number of pages\" field can't be empty!");
        } else if (publicationsNumField.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error: empty field");
            alert.setContentText("\"Number of publications\" field can't be empty!");
        } else if (circulationField.getText().isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error: empty field");
            alert.setContentText("\"Circulation\" field can't be empty!");
        }

        String author = authorField.getText();
        String title = titleField.getText();
        int year = 0, pagesNum = 0, publications = 0, circulation = 0;

        try {
            year = Integer.parseInt(yearField.getText());

            if (year < 1800 || year > 2023) {
                throw new Exception();
            }
        } catch (NumberFormatException e) {
            if (alert == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error: incompatible type");
                alert.setContentText("\"Year\" field can only contain numbers!");
            }
        } catch (Exception e) {
            if (alert == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error: impossible value");
                alert.setContentText("\"Year\" field can only contain numbers from range [1800; 2023]!");
            }
        }

        try {
            pagesNum = Integer.parseInt(pagesNumField.getText());

            if (pagesNum <= 0) {
                throw new Exception();
            }
        } catch (NumberFormatException e) {
            if (alert == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error: incompatible type");
                alert.setContentText("\"Number of pages\" field can only contain numbers!");
            }
        } catch (Exception e) {
            if (alert == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error: impossible value");
                alert.setContentText("\"Number of pages\" field can only contain positive numbers!");
            }
        }

        try {
            publications = Integer.parseInt(publicationsNumField.getText());

            if (publications <= 0) {
                throw new Exception();
            }
        } catch (NumberFormatException e) {
            if (alert == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error: incompatible type");
                alert.setContentText("\"Number of publications\" field can only contain numbers!");
            }
        } catch (Exception e) {
            if (alert == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error: impossible value");
                alert.setContentText("\"Number of publications\" field can only contain positive numbers!");
            }
        }

        boolean hasImages = hasImagesCheckBox.isSelected();
        boolean hasSolidCover = hasSolidCoverCheckBox.isSelected();

        try {
            circulation = Integer.parseInt(circulationField.getText());
            if (circulation <= 0) {
                throw new Exception();
            }
        } catch (NumberFormatException e) {
            if (alert == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error: incompatible type");
                alert.setContentText("\"Circulation\" field can only contain numbers!");
            }
        } catch (Exception e) {
            if (alert == null) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error: impossible value");
                alert.setContentText("\"Circulation\" field can only contain POSITIVE numbers!");
            }
        }

        if (alert != null) {
            alert.show();
            return;
        }

        Book book = new Book(author, title, year, pagesNum, publications, hasImages, hasSolidCover, circulation);
        MainController.getBooks().add(book);
        MainController.stage.close();

        MainApplication.stage.getScene();

        MainApplication.stage.setTitle("Main window");
        MainApplication.stage.setScene(MainController.scene);
        MainApplication.stage.show();
    }
}