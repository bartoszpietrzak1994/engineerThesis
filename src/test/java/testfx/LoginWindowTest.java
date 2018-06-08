package testfx;

import config.MainApplicationConfiguration;
import config.PersistenceConfiguration;
import config.TestApplicationConfiguration;
import controller.LoginWindowController;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.loadui.testfx.GuiTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;

public class LoginWindowTest extends ApplicationTest
{
    private static final String RELATIVE_CONTROLLER_PATH = "../ui/loginWindow.fxml";

    private JFXPanel jfxPanel;

    @Override
    public void start(Stage stage) throws Exception
    {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext(TestApplicationConfiguration.class, PersistenceConfiguration.class);

        FxRobot fxRobot = new FxRobot();
        fxRobot.interact(() ->
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApplicationConfiguration.class.getResource(RELATIVE_CONTROLLER_PATH));
            fxmlLoader.setControllerFactory(annotationConfigApplicationContext::getBean);
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
            LoginWindowController loginWindowController = fxmlLoader.getController();
            loginWindowController.setApplicationContext(annotationConfigApplicationContext);

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

    public void fillLoginFieldWithLogin(String login)
    {
        ((TextField)GuiTest.find("#login")).setText(login);
    }

    public void fillPasswordFieldWithPassword(String password)
    {
        ((TextField)GuiTest.find("#password")).setText(password);
    }

    public void clickLoginButton()
    {
        clickOn("#loginButton");
    }

    public void clickRegisterButton()
    {
        clickOn("#registerButton");
    }

    public boolean messageShouldBeVisible(String expectedMessage)
    {
        Label message = lookup("#message").query();

        if (message == null)
        {
            return false;
        }

        return message.getText().equals(expectedMessage);
    }
}
