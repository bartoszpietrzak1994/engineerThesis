package controller;

import config.MainApplicationConfiguration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.album.Album;
import model.album.AlbumRating;
import model.album.AlbumSortingCriterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import request.album.FindAllUserAlbumRequest;
import request.album.GetAlbumCoverRequest;
import response.album.FindAllUserAlbumsResponse;
import response.album.GetAlbumCoverResponse;
import service.AlbumService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
final public class MainWindowController implements Initializable
{
    private static final String RELATIVE_LOGIN_CONTROLLER_PATH = "../ui/loginWindow.fxml";
    private static final String RELATIVE_ADD_ALBUM_CONTROLLER_PATH = "../ui/addAlbumWindow.fxml";

    @Autowired
    private AlbumService albumService;

    @FXML
    private ComboBox<String> sortBy;

    @FXML
    private ComboBox<String> albumRatings;

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

    @FXML
    private ListView<String> userAlbums;

    @FXML
    private Label message;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        List<String> stringAlbumSortingCriterias = Arrays.stream(AlbumSortingCriterias.values()).map(Enum::toString)
                .collect(Collectors.toList());
        ObservableList<String> ioOperations = FXCollections.observableList(stringAlbumSortingCriterias);
        sortBy.setItems(ioOperations);

        List<String> stringAlbumRatings = Arrays.stream(AlbumRating.values()).map(Enum::toString)
                .collect(Collectors.toList());
        ObservableList<String> albumRating = FXCollections.observableList(stringAlbumRatings);
        albumRatings.setItems(albumRating);

        userAlbums.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
        {
            String[] albumProperties = newValue.split(":");
            String albumId = albumProperties[albumProperties.length-1].trim();

            GetAlbumCoverRequest getAlbumCoverRequest = new GetAlbumCoverRequest();
            getAlbumCoverRequest.setAlbumId(albumId);

            GetAlbumCoverResponse albumCover = albumService.getAlbumCover(getAlbumCoverRequest);

            if (!albumCover.isSuccessful())
            {
                return;
            }

            albumCoverPreview.setImage(new Image(new ByteArrayInputStream(albumCover.getAlbumCover())));
        });
    }

    @FXML
    public void onAddAlbumButtonClicked() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(RELATIVE_ADD_ALBUM_CONTROLLER_PATH));
        fxmlLoader.setControllerFactory(MainApplicationConfiguration.applicationContext::getBean);
        Parent root = fxmlLoader.load();
        AddAlbumWindowController addAlbumWindowController = fxmlLoader.getController();
        addAlbumWindowController.setUserName(this.userName.getText());
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("My music collection");
        stage.setScene(new Scene(root));
        stage.show();
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
    public void onLogoutButtonClicked() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(RELATIVE_LOGIN_CONTROLLER_PATH));
        fxmlLoader.setControllerFactory(MainApplicationConfiguration.applicationContext::getBean);
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("My music collection");
        stage.setScene(new Scene(root));
        stage.show();

        Stage loginStage = (Stage) addAlbum.getScene().getWindow();
        loginStage.close();
    }

    public void setUserName(String userName)
    {
        this.userName.setText(userName);
    }

    public void loadUserAlbums()
    {
        FindAllUserAlbumRequest findAllUserAlbumRequest = new FindAllUserAlbumRequest();
        findAllUserAlbumRequest.setUserName(this.userName.getText());

        FindAllUserAlbumsResponse allAlbumsAddedByUser = this.albumService.findAllAlbumsAddedByUser
                (findAllUserAlbumRequest);

        if (!allAlbumsAddedByUser.isSuccessful())
        {
            this.message.setText(allAlbumsAddedByUser.getErrorMessage());
            return;
        }

        List<Album> albumList = allAlbumsAddedByUser.getAlbumList();
        List<String> albumsAsString = albumList.stream().map(Album::toString).collect(Collectors.toList());

        userAlbums.getItems().addAll(albumsAsString);
    }
}
