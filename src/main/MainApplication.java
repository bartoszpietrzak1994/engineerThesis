import config.MainApplicationConfiguration;
import config.PersistenceConfiguration;
import controller.LoginWindowController;
import controller.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApplication extends Application
{
    private static final String RELATIVE_LOGIN_CONTROLLER_PATH = "ui/loginWindow.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(MainApplicationConfiguration.class, PersistenceConfiguration.class);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(RELATIVE_LOGIN_CONTROLLER_PATH));
        fxmlLoader.setControllerFactory(annotationConfigApplicationContext::getBean);
        Parent root = fxmlLoader.load();
        LoginWindowController loginWindowController = fxmlLoader.getController();
        loginWindowController.setApplicationContext(annotationConfigApplicationContext);
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
