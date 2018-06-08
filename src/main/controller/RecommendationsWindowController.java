package controller;

import config.MainApplicationConfiguration;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RecommendationsWindowController extends BaseController implements Initializable
{
    private static final String RELATIVE_MAIN_WINDOW_CONTROLLER_PATH = "../ui/mainWindow.fxml";

    private List<String> recommendedArtists;

    @FXML
    private ListView<String> recommendations;

    @FXML
    private Button close;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    public void setRecommendedArtists(List<String> recommendedArtists)
    {
        this.recommendedArtists = recommendedArtists;
    }

    public void loadRecommendations()
    {
        if (CollectionUtils.isEmpty(this.recommendedArtists))
        {
            return;
        }

        recommendations.getItems().addAll(recommendedArtists);
    }

    @FXML
    public void onCloseButtonClicked() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(RELATIVE_MAIN_WINDOW_CONTROLLER_PATH));
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("My music collection");
        stage.setScene(new Scene(root));
        stage.show();

        Stage recommendationsStage = (Stage) recommendations.getScene().getWindow();
        recommendationsStage.close();
    }
}
