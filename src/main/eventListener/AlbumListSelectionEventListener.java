package eventListener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import request.album.GetAlbumCoverRequest;
import response.album.GetAlbumCoverResponse;
import service.AlbumService;
import util.album.AlbumPropertiesUtils;

import java.io.ByteArrayInputStream;

public class AlbumListSelectionEventListener implements ChangeListener<String>
{
    private AlbumService albumService;

    private ImageView albumCoverPreview;

    public AlbumListSelectionEventListener(AlbumService albumService, ImageView albumCoverPreview)
    {
        this.albumService = albumService;
        this.albumCoverPreview = albumCoverPreview;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue)
    {
        GetAlbumCoverRequest getAlbumCoverRequest = new GetAlbumCoverRequest();
        getAlbumCoverRequest.setAlbumId(AlbumPropertiesUtils.getAlbumIdFromAlbumProperties(newValue));
        GetAlbumCoverResponse response = albumService.getAlbumCover(getAlbumCoverRequest);

        albumCoverPreview.setImage(null);
        if (!response.isSuccessful())
        {
            return;
        }

        albumCoverPreview.setImage(new Image(new ByteArrayInputStream(response.getAlbumCover()), 200, 150,
                false, false));
    }
}
