package MainWindow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

// Наслідування від абстрактного класу Application, який надає можливість створювати віконні додатки
public class MainApplication extends Application {
    // stage - можна розуміти, як нове вікно програми
    public static Stage stage;

    // Перевизначення абстрактного методу класу Application, з якого розпочинається робота віконного застосунку
    @Override
    public void start(Stage stage) throws IOException {
        // Саме цей рядок викидає IOException (виняток, пов'язаний із вводом-виводом), що й зазначено в сигнатурі методу
        // Цей рядок відповідає за створення вікна за макетом, описаним у файлі main-view.fxml
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        // Сцена - те, що буде відображено на вікні програми (Stage)
        Scene scene = new Scene(fxmlLoader.load());

        // Ініціалізація публічної статичної змінної класу. Її я використав для того, щоб мати доступ до головного вікна
        // з усіх інших класів
        MainApplication.stage = stage;
        stage.setResizable(false);
        // Іконка вікна програми
        stage.getIcons().add(new Image(
                "C:\\Users\\Роман3071\\IdeaProjects\\CourseworkFX\\src\\main\\resources\\Pictures\\img.png"));

        stage.setTitle("Main window");
        // Встановлення сцени для вікна
        stage.setScene(scene);
        // Вивід вікна на екран
        stage.show();
    }

    // Стартова точка програми. Виклик методу launch() розпочинає роботу методу start()
    public static void main(String[] args) {
        launch();
    }
}