package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import model.album.AlbumSortingCriterias;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
final public class MainWindowController implements Initializable
{
    @FXML
    private ComboBox<String> sortBy;

    @FXML
    private TextField userName;

    @FXML
    private Button addAlbum;

    @FXML
    private Button rate;

    @FXML
    private Button details;

    @FXML
    private Button getRecommendations;

    @FXML
    private Button sort;

    @FXML
    private Button logout;

    @FXML
    private ImageView albumCoverPreview;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        List<String> stringAlbumSortingCriterias = Arrays.stream(AlbumSortingCriterias.values()).map(Enum::toString)
                .collect(Collectors.toList());
        ObservableList<String> ioOperations = FXCollections.observableList(stringAlbumSortingCriterias);
        sortBy.setItems(ioOperations);
    }

    @FXML
    public void onAddAlbumButtonClicked()
    {

    }

    @FXML
    public void onRateButtonClicked()
    {

    }

    @FXML
    public void onDetailsButtonClicked()
    {

    }

    @FXML
    public void onGetRecommendationsButtonClicked()
    {

    }

    @FXML
    public void onSortButtonClicked()
    {

    }

    @FXML
    public void onLogoutButtonClicked()
    {

    }
}
