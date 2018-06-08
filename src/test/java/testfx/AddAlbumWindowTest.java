package testfx;

import config.MainApplicationConfiguration;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.loadui.testfx.GuiTest;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.io.IOException;
import java.time.LocalDate;

public class AddAlbumWindowTest extends ApplicationTest
{
    private static final String RELATIVE_CONTROLLER_PATH = "../ui/addAlbumWindow.fxml";

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

    public void clickAddButton()
    {
        clickOn("#add");
    }

    public void fillArtistField()
    {
        ((TextField)GuiTest.find("#artist")).setText(RandomStringUtils.randomAlphabetic(5));
    }

    public void fillArtistField(String artist)
    {
        ((TextField)GuiTest.find("#artist")).setText(artist);
    }

    public void fillTitleField()
    {
        ((TextField)GuiTest.find("#title")).setText(RandomStringUtils.randomAlphabetic(5));
    }

    public void fillTitleField(String title)
    {
        ((TextField)GuiTest.find("#title")).setText(title);
    }

    public void fillReleaseDate()
    {
        ((DatePicker) GuiTest.find("#releaseDate")).setValue(LocalDate.now());
    }

    public void fillReleaseDate(String releaseDate)
    {
        ((DatePicker) GuiTest.find("#releaseDate")).setValue(LocalDate.parse(releaseDate));
    }

    public boolean isErrorMessageVisible()
    {
        return StringUtils.isNotEmpty(((Label) GuiTest.find("#message")).getText());
    }
}
