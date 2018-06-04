package testfx;

import config.MainApplicationConfiguration;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import sun.plugin.dom.exception.InvalidStateException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class MainWindowTest extends ApplicationTest
{
    private static final String RELATIVE_CONTROLLER_PATH = "../ui/mainWindow.fxml";

    private JFXPanel jfxPanel;

    @Override
    public void start(Stage stage) throws Exception
    {
        FxRobot fxRobot = new FxRobot();
        fxRobot.interact(() ->
        {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApplicationConfiguration.class.getResource(RELATIVE_CONTROLLER_PATH));
            fxmlLoader.setControllerFactory(MainApplicationConfiguration.applicationContext::getBean);
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
        List<Window> windows = listWindows();

        for (Window window : windows)
        {
            if (window.getScene().lookup("#rating") != null)
            {
                return;
            }
        }

        throw new InvalidStateException("Album details window did not appear");
    }

    public void addAlbumWindowShouldAppear()
    {
        List<Window> windows = listWindows();

        for (Window window : windows)
        {
            if (window.getScene().lookup("#add") != null)
            {
                return;
            }
        }

        throw new InvalidStateException("Add album window did not appear");
    }

    public void chooseRatingOption(String ratingOption)
    {
        ComboBox<String> ratings = null;

        for (Window window : listWindows())
        {
            if (window.getScene().lookup("#albumRatings") != null)
            {
                ratings = (ComboBox<String>) window.getScene().lookup("#albumRatings");
            }
        }

        if (ratings == null)
        {
            throw new IllegalStateException("Album ratings combo box not found");
        }

        clickOn("#albumRatings");

        for (String rating : ratings.getItems())
        {
            if (rating.equals(ratingOption))
            {
                type(KeyCode.DOWN);

                type(KeyCode.ENTER);
                return;
            }

            type(KeyCode.DOWN);
        }

        throw new IllegalStateException("Album rating not found");
    }

    public void albumShouldBeRatedWith(String ratingOption)
    {
        ListView<String> albums = getUserAlbums();

        List<String> ratedAlbums = albums.getItems().stream().filter(item -> item.contains(ratingOption)).collect
                (Collectors.toList());

        assertThat(ratedAlbums).hasSize(1);
    }

    public void selectFirstAlbum()
    {
        ListView<String> albums = getUserAlbums();
        albums.getSelectionModel().select(0);
    }

    public boolean isMainApplicationWindowCurrentWindow()
    {
        return lookup("#userAlbums").query() != null;
    }

    public boolean albumCollectionIsVisible()
    {
        return lookup("#userAlbums").query() != null;
    }

    private ListView<String> getUserAlbums()
    {
        ListView<String> albums = null;

        for (Window window : listWindows())
        {
            if (window.getScene().lookup("#userAlbums") != null)
            {
                albums = (ListView<String>) window.getScene().lookup("#userAlbums");
            }
        }

        if (albums == null)
        {
            throw new IllegalStateException("Album ratings combo box not found");
        }

        return albums;
    }
}
