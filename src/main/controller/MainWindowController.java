package controller;

import com.google.common.collect.Iterables;
import dto.album.AlbumDto;
import event.AlbumAddedEvent;
import eventListener.AlbumListSelectionEventListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.album.AlbumOrderingCriteria;
import model.album.AlbumRating;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import request.album.FindAllUserAlbumRequest;
import request.album.GetAlbumByIdRequest;
import request.album.GetAlbumsOrderedByCriteriaRequest;
import request.album.RateAlbumRequest;
import request.recommendation.GetRecommendationsRequest;
import response.GenericResponse;
import response.album.FindAllUserAlbumsResponse;
import response.album.GetAlbumsOrderedByCriteriaResponse;
import response.recommendation.GetRecommendationsResponse;
import service.AlbumService;
import service.RecommendationsService;
import util.album.AlbumPropertiesUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
final public class MainWindowController extends BaseController implements ApplicationListener<AlbumAddedEvent>
{
    private static final String RELATIVE_LOGIN_CONTROLLER_PATH = "../ui/loginWindow.fxml";
    private static final String RELATIVE_ADD_ALBUM_CONTROLLER_PATH = "../ui/addAlbumWindow.fxml";
    private static final String RELATIVE_ALBUM_DETAILS_CONTROLLER_PATH = "../ui/albumDetailsWindow.fxml";
    private static final String RELATIVE_RECOMMENDATIONS_CONTROLLER_PATH = "../ui/recommendationsWindow.fxml";

    private String username;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private RecommendationsService recommendationsService;

    @Autowired
    private Environment environment;

    @FXML
    private ComboBox<String> orderBy;

    @FXML
    private ComboBox<String> albumRatings;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button addAlbum;

    @FXML
    private Button rate;

    @FXML
    private Button albumDetails;

    @FXML
    private Button getRecommendations;

    @FXML
    private Button order;

    @FXML
    private Button logout;

    @FXML
    private ImageView albumCoverPreview;

    @FXML
    private ListView<String> userAlbums;

    @FXML
    private Label message;

    @FXML
    public void initialize()
    {
        List<String> stringAlbumSortingCriterias = Arrays.stream(AlbumOrderingCriteria.values()).map(Enum::toString)
                .collect(Collectors.toList());
        ObservableList<String> ioOperations = FXCollections.observableList(stringAlbumSortingCriterias);
        orderBy.setItems(ioOperations);

        List<String> stringAlbumRatings = Arrays.stream(AlbumRating.values()).map(Enum::toString)
                .collect(Collectors.toList());
        ObservableList<String> albumRating = FXCollections.observableList(stringAlbumRatings);
        albumRatings.setItems(albumRating);

        userAlbums.getSelectionModel().selectedItemProperty().addListener(new AlbumListSelectionEventListener(
                this.albumService, this.albumCoverPreview));
    }

    @FXML
    public void onAddAlbumButtonClicked() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(RELATIVE_ADD_ALBUM_CONTROLLER_PATH));
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        Parent root = fxmlLoader.load();
        AddAlbumWindowController addAlbumWindowController = fxmlLoader.getController();
        addAlbumWindowController.setUserName(this.username);
        addAlbumWindowController.setApplicationContext(this.applicationContext);
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
        String albumRating = this.albumRatings.getValue();

        if (StringUtils.isEmpty(albumRating))
        {
            message.setText("In order to rate an album you have to choose the rating");
            return;
        }

        String selectedAlbum = Iterables.getFirst(userAlbums.getSelectionModel().getSelectedItems(), null);

        if (StringUtils.isEmpty(selectedAlbum))
        {
            message.setText("In order to rate an album you have to choose an album");
            return;
        }

        String albumId = AlbumPropertiesUtils.getAlbumIdFromAlbumProperties(selectedAlbum);

        RateAlbumRequest rateAlbumRequest = new RateAlbumRequest();
        rateAlbumRequest.setAlbumId(albumId);
        rateAlbumRequest.setAlbumRating(albumRating);

        GenericResponse rateAlbumResponse = albumService.rateAlbum(rateAlbumRequest);

        if (!rateAlbumResponse.isSuccessful())
        {
            message.setText(rateAlbumResponse.getErrorMessage());
            return;
        }

        loadUserAlbums();
    }

    @FXML
    public void onDetailsButtonClicked() throws IOException
    {
        String selectedAlbum = Iterables.getFirst(userAlbums.getSelectionModel().getSelectedItems(), null);

        if (StringUtils.isEmpty(selectedAlbum))
        {
            message.setText("In order to see albumDetails of an album you have to choose an album");
            return;
        }

        String albumId = AlbumPropertiesUtils.getAlbumIdFromAlbumProperties(selectedAlbum);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(RELATIVE_ALBUM_DETAILS_CONTROLLER_PATH));
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        Parent root = fxmlLoader.load();
        AlbumDetailsController albumDetailsController = fxmlLoader.getController();
        albumDetailsController.setAlbumId(albumId);
        albumDetailsController.fetchAlbumData();
        albumDetailsController.setApplicationContext(this.applicationContext);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("My music collection");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void onGetRecommendationsButtonClicked() throws IOException
    {
        this.message.setText("");

        GetRecommendationsRequest getRecommendationsRequest = new GetRecommendationsRequest();

        List<AlbumDto> albums = new ArrayList<>();

        for (String albumAsString : userAlbums.getItems())
        {
            String albumId = AlbumPropertiesUtils.getAlbumIdFromAlbumProperties(albumAsString);
            albums.add(albumService.getAlbumById(new GetAlbumByIdRequest(albumId), new BindException()).getAlbum());
        }

        getRecommendationsRequest.setUserAlbums(albums);

        GetRecommendationsResponse recommendations = this.recommendationsService.getRecommendations
                (getRecommendationsRequest);

        if (!recommendations.isSuccessful())
        {
            this.message.setText(recommendations.getErrorMessage());
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(RELATIVE_RECOMMENDATIONS_CONTROLLER_PATH));
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
        Parent root = fxmlLoader.load();
        RecommendationsWindowController controller = fxmlLoader.getController();
        controller.setRecommendedArtists(recommendations.getRecommendedArtists());
        controller.loadRecommendations();
        controller.setApplicationContext(this.applicationContext);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initStyle(StageStyle.DECORATED);
        stage.setTitle("My music collection");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void onOrderButtonClicked()
    {
        this.message.setText("");

        GetAlbumsOrderedByCriteriaRequest request = new GetAlbumsOrderedByCriteriaRequest();
        request.setSortingCriteria(this.orderBy.getValue());

        GetAlbumsOrderedByCriteriaResponse albumsGrouppedByCriteria = albumService.getAlbumsOrderedByCriteria(request);

        if (!albumsGrouppedByCriteria.isSuccessful())
        {
            this.message.setText(albumsGrouppedByCriteria.getErrorMessage());
            return;
        }

        List<String> albumsAsString = albumsGrouppedByCriteria.getAlbums().stream().map(AlbumDto::toString).collect
                (Collectors.toList());

        userAlbums.getItems().clear();
        userAlbums.getItems().addAll(albumsAsString);
    }

    @FXML
    public void onLogoutButtonClicked() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(RELATIVE_LOGIN_CONTROLLER_PATH));
        fxmlLoader.setControllerFactory(this.applicationContext::getBean);
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
        this.username = userName;
    }

    public void loadUserAlbums()
    {
        this.usernameTextField.setText(this.username);

        FindAllUserAlbumRequest findAllUserAlbumRequest = new FindAllUserAlbumRequest();
        findAllUserAlbumRequest.setUserName(this.usernameTextField.getText());

        FindAllUserAlbumsResponse allAlbumsAddedByUser = this.albumService.findAllAlbumsAddedByUser
                (findAllUserAlbumRequest);

        if (!allAlbumsAddedByUser.isSuccessful())
        {
            this.message.setText(allAlbumsAddedByUser.getErrorMessage());
            return;
        }

        List<AlbumDto> albumList = allAlbumsAddedByUser.getAlbumList();
        List<String> albumsAsString = albumList.stream().map(AlbumDto::toString).collect(Collectors.toList());

        ObservableList<String> userAlbumItems = userAlbums.getItems();

        if (!CollectionUtils.isEmpty(userAlbumItems))
        {
            userAlbumItems.clear();
        }

        userAlbumItems.addAll(albumsAsString);
    }

    @Override
    public void onApplicationEvent(AlbumAddedEvent albumAddedEvent)
    {
        loadUserAlbums();
    }
}
