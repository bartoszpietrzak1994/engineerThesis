import config.MainApplicationConfiguration;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApplication extends Application
{
    private static final String RELATIVE_CONTROLLER_PATH = "ui/mainWindow.fxml";

    private static final ApplicationContext applicationContext = new AnnotationConfigApplicationContext
            (MainApplicationConfiguration.class);

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(RELATIVE_CONTROLLER_PATH));
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("My Music Collection");
        Scene scene = new Scene(root, 1024, 768);
        scene.getStylesheets().add("css/styles.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
