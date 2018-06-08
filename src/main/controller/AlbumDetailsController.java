package controller;

import dto.album.AlbumDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import request.album.GetAlbumByIdRequest;
import response.album.GetAlbumByIdResponse;
import service.AlbumService;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

@Component
final public class AlbumDetailsController extends BaseController implements Initializable
{
    @Autowired
    private AlbumService albumService;

    @FXML
    private TextField artist;

    @FXML
    private TextField title;

    @FXML
    private TextField releaseDate;

    @FXML
    private TextField rating;

    @FXML
    private ImageView albumCoverPreview;

    @FXML
    private Button close;

    private String albumId;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }

    @FXML
    public void onCloseButtonClicked()
    {
        Stage detailsStage = (Stage) close.getScene().getWindow();
        detailsStage.close();
    }

    public void setAlbumId(String albumId)
    {
        this.albumId = albumId;
    }

    public void fetchAlbumData()
    {
        GetAlbumByIdRequest getAlbumByIdRequest = new GetAlbumByIdRequest();
        getAlbumByIdRequest.setAlbumId(this.albumId);

        GetAlbumByIdResponse response = albumService.getAlbumById(getAlbumByIdRequest);

        if (!response.isSuccessful())
        {
            // todo
            return;
        }

        AlbumDto album = response.getAlbum();

        this.artist.setText(album.getArtist());
        this.title.setText(album.getTitle());
        this.rating.setText(album.getAlbumRating());
        this.releaseDate.setText(album.getReleaseDate());

        byte[] albumCover = album.getAlbumCover();
        if (albumCover != null)
        {
            this.albumCoverPreview.setImage(new Image(new ByteArrayInputStream(albumCover), 160, 160,
                    false, false));
        }
    }
}
