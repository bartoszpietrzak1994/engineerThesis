package testfx;

import config.MainApplicationConfiguration;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.loadui.testfx.GuiTest;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;

public class AlbumDetailsWindowTest extends ApplicationTest
{
    private static final String RELATIVE_CONTROLLER_PATH = "../ui/albumDetailsWindow.fxml";

    private JFXPanel jfxPanel;

    @Override
    public void start(Stage stage) throws Exception
    {
        FxRobot fxRobot = new FxRobot();
        fxRobot.interact(() ->
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApplicationConfiguration.class.getResource(RELATIVE_CONTROLLER_PATH));
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

    public boolean isArtistFieldFilledWith(String artist)
    {
        return ((TextField)GuiTest.find("#artist")).getText().equals(artist);
    }

    public boolean isTitleFieldFilledWith(String title)
    {
        return ((TextField)GuiTest.find("#title")).getText().equals(title);
    }

    public boolean isReleaseDateFieldFilledWith(String releaseDate)
    {
        return ((DatePicker)GuiTest.find("#releaseDate")).getValue().toString().equals(releaseDate);
    }
}
