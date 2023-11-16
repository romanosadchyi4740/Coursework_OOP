package MainWindow;

import common.Book;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddBookController {
    public Stage stage;
    public Scene scene;

    // Усі візуальні об'єкти вікна додавання книг
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

    // Функція, яка викликається при натисканні на кнопку
    @FXML
    public void onAddButtonClick() {
        // Спочатку здійснюється перевірка на те, чи є незаповнені поля. Якщо такі поля є то на екрані з'являється
        // вікно помилки, яке підказує користувачу, які поля він пропустив.
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

        // Значення стрічкових полів дістаємо просто використовуючи необхідний метод до відповідних полів введення
        String author = authorField.getText();
        String title = titleField.getText();
        int year = 0, pagesNum = 0, publications = 0, circulation = 0;

        // Значення числових полів спочатку необхідно перевірити на те, чи справді вони є числами.
        // (Якщо Integer.parseInt() викине виняток, то записуємо інформацію, яка має бути виведена у вікні помилки).
        // Також здійснюється перевірка на логічну правильність даних.
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

        // Отримання значень логічних полів за допомогою чек-боксів
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

        // Якщо об'єкт вікна помилки був створений (!= null), то виводиться вікно повідомлення про помилку, яке підкаже
        // користувачу, як виправити свою помилку. Виконання функції завершується.
        if (alert != null) {
            alert.show();
            return;
        }

        // Якщо ж помилок не було, то створюється нова книжка, й додається до загального списку.
        Book book = new Book(author, title, year, pagesNum, publications, hasImages, hasSolidCover, circulation);
        MainController.getBooks().add(book);
        // Після успішного додавання книги вікно закривається, й стає активним головне вікно
        MainController.stage.close();
    }
}