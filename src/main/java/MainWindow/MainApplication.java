package MainWindow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        MainApplication.stage = stage;
        stage.setResizable(false);
        stage.getIcons().add(new Image(
                "C:\\Users\\Роман3071\\IdeaProjects\\CourseworkFX\\src\\main\\resources\\Pictures\\img.png"));

        stage.setTitle("Main window");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}