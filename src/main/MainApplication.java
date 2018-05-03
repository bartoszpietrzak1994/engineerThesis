import config.MainApplicationConfiguration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application
{
    private static final String RELATIVE_LOGIN_CONTROLLER_PATH = "ui/loginWindow.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(RELATIVE_LOGIN_CONTROLLER_PATH));
        fxmlLoader.setControllerFactory(MainApplicationConfiguration.applicationContext::getBean);
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("My Music Collection");
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add("css/styles.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
