package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import org.springframework.util.CollectionUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RecommendationsWindowController implements Initializable
{
    private List<String> recommendedArtists;

    @FXML
    private ListView<String> recommendations;

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
}
