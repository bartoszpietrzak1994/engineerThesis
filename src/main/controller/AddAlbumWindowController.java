package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import request.album.AddAlbumRequest;
import response.album.AddAlbumResponse;
import service.AlbumService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
final public class AddAlbumWindowController implements Initializable
{
    @Autowired
    private AlbumService albumService;

    @Autowired
    private Environment environment;

    @FXML
    private TextField artist;

    @FXML
    private TextField title;

    @FXML
    private TextField albumCover;

    @FXML
    private DatePicker releaseDate;

    @FXML
    private ImageView albumCoverPreview;

    @FXML
    private Button browse;

    @FXML
    private Button add;

    @FXML
    private Label message;

    private String userName;

    private FileChooser fileChooser;

    private MainWindowController mainWindowController;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PNG, JPG files (*.png, *.jpg)" +
                "", "*.png", "*.jpg");
        this.fileChooser.getExtensionFilters().add(extensionFilter);
    }

    @FXML
    public void addAlbum()
    {
        AddAlbumRequest addAlbumRequest = new AddAlbumRequest();

        if (!StringUtils.isEmpty(albumCover.getText()))
        {
            addAlbumRequest.setAlbumCover(getAlbumCoverAsBytes());
        }

        addAlbumRequest.setArtist(this.artist.getText());
        addAlbumRequest.setTitle(this.title.getText());
        addAlbumRequest.setUserName(this.userName);
        addAlbumRequest.setReleaseDate(releaseDate.getValue());

        AddAlbumResponse addAlbumResponse = albumService.addAlbum(addAlbumRequest);

        if (!addAlbumResponse.isSuccessful())
        {
            message.setText(addAlbumResponse.getErrorMessage());
            return;
        }

        message.setText(environment.getProperty("album.added_successfully"));
        this.mainWindowController.loadUserAlbums();
    }

    public void onBrowseButtonClicked()
    {
        File file = this.fileChooser.showOpenDialog(this.browse.getScene().getWindow());

        if (file != null)
        {
            String albumCoverPath = file.getAbsolutePath();
            albumCover.setText(albumCoverPath);
            albumCoverPreview.setImage(new Image(file.toURI().toString()));
        }
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public void setMainWindowController(MainWindowController mainWindowController)
    {
        this.mainWindowController = mainWindowController;
    }

    private byte[] getAlbumCoverAsBytes()
    {
        BufferedImage bImage = SwingFXUtils.fromFXImage(albumCoverPreview.getImage(), null);
        ByteArrayOutputStream s = new ByteArrayOutputStream();
        try
        {
            ImageIO.write(bImage, "png", s);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        byte[] res  = s.toByteArray();
        try
        {
            s.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return res;
    }
}
