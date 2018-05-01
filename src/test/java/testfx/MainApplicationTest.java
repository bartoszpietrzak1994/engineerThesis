package testfx;

import config.MainApplicationConfiguration;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;

public class MainApplicationTest extends ApplicationTest
{
    private static final String RELATIVE_CONTROLLER_PATH = "../ui/mainWindow.fxml";

    private static final ApplicationContext applicationContext = new AnnotationConfigApplicationContext
            (MainApplicationConfiguration.class);

    private JFXPanel jfxPanel;

    @Override
    public void start(Stage stage) throws Exception
    {
        FxRobot fxRobot = new FxRobot();
        fxRobot.interact(() ->
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApplicationConfiguration.class.getResource(RELATIVE_CONTROLLER_PATH));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent root;
            try
            {
                root = fxmlLoader.load();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return;
            }
            stage.setScene(new Scene(root, 1024, 768));
            stage.show();
            stage.toFront();
        });

        this.jfxPanel = new JFXPanel();
    }

    public void setUp() throws Exception
    {
        start(FxToolkit.registerPrimaryStage());
    }

    public void tearDown() throws Exception
    {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    public void clickAddAlbumButton()
    {
        clickOn("#addAlbum");
    }

    public void clickRateButton()
    {
        clickOn("#rate");
    }

    public void clickGetRecommendationsButton()
    {
        clickOn("#getRecommendations");
    }

    public void clickDetailsButton()
    {
        clickOn("#albumDetails");
    }

    public void rateAlbumWindowShouldAppear()
    {
        return;
    }

    public void albumDetailsWindowShouldAppear()
    {
        return;
    }

    public void addAlbumWindowShouldAppear()
    {
        return;
    }
}
