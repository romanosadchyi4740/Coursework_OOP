package MainWindow;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddBookController {
    @FXML
    public Stage stage;
    @FXML
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
        try {
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
